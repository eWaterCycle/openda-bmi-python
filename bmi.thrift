namespace java nl.esciencecenter.openda.bmi.thrift
namespace py bmi.thrift

/*
 * BMI thrift file. Specifically written to match the python BMI interface 
 * (https://github.com/csdms/bmi/blob/master/bindings/python/bmi/BMI.py)
 * from the csdms project (https://github.com/csdms/bmi)
 */

enum BmiGridType {
    UNKNOWN = 0,
    UNIFORM = 1,
    RECTILINEAR = 2,
    STRUCTURED = 3,
    UNSTRUCTURED = 4
}

exception ModelException {

    1: required string message
    
}

service BMI {

    void initialize(1:string file) throws (1:ModelException error),
    
    void update();
    
    void update_until(1:double time);
    
    void finalize();
    
    void run_model();

    string get_component_name();
    
    list<string> get_input_var_names();
    
    list<string> get_output_var_names();

    string get_var_type (1:string long_var_name);

    string get_var_units (1:string long_var_name);

    i32 get_var_rank (1:string long_var_name);

    double get_start_time();
    
    double get_end_time();
    
    double get_current_time();

    binary get_value(1:string long_var_name);

    binary get_value_at_indices(1:string long_var_name, 2:list<i32> inds);
 
    void set_value (1:string long_var_name, 2:binary src);
    
    void set_value_at_indices (1:string long_var_name, 2:list<i32> inds, 3:binary src);
    
    BmiGridType get_grid_type(1:string long_var_name);
}

service BmiRaster extends BMI {

    list<i32> get_grid_shape(1:string long_var_name);
    
    list<double> get_grid_spacing(1:string long_var_name);
    
    list<double> get_grid_origin(1:string long_var_name);
    
}

service BmiRectilinear extends BMI {
    list<i32> get_grid_shape(1:string long_var_name);

    list<double> get_grid_x(1:string long_var_name);
    
    list<double> get_grid_y(1:string long_var_name);
    
    list<double> get_grid_z(1:string long_var_name);
    
}

service BmiStructured extends BMI {
    list<i32> get_grid_shape(1:string long_var_name);

    list<double> get_grid_x(1:string long_var_name);
    
    list<double> get_grid_y(1:string long_var_name);
    
    list<double> get_grid_z(1:string long_var_name);

}

service BmiUnstructured extends BMI {
    list<double> get_grid_x(1:string long_var_name);
    
    list<double> get_grid_y(1:string long_var_name);
    
    list<double> get_grid_z(1:string long_var_name);

    list<i32> get_grid_connectivity(1:string long_var_name);

    list<i32> get_grid_offset(1:string long_var_name);

}
