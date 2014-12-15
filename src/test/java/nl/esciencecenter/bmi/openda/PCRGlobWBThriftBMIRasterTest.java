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
    
    private static final String iniFile = "/home/niels/workspace/PCR-GLOBWB/config/setup_RhineMeuse_30arcmin_3layers_ndrost.ini";
    private static int[] shape = new int[] {13, 17};
    private static int dataSize = shape[0] * shape[1];
    private static double[] gridSpacing = new double[] {0.5, 0.5};
    
    private static String variableName = "top_layer_soil_saturation";

    private static BMIRaster createModel() throws IOException, BMIModelException {
        String pythonExecutable = "/usr/bin/python";
        File bridgeDir = new File("/home/niels/workspace/eWaterCycle-openda_bmi_python");
        File modelDir = new File("/home/niels/workspace/PCR-GLOBWB/model");
        String modelModule = "bmiPcrglobwb";
        String modelClass = "BmiPCRGlobWB";
        File cwd = new File("/home/niels/workspace/eWaterCycle-openda_bmi_python");

        BMIRaster model = LocalPythonThriftBMIRaster.createModel(null, pythonExecutable, bridgeDir, modelDir, modelModule, modelClass, cwd);

        model.initialize(iniFile);

        return model;
    }

    @Test
    public void testNewModel() throws IOException, BMIModelException {

        BMIRaster model = createModel();

        model.finalize_model();

    }

    @Test
    public void testGetVariable() throws IOException, BMIModelException {

        BMIRaster model = createModel();

        model.update();

        double[] result = model.get_double("top_layer_soil_saturation");

        model.finalize_model();
        
        assertEquals("result should have the correct amount of elements", dataSize, result.length);
    }
    
    @Test
    public void testGetShape() throws IOException, BMIModelException {

        BMIRaster model = createModel();

        model.update();

        int[] result = model.get_grid_shape(variableName);

        model.finalize_model();
        
        assertArrayEquals("result should have the correct shape", shape, result);
        
    }
    
    @Test
    public void testGetGridSpacing() throws IOException, BMIModelException {

        BMIRaster model = createModel();

        model.update();

        double[] result = model.get_grid_spacing(variableName);

        model.finalize_model();
        
        assertArrayEquals("result should have the correct spacing", gridSpacing, result, 0.0);
        
    }

    @Test
    public void testSetVariable() throws IOException, BMIModelException {

        BMIRaster model = createModel();

        model.update();

        int[] shape = model.get_grid_shape("top_layer_soil_saturation");

        assertEquals("grid is expected to be two dimensional", 2, shape.length);

        int dataSize = shape[0] * shape[1];

        double[] data = new double[dataSize];

        model.set_double("top_layer_soil_saturation", data);

        model.finalize_model();
    }

    @Test
    public void testRunModel() throws IOException, BMIModelException {

        BMIRaster model = createModel();

        while (model.get_current_time() < model.get_end_time()) {
            model.update();
        }

        model.finalize_model();

    }



}
