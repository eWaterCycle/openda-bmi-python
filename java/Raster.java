public class Raster {
    private float[][] data;
    
    public Raster(int x,int y){
	data = new float[x][y];
    }

    public void setScalar(int input){
	for (int xCounter=0; xCounter<data.size(); xCounter++){
	    for (int yCounter=0; yCounter<data[xCounter].size(); yCounter++){
		    data[xCounter][yCounter]=input;
	    }
	}
    }

    public void setAtIndices(int input, int x, int y){
	data[x][y]=input;
    }

    public void addScalar(int input){
	for (int xCounter=0; xCounter<data.size(); xCounter++){
	    for (int yCounter=0; yCounter<data[xCounter].size(); yCounter++){
		    data[xCounter][yCounter]=data[xCounter][yCounter]+input;
	    }
	}
    }
    
}