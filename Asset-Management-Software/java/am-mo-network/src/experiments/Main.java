package experiments;



public class Main
{
    
    static String problemFile = "param_problem.dat"; //  name
    static String settingsFile = "param_solver.dat"; //  name
    static String outputFile = "param_output.dat"; //  name
    static String folderWeights = "weights";
    
    
    public static void main(String[] args)
    {
        if(args.length == 3)
        {
            problemFile = args[0];
            settingsFile = args[1];
            outputFile = args[2];
        }
        else if(args.length == 4)
        {
            problemFile = args[0];
            settingsFile = args[1];
            outputFile = args[2];
            folderWeights = args[3];
        }
                
        
        (new moead.MOEAD(problemFile, settingsFile, outputFile, folderWeights)).execute();        
    }
    
}



