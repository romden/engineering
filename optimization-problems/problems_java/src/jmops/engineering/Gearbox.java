package jmops.engineering;



/**
 * Gearbox Design Problem
 *
 * References:
 * <ol>
 * Huang, H.Z., Gu,Y.K., and Du, X., 2006. 
 * An interactive fuzzy multi-objective optimization method for engineering design. 
 * Engineering Applications of Artificial Intelligence, 19, 451–460.
 * 
 * Annamdas, K., and Rao, S., (2009). 
 * Multi-objective optimization of engineering systems using game theory and particle swarm optimization. 
 * Engineering Optimization, 41(8): 37-752.
 * 
 * </ol>
 */


public class Gearbox{
    
    /**
     * Constructs the Gearbox problem.
     */
    public Gearbox() {
        
    }
    
    public static final int numberOfVariables = 7;
    public static final int numberOfObjectives = 3;
    public static final int numberOfConstraints = 11;
    
    public static double objectives[] = new double[numberOfObjectives];
    public static double constraints[] = new double[numberOfConstraints];
    
    public static final double lb[] = {2.6, 0.7, 17.0, 7.3, 7.3, 2.9, 5.0};
    public static final double ub[] = {3.6, 0.8, 28.0, 8.3, 8.3, 3.9, 5.5};
    
    
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
        
        double f1 = 0.7854*x1*x2*x2*(10.0*x3*x3/3.0+14.9334*x3-43.0934)-1.508*x1*(x6*x6+x7*x7) +
                    7.4777*(x6*x6*x6+x7*x7*x7)+0.7854*(x4*x6*x6+x5*x7*x7);
        double f2 = Math.sqrt(Math.pow(745.0*x4/(x2*x3),2)+1.69E+7)/(0.1*x6*x6*x6);
        double f3 = Math.sqrt(Math.pow(745.0*x5/(x2*x3),2)+1.575E+8)/(0.1*x7*x7*x7);
        
        double g1 = 27.0/(x1*x2*x2*x3*x3*x3)-1.0;
        double g2 = 397.5/(x1*x2*x2*x3*x3)-1.0;
        double g3 = 1.93*x4*x4*x4/(x2*x3*Math.pow(x6,4))-1.0;
        double g4 = 1.93*Math.pow(x5,3)/(x2*x3*Math.pow(x7,4))-1.0;
        double g5 = x2*x3-40.0;
        double g6 = x1/x2-12.0;
        double g7 = 5.0-x1/x2;
        double g8 = 1.9-x4+1.5*x6;
        double g9 = 1.9-x5+1.1*x7;
        double g10 = f2-1300.0;
        double g11 = f3-1100.0;
        
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
        constraints[10] = g11;
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
