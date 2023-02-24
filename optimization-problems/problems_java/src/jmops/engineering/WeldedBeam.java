package jmops.engineering;


/**
 * The WeldedBeam problem
 * 
 * References:
 * <ol>
 * 
 * K. Deb, A. Pratap, S. Moitra. (2000).
 * Mechanical Component Design for Multiple Objectives Using Elitist Non-Dominated Sorting GA.
 * Parallel Problem Solving from Nature PPSN VI.
 * Volume 1917 of the series Lecture Notes in Computer Science pp 859-868.
 * 
 * Ray, T., and Liew, K. M., 2002. 
 * A Swarm Metaphor for Multiobjective Design Optimization. 
 * Engineering Optimization, 34(2): 141-153.
 * 
 * </ol>
 */

public class WeldedBeam{
    
    /**
     * Constructs the WeldedBeam problem.
     */
    public WeldedBeam() {
        
    }
    
    
    public static final int numberOfVariables = 4;
    public static final int numberOfObjectives = 2;
    public static final int numberOfConstraints = 5;
    
    public static double objectives[] = new double[numberOfObjectives];
    public static double constraints[] = new double[numberOfConstraints];
    
    public static final double lb[] = {0.125, 0.1, 0.1, 0.125};
    public static final double ub[] = {5.0, 10.0, 10.0, 5.0};
    
    
    //@Override
    public static void evaluate(final double [] vars)
    {
        double x1 = vars[0];
        double x2 = vars[1];
        double x3 = vars[2];
        double x4 = vars[3];
        
        double P = 6000.0;
        double L = 14.0;
        double E = 30.0e+6;
        double G = 12.0e+6;
        double tau_max = 13600.0;
        double sigma_max = 30000.0;
        
        double delta = 4.0*P*Math.pow(L, 3)/(E*x4*Math.pow(x3, 3));
        double J = Math.sqrt(2)*x1*x2*(Math.pow(x2,2)/12.0+Math.pow(x1+x2,2)/4.0);
        double R = Math.sqrt(Math.pow(x2,2)/4.0+Math.pow(x1+x2,2)/4.0);
        double M = P*(L+x2/2.0);
        double tau_1 = P/(Math.sqrt(2)*x1*x2);
        double tau_11 = M*R/J;
        double tau = Math.sqrt(Math.pow(tau_1,2)+tau_1*tau_11*x2/R+Math.pow(tau_11,2));
        double sigma = 6.0*P*L/(x4*Math.pow(x3,2));
        double P_C = 4.013*Math.sqrt(E*G*Math.pow(x3,2)*Math.pow(x4,6)/36.0)/Math.pow(L,2)*(1-x3/Math.pow(2.0*L,2)*Math.sqrt(E/(4.0*L*G)));
        
        double f1 = 1.10471*Math.pow(x1, 2)*x2+0.04811*x3*x4*(14.0+x2);
        double f2 = delta;
        
        double g1 = tau - tau_max;
        double g2 = sigma - sigma_max;
        double g3 = x1 - x4;
        double g4 = 0.125 - x1;
        double g5 = P - P_C;
        
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
