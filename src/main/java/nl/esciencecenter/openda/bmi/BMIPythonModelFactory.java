package nl.esciencecenter.openda.bmi;

import java.io.File;

import org.openda.blackbox.interfaces.IModelFactory;
import org.openda.interfaces.IModelInstance;
import org.openda.interfaces.IStochModelFactory.OutputLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BMIPythonModelFactory implements IModelFactory {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BMIPythonModelFactory.class);
    
    @Override
    public void initialize(File workingDir, String[] arguments) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public IModelInstance getInstance(String[] arguments, OutputLevel outputLevel) {
        try {
            return new BMIPythonModelInstance();
        } catch (Exception e){
            LOGGER.error("failed to create instance", e);
            return null;
        }
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        
    }
}
