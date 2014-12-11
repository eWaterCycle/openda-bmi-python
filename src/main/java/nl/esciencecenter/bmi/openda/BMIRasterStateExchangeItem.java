/* MOD_V2.0
 * Copyright (c) 2012 OpenDA Association
 * All rights reserved.
 *
 * This file is part of OpenDA.
 *
 * OpenDA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * OpenDA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with OpenDA.  If not, see <http://www.gnu.org/licenses/>.
 */

package nl.esciencecenter.bmi.openda;

import nl.esciencecenter.bmi.BMIModelException;
import nl.esciencecenter.bmi.BMIRaster;

import org.openda.exchange.ArrayGeometryInfo;
import org.openda.exchange.QuantityInfo;
import org.openda.exchange.TimeInfo;
import org.openda.interfaces.IArray;
import org.openda.interfaces.IExchangeItem;
import org.openda.interfaces.IGeometryInfo;
import org.openda.interfaces.IPrevExchangeItem;
import org.openda.interfaces.IQuantityInfo;
import org.openda.interfaces.IStochVector;
import org.openda.interfaces.ITimeInfo;
import org.openda.interfaces.IVector;
import org.openda.utils.Array;
import org.openda.utils.StochVector;
import org.openda.utils.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exchange item representing a 2D map with values for BMI models
 *
 * @author Arno Kockx
 * @author Niels Drost
 */
public class BMIRasterStateExchangeItem implements IExchangeItem {

    private static Logger LOGGER = LoggerFactory.getLogger(BMIRasterStateExchangeItem.class);

    private static final long serialVersionUID = 1L;
    private final String variableName;
    private final IPrevExchangeItem.Role role;
    private final BMIRaster model;
    private final IQuantityInfo quantityInfo;
    private final IGeometryInfo geometryInfo;

    /**
     * @param variableName
     *            the name of the variable as used by a BMI model
     * @param role
     * @param adapter
     * @throws BMIModelException
     */
    public BMIRasterStateExchangeItem(String variableName, IPrevExchangeItem.Role role, BMIRaster model) throws BMIModelException {
        this.variableName = variableName;
        this.role = role;
        this.model = model;

        this.quantityInfo = new QuantityInfo(variableName, model.get_var_units(variableName));

        this.geometryInfo = createGeometryInfo();

    }

    /**
     * @return
     */
    private IGeometryInfo createGeometryInfo() {
        //lower-left corner
        try {
            if (model == null) {
                throw new NullPointerException("model is null!");
            }

            double[] origin = this.model.get_grid_origin(variableName);
            double[] spacing = this.model.get_grid_spacing(variableName);
            int[] shape = this.model.get_grid_shape(variableName);

            //data in grid lower-to-higher latitudes (south to north)
            double[] latitudes = new double[shape[0]];
            for (int n = 0; n < latitudes.length; n++) {
                //calculate latitude at center of each cell
                latitudes[n] = origin[0] + (spacing[0] / 2) + (n * spacing[0]);
            }
            IArray latitudeArray = new Array(latitudes);

            double[] longitudes = new double[shape[1]];
            for (int n = 0; n < longitudes.length; n++) {
                longitudes[n] = origin[1] + (spacing[1] / 2) + (n * spacing[1]);
            }
            IArray longitudeArray = new Array(longitudes);

            int[] latitudeValueIndices = new int[] { 0 };
            int[] longitudeValueIndices = new int[] { 1 };

            IQuantityInfo latitudeQuantityInfo = new QuantityInfo("y coordinate according to model coordinate system", "meter");
            IQuantityInfo longitudeQuantityInfo = new QuantityInfo("x coordinate according to model coordinate system", "meter");
            return new ArrayGeometryInfo(latitudeArray, latitudeValueIndices, latitudeQuantityInfo, longitudeArray,
                    longitudeValueIndices, longitudeQuantityInfo, null, null, null, null);
        } catch (BMIModelException e) {
            throw new RuntimeException(e);
        }

    }

