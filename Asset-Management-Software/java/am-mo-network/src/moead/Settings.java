package moead;


public class Settings
{
    
    public double pc; // crossover probability
    public double pm; // mutation probability
    
    public double delta; // probability that parent solutions are selected from neighbourhood
    public int T; // neighbour size
    public int nr; // maximal number of solutions replaced by each child solution
    
    public int mu; // population size
    public int maxGen; // stopping criterion
    
    public String functionType = "CHB"; // type of scalarizing function
    public String variationType = "twoPointCrossover"; // type of producing offspring
    
    public int seed; // seed for random numbers
    
    // constructor
    public Settings(String settingsFile)
    {
        
        // open file
        finOpen(settingsFile);
        
        // read data
        
        // read crossover probability
        fin.next();
        pc = Double.parseDouble(fin.next());
        
        // read mutation probability
        fin.next();
        pm = Double.parseDouble(fin.next());
        
        // read prob for mating pool
        fin.next();
        delta = Double.parseDouble(fin.next());
        
        // read neighbour size
        fin.next();
        T = Integer.parseInt(fin.next());
        
        // read max number of replacements
        fin.next();
        nr = Integer.parseInt(fin.next());
        
        // read population size
        fin.next();
        mu = Integer.parseInt(fin.next());
        
        // read stopping criterion
        fin.next();
        maxGen = Integer.parseInt(fin.next());
        
        // init random seed
        seed = 10000;
        if(fin.hasNext())
        {
            fin.next();
            seed = Integer.parseInt(fin.next());
        }
        
        // close file
        finClose();
    }
    
    
    private java.util.Scanner fin;
    
    // method to open file
    private void finOpen(String fileName)
    {
        try{
            fin = new java.util.Scanner (new java.io.File(fileName));
        }
        catch (Exception e){
            System.out.println("error opening " + fileName + " : " + e);
        }
        
    } // openInFile method
    
    // method to close file
    private void finClose()
    {
        fin.close();
    } // closeInFile method
    
}