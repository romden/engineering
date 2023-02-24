package experiments;


public class Main
{
    
    public static void main(String [] args)
    {
        
        String fileProblem = args[0]; // file containing problem definition
        String fileSettigs = args[1]; // file containing solver settings
        String fileOutput = args[2];  // file containing output information 
        
        moead.MOEAD solver = new moead.MOEAD(fileProblem, fileSettigs); // create object
        solver.execute(); // run optimization
        solver.save(fileOutput);  // save results
         
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
