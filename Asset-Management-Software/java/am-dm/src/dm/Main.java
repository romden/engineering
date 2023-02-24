package dm;


public class Main
{
    
    public static void main(String [] args)
    {
        
        
        String inputFile = args[0]; // file containing problem definition
        String outputFile = args[1]; // file containing solver settings
        
        new DecisionMaking().execute(inputFile, outputFile); // create object
         
    }  

    
}




//    // method to create folder if there is no folder
//    private static void createFolder(String folderName)
//    {
//        java.io.File folder = new java.io.File(folderName);
//        if(!folder.exists() || !folder.isDirectory())
//        {
//            new java.io.File(folderName).mkdir();
//        }
//        
//    } // createFolder method



//       System.out.println("SustIMS - mo - asset");
//        long startTime = System.currentTimeMillis();



//        long stopTime = System.currentTimeMillis();
//        System.out.println("run time: " + ((stopTime-startTime)/1000.0) + " seconds");
//        System.out.println("Press Any Key To Continue...");
//        new java.util.Scanner(System.in).nextLine();

//System.out.println("number of processors: " + Runtime.getRuntime().availableProcessors()); 
