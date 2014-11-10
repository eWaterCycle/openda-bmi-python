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
package nl.esciencecenter.bmi.toymodels;

import static org.junit.Assert.*;

import java.util.Arrays;

import nl.esciencecenter.bmi.BMI;
import nl.esciencecenter.bmi.BMIGridType;
import nl.esciencecenter.bmi.BMIModelException;
import nl.esciencecenter.bmi.BMIRaster;
import nl.esciencecenter.bmi.toymodels.IncrementModel;

import org.junit.Test;

public class IncrementModelTest {

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#IncrementModel()}.
     */
    @Test
    public void testIncrementModel() {
        IncrementModel model = new IncrementModel();

        model.finalize_model();
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#initialize(java.lang.String)}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testInitialize() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        model.finalize_model();
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#update()}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testUpdate() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        double startTime = model.get_current_time();
        double startValue = model.get_double("var1")[0];

        assertEquals(1.0, startTime, 0.0);
        assertEquals(1.0, startValue, 0.0);

        model.update();

        double endTime = model.get_current_time();
        double endValue = model.get_double("var1")[0];

        model.finalize_model();

        assertEquals(2.0, endTime, 0.0);
        assertEquals(2.0, endValue, 0.0);
    }

    @Test
    public void testUpdate_AfterEndTime_Exception() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        model.update_until(model.get_end_time());

        try {
            model.update();

            fail("required exception not thrown");
        } catch (BMIModelException e) {
            //EXPECTED
        } finally {
            model.finalize_model();
        }

    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#update_until(double)}.
     * 
     * @throws Throwable
     */
    @Test
    public void testUpdate_until() throws Throwable {
        BMI model = new IncrementModel();

        model.initialize("");

        double startTime = model.get_current_time();
        double startValue = model.get_double("var1")[0];

        assertEquals(1.0, startTime, 0.0);
        assertEquals(1.0, startValue, 0.0);

        model.update_until(16.0);

        double endTime = model.get_current_time();
        double endValue = model.get_double("var1")[0];

        model.finalize_model();

        assertEquals(16.0, endTime, 0.0);
        assertEquals(16.0, endValue, 0.0);
    }

