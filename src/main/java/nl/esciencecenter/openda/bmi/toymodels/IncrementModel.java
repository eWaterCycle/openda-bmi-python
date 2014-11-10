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

import nl.esciencecenter.bmi.BMIGridType;
import nl.esciencecenter.bmi.BMIModelException;
import nl.esciencecenter.bmi.BMIRaster;

/**
 * Model that holds only a single (grid) variable, which increments at every timestep.
 * 
 * 
 * @author Rolf Hut
 * @author Niels Drost
 *
 */
public class IncrementModel implements BMIRaster {
    private double dt;
    private double t;
    private double startTime;
    private double endTime;
    private DoubleRaster state;

    private final int[] shape;
    private final String name = "Example java toy increment Model";

    public IncrementModel() {
        shape = new int[] { 10, 10 };
    }

    @Override
    public void initialize(String file) throws BMIModelException {
        dt = 1.0;
        startTime = 1.0;
        t = startTime;
        endTime = 20.0;

        state = new DoubleRaster(shape[0], shape[1]);
        state.setScalar(startTime);

    }

    @Override
    public void update() throws BMIModelException {
        if (t >= endTime) {
            throw new BMIModelException("endTime already reached, model not updated");
        }
        state.addScalar(1);
        t += dt;
    };

    @Override
    public void update_until(double time) throws BMIModelException {
        if ((time < t) | (time > endTime)) {
            throw new BMIModelException("wrong time input: smaller than model time or larger than endTime");
        }
        while (t < time) {
            update();
        }
    };

    @Override
    public void finalize_model() {
        dt = 0;
        t = 0;
        state = null;
    };

    @Override
    public String[] get_input_var_names() {
        return new String[] { "var1" };
    };

    @Override
    public String[] get_output_var_names() {
        return new String[] { "var1" };
    };

    @Override
    public String get_var_type(String long_var_name) throws BMIModelException {
        if (!long_var_name.equals("var1")) {
            throw new BMIModelException("variable " + long_var_name + " does not exist");
        }
        return "BMI_DOUBLE";
    };

    @Override
    public String get_var_units(String long_var_name) throws BMIModelException {
        if (!long_var_name.equals("var1")) {
            throw new BMIModelException("variable " + long_var_name + " does not exist");
        }
        return "-";
    };

    @Override
    public int get_var_rank(String long_var_name) throws BMIModelException {
        if (!long_var_name.equals("var1")) {
            throw new BMIModelException("variable " + long_var_name + " does not exist");
        }
        return state.getRank();
    };

    @Override
    public double get_start_time() {
        return startTime;
    };

    @Override
    public double get_end_time() {
        return endTime;
    };

    @Override
    public double get_current_time() {
        return t;
    }

    @Override
    public void run_model() throws BMIModelException {
        update_until(get_end_time());
    }

    @Override
    public String get_component_name() {
        return this.name;
    }

    @Override
    public double[] get_double(String long_var_name) throws BMIModelException {
        if (!long_var_name.equals("var1")) {
            throw new BMIModelException("variable " + long_var_name + " does not exist");
        }

        return state.getValues();
    }

    @Override
    public double[] get_double_at_indices(String long_var_name, int[] indices) throws BMIModelException {
        if (!long_var_name.equals("var1")) {
            throw new BMIModelException("variable " + long_var_name + " does not exist");
        }

        return state.getValues(indices);
    }

    @Override
    public void set_double(String long_var_name, double[] src) throws BMIModelException {
        if (!long_var_name.equals("var1")) {
            throw new BMIModelException("variable " + long_var_name + " does not exist");
        }

        state.setValues(src);
    }

    @Override
    public void set_double_at_indices(String long_var_name, int[] indices, double[] src) throws BMIModelException {
        if (!long_var_name.equals("var1")) {
            throw new BMIModelException("variable " + long_var_name + " does not exist");
        }

        state.setValues(indices, src);
    }

    @Override
    public BMIGridType get_grid_type(String long_var_name) throws BMIModelException {
        if (!long_var_name.equals("var1")) {
            throw new BMIModelException("variable " + long_var_name + " does not exist");
        }
        return BMIGridType.UNIFORM;
    }

    @Override
    public int[] get_grid_shape(String long_var_name) throws BMIModelException {
        if (!long_var_name.equals("var1")) {
            throw new BMIModelException("variable " + long_var_name + " does not exist");
        }
        return shape;
    }

    @Override
    public double[] get_grid_spacing(String long_var_name) throws BMIModelException {
        if (!long_var_name.equals("var1")) {
            throw new BMIModelException("variable " + long_var_name + " does not exist");
        }
        return new double[] { 1.0, 1.0 };
    }

    @Override
    public double[] get_grid_origin(String long_var_name) throws BMIModelException {
        if (!long_var_name.equals("var1")) {
            throw new BMIModelException("variable " + long_var_name + " does not exist");
        }
        return new double[] { 0.0, 0.0 };
    }
}
