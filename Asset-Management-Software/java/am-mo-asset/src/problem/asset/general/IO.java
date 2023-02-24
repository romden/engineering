package problem.asset.general;

import java.io.File;
import java.util.Formatter;
import java.util.Scanner;

import java.util.ArrayList;

public class IO
{
    private Scanner fin; // object to read data from file    
    private Formatter fout; // object to save data to file    
    
    // comonent vars  
    private int numberOfActions; // number of actions    
    private int numberOfIndexes; // number of performance indicators for asset        
    private ArrayList<Integer> minState = new ArrayList();    
    private ArrayList<Integer> maxState = new ArrayList();    
    private ArrayList<Double> criticalState = new ArrayList();    
    private ArrayList<Double> lastState = new ArrayList();    
    private ArrayList<Double> elapsedTime = new ArrayList();    
    private ArrayList<String> fileQ = new ArrayList();    
    private ArrayList<String> fileMA = new ArrayList();    
    private String fileCosts;    
    
    // PROBLEM
    
    // asset definition
    private String nameOfAsset;
    private int numberOfCriticalElements;
    private int [] criticalElements;
    
    // problem definition
    private int numberOfObjectives; // number of objectives
    private double discountRate; // discount rate
    private int timeHorizon; // number of time intervals in the future when the sistem is evaluated
    private int numberOfSamples; // number of sample points    
    private double criticalStateOfAsset;
    private int  numberOfComponents;
    
    private static ArrayList<String> [] listComponentProblem;
    
    
    // read problem-------------------------------------------------------------
    public void readProblem(String fileName)
    {
        finOpen(fileName);
        
        fin.next();
        nameOfAsset = fin.next();
        
        fin.next();
        numberOfCriticalElements = Integer.parseInt(fin.next());
        
        fin.next();
        criticalElements = new int[numberOfCriticalElements];
        for(int i = 0; i < numberOfCriticalElements; i++){
            criticalElements[i] = Integer.parseInt(fin.next())-1;
        }        
      
        fin.next();
        numberOfObjectives = Integer.parseInt(fin.next());
        
        fin.next();
        discountRate = Double.parseDouble(fin.next());
        
        fin.next();
        timeHorizon = Integer.parseInt(fin.next());
        
        fin.next();
        numberOfSamples = Integer.parseInt(fin.next());
        
        fin.next();
        criticalStateOfAsset = Double.parseDouble(fin.next());
        
        fin.next();
        numberOfComponents = Integer.parseInt(fin.next());
        
       
        // read components problems
        listComponentProblem = new ArrayList[numberOfComponents];
        if(numberOfComponents == 1)
        {
            listComponentProblem[0] = new ArrayList<String>();
            fin.next(); fin.next();
            while(fin.hasNext()){
                listComponentProblem[0].add(fin.next());
            }
        }
        else
        {
            for(int i = 0; i < numberOfComponents; i++)
            {
                listComponentProblem[i] = new ArrayList<String>();
                fin.next(); fin.next();
                for(int j = 0; j < 8; j++)
                {
                    listComponentProblem[i].add(fin.next());
                    listComponentProblem[i].add(fin.next());
                }
            }
        }
        
        finClose();
    }
      
        
    public String getNameOfAsset()
    {
        return nameOfAsset;
    }
    
    public int getNumberOfCriticalElements()
    {
        return numberOfCriticalElements;
    }
    
    public int [] getCriticalElements()
    {
        return criticalElements;
    }
    
    public int getNumberOfObjectives()
    {
        return numberOfObjectives;
    }
    
    public double getDiscountRate()
    {
        return discountRate;
    }
    
    public int getTimeHorizon()
    {
        return timeHorizon;
    }
    
    public int getNumberOfSamples()
    {
        return numberOfSamples;
    }
    
    public double getCriticalStateOfAsset()
    {
        return criticalStateOfAsset;
    }
        
    public int getNumberOfComponents()
    {
        return numberOfComponents;
    }

    // read problem-------------------------------------------------------------
    
    
    
