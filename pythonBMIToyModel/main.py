#! /usr/bin/env python


import numpy as np
import BMIToyModel

#make instance of BMIToyModel
toy=BMIToyModel.BMIToyModel ()

#initialize and ask for varlists
toy.initialize ()

print "component name: "
print toy.get_component_name()
print "input variables: "
print toy.get_input_var_names()
print "output variables: "
print toy.get_output_var_names()
print "Variable type of 'var1'"
print toy.get_var_type('var1')
print "Unit type of 'var1'"
print toy.get_var_units('var1')
print "Rank of 'var1'"
print toy.get_var_rank('var1')
print "Value of 'var1':"
print toy.get_value ('var1')
print "Value of 'var1' at indeces [4,4]"
print toy.get_value_at_indices('var1',(4,4))

print "Start time, end time and current time:"
print toy.get_start_time()
print toy.get_end_time()
print toy.get_current_time()


#change 1 value
print "changing value at [4,4] to 3"
toy.set_value_at_indices ('var1', (4,4), 3)
print "Value of 'var1'"
print toy.get_value ('var1')

#update 1 timestep and check values
print "updateing 1 timestep"
toy.update()
print "Value of 'var1'"
print toy.get_value ('var1')

#update 5 steps
print "updating until timestep 6"
toy.update_until(6)
print "Value of 'var1'"
print toy.get_value ('var1')
print "setting value of 'var1' to 2" 
toy.set_value('var1',np.zeros([10,10])+2)
print "Value of 'var1'"
print toy.get_value ('var1')


#finaliz
print "finalize model"
toy.finalize()
print "Value of 'var1'"
print toy.get_value ('var1')
