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

/*

	std::string get_var_type (std::string);
      std::string get_var_units (std::string);
      int get_var_rank (std::string);

      double get_current_time ();
      double get_start_time ();
      double get_end_time ();
      double get_time_step ();
      std::string get_time_units ();

      double *get_double (std::string, double *);
      double *get_double_at_indices (std::string, double *, int *, int);

      void *get_value (std::string, void *);
      void *get_value_ptr (std::string);
      void *get_value_at_indices (std::string, void *, int *, int);

      void set_double (std::string, double *);
      void set_double_at_indices (std::string, int *, int, double *);

      Grid_type get_grid_type (std::string long_var_name);

      void get_grid_shape (std::string, int *);
      void get_grid_spacing (std::string, double *);
      void get_grid_origin (std::string, double *);

      double * get_grid_x (std::string, int&);
      double * get_grid_y (std::string, int&);
      double * get_grid_z (std::string, int&);

      int * get_grid_connectivity (std::string, int &);
      int * get_grid_offset (std::string, int &);

class BMI (object):
    def initialize (self, file):
        pass
    def update (self):
        pass
    def update_until (self, time):
        pass
    def finalize (self):
        pass
    def run_model (self):
        pass

    def get_var_type (self, long_var_name):
        pass
    def get_var_units (self, long_var_name):
        pass
    def get_var_rank (self, long_var_name):
        pass

    def get_value (self, long_var_name):
        pass
    def get_value_at_indices (self, long_var_name, inds):
        pass
    def set_value (self, long_var_name, src):
        pass
    def set_value_at_indices (self, long_var_name, inds, src):
        pass

    def get_component_name (self):
        pass
    def get_input_var_names (self):
        pass
    def get_output_var_names (self):
        pass

    def get_start_time (self):
        pass
    def get_end_time (self):
        pass
    def get_current_time (self):
        pass

class BmiRaster (BMI):
    def get_grid_shape (self, long_var_name):
        pass
    def get_grid_spacing (self, long_var_name):
        pass
    def get_grid_origin (self, long_var_name):
        pass

class BmiRectilinear (BMI):
    def get_grid_shape (self, long_var_name):
        pass
    def get_grid_x (self, long_var_name):
        pass
    def get_grid_y (self, long_var_name):
        pass
    def get_grid_z (self, long_var_name):
        pass

class BmiStructured (BMI):
    def get_grid_shape (self, long_var_name):
        pass
    def get_grid_x (self, long_var_name):
        pass
    def get_grid_y (self, long_var_name):
        pass
    def get_grid_z (self, long_var_name):
        pass

class BmiUnstructured (BMI):
    def get_grid_x (self, long_var_name):
        pass
    def get_grid_y (self, long_var_name):
        pass
    def get_grid_z (self, long_var_name):
        pass
    def get_grid_connectivity (self, long_var_name):
        pass
    def get_grid_offset (self, long_var_name):
        pass
        
*/