    public String getId() {
        return this.variableName;
    }

    public String getDescription() {
        return null;
    }

    public Role getRole() {
        return this.role;
    }

    public ITimeInfo getTimeInfo() {
        return new TimeInfo(getTimes());
    }

    public double[] getTimes() {
        //return current time, since BMI models only store the current values in memory.
        try {
            return new double[] { model.get_current_time() };
        } catch (BMIModelException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTimes(double[] times) {
        throw new RuntimeException(this.getClass().getName() + ": setting time stamps not supported for wflow model.");
    }

    public ValueType getValuesType() {
        return ValueType.IVectorType;
    }

    public Class<?> getValueType() {
        return IVector.class;
    }

    public Object getValues() {
        double[] values = getValuesAsDoubles();
        IVector vector = new Vector(values);
        //              return new TreeVector(getId(), vector, rowCount, columnCount);
        return vector;
    }

    /**
     * Returns only the current values, since the model only stores the current values in memory. The values of inactive grid
     * cells are converted to Double.NaN, because the algorithms cannot cope with inactive grid cells.
     */
    public double[] getValuesAsDoubles() {
        return getPerturbedValuesAsDoubles();
    }

    private double[] getPerturbedValuesAsDoubles() {
        final double stdDev = 0.001;
        double[] values = getOriginalValuesAsDoubles();
        double[] stdDevs = new double[values.length];
        for (int n = 0; n < values.length; n++) {
            stdDevs[n] = stdDev;
        }

        IVector mean = new Vector(values);
        IVector std = new Vector(stdDevs);
        IStochVector sv = new StochVector(mean, std);
        IVector perturbedValues = sv.createRealization();
        return perturbedValues.getValues();
    }

    private double[] getOriginalValuesAsDoubles() {
        try {
            return model.get_double(variableName);
        } catch (BMIModelException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Only changes the current values, since the model only stores the current values in memory. Only changes the values of the
     * active grid cells.
     */
    public void axpyOnValues(double alpha, double[] axpyValues) {
        double[] allValues = getOriginalValuesAsDoubles();
        
        for (int n = 0; n < allValues.length; n++) {
            //for all NaNs results in NaN
            allValues[n] += alpha * axpyValues[n];
        }
        setValuesAsDoubles(allValues);
    }

    /**
     * Only changes the current values, since the wflow model only stores the current values in memory. Only changes the values of
     * the active grid cells.
     */
    public void multiplyValues(double[] multiplicationFactors) {

        double[] allValues = getOriginalValuesAsDoubles();
        for (int n = 0; n < allValues.length; n++) {
            allValues[n] *= multiplicationFactors[n];
        }
        setValuesAsDoubles(allValues);
    }

    public void setValues(Object vector) {
        if (!(vector instanceof IVector)) {
            throw new IllegalArgumentException(this.getClass().getName() + ": supply values as an IVector not as "
                    + vector.getClass().getName());
        }

        setValuesAsDoubles(((IVector) vector).getValues());
    }

    /**
     * Sets only the current values, since the wflow model only stores the current values in memory. Sets the values of all grid
     * cells, also of the inactive grid cells. This is not a problem, because the wflow model ignores the values of the inactive
     * grid cells anyway.
     */
    public void setValuesAsDoubles(double[] values) {
        LOGGER.info("Setting " + values.length + " values in variable " + variableName);

        try {
            model.set_double(variableName, values);
        } catch (BMIModelException e) {
            throw new RuntimeException(e);
        }
    }

    public void copyValuesFromItem(IExchangeItem sourceItem) {
        throw new UnsupportedOperationException(getClass().getName() + ": not implemented");
    }

    public IQuantityInfo getQuantityInfo() {
        return this.quantityInfo;
    }

    public IGeometryInfo getGeometryInfo() {
        return this.geometryInfo;
    }
}
