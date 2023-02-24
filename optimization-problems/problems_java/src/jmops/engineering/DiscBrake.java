package jmops.engineering;


/**
 * DiscBrake Design Problem
 *
 * References:
 * <ol>
 * Ray, T., and Liew, K. M., 2002. 
 * A Swarm Metaphor for Multiobjective Design Optimization. 
 * Engineering Optimization, 34(2): 141-153.
 * 
 * </ol>
 */


public class DiscBrake{
    
    /**
     * Constructs the DiscBrake problem.
     */
    public DiscBrake() {
    }
    
    public static final int numberOfVariables = 4;
    public static final int numberOfObjectives = 2;
    public static final int numberOfConstraints = 5;
    
    public static double objectives[] = new double[numberOfObjectives];
    public static double constraints[] = new double[numberOfConstraints];
    
    public static final double lb[] = {55.0, 75.0, 1000.0, 2.0};
    public static final double ub[] = {80.0, 110.0, 3000.0, 20.0};
    
    //@Override
    public static void evaluate(double [] vars) 
    {
        vars[3] = Math.round(vars[3]); // integer variable
        
        double x1 = vars[0];
        double x2 = vars[1];
        double x3 = vars[2];
        double x4 = vars[3];  
        
        double f1 = 4.9e-5*(Math.pow(x2,2)-Math.pow(x1,2))*(x4-1.0);
        double f2 = 9.82e+6*(Math.pow(x2,2)-Math.pow(x1,2))/(x3*x4*(Math.pow(x2,3)-Math.pow(x1,3)));
        
        double g1 = 20.0-(x2-x1);
        double g2 = 2.5*(x4+1.0)-30.0;
        double g3 = x3/(3.14*(Math.pow(x2,2)-Math.pow(x1,2)))-0.4;
        double g4 = 2.22e-3*x3*(Math.pow(x2,3)-Math.pow(x1,3))/Math.pow(Math.pow(x2,2)-Math.pow(x1,2),2)-1.0;
        double g5 = 900.0-2.66e-2*x3*x4*(Math.pow(x2,3)-Math.pow(x1,3))/(Math.pow(x2,2)-Math.pow(x1,2));
                
        objectives[0] = f1;
        objectives[1] = f2;
        
        constraints[0] = g1;
        constraints[1] = g2;
        constraints[2] = g3;
        constraints[3] = g4;
        constraints[4] = g5;
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
