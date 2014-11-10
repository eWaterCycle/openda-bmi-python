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

import java.io.IOException;

import nl.esciencecenter.bmi.openda.BMIPythonModelInstance;
import nl.esciencecenter.bmi.thrift.ModelException;

import org.apache.thrift.TException;
import org.junit.Test;

public class BMIPythonModelInstanceTest {

    @Test
    public void testBMIPythonModelInstance() throws IOException, ModelException, TException {
        new BMIPythonModelInstance();
    }

    @Test
    public void testGetExchangeItemIDs() throws IOException, ModelException, TException {
        BMIPythonModelInstance instance = new BMIPythonModelInstance();
        
        String[] ids = instance.getExchangeItemIDs();
        
        String[] expected = {"surface_elevation"};
        
        assertArrayEquals(expected, ids);
        
        instance.finish();
    }

    @Test
    public void testGetExchangeItemIDsRole() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetDataObjectExchangeItem() {
        fail("Not yet implemented");
    }

    @Test
    public void testFinish() {
        fail("Not yet implemented");
    }

    @Test
    public void testInitialize() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetParent() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetExchangeItem() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetTimeHorizon() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetCurrentTime() {
        fail("Not yet implemented");
    }

    @Test
    public void testCompute() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetObservedLocalization() {
        fail("Not yet implemented");
    }

    @Test
    public void testSaveInternalState() {
        fail("Not yet implemented");
    }

    @Test
    public void testRestoreInternalState() {
        fail("Not yet implemented");
    }

    @Test
    public void testReleaseInternalState() {
        fail("Not yet implemented");
    }

    @Test
    public void testLoadPersistentState() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetModelRunDir() {
        fail("Not yet implemented");
    }

}
