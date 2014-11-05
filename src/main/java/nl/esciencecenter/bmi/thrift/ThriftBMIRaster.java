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
package nl.esciencecenter.bmi.thrift;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

import nl.esciencecenter.bmi.BMIGridType;
import nl.esciencecenter.bmi.BMIModelException;
import nl.esciencecenter.bmi.BMIRaster;
import nl.esciencecenter.openda.bmi.thrift.BmiRaster;
import nl.esciencecenter.openda.bmi.thrift.BmiRaster.Client;

import org.apache.thrift.TException;

/**
 * Implementation of the Java BMI Interface that communicates with a remote BMI model using Thrift.
 * 
 * @author Niels Drost
 *
 */
public  class ThriftBMIRaster implements BMIRaster {

    private final Client client;

    /**
     * It is up to the user to create the (connection to) the model.
     * 
     * @param client
     */
    public ThriftBMIRaster(BmiRaster.Client client) {
        this.client = client;

    }

    @Override
    public void initialize(String file) throws BMIModelException {
        try {
            client.initialize(file);
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public void update() throws BMIModelException {
        try {
            client.update();
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public void update_until(double time) throws BMIModelException {
        try {
            client.update_until(time);
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public void finalize_model() throws BMIModelException {
        try {
            client.finalize_model();
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public void run_model() throws BMIModelException {
        try {
            client.run_model();
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public String get_component_name() throws BMIModelException {
        try {
            return client.get_component_name();
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public String[] get_input_var_names() throws BMIModelException {
        try {
            List<String> result = client.get_input_var_names();
            return result.toArray(new String[result.size()]);
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public String[] get_output_var_names() throws BMIModelException {
        try {
            List<String> result = client.get_output_var_names();
            return result.toArray(new String[result.size()]);
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public String get_var_type(String long_var_name) throws BMIModelException {
        try {
            return client.get_var_type(long_var_name);
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public String get_var_units(String long_var_name) throws BMIModelException {
        try {
            return client.get_var_units(long_var_name);
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public int get_var_rank(String long_var_name) throws BMIModelException {
        try {
            return client.get_var_rank(long_var_name);
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public double get_start_time() throws BMIModelException {
        try {
            return client.get_start_time();
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public double get_end_time() throws BMIModelException {
        try {
            return client.get_end_time();
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public double get_current_time() throws BMIModelException {
        try {
            return client.get_current_time();
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    //get a 1d array of doubles from the buffer given
    private static double[] bufferToDoubleArray(ByteBuffer buffer) {
        DoubleBuffer doubles = buffer.asDoubleBuffer();

        if (doubles.hasArray()) {
            return doubles.array();
        } else {
            double[] resultArray = new double[doubles.capacity()];
            doubles.get(resultArray);
            return resultArray;
        }
    }

    @Override
    public double[] get_double(String long_var_name) throws BMIModelException {
        try {
            ByteBuffer result = client.get_value(long_var_name);
            return bufferToDoubleArray(result);
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    private static List<Integer> intArrayToList(int[] array) {
        List<Integer> result = new ArrayList<Integer>();

        for (int index : array) {
            result.add(index);
        }

        return result;
    }

    private static int[] listToIntArray(List<Integer> list) {
        int[] result = new int[list.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    private static double[] listToDoubleArray(List<Double> list) {
        double[] result = new double[list.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    @Override
    public double[] get_double_at_indices(String long_var_name, int[] indices) throws BMIModelException {
        try {
            List<Integer> indicesList = intArrayToList(indices);

            ByteBuffer result = client.get_value_at_indices(long_var_name, indicesList);
            return bufferToDoubleArray(result);
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public void set_double(String long_var_name, double[] src) throws BMIModelException {
        try {
            //add data to a buffer
            ByteBuffer buffer = ByteBuffer.allocate(src.length * 8);
            buffer.asDoubleBuffer().put(src);

            client.set_value(long_var_name, buffer);
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public void set_double_at_indices(String long_var_name, int[] indices, double[] src) throws BMIModelException {
        try {
            //add data to a buffer
            ByteBuffer buffer = ByteBuffer.allocate(src.length * 8);
            buffer.asDoubleBuffer().put(src);

            List<Integer> indicesList = intArrayToList(indices);

            client.set_value_at_indices(long_var_name, indicesList, buffer);
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public BMIGridType get_grid_type(String long_var_name) throws BMIModelException {
        try {
            //convert from Thrift type to binding type
            return BMIGridType.findByValue(client.get_grid_type(long_var_name).getValue());
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public int[] get_grid_shape(String long_var_name) throws BMIModelException {
        try {
            //convert from Thrift type to binding type
            return listToIntArray(client.get_grid_shape(long_var_name));
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public double[] get_grid_spacing(String long_var_name) throws BMIModelException {
        try {
            //convert from Thrift type to binding type
            return listToDoubleArray(client.get_grid_spacing(long_var_name));
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }

    @Override
    public double[] get_grid_origin(String long_var_name) throws BMIModelException {
        try {
            //convert from Thrift type to binding type
            return listToDoubleArray(client.get_grid_origin(long_var_name));
        } catch (TException e) {
            throw new BMIModelException("failed to execute function on remote model", e);
        }
    }
}
