package nl.esciencecenter.openda.bmi.python;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.ServerSocket;
import java.util.HashSet;

import nl.esciencecenter.openda.bmi.thrift.BmiRaster;
import nl.esciencecenter.openda.bmi.thrift.ModelException;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.openda.interfaces.IExchangeItem;
import org.openda.interfaces.IInstance;
import org.openda.interfaces.IModelInstance;
import org.openda.interfaces.IModelState;
import org.openda.interfaces.IObservationDescriptions;
import org.openda.interfaces.IPrevExchangeItem;
import org.openda.interfaces.IPrevExchangeItem.Role;
import org.openda.interfaces.ITime;
import org.openda.interfaces.IVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interface to a Python BMI Model. Passes calls to the BMI interface.
 * 
 * @author Niels Drost
 * 
 */
public class BMIPythonModelInstance implements IModelInstance {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BMIPythonModelInstance.class);

    private static final int MAX_CONNECT_ATTEMPTS = 20;
    private static final long CONNECT_TIMEOUT = 100; //ms
    
    //Obtain a free port by opening a server socket without explicitly giving the port,
    //asking its port, and closing the server socket again.
    private static int getFreePort() throws IOException {
        ServerSocket serverSocket = new ServerSocket(0);
        int result = serverSocket.getLocalPort();

        serverSocket.close();

        return result;
    }

    private static Process startModelProcess(int port) throws IOException {
        ProcessBuilder builder = new ProcessBuilder();

        builder.directory(new File("/home/niels/workspace/OpenDABmiPython/src"));

        builder.environment().put("PYTHONPATH", "python:gen-py");

        builder.command().add("/usr/bin/python");
        builder.command().add("python/testBMIServer.py");
        builder.command().add(Integer.toString(port));

        builder.redirectError(Redirect.INHERIT);
        builder.redirectOutput(Redirect.INHERIT);

        return builder.start();
    }

    //will attempt to connect to the code
    private static TTransport connectToCode(int port, Process process) throws IOException {
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
                TTransport transport = new TSocket("localhost", port);
                transport.open();
                LOGGER.debug("obtained connection on " + i + "th attempt");
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

    private final BmiRaster.Client client;

    public BMIPythonModelInstance() throws IOException, ModelException, TException {

        int port = getFreePort();

        Process process = startModelProcess(port);

        TTransport transport = connectToCode(port, process);
        
        TProtocol protocol = new TBinaryProtocol(transport);
        client = new BmiRaster.Client(protocol);

        client.initialize("");
    }

    @Override
    public String[] getExchangeItemIDs() {
        try {
            HashSet<String> result = new HashSet<String>();

            result.addAll(client.get_output_var_names());
            result.addAll(client.get_input_var_names());

            return result.toArray(new String[0]);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] getExchangeItemIDs(Role role) {
        try {
            if (role.equals(Role.Input)) {
                return client.get_input_var_names().toArray(new String[0]);
            } else if (role.equals(Role.Output)) {
                return client.get_output_var_names().toArray(new String[0]);
            } else if (role.equals(Role.InOut)) {
                HashSet<String> result = new HashSet<String>();

                result.addAll(client.get_output_var_names());
                result.retainAll(client.get_input_var_names());

                return result.toArray(new String[0]);
            } else {
                return new String[0];
            }

        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IExchangeItem getDataObjectExchangeItem(String exchangeItemID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialize(File workingDir, String[] arguments) {
        // TODO Auto-generated method stub

    }

    @Override
    public IInstance getParent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPrevExchangeItem getExchangeItem(String exchangeItemID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITime getTimeHorizon() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITime getCurrentTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void compute(ITime targetTime) {
        // TODO Auto-generated method stub

    }

    @Override
    public IVector[] getObservedLocalization(IObservationDescriptions observationDescriptions, double distance) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IModelState saveInternalState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void restoreInternalState(IModelState savedInternalState) {
        // TODO Auto-generated method stub

    }

    @Override
    public void releaseInternalState(IModelState savedInternalState) {
        // TODO Auto-generated method stub

    }

    @Override
    public IModelState loadPersistentState(File persistentStateFile) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File getModelRunDir() {
        // TODO Auto-generated method stub
        return null;
    }

}
