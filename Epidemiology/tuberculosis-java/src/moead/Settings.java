package moead;

import problem.Tuberculosis;


public class Settings
{
    
    public double CR; // DE crossover probability
    public double F; // scaling factor
    
    public double pc; // GA crossover probability
    public double etac; // GA crossover distribution index
    
    public double pm; // polynomial mutation probability
    public double etam; // polynomial mutation distribution index
    
    public double delta; // probability that parent solutions are selected from neighbourhood
    public int T; // neighborhood size
    public int nr; // maximal number of solutions replaced by each child solution
    public int mu; // population size
    public int maxGen; // stopping criterion
    
    String variationType;
    String functionType;
    boolean normalization;
    
    Tuberculosis problem = new Tuberculosis();
//    LZ09F problem = new LZ09F();
    
    private java.util.Scanner fin;
    
    
    // constructor
    public Settings()
    {
        
        defaultParameters();
        
//        setParameters();
        
    }
    
    
    private void defaultParameters()
    {
        CR = 1.0;
        F = 0.5;
        
        pc = 1.0;
        etac = 20.0;
        
        pm = 1.0/problem.numberOfVariables;
        etam = 20.0;
        
        delta = 0.9;
        T = 20;
        nr = 2;
        mu = 1000;
        maxGen = 1000;
        
        normalization = true;
//        normalization = false;
        functionType = "CHB";
        variationType = "DE"; // DE, PM, GM, GA
        
    }
    
    
    private void setParameters()
    {
        // create object to read file
        try{
            fin = new java.util.Scanner(new java.io.File("param_algorithm.dat"));
        }
        catch(Exception e){
            System.out.println("error openning param_algorithm.dat : " + e);
        }
        
        // read variation type
        fin.next(); variationType = fin.next();
        
        // close file
        fin.close();
    }
    
}