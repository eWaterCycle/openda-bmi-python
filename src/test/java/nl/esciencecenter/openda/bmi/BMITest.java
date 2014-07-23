package nl.esciencecenter.openda.bmi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import nl.esciencecenter.openda.bmi.thrift.BmiRaster;
import nl.esciencecenter.openda.bmi.thrift.ModelException;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.openda.interfaces.IModelInstance;
import org.openda.interfaces.IPrevExchangeItem.Role;

public class BMITest {

    public static void main(String[] args) throws Exception {
        //TTransport transport = new TSocket("localhost", 9090);
        //transport.open();
        
        IModelInstance instance = new BMIPythonModelInstance();
        
        System.err.println(Arrays.toString(instance.getExchangeItemIDs()));
        
        System.err.println(Arrays.toString(instance.getExchangeItemIDs(Role.InOut)));
        
        System.err.println(Arrays.toString(instance.getExchangeItemIDs(Role.Input)));
        
        System.err.println(Arrays.toString(instance.getExchangeItemIDs(Role.Output)));
        
        instance.finish();
//        
//        System.err.println(client.get_input_var_names ());
//        System.err.println(client.get_output_var_names ());
//
//        String var_name = "surface_elevation";
//
//        System.err.println(client.get_grid_type(var_name));
//
//        System.err.println(client.get_grid_shape (var_name));
//        System.err.println(client.get_grid_spacing (var_name));
//        System.err.println(client.get_grid_origin (var_name));
//
//        System.err.println(client.get_start_time ());
//        System.err.println(client.get_current_time ());
//        System.err.println(client.get_end_time ());
//
//        client.update ();
//        System.err.println(client.get_current_time ());
//
//        ByteBuffer z = client.get_value (var_name);
//        
//        IntBuffer ints = z.asIntBuffer();
//        
//        System.err.println(ints);
//        
//        while(ints.hasRemaining()) {
//            System.err.print(ints.get() + ", ");
//        }
//        System.err.println();
//
//        client.update_until (50.);
//        System.err.println(client.get_current_time ());
//
//        z = client.get_value (var_name);
//        
//        ints = z.asIntBuffer();
//        
//        System.err.println(ints);
//        while(ints.hasRemaining()) {
//            System.err.print(ints.get() + ", ");
//        }
//        System.err.println();
//
//        
//        transport.close();
    }

}
