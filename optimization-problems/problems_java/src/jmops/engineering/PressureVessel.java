/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jmops.engineering;

/**
 * References:
 * <ol>
 * PressureVessel Design Problem.
 * Deb, K. and Srinivasan, A. (June, 2006). 
 * Monotonicity Analysis, Evolutionary Multi-Objective Optimization, and Discovery of Design Principles. 
 * KanGAL Report No. 2006004
 * References:
 * </ol>
 */

public class PressureVessel {
    
    /**
     * Constructs PressureVesselproblem.
     */
    public PressureVessel() {
        
    }
    
    public static final int numberOfVariables = 4;
    public static final int numberOfObjectives = 2;
    public static final int numberOfConstraints = 2;
    
    public static double objectives[] = new double[numberOfObjectives];
    public static double constraints[] = new double[numberOfConstraints];
    
    public static final double lb[] = {0.0625, 0.0625, 10.0, 10.0};
    public static final double ub[] = {5.0, 5.0, 200.0, 240.0};
    
    public static void evaluate(final double [] vars)
    {
        
        double T_s = vars[0];
        double T_h = vars[1];
        double R = vars[2];
        double L = vars[3];        
        
        double f1 = 0.6224*T_s*L*R + 1.7781*T_h*R*R + 3.1661*T_s*T_s*L + 19.84*T_s*T_s*R;
        double f2 = -(Math.PI*R*R*L + 1.333*Math.PI*R*R*R);
        
        objectives[0] = f1;
        objectives[1] = f2;
        
        double g1 = 0.193*R - T_s;
        double g2 = 0.00954*R - T_h;
        
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
