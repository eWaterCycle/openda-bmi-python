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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import nl.esciencecenter.bmi.BMIModelException;
import nl.esciencecenter.bmi.BMIRaster;

import org.openda.interfaces.IExchangeItem;
import org.openda.interfaces.IGeometryInfo;
import org.openda.interfaces.IModelExtensions;
import org.openda.interfaces.IModelInstance;
import org.openda.interfaces.IModelState;
import org.openda.interfaces.IObservationDescriptions;
import org.openda.interfaces.IPrevExchangeItem;
import org.openda.interfaces.IPrevExchangeItem.Role;
import org.openda.interfaces.ITime;
import org.openda.interfaces.IVector;
import org.openda.utils.Instance;
import org.openda.utils.Time;
import org.openda.utils.geometry.GeometryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import visad.data.netcdf.UnsupportedOperationException;

/**
 * Interface to a Python BMI Model. Passes calls to the BMI interface.
 * 
 * @author Niels Drost
 * 
 */
public class BMIRasterModelInstance extends Instance implements IModelInstance, IModelExtensions {

    private static final Logger LOGGER = LoggerFactory.getLogger(BMIRasterModelInstance.class);

    private final BMIRaster model;
    private final File modelRunDir;

    private final Map<String, BMIRasterStateExchangeItem> exchangeItems;

    BMIRasterModelInstance(BMIRaster model, File modelRunDir, String initFile) throws BMIModelException {
        this.model = model;
        this.modelRunDir = modelRunDir;

        //initialize model
        model.initialize(initFile);

        exchangeItems = createExchangeItems(model);
    }

    private static Map<String, BMIRasterStateExchangeItem> createExchangeItems(BMIRaster model) throws BMIModelException {
        HashSet<String> inputVars = new HashSet<String>();
        HashSet<String> outputVars = new HashSet<String>();
        HashSet<String> inoutVars = new HashSet<String>();

        //first fill sets with input and output variables
        for (String var : model.get_input_var_names()) {
            inputVars.add(var);
        }
        for (String var : model.get_output_var_names()) {
            outputVars.add(var);
        }

        //then put duplicates in inout variables.
        //Note: Loop over copy of set to prevent iterator exception 
        for (String var : inputVars.toArray(new String[0])) {
            if (outputVars.contains(var)) {
                inputVars.remove(var);
                outputVars.remove(var);
                inoutVars.add(var);
            }
        }

        Map<String, BMIRasterStateExchangeItem> result = new HashMap<String, BMIRasterStateExchangeItem>();

        for (String variable : inputVars) {
            BMIRasterStateExchangeItem item = new BMIRasterStateExchangeItem(variable, IPrevExchangeItem.Role.Input, model);
            result.put(variable, item);
        }

        for (String variable : outputVars) {
            BMIRasterStateExchangeItem item = new BMIRasterStateExchangeItem(variable, IPrevExchangeItem.Role.Output, model);
            result.put(variable, item);
        }

        for (String variable : inoutVars) {
            BMIRasterStateExchangeItem item = new BMIRasterStateExchangeItem(variable, IPrevExchangeItem.Role.InOut, model);
            result.put(variable, item);
        }
        return result;
    }

    @Override
    public String[] getExchangeItemIDs() {
        return exchangeItems.keySet().toArray(new String[0]);
    }

