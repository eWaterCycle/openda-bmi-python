'''
Created on Jul 8, 2014

@author: niels
'''

from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

from bmi.thrift.BmiRaster import Iface
from bmi.thrift.BmiRaster import Processor
from BMI_impl import Model
from bmi.thrift.ttypes import ModelException, BmiGridType
import sys
import signal

class RasterModelHandler(Iface):

    def __init__(self, model):
        self.model = model

    def initialize(self, config_file):
        """
        Parameters:
         - file
        """
        
        if config_file is None or config_file == "":
            config_file = None
        
        try:
            model.initialize(config_file)
        except Exception as e:
            raise ModelException(str(e)) 
    
    def update(self):
        model.update()
    
    def update_until(self, time):
        """
        Parameters:
         - time
        """
        model.update_until(time)
    
    def finalize(self):
        model.finalize()
        
        #raise TTransport.TTransportException("end of model, shut down server")
    
    def run_model(self):
        model.run_model()
    
    def get_component_name(self):
        return model.get_component_name()
    
    def get_input_var_names(self):
        return model.get_input_var_names()
    
    def get_output_var_names(self):
        return model.get_output_var_names()
    
    def get_var_type(self, long_var_name):
        """
        Parameters:
         - long_var_name
        """
        return model.get_var_type(long_var_name)
    
    def get_var_units(self, long_var_name):
        """
        Parameters:
         - long_var_name
        """
        return model.get_var_units(long_var_name)
    
    def get_var_rank(self, long_var_name):
        """
        Parameters:
         - long_var_name
        """
        return model.get_var_rank(long_var_name)
    
    def get_start_time(self):
        return model.get_start_time()
    
    def get_end_time(self):
        return model.get_end_time()
    
    def get_current_time(self):
        return model.get_current_time()
    
    def get_value(self, long_var_name):
        """
        Parameters:
         - long_var_name
        """
        return model.get_value(long_var_name).tostring()

    def get_grid_type(self, long_var_name):
        """
        Parameters:
         - long_var_name
        """
        result = model.get_grid_type(long_var_name)
        
        print result
        
        return result
    
    def get_grid_shape(self, long_var_name):
        """
        Parameters:
         - long_var_name
        """
        return model.get_grid_shape(long_var_name)

    def get_grid_spacing(self, long_var_name):
        """
        Parameters:
         - long_var_name
        """
        return model.get_grid_spacing(long_var_name)

    def get_grid_origin(self, long_var_name):
        """
        Parameters:
         - long_var_name
        """
        return model.get_grid_origin(long_var_name)
    
    
def handleSIGINT(sig, frame):
    #clean up state or what ever is necessary
    sys.exit(0)
    

if __name__ == '__main__':
    
    print sys.argv
    
    model = Model()
    
    handler = RasterModelHandler(model)
    processor = Processor(handler)
    
    transport = TSocket.TServerSocket(port=sys.argv[1])
    
    tfactory = TTransport.TBufferedTransportFactory()
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()
    
    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)
    
    signal.signal(signal.SIGINT, handleSIGINT)
    
    print server
    
    server.serve()
    
    print "done"
