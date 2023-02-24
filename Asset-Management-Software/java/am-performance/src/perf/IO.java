package perf;

import java.io.File;
import java.util.Formatter;
import java.util.Scanner;


public class IO
{
    Scanner fin; // object to read data from file
    
    Formatter fout; // object to save data to file
    
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
    
    
    // method to read matrix Q from file
    public double [][] getQ(String fileName)
    {
        int numberOfStates = 0;
        // read number of states
        openInFile(fileName);
        while(fin.hasNextLine())
        {
            fin.nextLine();
            numberOfStates++;
            
        }
        closeInFile();
        
        // open file
        openInFile(fileName);
        
        int i, j;
        // initialize Q
        double [][] Q = new double[numberOfStates][numberOfStates];
        for(i = 0; i < numberOfStates; i++)
        {
            for(j = 0; j < numberOfStates; j++){
                Q[i][j] = Double.parseDouble(fin.next());
            }
        }
        
        // close file
        closeInFile();
        
        return Q;
    }
    
    
//    public int [] getTimetable(String fileName)
//    {
//        int [] actions = new int[10];
//        
//        return actions;
//    }
    
    
    
    public ActionEffects [] getMA(String fileName)
    {
//        int minState = 1;
//        int maxState = 5;
        int numberOfStates = 5;
        int numberOfActions = 1;
        
        // get number of actions in file
        openInFile(fileName);
        while(fin.hasNext())
        {
            fin.nextLine();
            numberOfActions++;            
        }
        closeInFile();
        
        // init actions
        ActionEffects [] maintenanceAction = new ActionEffects[numberOfActions];
        for(int j = 0; j < numberOfActions; j++){
            maintenanceAction[j] = new ActionEffects();
            maintenanceAction[j].initialize(numberOfStates);
        }
        
        
        // read actions affects
        openInFile(fileName);
        
        String token;
        
        for(int k = 1; k < numberOfActions; k++)
        {
            
            // read time of delay
            for(int ii = 0; ii < numberOfStates; ii++)
            {
                for(int j = 0; j < 3; j++)
                {
                    token = fin.next();
                    if(!token.equals("-")){
                        maintenanceAction[k].timeOfDelay[ii][j] = Double.parseDouble(token);
                    }
                }
            }
            
            // read improvement
            for(int ii = 0; ii < numberOfStates; ii++)
            {
                for(int j = 0; j < 3; j++)
                {
                    token = fin.next();
                    if(!token.equals("-")){
                        maintenanceAction[k].improvement[ii][j] = Double.parseDouble(token);
                    }
                }
            }
            
            
            // read time of reduction
            for(int ii = 0; ii < numberOfStates; ii++)
            {
                for(int j = 0; j < 3; j++)
                {
                    token = fin.next();
                    if(!token.equals("-")){
                        maintenanceAction[k].timeOfReduction[ii][j] = Double.parseDouble(token);
                    }
                }
            }
            
            // read rate of reduction
            for(int ii = 0; ii < numberOfStates; ii++)
            {
                for(int j = 0; j < 3; j++)
                {
                    token = fin.next();
                    if(!token.equals("-")){
                        maintenanceAction[k].rateOfReduction[ii][j] = Double.parseDouble(token);
                    }
                    if(maintenanceAction[k].rateOfReduction[ii][j] > 1){
                        maintenanceAction[k].rateOfReduction[ii][j] /= 100.0;
                    }
                }
            }
            
        }
        
        // close file
        closeInFile();
        
        return maintenanceAction;
        
    } // getMA method
    
    
} // class
