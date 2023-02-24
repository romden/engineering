/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jmops.engineering;

/**
 * Spring Design Problem.
 * T. Ray and K. Tai, 
 * An evolutionary algorithm with a multilevel pairing strategy for single and multiobjective optimization, 
 * Foundations of Computing and Decision Sciences, 26, 75–98, 2001.
 * 
 * M. Fernanda P. Costa and Edite M.G.P. Fernandes.
 * 8th World Congress on Structural and Multidisciplinary Optimization.
 * June 1-5, 2009, Lisbon, Portugal
 * References:
 * <ol>

 * </ol>
 */

public class TankerFleet {
    
    /**
     * Constructs TankerFleet.
     */
    public TankerFleet() {
        
    }
    
    public static final int numberOfVariables = 9;
    public static final int numberOfObjectives = 2;
    public static final int numberOfConstraints = 19;
    
    public static double objectives[] = new double[numberOfObjectives];
    public static double constraints[] = new double[numberOfConstraints];
    
    public static final double lb[] = {0.01, 0.01, 0.01, 150.0, 1.0, 0.01, 0.01, 0.01, 0.01};
    public static final double ub[] = {50.0, 50.0, 5.0e+5, 480.0, 50.0, 50.0, 1.0, 30.0, 6.0e+5};
    
    public static void evaluate(final double [] vars)
    {
        
        double x1 = vars[0];
        double x2 = vars[1];
        double x3 = vars[2];
        double x4 = vars[3]; 
        double x5 = vars[4]; 
        double x6 = vars[5]; 
        double x7 = vars[6]; 
        double x8 = vars[7]; 
        double x9 = vars[8]; 
        
        double K_alpha = 427.1;
        double C_m = 0.98;
        double F = 0.00005;
        double g = 9.8065;
        double O = 2500.0;
        double W = 8640.0;
        double R = 2900.0;
        double Q = 10.0;
        double k0 = 3689.03;        
        
        double k1 = 4.0/Math.pow(x4, 1.0/3.0) + 3.0/x4 + 0.2082;
        double k2 = 3.0/(2.58 + x9/(x4*x1*x6)) - 0.07*(1.0 - x9/(0.65*x4*x1*x6));
        double K_st = k0*k1*k2;
        double C_b = x9/(1.025*x4*x1*x6);
        double C_ma = 2.0*Math.pow(x8*x8*x8*Math.pow(x9, 2.0/3.0), 0.72);
        double C_f = 0.8*x7*Math.pow(x8*x8*x8*Math.pow(x9, 2.0/3.0), 0.72);
        double alpha_l = (0.2771+0.02053*x4/x1)*Math.pow(100.0*x4/x2, -0.78);
        double alpha_t = 0.029+0.00235*x9/100000.0;
        double C_hl = 0.25*K_st*x9*(alpha_l+0.06*alpha_t*(1.009-0.004*x4/x1)*(28.7-x4/x2));
        double wst = C_hl/K_st;
        
        double f1 = x5*(C_hl + C_ma + C_f);
        double f2 = x5*x7*W*(x3*x8/R - F*Math.pow(x8, 3)*Math.pow(x9, 2.0/3.0)/K_alpha);
        
        objectives[0] = f1; // min
        objectives[1] = -f2; // max
        
        double g1 = 2.0*Q - f2;
        double g2 = wst + 0.02*Math.pow(Math.pow(x8, 3)*Math.pow(x9, 2.0/3.0), 0.72) + x3 - x9;
        double g3 = x7*(R/x8 + 2.0*x3/O) - R/x8;
        double g4 = R/x8 - (R/x8 + 2.0*x3/O);
        double g5 = x3/(x4*x1*x2) - 1.0/3.0;
        double g6 = 1.5 + 0.45*x2 - x1*(0.08*x1/(x6*Math.pow(C_m, 0.5)) + x6*(0.9-0.3*C_m-0.1*C_b)/x1);
        double g7 = 0.0019*Math.pow(x4, 1.43) + x6 - x2;
        double g8 = 0.14 - x8/Math.pow(g*x4, 0.5);
        double g9 = x8/Math.pow(g*x4, 0.5) - 0.32;
        double g10 = 0.6 - C_b;
        double g11 = C_b - 0.72;
        double g12 = 5.0 - x4/x1;
        double g13 = x4/x1 - 7.0;
        double g14 = 10.0 - x4/x2;
        double g15 = x4/x2 - 14.0;
        double g16 = 2.0 - x1/x6;
        double g17 = x1/x6 - 4.0;
        double g18 = 0.61 - x6/x2;
        double g19 = x6/x2 - 0.87;        
        
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
        constraints[10] = g11;
        constraints[11] = g12;
        constraints[12] = g13;
        constraints[13] = g14;
        constraints[14] = g15;
        constraints[14] = g16;
        constraints[16] = g17;
        constraints[17] = g18;
        constraints[18] = g19;
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
