classdef testClass < testSuperClass
    %properties needed at runTime, which can be querried with a get command
    properties
        modelSize
        output
    end
    %proerties that are constant for all model instances
    properties (Constant)
        testConst=pi;
    end
    %properties needed at runTime, which can NOT be querried with a get command
    properties (GetAccess = 'private', SetAccess = 'private')
        multFactor;
    end
    
    %the following is the heart of the BMI class. Every BMI model needs to
    %implement these methods.
    methods
        %constructor
        function obj = testClass(modelSize)
            if nargin > 0
                obj.modelSize=modelSize;
            else
                obj.modelSize=2;
            end
        end
        
        function initialize(obj,fileName)
            obj.time=0;
            obj.timeStep=1;
            obj.output=zeros(obj.modelSize,1);
            obj.multFactor=2;
        end
        function update(obj)
            obj.output=obj.output+randn(obj.modelSize,1)*obj.testConst*obj.multFactor;
            obj.time = obj.time + obj.timeStep;
        end
        
    end
    
end
%
%     def initialize (self, file):
%         pass
%     def update (self):
%         pass
%     def update_until (self, time):
%         pass
%     def finalize (self):
%         pass
%     def run_model (self):
%         pass
%
%     def get_var_type (self, long_var_name):
%         pass
%     def get_var_units (self, long_var_name):
%         pass
%     def get_var_rank (self, long_var_name):
%         pass
%
%     def get_value (self, long_var_name):
%         pass
%     def get_value_at_indices (self, long_var_name, inds):
%         pass
%     def set_value (self, long_var_name, src):
%         pass
%     def set_value_at_indices (self, long_var_name, inds, src):
%         pass
%
%     def get_component_name (self):
%         pass
%     def get_input_var_names (self):
%         pass
%     def get_output_var_names (self):
%         pass
%
%     def get_start_time (self):
%         pass
%     def get_end_time (self):
%         pass
%     def get_current_time (self):
%         pass
%
