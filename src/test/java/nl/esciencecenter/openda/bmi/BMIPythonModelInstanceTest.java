package nl.esciencecenter.openda.bmi;

import static org.junit.Assert.*;

import java.io.IOException;

import nl.esciencecenter.openda.bmi.thrift.ModelException;

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
