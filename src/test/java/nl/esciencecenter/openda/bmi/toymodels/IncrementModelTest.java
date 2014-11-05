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
package nl.esciencecenter.openda.bmi.toymodels;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import nl.esciencecenter.bmi.BMI;
import nl.esciencecenter.bmi.BMIModelException;

import org.apache.thrift.TException;
import org.junit.Test;

public class IncrementModelTest {

    @Test
    public void test01() {
        new IncrementModel();
    }
    
    @Test
    public void test02() throws BMIModelException, TException {
        BMI model = new IncrementModel();
        
        model.initialize("");
        
        model.finalize_model();
    }
    
    @Test
    public void test03() throws BMIModelException, TException {
        BMI model = new IncrementModel();
        
        model.initialize("");
        
        String[] result = model.get_input_var_names();
        String[] expected = new String[] {"var1"};
        
        assertArrayEquals(expected, result);
        
        model.finalize_model();
    }
    

    @Test
    public void test04() throws BMIModelException, TException {
        BMI model = new IncrementModel();
        
        model.initialize("");

        System.err.println(Arrays.toString(model.get_double("var1")));
        
        model.update();

        System.err.println(Arrays.toString(model.get_double("var1")));
        
        model.update();
        
        System.err.println(Arrays.toString(model.get_double("var1")));
        
        model.update_until(model.get_end_time());
        
        System.err.println(Arrays.toString(model.get_double("var1")));
        
        model.finalize_model();
    }

    @Test
    public void test05() throws BMIModelException, TException {
        BMI model = new IncrementModel();
        
        model.initialize("");
        
        model.run_model();
        
        System.err.println(Arrays.toString(model.get_double("var1")));
        
        model.finalize_model();
    }



}
