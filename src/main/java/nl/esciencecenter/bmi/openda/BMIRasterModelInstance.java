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
import java.io.IOException;
import java.util.HashSet;

import nl.esciencecenter.bmi.BMIModelException;
import nl.esciencecenter.bmi.BMIRaster;

import org.openda.interfaces.IExchangeItem;
import org.openda.interfaces.IModelInstance;
import org.openda.interfaces.IModelState;
import org.openda.interfaces.IObservationDescriptions;
import org.openda.interfaces.IPrevExchangeItem;
import org.openda.interfaces.IPrevExchangeItem.Role;
import org.openda.interfaces.ITime;
import org.openda.interfaces.IVector;
import org.openda.utils.Instance;
import org.openda.utils.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interface to a Python BMI Model. Passes calls to the BMI interface.
 * 
 * @author Niels Drost
 * 
 */
public class BMIRasterModelInstance extends Instance implements IModelInstance {

    private static final Logger LOGGER = LoggerFactory.getLogger(BMIRasterModelInstance.class);

    private final BMIRaster model;
    private final File modelRunDir;

    BMIRasterModelInstance(BMIRaster model, File modelRunDir) {
        this.model = model;
        this.modelRunDir = modelRunDir;
    }

    @Override
    public String[] getExchangeItemIDs() {
        try {
            HashSet<String> result = new HashSet<String>();

            for(String var: model.get_output_var_names()) {
                result.add(var);
            }
            
            for(String var: model.get_input_var_names()) {
                result.add(var);
            }

            return result.toArray(new String[0]);
        } catch (BMIModelException e) {
            throw new RuntimeException(e);
        }
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

                for(String var: model.get_output_var_names()) {
                    result.add(var);
                }
                
                for(String var: model.get_input_var_names()) {
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
        
        // TODO Auto-generated method stub
        return null;
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
        
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IModelState saveInternalState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void restoreInternalState(IModelState savedInternalState) {
        // TODO Auto-generated method stub

    }

    @Override
    public void releaseInternalState(IModelState savedInternalState) {
        // TODO Auto-generated method stub

    }

    @Override
    public IModelState loadPersistentState(File persistentStateFile) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File getModelRunDir() {
        return this.modelRunDir;
    }

}
