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
    
    private String pythonExecutable;
    private File bridgeDir;
    private File modelDir;
    private String modelModule;
    private String modelClass;

    //called by OpenDA
    public BMIPythonModelFactory() {
        //NOTHING
    }

    @Override
    public void initialize(File configRootDir, String[] arguments) {
        //get arguments such as python location, etc
        // TODO Auto-generated method stub
        //        String pythonExecutable, File bridgeDir, File modelDir, String modelModule, String modelClass;
    }

    @Override
    public IModelInstance getInstance(String[] arguments, OutputLevel outputLevel) {
        try {
            int instanceID = 0;
            
            BMIRaster model = LocalPythonThriftBMIRaster.createModel(pythonExecutable, bridgeDir, modelDir, modelModule,
                    modelClass);

            File modelRunDir = null;

            return new BMIRasterModelInstance(model, modelRunDir);
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
