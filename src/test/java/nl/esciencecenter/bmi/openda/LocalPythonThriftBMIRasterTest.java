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

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import nl.esciencecenter.bmi.BMIModelException;
import nl.esciencecenter.bmi.BMIRaster;
import nl.esciencecenter.bmi.openda.LocalPythonThriftBMIRaster;
import nl.esciencecenter.bmi.toymodels.IncrementModel;
import nl.esciencecenter.bmi.toymodels.IncrementModelTest;

import org.junit.Test;

/**
 * @author Niels Drost
 *
 */
public class LocalPythonThriftBMIRasterTest extends IncrementModelTest {

    protected BMIRaster newIncrementModel() throws IOException {

        String pythonExecutable = "/usr/bin/python";
        File bridgeDir = new File("/home/niels/workspace/eWaterCycle-openda_bmi_python");
        File modelDir = new File("/home/niels/workspace/eWaterCycle-openda_bmi_python/src/test/python/increment_model");
        String modelModule = "increment_model";
        String modelClass = "IncrementModel";
        
        File cwd = new File("/home/niels/workspace/eWaterCycle-openda_bmi_python");

        return LocalPythonThriftBMIRaster.createModel(pythonExecutable, bridgeDir, modelDir, modelModule, modelClass, cwd);

    }

}