    @Test
    public void testUpdate_until_AfterEndTime_Exception() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        try {
            model.update_until(100000.0);

            fail("required exception not thrown");
        } catch (BMIModelException e) {
            //EXPECTED
        } finally {
            model.finalize_model();
        }

    }

    @Test
    public void testUpdate_until_BeforeCurrentTime_Exception() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        try {
            model.update_until(-100000.0);

            fail("required exception not thrown");
        } catch (BMIModelException e) {
            //EXPECTED
        } finally {
            model.finalize_model();
        }

    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#finalize_model()}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testFinalize_model() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");
        model.finalize_model();

        try {
            model.get_double("var1");
            fail("required exception not thrown");
        } catch (NullPointerException e) {
            //EXPECTED
        }

    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_input_var_names()}.
     * 
     * @throws BMIModelException
     *             in case of a failure
     */
    @Test
    public void testGet_input_var_names() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        String[] result = model.get_input_var_names();
        String[] expected = new String[] { "var1" };

        assertArrayEquals(expected, result);

        model.finalize_model();
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_output_var_names()}.
     * 
     * @throws BMIModelException
     *             in case of a failure
     */
    @Test
    public void testGet_output_var_names() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        String[] result = model.get_output_var_names();
        String[] expected = new String[] { "var1" };

        model.finalize_model();

        assertArrayEquals(expected, result);

    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_var_type(java.lang.String)}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testGet_var_type() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        String result = model.get_var_type("var1");

        model.finalize_model();

        assertEquals("BMI_DOUBLE", result);
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_var_type(java.lang.String)}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testGet_var_type_invalidVariable_exception() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        try {

            model.get_var_type("does_not_exist_var");

            fail("required exception not thrown");
        } catch (BMIModelException e) {
            //EXPECTED
        } finally {

            model.finalize_model();

        }
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_var_units(java.lang.String)}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testGet_var_units() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        String result = model.get_var_units("var1");

        model.finalize_model();

        assertEquals("-", result);
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_var_units(java.lang.String)}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testGet_var_units_invalidVariable_exception() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        try {

            model.get_var_units("does_not_exist_var");

            fail("required exception not thrown");
        } catch (BMIModelException e) {
            //EXPECTED
        } finally {

            model.finalize_model();

        }
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_var_rank(java.lang.String)}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testGet_var_rank() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        int result = model.get_var_rank("var1");

        model.finalize_model();

        assertEquals(100, result);
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_var_rank(java.lang.String)}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testGet_var_rank_invalidVariable_exception() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        try {

            model.get_var_rank("does_not_exist_var");

            fail("required exception not thrown");
        } catch (BMIModelException e) {
            //EXPECTED
        } finally {

            model.finalize_model();

        }
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_start_time()}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testGet_start_time() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        double result = model.get_start_time();

        assertEquals(1.0, result, 0.0);

        model.finalize_model();

    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_end_time()}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testGet_end_time() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        double result = model.get_end_time();

        assertEquals(20.0, result, 0.0);

        model.finalize_model();
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_current_time()}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testGet_current_time() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        model.update();
        model.update();

        double result = model.get_current_time();

        assertEquals(3.0, result, 0.0);

        model.finalize_model();
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#run_model()}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testRun_model() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        model.run_model();

        double result = model.get_double("var1")[0];

        assertEquals(20.0, result, 0.0);

        model.finalize_model();
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_component_name()}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testGet_component_name() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        String expected = "Example java toy increment Model";
        String result = model.get_component_name();

        model.finalize_model();

        assertEquals(expected, result);
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_double(java.lang.String)}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testGet_double() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        model.update();

        double[] result = model.get_double("var1");

        double expected = 2.0;

        model.finalize_model();

        assertEquals(100, result.length);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expected, result[i], 0.0);
        }
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_double(java.lang.String)}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testGet_double_invalidVariable_exception() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        try {

            model.get_double("does_not_exist_var");

            fail("required exception not thrown");
        } catch (BMIModelException e) {
            //EXPECTED
        } finally {

            model.finalize_model();

        }
    }

    /**
     * Test method for
     * {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_double_at_indices(java.lang.String, int[])}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testGet_double_at_indices() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        model.update();

        int[] indices = new int[] { 4, 5, 6, 7, 8 };

        double[] result = model.get_double_at_indices("var1", indices);

        double expected = 2.0;

        model.finalize_model();

        assertEquals(5, result.length);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expected, result[i], 0.0);
        }
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_double(java.lang.String)}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testGet_double_at_indices_invalidVariable_exception() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        int[] indices = new int[] { 4, 5, 6, 7, 8 };

        try {
            model.get_double_at_indices("does_not_exist_var", indices);

            fail("required exception not thrown");
        } catch (BMIModelException e) {
            //EXPECTED
        } finally {

            model.finalize_model();

        }
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#set_double(java.lang.String, double[])}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testSet_double() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        model.update_until(10.0);

        double[] data = new double[100];

        model.set_double("var1", data);

        model.update_until(20.0);

        double[] result = model.get_double("var1");

        double expected = 10.0;

        model.finalize_model();

        assertEquals(100, result.length);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expected, result[i], 0.0);
        }
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#set_double(java.lang.String, double[])}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testSet_double_invalidVariable_exception() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        double[] data = new double[100];

        try {
            model.set_double("does_not_exist_var", data);

            fail("required exception not thrown");
        } catch (BMIModelException e) {
            //EXPECTED
        } finally {

            model.finalize_model();

        }
    }

    /**
     * Test method for
     * {@link nl.esciencecenter.bmi.toymodels.IncrementModel#set_double_at_indices(java.lang.String, int[], double[])}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testSet_double_at_indices() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        model.update_until(10.0);

        int[] indices = new int[] { 4, 5, 6, 7, 8 };
        double[] values = new double[] { 2.0, 2.0, 2.0, 2.0, 2.0 };

        model.set_double_at_indices("var1", indices, values);

        model.update_until(20.0);

        double[] result = model.get_double("var1");

        double expected = 12.0;

        model.finalize_model();

        assertEquals(100, result.length);

        //value should be "as normal" in most result indices (including the first)
        assertEquals(20.0, result[0], 0.0);

        //values should be different at set indices
        for (int i = 0; i < indices.length; i++) {
            assertEquals(expected, result[indices[i]], 0.0);
        }
    }

    /**
     * Test method for
     * {@link nl.esciencecenter.bmi.toymodels.IncrementModel#set_double_at_indices(java.lang.String, int[], double[])}.
     */
    @Test
    public void testSet_double_at_indices_invalidVariable_exception() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        int[] indices = new int[] { 4, 5, 6, 7, 8 };
        double[] values = new double[] { 4., 5., 6., 7., 8. };

        try {
            model.set_double_at_indices("does_not_exist_var", indices, values);

            fail("required exception not thrown");
        } catch (BMIModelException e) {
            //EXPECTED
        } finally {

            model.finalize_model();

        }
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_grid_type(java.lang.String)}.
     * @throws BMIModelException 
     */
    @Test
    public void testGet_grid_type() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        BMIGridType result = model.get_grid_type("var1");
        
        model.finalize_model();
        
        assertEquals(BMIGridType.UNIFORM, result);
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_grid_type(java.lang.String)}.
     * 
     * @throws BMIModelException
     */
    @Test
    public void testGet_grid_type_invalidVariable_exception() throws BMIModelException {
        BMI model = new IncrementModel();

        model.initialize("");

        try {
            model.get_grid_type("does_not_exist_var");

            fail("required exception not thrown");
        } catch (BMIModelException e) {
            //EXPECTED
        } finally {

            model.finalize_model();

        }
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_grid_shape(java.lang.String)}.
     * @throws BMIModelException 
     */
    @Test
    public void testGet_grid_shape() throws BMIModelException {
        BMIRaster model = new IncrementModel();

        model.initialize("");

        int[] expected = new int[] {10, 10};
        
        int[] result = model.get_grid_shape("var1");
        
        model.finalize_model();
        
        assertArrayEquals(expected, result);

    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_grid_shape(java.lang.String)}.
     * @throws BMIModelException 
     */
    @Test
    public void testGet_grid_shape_invalidVariable_exception() throws BMIModelException {
        BMIRaster model = new IncrementModel();

        model.initialize("");

        try {
            model.get_grid_shape("does_not_exist_var");

            fail("required exception not thrown");
        } catch (BMIModelException e) {
            //EXPECTED
        } finally {

            model.finalize_model();

        }

    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_grid_spacing(java.lang.String)}.
     * @throws BMIModelException 
     */
    @Test
    public void testGet_grid_spacing() throws BMIModelException {
        BMIRaster model = new IncrementModel();

        model.initialize("");

        double[] expected = new double[] {1.0, 1.0};
        
        double[] result = model.get_grid_spacing("var1");
        
        model.finalize_model();
        
        assertArrayEquals(expected, result, 0.0);
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_grid_spacing(java.lang.String)}.
     * @throws BMIModelException 
     */
    @Test
    public void testGet_grid_spacing_invalidVariable_exception() throws BMIModelException {
        BMIRaster model = new IncrementModel();

        model.initialize("");

        try {
            model.get_grid_spacing("does_not_exist_var");

            fail("required exception not thrown");
        } catch (BMIModelException e) {
            //EXPECTED
        } finally {

            model.finalize_model();

        }

    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_grid_origin(java.lang.String)}.
     * @throws BMIModelException 
     */
    @Test
    public void testGet_grid_origin() throws BMIModelException {
        BMIRaster model = new IncrementModel();

        model.initialize("");

        double[] expected = new double[] {0.0, 0.0};
        
        double[] result = model.get_grid_origin("var1");
        
        model.finalize_model();
        
        assertArrayEquals(expected, result, 0.0);
    }

    /**
     * Test method for {@link nl.esciencecenter.bmi.toymodels.IncrementModel#get_grid_origin(java.lang.String)}.
     * @throws BMIModelException 
     */
    @Test
    public void testGet_grid_origin_invalidVariable_exception() throws BMIModelException {
        BMIRaster model = new IncrementModel();

        model.initialize("");

        try {
            model.get_grid_origin("does_not_exist_var");

            fail("required exception not thrown");
        } catch (BMIModelException e) {
            //EXPECTED
        } finally {

            model.finalize_model();

        }

    }

}
