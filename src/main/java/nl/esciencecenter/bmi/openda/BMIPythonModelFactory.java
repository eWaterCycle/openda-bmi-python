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

import nl.esciencecenter.bmi.BMIRaster;

import org.openda.blackbox.interfaces.IModelFactory;
import org.openda.interfaces.IModelInstance;
import org.openda.interfaces.IStochModelFactory.OutputLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BMIPythonModelFactory implements IModelFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(BMIPythonModelFactory.class);
    
    private static int nextModelID = 0;
    
    private static int getNextID() {
        nextModelID += 1;
        
        return nextModelID;
    }
    
    private String pythonExecutable;
    private File bridgeDir;
    private File modelDir;
    private String modelModule;
    private String modelClass;
    
    
    private File modelRunRootDir;
    
    private String configFile;

    //called by OpenDA
    public BMIPythonModelFactory() {
        //NOTHING
    }

    @Override
    public void initialize(File configRootDir, String[] arguments) {
        
        pythonExecutable = "/usr/bin/python";
        bridgeDir = new File("/home/niels/workspace/eWaterCycle-openda_bmi_python");
        modelDir = new File("/home/niels/workspace/PCR-GLOBWB/model");
        modelModule = "bmiPcrglobwb";
        modelClass = "BmiPCRGlobWB";
        
        configFile = "/home/niels/workspace/PCR-GLOBWB/config/setup_RhineMeuse_30arcmin_3layers_ndrost.ini";
    }

    @Override
    public IModelInstance getInstance(String[] arguments, OutputLevel outputLevel) {
        try {
            int instanceID = getNextID();
            
            BMIRaster model = LocalPythonThriftBMIRaster.createModel(pythonExecutable, bridgeDir, modelDir, modelModule,

                   modelClass);

            String workDir = String.format("work%2d", instanceID);
            
            File modelWorkDir = new File(modelRunRootDir, workDir);
            
            return new BMIRasterModelInstance(model, modelWorkDir, configFile);
        } catch (Exception e) {
            LOGGER.error("failed to create instance", e);
            return null;
        }
    }

  

    @Override
    public void finish() {
        // TODO Auto-generated method stub

    }
}
