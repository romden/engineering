
import org.apache.commons.math3.analysis.MultivariateFunction;

// class defining obj fucntion
class ObjFunction implements MultivariateFunction
{
    
    WWTP wwtp = new WWTP();
    
    public ObjFunction()
    {
        
    }
    
    @Override
    public double value(double[] x)
    {
        double [][] c = wwtp.constraints(x);
        
        double val = 0;
        
        for(int i = 0; i < c[0].length; i++){
            val += Math.max(c[0][i], 0);
        }
        
        for(int i = 0; i < c[1].length; i++){
            val += Math.abs(c[1][i]);
        }
        
        return val;
    }
}