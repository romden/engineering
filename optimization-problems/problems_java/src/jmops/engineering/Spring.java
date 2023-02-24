/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jmops.engineering;

/**
 * Spring Design Problem.
 *
 * References:
 * <ol>
 * K. Deb, J. Sundar, Udaya Bhaskara Rao N., and S. Chaudhuri. (2006). 
 * Reference Point Based Multi-objective Optimization Using Evolutionary Algorithms. 
 * International Journal of Computational Intelligence Research, 2(3) 273-86.
 * </ol>
 */

public class Spring{
    
    /**
     * Constructs the Spring problem.
     */
    public Spring() {
        
    }
    
    public static final int numberOfVariables = 3;
    public static final int numberOfObjectives = 2;
    public static final int numberOfConstraints = 8;
    
    public static double objectives[] = new double[numberOfObjectives];
    public static double constraints[] = new double[numberOfConstraints];
    
    public static final double lb[] = {1.0, 0.0, 0.001};
    public static final double ub[] = {32.0, 41.9999, 3.0};
    
        //@Override
    public static void evaluate(final double [] vars)
    {
        int N = (int)Math.round(vars[0]); 
        int idx = (int)Math.floor(vars[1]);
        double D = vars[2];
        
        double d[] = {0.009, 0.0095, 0.0104, 0.0118, 0.0128, 0.0132,
                      0.014, 0.015, 0.0162, 0.0173, 0.018, 0.020,
                      0.023, 0.025, 0.028, 0.032, 0.035, 0.041,
                      0.047, 0.054, 0.063, 0.072, 0.080, 0.092,
                      0.105, 0.120, 0.135, 0.148, 0.162, 0.177,
                      0.192, 0.207, 0.225, 0.244, 0.263, 0.283,
                      0.307, 0.331, 0.362, 0.394, 0.4375, 0.5};
        
        double x1 = (double)N;
        double x2 = d[idx];
        double x3 = D;
        
        double P_max = 1000.0;
        double delta_pm = 6.0;
        double G = 11500000.0;
        double l_max = 14.0;
        double P = 300.0;
        double delta_w = 1.25;
        double S = 189.0;
        double V_max = 30.0;
        double D_max = 3.0;
        double d_min = 0.2;
        double k = G*Math.pow(x2, 4)/(8.0*x1*Math.pow(x3, 3));
        double delta_p = P/k;
        double C = x3/x2;
        double K = (4.0*C-1.0)/(4.0*C-4.0) + 0.615*x2/x3;
        
        double f1 = 0.25*Math.pow(Math.PI, 2)*Math.pow(x2, 2)*x3*(x1+2.0);
        double f2 = 8.0*K*P_max*x3/(Math.PI*Math.pow(x2, 3));
        
        objectives[0] = f1;
        objectives[1] = f2;
        
        double g1 = l_max - P_max/k - 1.05*(x1+2.0)*x2;
        double g2 = x2 - d_min;
        double g3 = D_max - (x2+x3);
        double g4 = C - 3.0;
        double g5 = delta_pm - delta_p;
        double g6 = (P_max-P)/k - delta_w;
        double g7 = S - 8.0*K*P_max*x3/(Math.PI*Math.pow(x2, 3));
        double g8 = V_max - 0.25*Math.pow(Math.PI, 2)*Math.pow(x2, 2)*x3*(x1+2);
        
        constraints[0] = -g1;
        constraints[1] = -g2;
        constraints[2] = -g3;
        constraints[3] = -g4;
        constraints[4] = -g5;
        constraints[5] = -g6;
        constraints[6] = -g7;
        constraints[7] = -g8;
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
