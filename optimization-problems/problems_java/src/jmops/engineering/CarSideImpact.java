package jmops.engineering;

/**
 * The CarSideImpact problem
 *
 * References:
 * <ol>
 * Jain, H., and Deb, K., (2014). 
 * An Evolutionary Many-Objective Optimization Algorithm Using Reference-Point-Based Nondominated Sorting Approach, 
 * Part II: Handling Constraints and Extending to an Adaptive Approach. 
 * IEEE Transactions on Evolutionary Computation, 18 (4): 577–601. 
 * </ol>
 */


public class CarSideImpact{
    
    /**
     * Constructs the WaterResourcePlanning problem.
     */
    public CarSideImpact() {
        
    }
    
    public static final int numberOfVariables = 7;
    public static final int numberOfObjectives = 3;
    public static final int numberOfConstraints = 10;
    
    public static double objectives[] = new double[numberOfObjectives];
    public static double constraints[] = new double[numberOfConstraints];
    
    public static final double lb[] = {0.5, 0.45, 0.5, 0.5, 0.875, 0.4, 0.4};
    public static final double ub[] = {1.5, 1.35, 1.5, 1.5, 2.625, 1.2, 1.2};
    
    
    //@Override
    public static void evaluate(final double [] vars)
    {
        double x1 = vars[0];
        double x2 = vars[1];
        double x3 = vars[2];
        double x4 = vars[3];
        double x5 = vars[4];
        double x6 = vars[5];
        double x7 = vars[6];
        
        double F = 4.72-0.5*x4-0.19*x2*x3;
        double Vmbp = 10.58-0.674*x1*x2-0.67275*x2;
        double Vfd = 16.45-0.489*x3*x7-0.843*x5*x6;
        
        double f1 = 1.98+4.9*x1+6.67*x2+6.98*x3+4.01*x4+1.78*x5+0.00001*x6+2.73*x7;
        double f2 = F;
        double f3 = 0.5*(Vmbp+Vfd);
        
        double g1 = 1.16-0.3717*x2*x4-0.0092928*x3-1.0;
        double g2 = 0.261-0.0159*x1*x2-0.06486*x1-0.019*x2*x7+0.0144*x3*x5+0.0154464*x6-0.32;
        double g3 = 0.214+0.00817*x5-0.045195*x1-0.0135168*x1+0.03099*x2*x6-0.018*x2*x7+0.007176*x3+0.023232*x3-0.00364*x5*x6-0.018*x2*x2-0.32;
        double g4 = 0.74-0.61*x2-0.031296*x3-0.031872*x7+0.227*x2*x2-0.32;
        double g5 = 28.98+3.818*x3-4.2*x1*x2+1.27296*x6-2.68065*x7-32.0;
        double g6 = 33.86+2.95*x3-5.057*x1*x2-3.795*x2-3.4431*x7+1.45728-32.0;
        double g7 = 46.36-9.9*x2-4.4505*x1-32.0;
        double g8 = F-4.0;
        double g9 = Vmbp-9.9;
        double g10 = Vfd-15.7;
        
        objectives[0] = f1;
        objectives[1] = f2;
        objectives[2] = f3;
        
        constraints[0] = g1;
        constraints[1] = g2;
        constraints[2] = g3;
        constraints[3] = g4;
        constraints[4] = g5;
        constraints[5] = g6;
        constraints[6] = g7;
        constraints[7] = g8;
        constraints[8] = g9;
        constraints[9] = g10;
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
