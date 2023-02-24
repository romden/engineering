/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jmops.engineering;


/**
 * Spring GearTrain Problem.
 *
 * References:
 * <ol>
 * K. Deb, A. Pratap, S. Moitra. (2000).
 * Mechanical Component Design for Multiple Objectives Using Elitist Non-Dominated Sorting GA.
 * Parallel Problem Solving from Nature PPSN VI.
 * Volume 1917 of the series Lecture Notes in Computer Science pp 859-868.
 * 
 * K. Deb. Mechanical component design using genetic algorithms. 
 * In D. Dasgupta and Z. Michalewicz, editors, 
 * Evolutionary Algorithms in Engineering Applications, pages 495–512. 
 * New York: Springer-Verlag, 1997.
 * </ol>
 */

public class GearTrain {
    
    /**
     * Constructs GearTrain
     */
    public GearTrain() {
        
    }
    
    public static final int numberOfVariables = 4;
    public static final int numberOfObjectives = 2;
    public static final int numberOfConstraints = 0;
    
    public static double objectives[] = new double[numberOfObjectives];
    public static double constraints[] = new double[numberOfConstraints];
    
    public static final double lb[] = {12.0, 12.0, 12.0, 12.0};
    public static final double ub[] = {60.0, 60.0, 60.0, 60.0};
    
    
    public static void evaluate(final double [] vars)
    {        
        double x1 = vars[0];
        double x2 = vars[1];
        double x3 = vars[2];
        double x4 = vars[3];        
       
        double f1 = Math.pow(1.0/6.931 - x1*x2/(x3*x4), 2);
        double f2 = Math.max(Math.max(Math.max(x1, x2), x3), x4);
        
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
