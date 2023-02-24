
import java.util.Arrays;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.PointValuePair;
import org.apache.commons.math3.optimization.direct.BOBYQAOptimizer;




public class Main
{
    static Matrix mx = new Matrix();
    static WWTP wwtp = new WWTP();
    static ObjFunction objFunction = new ObjFunction();
    
    public static void main(String[] args)
    {
        
//        System.out.println("WWTP-ATV-DE");
//        
//        double [] x = mx.input("x.dat", 115);
//        
//        double [][] c = wwtp.constraints(x);
//        
//         mx.output("ceq.dat", c[1]);
        
        
        // initial point
        double [] x0 = mx.input("x0.dat", 115);
        
        // lower and upper bounds
        double [] lb = mx.input("lb.dat", 115);
        double [] ub = mx.input("ub.dat", 115);

        
        // parameter settings for solver
        int numberOfInterpolationPoints = 2*x0.length+1;
        double initialTrustRegionRadius = 1;//0.5;
        double stoppingTrustRegionRadius = 1e-8;
        int  maxEval = 50000;
        
        // create object
        BOBYQAOptimizer babyao = new BOBYQAOptimizer(numberOfInterpolationPoints,
                initialTrustRegionRadius,
                stoppingTrustRegionRadius);
        
        // perform optimization
        PointValuePair result = babyao.optimize(maxEval, objFunction, GoalType.MINIMIZE, x0, lb, ub);
        
        // get results
        double [] x = result.getPoint();
   
        mx.output("x.dat", x);
        
    }
    
}
