package jmops.engineering;


/**
 * The VibrationPlatform problem
 * 
 * References:
 * <ol>
 * T. Ray, K. Tai, and K. C. Seow. (2001). 
 * Multiobjective design optimization by an evolutionary algorithm. 
 * Engineering Optimization, 33(4) 399-424.
 * </ol>
 */

public class VibrationPlatform{
    
    /**
     * Constructs the VibrationPlatform problem.
     */
    public VibrationPlatform() {
        
    }
    
    public static final int numberOfVariables = 8;
    public static final int numberOfObjectives = 2;
    public static final int numberOfConstraints = 5;
    
    public static double objectives[] = new double[numberOfObjectives];
    public static double constraints[] = new double[numberOfConstraints];
    
    public static final double lb[] = {3.0, 0.35, 0.01, 0.01, 0.01, 0.0, 0.0, 0.0};
    public static final double ub[] = {6.0, 0.50, 0.60, 0.60, 0.60, 2.9999, 2.9999, 2.9999};
    
        //@Override
    public static void evaluate(final double [] vars)
    {
        double L = vars[0];
        double b = vars[1];
        double d1 = vars[2];
        double d2 = vars[3];
        double d3 = vars[4];
        // combinatorial variables indicating material, for each layer, values = (0 1 2)
        int M1 = (int)Math.floor(vars[5]); // 1 layer
        int M2 = (int)Math.floor(vars[6]); // 2 layer
        int M3 = (int)Math.floor(vars[7]); // 3 layer   
        
        double rho[] = {2770.0, 100.0, 7780.0};
        double E[] = {70.0e9, 1.6e9, 200.0e9};
        double c[] = {1500.0, 500.0, 800.0};
        
        double mu = 2.0*b*( rho[M1]*d1 + rho[M2]*(d2-d1) + rho[M3]*(d3-d2) );
        double EI = (2.0*b/3.0)*( E[M1]*d1*d1*d1 + E[M2]*(d2*d2*d2-d1*d1*d1) + E[M3]*(d3*d3*d3-d2*d2*d2) );
        
        double f1 = -Math.PI/(2.0*L*L)*Math.pow(EI/mu, 0.5); 
        double f2 = 2.0*b*( c[M1]*d1 + c[M2]*(d2-d1) + c[M3]*(d3-d2) );
        
        objectives[0] = f1;
        objectives[1] = f2;
        
        double g1 = mu*L - 2800.0;
        double g2 = 0.00001 - (d2-d1);
        double g3 = 0.00001 - (d3-d2);
        double g4 = d2-d1 - 0.01;
        double g5 = d3-d2 - 0.01;
        
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

