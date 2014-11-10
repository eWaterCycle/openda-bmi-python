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

import org.openda.blackbox.interfaces.IModelFactory;
import org.openda.interfaces.IModelInstance;
import org.openda.interfaces.IStochModelFactory.OutputLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BMIPythonModelFactory implements IModelFactory {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BMIPythonModelFactory.class);
    
    @Override
    public void initialize(File workingDir, String[] arguments) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public IModelInstance getInstance(String[] arguments, OutputLevel outputLevel) {
        try {
            return new BMIPythonModelInstance();
        } catch (Exception e){
            LOGGER.error("failed to create instance", e);
            return null;
        }
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        
    }
}
