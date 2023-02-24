package jmops.engineering;


/**
 * BarTruss4 Design Problem
 *
 * References:
 * <ol>
 * Ray, T., and Liew, K. M., 2002. 
 * A Swarm Metaphor for Multiobjective Design Optimization. 
 * Engineering Optimization, 34(2): 141-153.
 * 
 * </ol>
 */



public class BarTruss4{
    
    /**
     * Constructs the BarTruss4 problem.
     */
    public BarTruss4() {
    }
    
    
    public static final int numberOfVariables = 4;
    public static final int numberOfObjectives = 2;
    public static final int numberOfConstraints = 0;
    
    public static double objectives[] = new double[numberOfObjectives];
    public static double constraints[] = new double[numberOfConstraints];
    
    public static final double F = 10.0, E = 2.0E+5, L = 200.0, sigma = 10.0;
    
    public static final double lb[] = {F/sigma, Math.sqrt(2.0)*F/sigma, Math.sqrt(2.0)*F/sigma, F/sigma};
    public static double ub[] = {3.0*F/sigma, 3.0*F/sigma, 3.0*F/sigma, 3.0*F/sigma};   
    
    
    //@Override
    public static void evaluate(final double [] vars)
    {
        double x1 = vars[0];
        double x2 = vars[1];
        double x3 = vars[2];
        double x4 = vars[3];
        
        double f1 = L*(2.0*x1+Math.sqrt(2.0)*x2+Math.sqrt(2.0)*x3+x4);
        double f2 = F*L/E*(2.0/x1+2.0*Math.sqrt(2.0)/x2-2.0*Math.sqrt(2.0)/x3+2.0/x4);
        
        objectives[0] = f1;
        objectives[1] = f2;
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
