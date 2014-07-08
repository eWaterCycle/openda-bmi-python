package nl.esciencecenter.openda.bmi.python;

import nl.esciencecenter.openda.bmi.thrift.BmiRaster;
import nl.esciencecenter.openda.bmi.thrift.ModelException;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class BMITest {

    public static void main(String[] args) throws TException, ModelException {
        //TTransport transport = new TSocket("localhost", 9090);
        //transport.open();
        
        TTransport transport = new TSocket("localhost", 9090);
        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);
        BmiRaster.Client client = new BmiRaster.Client(protocol);

        client.initialize("thefile.txt");
        
        
        
        transport.close();
    }

}
