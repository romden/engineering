package perf;

import java.io.File;
import java.util.Formatter;
import java.util.Scanner;


public class Performance
{
    
    double [] states;
    
    Scanner fin; // object to read data from file
    
    Scanner fin2; // object to read data from file
    
    Formatter fout; // object to save data to file
    
    
    Simulation simulation = new Simulation();
    
    IO io = new IO();    
    
    
    // method to calculate performance using analitic method
    public void executeAnalytic(String matrixFile, String paramsFile, String outputFile)
    {
        
        int minState = 1;
        int maxState = 5;
        int lastState;
        double timeElapsed;
        int timeHorizon;
        
        // read intensity matrix
        double [][] Q = io.getQ(matrixFile);
        
        // open files
        openInFile(paramsFile);
        openOutFile(outputFile);
       
        
        while(fin.hasNext())
        {
            lastState = (int)Double.parseDouble(fin.next());
            timeElapsed = Double.parseDouble(fin.next());
            timeHorizon = Integer.parseInt(fin.next());
            
            states = simulation.analytic(lastState-minState, timeElapsed, timeHorizon, Q);
                        
            for(double state : states){
                fout.format("%s ", state+minState);
            }
            fout.format("%n");
        }
        
        // close file
        closeInFile();
        closeOutFile();
        
    }
    
    
    // method to calculate performance using monte carlo method
    public void executeMonteCarlo(String matrixFile, String paramsFile, String outputFile, int numberOfSamples)
    {
        int minState = 1;
        int maxState = 5;
        numberOfSamples = Math.max(numberOfSamples, 100);
        int lastState;
        double timeElapsed;
        int timeHorizon;
                
        // read intensity matrix
        double [][] Q = io.getQ(matrixFile);
        
        // open files
        openInFile(paramsFile);
        openOutFile(outputFile);
       
        
        while(fin.hasNext())
        {
            lastState = Integer.parseInt(fin.next());
            timeElapsed = Double.parseDouble(fin.next());
            timeHorizon = Integer.parseInt(fin.next());
            
            states = simulation.monteCarlo(lastState-minState, timeElapsed, timeHorizon, Q, numberOfSamples);
                        
            for(double state : states){
                fout.format("%s ", state+minState);
            }
            fout.format("%n");
        }
        
        // close file
        closeInFile();
        closeOutFile();
        
    }
    
    
    // method to calculate performance with actions, based on monte carlo simulation
    public void executeMaintenance(String matrixFile, 
                                   String paramsFile, 
                                   String actionsTimetableFile, 
                                   String actionsEffectsFile, 
                                   String outputFile,
                                   int numberOfSamples)
    {
        int minState = 1;
        int maxState = 5;
        numberOfSamples = Math.max(numberOfSamples, 100);
        double lastState;
        double timeElapsed;
        int timeHorizon;
        
        // read intensity matrix
        double [][] Q = io.getQ(matrixFile);
        
        int [] actions;
        
        // read actions effects 
        ActionEffects [] actionsEffects = io.getMA(actionsEffectsFile);
        
        // open files
        openInFile(paramsFile);
        openOutFile(outputFile);
       
        openInFile2(actionsTimetableFile);
        
        while(fin.hasNext())
        {
//            minState = 1;
//            maxState = 5;
//            numberOfSamples = 100;
            lastState = Double.parseDouble(fin.next());
            timeElapsed = Double.parseDouble(fin.next());
            timeHorizon = Integer.parseInt(fin.next());            
            
            actions = new int[timeHorizon];
            
            for(int i = 0; i < timeHorizon; i++){
                actions[i] = Integer.parseInt(fin2.next());
            }
            //fin2.nextLine();
            
            states = simulation.maintenance(lastState-minState, timeElapsed, timeHorizon, numberOfSamples, Q, actions, actionsEffects);
                        
            for(double state : states){
                fout.format("%s ", state+minState);
            }
            fout.format("%n");
        }
        
            
        // close file
        closeInFile();
        closeOutFile();
        
        closeInFile2();
        
    }    
    
  
    // method to open file
    protected void openInFile(String fileName)
    {
        try{
            fin = new Scanner(new File(fileName));
        }
        catch (Exception e){
            System.out.println("error opening " + fileName + " : " + e);
        }
        
    } // openInFile method
    
    
    // method to close file
    protected void closeInFile()
    {
        fin.close();
    } // closeInFile method
    
    
    // method to open file
    private void openOutFile(String fileName)
    {
        // open file
        try {
            fout = new Formatter(fileName);
        }
        catch (Exception e) {
            System.out.println("error opening " + fileName + " : " + e);
        }
    }
    
    
    // method to close file
    private void closeOutFile()
    {
        fout.close();
    }
    
    
        // method to open file
    protected void openInFile2(String fileName)
    {
        try{
            fin2 = new Scanner(new File(fileName));
        }
        catch (Exception e){
            System.out.println("error opening " + fileName + " : " + e);
        }
        
    } // openInFile method
    
    
    // method to close file
    protected void closeInFile2()
    {
        fin2.close();
    } // closeInFile method
}
