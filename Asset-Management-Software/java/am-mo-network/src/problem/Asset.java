package problem;

import java.io.File;
import java.util.Scanner;


public class Asset
{
    String fileName;
    int timeHorizon;
    int numberOfAlternatives;
    
    private int type;
    private double [][] costs;
    private double [][] states;
    private int [][] actions;
    
    String fileCosts;
    String fileStates;
    
    
    // constructor
    public Asset(){
        
    }
    
    
    public void setCosts(double [][] costs)
    {
        this.costs = costs;
    }
    
    public void setStates(double[][] states)
    {
        this.states = states;
    }
    
    public void setActions(int [][] actions)
    {
        this.actions = actions;
    }
    
    // constructor
    public Asset(String fileCosts, String fileStates, int numberOfAlternatives, int timeHorizon, int type)
    {
        this.fileCosts = fileCosts;
        this.fileStates = fileStates;
        this.numberOfAlternatives = numberOfAlternatives;
        this.timeHorizon =  timeHorizon;
        this.type = type;
        
        initAlternatives();
    }
    
    
    private void initAlternatives()
    {
        // init costs
        finOpen(fileCosts);
        costs = new double[numberOfAlternatives][timeHorizon];
        for(int i = 0; i < numberOfAlternatives; i++)
        {
            for(int j = 0; j < timeHorizon; j++){
                costs[i][j] = Double.parseDouble(fin.next());
            }
        }
        finClose();
        
        // init states
        finOpen(fileStates);
        states = new double[numberOfAlternatives][timeHorizon];
        for(int i = 0; i < numberOfAlternatives; i++)
        {
            for(int j = 0; j < timeHorizon; j++){
                states[i][j] = Double.parseDouble(fin.next());
            }
        }
        finClose();
    }
    
    
    public double getCostFor(int i, int j)
    {
        return costs[i][j];
    }
    
    public double getStateFor(int i, int j)
    {
        return states[i][j];
    }
    
    public int getActionFor(int i, int j)
    {
        return actions[i][j];
    }
    
    public int getType()
    {
        return type;
    }
    
    
    Scanner fin; // object to read data from file
    
    // method to open file
    private void finOpen(String fileName)
    {
        try{
            fin = new Scanner(new File(fileName));
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



