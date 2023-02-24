/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jmops.engineering;

/**
 * BarTruss2 Design Problem
 *
 * References:
 * <ol>
 *  
 * source:
 * H. Singh, M. Asafuddoula, K Alam, T. Ray. (2015). 
 * Re-design for Robustness: An Approach Based on Many Objective Optimization. 
 * A. Gaspar-Cunha et al. (Eds.): EMO 2015, Part II, LNCS 9019, pp. 343–357.
 * 
 * original:
 * Sun, G., Li, G., Zhou, S., Li, H., Hou, S., Li, Q.: 
 * Crashworthiness design of vehicle by using multiobjective robust optimization. 
 * Structural and Multidisciplinary Optimization 44(1), 99–110 (2011)
 * </ol>
 */


public class VehicleCrashWorthiness{
    
    /**
     * Constructs the BarTruss2 problem.
     */
    public VehicleCrashWorthiness() {
        
    }
    
    public static final int numberOfVariables = 3;
    public static final int numberOfObjectives = 2;
    public static final int numberOfConstraints = 1;
    
    public static final double lb[] = {1.0, 1.0, 1.0};
    public static final double ub[] = {2.0, 2.0, 2.0};
    
    public static double objectives[] = new double[numberOfObjectives];
    public static double constraints[] = new double[numberOfConstraints];
    
    //@Override
    public static void evaluate(final double [] vars) 
    {
        double t1 = vars[0];
        double t2 = vars[1];
        double t3 = vars[2];
        
        double f1 = 72.4996 + 2.8178366*t1 - 0.0778410*t1*t1 + 3.7901860*t2 
                    + 6.0060214*t2*t2 + 52.005026*t3 - 17.599580*t3*t3 
                    + 1.2718916*t1*t2 - 0.5211597*t1*t3 - 30.982883*t2*t3 
                    + 11.034587*t2*t3*t3;
                    
        double f2 = 0.00392497 + 4.9603440*t1 + 4.4474721*t2 + 4.7437340*t3;
        
        objectives[0] = -f1; // max
        objectives[1] = f2;  // min
        
        double g1 = 48.3807 - 8.4035115*t1 + 4.0333016*t1*t1 - 17.774059*t2 
                    + 4.2845324*t2*t2 - 11.547927*t3 + 4.3592314*t3*t3 
                    + 4.7775756*t1*t2 + 4.5825734*t2*t3 - 40.0;
        
        constraints[0] = g1;
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