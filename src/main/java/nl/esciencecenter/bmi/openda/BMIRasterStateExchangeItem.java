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

import org.openda.blackbox.config.BBUtils;
import org.openda.exchange.ArrayGeometryInfo;
import org.openda.exchange.NetcdfGridTimeSeriesExchangeItem;
import org.openda.exchange.QuantityInfo;
import org.openda.exchange.TimeInfo;
import org.openda.exchange.timeseries.TimeUtils;
import org.openda.interfaces.IArray;
import org.openda.interfaces.IExchangeItem;
import org.openda.interfaces.IGeometryInfo;
import org.openda.interfaces.IPrevExchangeItem;
import org.openda.interfaces.IQuantityInfo;
import org.openda.interfaces.ITime;
import org.openda.interfaces.ITimeInfo;
import org.openda.interfaces.IVector;
import org.openda.utils.Time;
import org.openda.utils.Vector;

/**
 * Exchange item representing a 2D map with values for the wflow model.
 *
 * @author Arno Kockx
 */
public class BMIRasterStateExchangeItem implements IExchangeItem {
    private final String variableName;
    private final IPrevExchangeItem.Role role;
    private final ITime timeHorizon;
    private final BMIRaster model;
    private final IQuantityInfo quantityInfo;
    private final IGeometryInfo geometryInfo;

    private final int activeGridCellCount;

    /**
     * @param variableName
     *            the name of the variable as used by the wflow model.
     * @param role
     * @param timeHorizon
     * @param adapter
     * @throws BMIModelException
     */
    public BMIRasterStateExchangeItem(String variableName, IPrevExchangeItem.Role role, ITime timeHorizon, BMIRaster model)
            throws BMIModelException {
        this.variableName = variableName;
        this.role = role;

        this.quantityInfo = new QuantityInfo(variableName, model.get_var_units(variableName));

        this.geometryInfo = createGeometryInfo();

        this.timeHorizon = timeHorizon;
        this.model = model;

    }

    /**
     * @return
     */
    private IGeometryInfo createGeometryInfo() {
        double upperLeftCenterX = this.model.get_grid_origin(variableName)[0];
        double upperLeftCenterY = this.model.get_grid_origin(variableName)[1];
        
        double cellWidth = this.model.get_grid_spacing(variableName)[0];
        double cellHeight = this.model.get_grid_spacing(variableName)[1];
        int rowCount = this.adapter.getRowCount();
        int columnCount = this.adapter.getColumnCount();
        int[] activeGridCellMask = BBUtils.toIntArray(this.adapter.getMapAsList(WFLOW_MASK_VARIABLE_NAME));

        //this code assumes that grid values in wflow start at upperLeft corner, then contain the first row from left to right,
        //then the second row from left to right, etc. Also see org.openda.model_wflow.WflowModelFactory.createInputDataObjects.
        double[] xValues = new double[columnCount];
        for (int n = 0; n < xValues.length; n++) {
            xValues[n] = upperLeftCenterX + n * cellWidth;
        }
        double[] yValues = new double[rowCount];
        for (int n = 0; n < yValues.length; n++) {
            yValues[n] = upperLeftCenterY - n * cellHeight;
        }
        
        //here for the purpose of writing to Netcdf the geometryInfo needs all grid cell coordinates and activeGridCellMask.
        IArray latitudeArray = new Array(yValues);
        IArray longitudeArray = new Array(xValues);
        IQuantityInfo latitudeQuantityInfo = new QuantityInfo("y coordinate according to model coordinate system", "meter");
        IQuantityInfo longitudeQuantityInfo = new QuantityInfo("x coordinate according to model coordinate system", "meter");
        return new ArrayGeometryInfo(latitudeArray, null, latitudeQuantityInfo, longitudeArray, null, longitudeQuantityInfo,
                null, null, null, activeGridCellMask);
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
        //return current time, since the wflow model only stores the current values in memory.
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
        //		return new TreeVector(getId(), vector, rowCount, columnCount);
        return vector;
    }

    /**
     * Returns only the current values, since the model only stores the current values in memory. The values of inactive
     * grid cells are converted to Double.NaN, because the algorithms cannot cope with inactive grid cells.
     */
    public double[] getValuesAsDoubles() {
        try {
            return model.get_double(variableName);
        } catch (BMIModelException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Only changes the current values, since the model only stores the current values in memory. Only changes the values of
     * the active grid cells.
     */
    public void axpyOnValues(double alpha, double[] axpyValues) {
        double[] allValues = getValuesAsDoubles();
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

        double[] allValues = getValuesAsDoubles();
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
