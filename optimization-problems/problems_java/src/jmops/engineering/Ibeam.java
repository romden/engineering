package jmops.engineering;



/**
 * The Design of an I-beam problem.
 * 
 * References:
 * <ol>
 * Huang, H.Z., Gu,Y.K., and Du, X., 2006. 
 * An interactive fuzzy multi-objective optimization method for engineering design. 
 * Engineering Applications of Artificial Intelligence, 19, 451–460.
 * </ol>
 */


public class Ibeam{
    
    /**
     * Constructs the Ibeam problem.
     */
    public Ibeam() {
        
    }
    
    public static final int numberOfVariables = 4;
    public static final int numberOfObjectives = 2;
    public static final int numberOfConstraints = 1;
    
    public static double objectives[] = new double[numberOfObjectives];
    public static double constraints[] = new double[numberOfConstraints];
    
    public static final double lb[] = {10.0, 10.0, 0.9, 0.9};
    public static final double ub[] = {80.0, 50.0, 5.0, 5.0};
    
    
    //@Override
    public static void evaluate(final double [] vars)
    {
        double x1 = vars[0];
        double x2 = vars[1];
        double x3 = vars[2];
        double x4 = vars[3];
        
        double L = 200.0, P = 600.0, E = 2.0e+4;
        
        double f1 = 2.0*x2*x4+x3*(x1-2.0*x4);
        double f2 = P*Math.pow(L,3)/(4.0*E) * 1.0/(x3*Math.pow(x1-2.0*x4, 3)+2.0*x2*x4*(4.0*x4*x4+3.0*x1*(x1-2.0*x4)));
        
        double g1 = 180000.0*x1/(x3*Math.pow(x1-2.0*x4,3)+2.0*x2*x4*(4.0*x4*x4+3.0*x1*(x1-2.0*x4)))+
                     15000.0*x2/((x1-2.0*x4)*x3*x3*x3+2.0*x4*x2*x2*x2)-16;

        
        objectives[0] = f1;
        objectives[1] = f2;
        
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
