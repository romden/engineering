package jmops.engineering;


/**
 * The WaterResourcePlanning problem
 * 
 * References:
 * <ol>
 * T. Ray, K. Tai, and K. C. Seow. (2001). 
 * Multiobjective design optimization by an evolutionary algorithm. 
 * Engineering Optimization, 33(4) 399-424.
 * </ol>
 */


public class WaterResourcePlanning{
    
    /**
     * Constructs the WaterResourcePlanning problem.
     */
    public WaterResourcePlanning() {
        
    }
    
    public static final int numberOfVariables = 3;
    public static final int numberOfObjectives = 5;
    public static final int numberOfConstraints = 7;
    
    public static double objectives[] = new double[numberOfObjectives];
    public static double constraints[] = new double[numberOfConstraints];
    
    public static final double lb[] = {0.01, 0.01, 0.01};
    public static final double ub[] = {0.45, 0.1, 0.1};
    
    
    //@Override
    public static void evaluate(final double [] vars)
    {
        double x1 = vars[0];
        double x2 = vars[1];
        double x3 = vars[2];
        
        double f1 = 106780.37*(x2+x3)+61704.67;
        double f2 = 3000.0*x1;
        double f3 = 305700.0*2289.0*x2/Math.pow(0.06*2289.0, 0.65);
        double f4 = 250.0*2289.0*Math.exp(-39.75*x2+9.9*x3+2.74);
        double f5 = 25.0*(1.39/(x1*x2)+4940.0*x3-80.0);
        
        double g1 = 0.00139/(x1*x2)+4.94*x3-0.08-1.0;
        double g2 = 0.000306/(x1*x2)+1.082*x3-0.0986-1.0;
        double g3 = 12.307/(x1*x2)+49408.24*x3+4051.02-50000.0;
        double g4 = 2.098/(x1*x2)+8046.33*x3-696.71-16000.0;
        double g5 = 2.138/(x1*x2)+7883.39*x3-705.04-10000.0;
        double g6 = 0.417/(x1*x2)+1721.26*x3-136.54-2000.0;
        double g7 = 0.164/(x1*x2)+631.13*x3-54.48-550.0;
        
        objectives[0] = f1;
        objectives[1] = f2;
        objectives[2] = f3;
        objectives[3] = f4;
        objectives[4] = f5;
        
        constraints[0] = g1;
        constraints[1] = g2;
        constraints[2] = g3;
        constraints[3] = g4;
        constraints[4] = g5;
        constraints[5] = g6;
        constraints[6] = g7;
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

