package jmops.engineering;


/**
 * BarTruss2 Design Problem
 *
 * References:
 * <ol>
 *  
 * T. Ray, K. Tai, and K. C. Seow. (2001). 
 * Multiobjective design optimization by an evolutionary algorithm. 
 * Engineering Optimization, 33(4) 399-424.
 * 
 * Rao, S. S. (1987). Game theory approach for multiobjective structural optimization.
 * Computers md Structures. 26(1). 119- 127.
 * </ol>
 */


public class BarTruss2{
    
    /**
     * Constructs the BarTruss2 problem.
     */
    public BarTruss2() {
        
    }
    
    public static final int numberOfVariables = 2;
    public static final int numberOfObjectives = 2;
    public static final int numberOfConstraints = 2;
    
    public static final double lb[] = {0.10, 0.50};
    public static final double ub[] = {2.25, 2.50};
    
    public static double objectives[] = new double[numberOfObjectives];
    public static double constraints[] = new double[numberOfConstraints];
    
    //@Override
    public static void evaluate(final double [] vars) 
    {
        double x1 = vars[0];
        double x2 = vars[1];
        
        double rho = 0.283, h = 100.0, P = 10.0e+4, E = 3.0e+7, sigma0 = 2.0e+4, Amin = 1.0;
        
        double f1 = 2.0*rho*h*x2*Math.sqrt(1+x1*x1);
        double f2 = P*h*Math.pow(1.0+x1*x1,1.5)*Math.pow(1.0+x1*x1*x1*x1,0.5)/(2*Math.sqrt(2)*E*x1*x1*x2);
        
        double g1 = P*(1.0+x1)*Math.pow(1.0+x1*x1,0.5)/(2.0*Math.sqrt(2.0)*x1*x2)-sigma0;
        double g2 = P*(1.0-x1)*Math.pow(1.0+x1*x1,0.5)/(2.0*Math.sqrt(2.0)*x1*x2)-sigma0;
        
        objectives[0] = f1;
        objectives[1] = f2;
        
        constraints[0] = g1;
        constraints[1] = g2;
    }   
    
    
    public static double [] getObjectives(){
        return objectives;
    }
    
    public static double [] getConstraints(){
        return constraints;
    }
 
    public static double [] getLB(){
        return lb;
    }
    
    public static double [] getUB(){
        return ub;
    }
}