    @Override
    public String[] getExchangeItemIDs(Role role) {
        try {
            if (role.equals(Role.Input)) {
                return model.get_input_var_names();
            } else if (role.equals(Role.Output)) {
                return model.get_output_var_names();
            } else if (role.equals(Role.InOut)) {
                HashSet<String> result = new HashSet<String>();

                for (String var : model.get_output_var_names()) {
                    result.add(var);
                }

                for (String var : model.get_input_var_names()) {
                    result.add(var);
                }

                return result.toArray(new String[0]);
            } else {
                return new String[0];
            }

        } catch (BMIModelException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IExchangeItem getDataObjectExchangeItem(String exchangeItemID) {
        if (!exchangeItems.containsKey(exchangeItemID)) {
            throw new RuntimeException("exchange item not present");
        }
        return exchangeItems.get(exchangeItemID);
    }

    @Override
    public void finish() {
        try {
            model.finalize_model();
        } catch (BMIModelException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(File workingDir, String[] arguments) {
        //NOTHING
    }

    @Override
    public IPrevExchangeItem getExchangeItem(String exchangeItemID) {
        return getDataObjectExchangeItem(exchangeItemID);
    }

    @Override
    public ITime getTimeHorizon() {
        //FIXME: get time step size from model

        try {
            return new Time(model.get_start_time(), model.get_end_time(), 1.0);
        } catch (BMIModelException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ITime getCurrentTime() {
        try {
            return new Time(model.get_current_time());
        } catch (BMIModelException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void compute(ITime targetTime) {
        try {
            model.update_until(targetTime.getMJD());
        } catch (BMIModelException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IVector[] getObservedLocalization(IObservationDescriptions observationDescriptions, double distance) {
        throw new UnsupportedOperationException(this.getClass().getName() + ".getObservedLocalization() not implemented");
    }

    @Override
    public IModelState saveInternalState() {
        throw new UnsupportedOperationException(this.getClass().getName() + ".saveInternalState() not implemented");
    }

    @Override
    public void restoreInternalState(IModelState savedInternalState) {
        throw new UnsupportedOperationException(this.getClass().getName() + ".restoreInternalState() not implemented");
    }

    @Override
    public void releaseInternalState(IModelState savedInternalState) {
        throw new UnsupportedOperationException(this.getClass().getName() + ".releaseInternalState() not implemented");
    }

    @Override
    public IModelState loadPersistentState(File persistentStateFile) {
        throw new UnsupportedOperationException(this.getClass().getName() + ".loadPersistentState() not implemented");
    }

    @Override
    public File getModelRunDir() {
        return this.modelRunDir;
    }

    
    /**
     * Get the observed values of the Model.
     * This returns what the observations would look like, if reality would be equal to the current model state.
     *
     * In other words this method returns a grid with values that would be observed by the satellite
     * if reality would be equal to the current model state. This is needed, because, to compare the
     * satellite observations with the model output, they should be defined on the same grid. The grid
     * of the satellite has a different position, size and orientation than the grid of the model state.
     * The values of the model state grid are interpolated to the observations grid using bilinear interpolation.
     * For satellite observations the interpolation has to be done for each observation separately, since for each time step
     * the satellite grid can be different, as the satellite moves along its orbit.
     *
     * @param observationDescriptions observation description
     * @return Model prediction interpolated to each observation (location).
     */
    @Override
    public IVector getObservedValues(IObservationDescriptions observationDescriptions) {
            validateSingleObservationsExchangeItem(observationDescriptions);

            //TODO if multiple grids for current time, e.g. soilMoisture and evaporation, then the coordinates of these grids are present in sequence in observationDescriptions,
            //in that case need to figure out which of the observations correspond to the given stateExchangeItemID here.
            IVector observationXCoordinates = observationDescriptions.getValueProperties("x");
            IVector observationYCoordinates = observationDescriptions.getValueProperties("y");

            if (exchangeItems.size() > 1) {
                throw new RuntimeException("Can only handle a single exchange item");
            }

            //here only use the model exchangeItem that corresponds to the observed values, e.g. "SoilMoisture".
            IExchangeItem modelExchangeItem = exchangeItems.values().iterator().next();
            
            IGeometryInfo modelGeometryInfo = modelExchangeItem.getGeometryInfo();
            double[] modelValues = modelExchangeItem.getValuesAsDoubles();

            IVector result = GeometryUtils.getObservedValuesBilinearInterpolation(observationXCoordinates, observationYCoordinates, modelGeometryInfo, modelValues);
            
            for (int i = 0; i< result.getSize(); i++) {
                double value = result.getValue(i);
                
                if (Double.isNaN(value)) {
                    throw new RuntimeException("Model value at Observation is Nan!");
                }
            }
            
            return result;
    }

    /**
     * Check whether the given observationDescriptions contains a single IExchangeItem, otherwise x and y coordinates will contain coordinates from multiple exchangeItems.
     *
     * @param observationDescriptions
     */
    private static void validateSingleObservationsExchangeItem(IObservationDescriptions observationDescriptions) {
            //get exchangeItem.
            List<IPrevExchangeItem> exchangeItems = observationDescriptions.getExchangeItems();
            if (exchangeItems == null || exchangeItems.size() != 1) {
                    throw new IllegalArgumentException("Given observationDescriptions contains none or multiple exchangeItems, but should contain only one.");
            }
    }

    /**
     * Returns the localization weights for each observation location.
     *
     * @param stateExchangeItemID id of the state vector for which the localization weights should be returned.
     * @param observationDescriptions observation description
     * @param distance characteristic distance for Cohn's formula
     * @return weight vector for each observation location.
     *         The size of the returned array must equal the number of observation locations in the given observationDescriptions.
     *         The size of each vector in the returned array must equal the size of the state vector with the given stateExchangeItemID.
     */
    public IVector[] getObservedLocalization(String stateExchangeItemID, IObservationDescriptions observationDescriptions, double distance) {
            //validateSingleObservationsExchangeItem(observationDescriptions);

            //TODO if multiple grids for current time, e.g. soilMoisture and evaporation, then the coordinates of these grids are present in sequence in observationDescriptions,
            //in that case need to figure out which of the observations correspond to the given stateExchangeItemID here.
            IVector observationXCoordinates = observationDescriptions.getValueProperties("x");
            IVector observationYCoordinates = observationDescriptions.getValueProperties("y");

            IExchangeItem stateExchangeItem = getDataObjectExchangeItem(stateExchangeItemID);
            IGeometryInfo stateGeometryInfo = stateExchangeItem.getGeometryInfo();

            return GeometryUtils.getLocalizationWeights(observationXCoordinates, observationYCoordinates, stateGeometryInfo, distance);
    }

}
