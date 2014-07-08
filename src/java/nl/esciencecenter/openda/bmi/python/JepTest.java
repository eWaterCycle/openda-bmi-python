package nl.esciencecenter.openda.bmi.python;

import jep.Jep;
import jep.JepException;


public class JepTest {

    public static void main(String[] args) throws JepException {
        Jep jep = new Jep();
        
        
        String theString = "some string";
        
        jep.set("query", theString);
        
        jep.eval("print query");
        
        // TODO Auto-generated method stub

    }

}
