classdef testSuperClass < handle
    %properties needed at runTime, which can be querried with a get command
    properties
        time
        timeStep
    end
    
    methods
        function update(obj)
            error('not implemented')
        end
        function updateUntil(obj,endTime)
            while obj.time < endTime
                obj.update;
            end
        end
    end
    
end