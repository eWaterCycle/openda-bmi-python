/*
 * Copyright 2014 Netherlands eScience Center
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.esciencecenter.bmi.openda;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.ServerSocket;
import java.util.Arrays;

import javax.swing.event.ListSelectionEvent;

import nl.esciencecenter.bmi.BMIRaster;
import nl.esciencecenter.bmi.thrift.BmiRasterService;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BMIRaster Model implemented as a (local) python process
 * 
 * @author Niels Drost
 *
 */
public class LocalPythonThriftBMIRaster extends ThriftBMIRaster {

    private static final Logger LOGGER = LoggerFactory.getLogger(BMIRasterModelInstance.class);

    private static final int MAX_CONNECT_ATTEMPTS = 20;
    private static final long CONNECT_TIMEOUT = 500; //ms

    //Obtain a free port by opening a server socket without explicitly giving the port,
    //asking its port, and closing the server socket again.
    private static int getFreePort() throws IOException {
        ServerSocket serverSocket = new ServerSocket(0);
        int result = serverSocket.getLocalPort();

        serverSocket.close();

        return result;
    }

    private static Process startModelProcess(String host, int port, String pythonExecutable, File bridgeDir, File modelDir, String modelModule, String modelClass, File runDir)
            throws IOException {
        ProcessBuilder builder = new ProcessBuilder();

        LOGGER.info("running process with cwd " + runDir);
        builder.directory(runDir);

        File generatedPythonCodeDir = new File(bridgeDir, "generated/main/python");
        File pythonCodeDir = new File(bridgeDir, "src/main/python");
        File pythonMainScript = new File(bridgeDir, "src/main/python/thrift_bmi_raster_server.py");

        String currentPythonPath = builder.environment().get("PYTHONPATH");

        String newPythonPath = modelDir + ":" + generatedPythonCodeDir + ":" + pythonCodeDir;
        
        builder.environment().put("PYTHONPATH", newPythonPath + ":" + currentPythonPath);

        LOGGER.info("Running with PYTHONPATH: " + newPythonPath+ ":" + currentPythonPath);
        
        //this assumes we can login with ssh without a password, or specifying any options
        if (host != null && host != "localhost") {
            builder.command().add("ssh");
            builder.command().add(host);
            builder.command().add("cd");
            builder.command().add(runDir + ";");
            builder.command().add("PYTHONPATH=" + newPythonPath + ":$PYTHONPATH");
        }
        
        builder.command().add(pythonExecutable);
        builder.command().add(pythonMainScript.getAbsolutePath());
        builder.command().add(modelModule);
        builder.command().add(modelClass);
        builder.command().add(Integer.toString(port));

        builder.redirectError(Redirect.INHERIT);
        builder.redirectOutput(Redirect.INHERIT);
        
        LOGGER.info("Running command: " + Arrays.toString(builder.command().toArray()));

        return builder.start();
    }

    //will attempt to connect to the code
    private static TTransport connectToCode(String host, int port, Process process) throws IOException {
        for (int i = 0; i < MAX_CONNECT_ATTEMPTS; i++) {
            //first check if the process is still alive
            try {
                int exitValue = process.exitValue();
                throw new IOException("Process has ended while waiting for Thrift connection with exit code " + exitValue);
            } catch (IllegalThreadStateException e) {
                //We are hoping to end up here, because it means the process is still running.
                //Note: Java 8 allows a smarter way of implementing this.
            }

            //then try connecting to the code
            try {
                TTransport transport = new TSocket(host, port);
                transport.open();
                LOGGER.debug("obtained connection on the " + i + "th attempt");
                return transport;
            } catch (TTransportException e) {
                LOGGER.debug("could not connect to code (yet)", e);
            }

            //finally, wait a certain time before trying again
            try {
                Thread.sleep(CONNECT_TIMEOUT);
            } catch (InterruptedException e) {
                //IGNORE
            }
        }
        throw new IOException("Failed to connect to model");
    }

    public static BMIRaster createModel(String host, String pythonExecutable, File bridgeDir, File modelDir, String modelModule, String modelClass, File runDir)
            throws IOException {
        int port = getFreePort();

        Process process = startModelProcess(host, port, pythonExecutable, bridgeDir, modelDir, modelModule, modelClass, runDir);

        TTransport transport = connectToCode(host, port, process);

        TProtocol protocol = new TBinaryProtocol(transport);
        BmiRasterService.Client client = new BmiRasterService.Client(protocol);

        return new LocalPythonThriftBMIRaster(client, process, transport);
    }

    private final Process process;

    private final TTransport transport;

    private LocalPythonThriftBMIRaster(BmiRasterService.Client client, Process process, TTransport transport) {
        super(client);
        this.process = process;
        this.transport = transport;
    }

    @Override
    public void stop_model() {
        transport.close();

        process.destroy();

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
