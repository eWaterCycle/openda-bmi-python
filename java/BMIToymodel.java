  
public class BMIToyModel extends BMI.Iface {
    private float dt;
    private float t;
    private float startTime;
    private float endTime;
    private int[] shape;
    private float[] spacing;
    private float[] origin;
    private Raster[] state;
    private Map<String,returnValue> value;
    
    private Map<String,String> var_units;
    private string name = "Example java toy Model";
    private String input_var_names[] = {"var1"};
    private String output_var_names[] = {"var1"};

    public void initialize(String file) throws ModelException, org.apache.thrift.TException {
        dt=1.;
      	t = 0.;
        startTime = 1.;
        endTime = 20.;

	int shape[] = {10,10};

	float spacing[] = {1.,1.};

	float origin[] = {0.,0.};

    	state = new Raster(shape[0],shape[1]);
        state.setScalar(startTime);
			   
	var_units = new HashMap<String,String>();
	var_units.put("var1","unit1");
    

	value = new HashMap<String,returnValue>();
	value.put("var1","state");

    };

    public void update() throws org.apache.thrift.TException {
	if (t >= endTime){
	    throw("endTime already reached, model not updated");
	};
        state.addScalar(1);
        t += dt;

};

    public void update_until(double time) throws org.apache.thrift.TException {
        if ((time<t) | (time>endTime)){
	    throw("wrong time input: smaller than model time or larger than endTime");
	};
        while (t < time){
	        update();
        };
};


    public void finalize() throws org.apache.thrift.TException {
	dt=0;
	t=0;
	state = new Raster[0][0];
};

    public void run_model() throws org.apache.thrift.TException;

    public String get_component_name() throws org.apache.thrift.TException;

    public List<String> get_input_var_names() throws org.apache.thrift.TException {
	return input_var_names;
    };

    public List<String> get_output_var_names() throws org.apache.thrift.TException{
	return output_var_names;
    };

    public String get_var_type(String long_var_name) throws org.apache.thrift.TException{
	return this.getValue(long_var_name).dtype;
    };

    public String get_var_units(String long_var_name) throws org.apache.thrift.TException{
	return var_units.get(long_var_name);
    };

    public int get_var_rank(String long_var_name) throws org.apache.thrift.TException{
	return this.getValue(long_var_name).size;
    };

    public double get_start_time() throws org.apache.thrift.TException {
	return startTime;
    };

    public double get_end_time() throws org.apache.thrift.TException{
	return endTime;
    };

    public double get_current_time() throws org.apache.thrift.TException{
	return t;
    };

    public ByteBuffer get_value(String long_var_name) throws org.apache.thrift.TException;

    public ByteBuffer get_value_at_indices(String long_var_name, List<Integer> inds) throws org.apache.thrift.TException;

    public void set_value(String long_var_name, ByteBuffer src) throws org.apache.thrift.TException;

    public void set_value_at_indices(String long_var_name, List<Integer> inds, ByteBuffer src) throws org.apache.thrift.TException;

    public BmiGridType get_grid_type(String long_var_name) throws org.apache.thrift.TException;

  }
