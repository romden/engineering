/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jmops.engineering;

/**
 * MetalCutting Design Problem
 *
 * References:
 * <ol>
 *  
 * source:
 * S. Bandaru, and K. Deb.. (2015). 
 * Temporal Innovization Evolution of Design Principles Using Multi-objective Optimization. 
 * A. Gaspar-Cunha et al. (Eds.): EMO 2015, Part I, LNCS 9018, pp. 79-93.
 * 
 * original:
 * Quiza Sardinas, R., Rivas Santana, M., Alfonso Brindis, E.: 
 * Genetic algorithm based multiobjective optimization of cutting parameters in turning processes. 
 * Engineering Applications of Artificial Intelligence 19(2), 127–133 (2006)
 * </ol>
 */


public class MetalCutting{
    
    /**
     * Constructs the BarTruss2 problem.
     */
    public MetalCutting() {
        
    }
    
    public static final int numberOfVariables = 3;
    public static final int numberOfObjectives = 2;
    public static final int numberOfConstraints = 3;
    
    public static final double lb[] = {250.0, 0.15, 0.5};
    public static final double ub[] = {400.0, 0.55, 6.0}; 
    
    public static double objectives[] = new double[numberOfObjectives];
    public static double constraints[] = new double[numberOfConstraints];
    
    //@Override
    public static void evaluate(final double [] vars) 
    {
        double v = vars[0];
        double f = vars[1];
        double a = vars[2];
        
        double r_n = 0.8;
        double P_max = 10.0;
        double F_c_max = 5000.0;
        double R_max = 50.0;
        double eta = 0.7976; // a transmission efficiency (My value, somewhere from net)
        
        double T = 5.48E+9/(Math.pow(v,3.46)*Math.pow(f,0.696)*Math.pow(a,0.460));
        double F_c = 6.56E+3*Math.pow(f,0.917)*Math.pow(a,1.10)/Math.pow(v,0.286);
        double P = v*F_c/60000.0;
        double MRR = 1000.0*v*f*a;
        double R = 125.0*f*f/r_n;
        
        double T_p = 0.15 + 219912.0*(1.0+0.2/T)/MRR + 0.05;                    
        double xi = 219912.0/(MRR*T)*100.0;
        
        objectives[0] = T_p; // min
        objectives[1] = xi;  // min
        
        double g1 = P - eta*P_max;
        double g2 = F_c - F_c_max;
        double g3 = R - R_max;
        
        constraints[0] = g1;
        constraints[1] = g2;
        constraints[2] = g3;
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