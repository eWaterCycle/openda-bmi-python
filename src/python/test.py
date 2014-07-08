'''
Created on Jul 7, 2014

@author: niels
'''
import numpy as np
from BMI import BMI
from BMI_impl import Model

def main ():
    c = Model()

    c.initialize ()

    print c.get_input_var_names ()
    print c.get_output_var_names ()

    var_name = 'surface_elevation'

    print c.get_grid_type(var_name)

    print c.get_grid_shape (var_name)
    print c.get_grid_spacing (var_name)
    print c.get_grid_origin (var_name)

    print c.get_start_time ()
    print c.get_current_time ()
    print c.get_end_time ()

    c.update ()
    print c.get_current_time ()

    z = c.get_value (var_name)
    print z

    c.update_until (3.)
    print c.get_current_time ()

    print z


if __name__ == '__main__':
    main()