    // read components ---------------------------------------------------------
    private void resetComponent()
    {
        minState.clear();
        maxState.clear();
        criticalState.clear();
        lastState.clear();
        elapsedTime.clear();
        fileQ.clear();
        fileMA.clear();
    }
    
    
    public void readComponent(int idx)
    {        
        resetComponent();
                
        java.util.Iterator itr = listComponentProblem[idx].iterator();

   
        itr.next();
        numberOfActions = Integer.parseInt((String)itr.next()) + 1; // plus 0 - no action
        
        itr.next();
        numberOfIndexes = Integer.parseInt((String)itr.next());
        
        itr.next();
        for(int i = 0; i < numberOfIndexes; i++){
//            minState.add(Integer.parseInt((String)itr.next()));
            minState.add(1);
        }
        
        //itr.next();
        for(int i = 0; i < numberOfIndexes; i++){
//            maxState.add(Integer.parseInt((String)itr.next()));
            maxState.add(5);
        }
        
        //itr.next();
        for(int i = 0; i < numberOfIndexes; i++){
            criticalState.add(Double.parseDouble((String)itr.next()));
        }
        
        itr.next();
        for(int i = 0; i < numberOfIndexes; i++){
            lastState.add(Double.parseDouble((String)itr.next()));
        }
        
        itr.next();
        for(int i = 0; i < numberOfIndexes; i++){
            elapsedTime.add(Double.parseDouble((String)itr.next()));
        }
        
        itr.next();
        for(int i = 0; i < numberOfIndexes; i++){
            fileQ.add((String)itr.next());
        }
        
        itr.next();
        for(int i = 0; i < numberOfIndexes; i++){
            fileMA.add((String)itr.next());
        }
        
        itr.next();
        fileCosts = (String)itr.next();
    }
    
  
    public int getNumberOfActions()
    {
        return numberOfActions;
    }
    
    public double [] getCosts()
    {
        double [] costs = new double[numberOfActions];
        costs[0] = 0.0;
        
        finOpen(fileCosts);
        for(int i = 1; i < numberOfActions; i++){
            costs[i] = Double.parseDouble(fin.next());
        }
        finClose();
        
        return costs;
    }
    
    
    public PerformanceIndex [] getPerfIdxs()
    {
        
        PerformanceIndex [] perfIdx = new PerformanceIndex[numberOfIndexes];
        
        for(int i = 0; i < numberOfIndexes; i++)
        {
            perfIdx[i] = new PerformanceIndex();
            
            perfIdx[i].setMinState(minState.get(i));
            perfIdx[i].setMaxState(maxState.get(i));
            perfIdx[i].setNumberOfStates(maxState.get(i) - minState.get(i) + 1);
            perfIdx[i].setCriticalState(criticalState.get(i) - minState.get(i));
            perfIdx[i].setLastState(lastState.get(i) - minState.get(i));
            perfIdx[i].setElapsedTime(elapsedTime.get(i));
            perfIdx[i].setQ(getQ(fileQ.get(i), maxState.get(i) - minState.get(i) + 1));
            perfIdx[i].setActionEffects(getMA(fileMA.get(i), minState.get(i), maxState.get(i)));
        }
        
        return perfIdx;
    }
    
    
    // method to read matrix Q from file
    public double [][] getQ(String fileName, int numberOfStates)
    {      
        // open file
        finOpen(fileName);
        
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
        finClose();
        
        return Q;
    }
    
    
    public ActionEffects [] getMA(String fileName, int minState, int maxState)
    {

        int numberOfStates = maxState - minState + 1;
        
        // init actions
        ActionEffects [] maintenanceAction = new ActionEffects[numberOfActions];
        for(int j = 0; j < numberOfActions; j++){
            maintenanceAction[j] = new ActionEffects();
            maintenanceAction[j].initialize(numberOfStates);
        }
        
        
        // read actions affects
        finOpen(fileName);
        
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
            
            // read improved state
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
        finClose();
        
        return maintenanceAction;
        
    } // getMA method    
    
    
    
    // method to open file
    private void finOpen(String fileName)
    {
        try{
            fin = new Scanner(new File(fileName));
        }
        catch (Exception e){
            System.out.println("error opening " + fileName + " : " + e);
        }
        
    } // finOpen method
    
    // method to close file
    private void finClose()
    {
        fin.close();
    } // finClose method
    
       
    
    // method to open file
    private void foutOpen(String fileName)
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
    private void foutClose()
    {
        fout.close();
    }
    
    
} // class
