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
import java.util.Arrays;

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
public class PCRGlobWBThriftBMIRasterTest {

    @Test
    public void newIncrementModel() throws IOException, BMIModelException {
        
//        String pythonExecutable = "/usr/bin/python";
//        File bridgeDir = new File("/home/niels/workspace/eWaterCycle-openda_bmi_python");
//        File modelDir = new File("/home/niels/workspace/eWaterCycle-openda_bmi_python/src/test/python/increment_model");
//        String modelClass = "IncrementModel";
//        
//        return LocalPythonThriftBMIRaster.createModel(pythonExecutable, bridgeDir, modelDir, modelClass);

      String pythonExecutable = "/usr/bin/python";
      File bridgeDir = new File("/home/niels/workspace/eWaterCycle-openda_bmi_python");
      File modelDir = new File("/home/niels/workspace/PCR-GLOBWB/model");
      String modelModule = "bmiPcrglobwb";
      String modelClass = "BmiPCRGlobWB";
      
      BMIRaster model =  LocalPythonThriftBMIRaster.createModel(pythonExecutable, bridgeDir, modelDir, modelModule, modelClass);
    
      model.initialize("/home/niels/workspace/PCR-GLOBWB/config/setup_RhineMeuse_30arcmin_3layers_ndrost.ini");

      //model.get_output_var_names();
      
      model.update();
      
      float[] result = model.get_float("top_layer_soil_saturation");
      
      System.err.println(Arrays.toString(result));
      
      model.set_float("top_layer_soil_saturation", result);
      
      model.update();
      
      model.finalize_model();
        
    }
    

    


}
