package problem;

import problem.asset.general.*;
import problem.asset.pfunc.*;

import util.*;
import moead.Solution;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;



public class Problem
{
    // problem definition varibles
    public String nameOfAsset; // name of asset
    private int numberOfCriticalElements;
    private int [] criticalElements;
    
    public int numberOfObjectives; // number of objectives    
    public double discountRate; // discount rate    
    public int timeHorizon; // number of time intervals in the future when the sistem is evaluated    
    public int numberOfSamples; // number of sample points    
    public double criticalState; // critical state for asset        
    public int numberOfComponents; // number of components in the asset
//    public double costReductionRate; // reduce cost when actions are applied for all components of the bridge
    
    public int numberOfStates;
    
    // variables used during optimization    
    public double timeInterval;
    
    public int [] numberOfActions; // number of actions for components
 
    public double [] statesOfAsset;
    
    public double [] annualCosts;        
    
    public int [] permutation;    
    
    public Component [] components; // components of the asset
    
    public Utils utils;
    
    public problem.asset.general.IO inout = new problem.asset.general.IO();
    
    ExecutorService threadPool;
    
    AbstractPerformanceFunction perfFunc;

    
    // constructor
    public Problem(String fileProblem, Utils utils)
    { 
        this.utils = utils;
        
        // read problem dafinition
        inout.readProblem(fileProblem);
        
        nameOfAsset = inout.getNameOfAsset();  
        numberOfCriticalElements = inout.getNumberOfCriticalElements();
        criticalElements = inout.getCriticalElements();
        
        numberOfObjectives = inout.getNumberOfObjectives();        
        discountRate = inout.getDiscountRate();        
        timeHorizon = inout.getTimeHorizon();        
        numberOfSamples = inout.getNumberOfSamples();        
        criticalState = inout.getCriticalStateOfAsset();        
        numberOfComponents = inout.getNumberOfComponents();
//        costReductionRate = inout.getCostReductionRate();      
 
        // initialize components
        initComponents();
        
        // initialize performance function
        if(nameOfAsset.equals("pavimento")){
            perfFunc = new PavementPerformanceFunction(components, statesOfAsset);
            numberOfStates = 5;
        }
        else if(nameOfAsset.equals("ponte")){
            perfFunc = new BridgePerformanceFunction(components, statesOfAsset, criticalElements);
            numberOfStates = numberOfComponents;
        }
        else{
            perfFunc = new ComponentPerformanceFunction(components, statesOfAsset);
            numberOfStates = 1;
        }

        
    } // constructor
    
    
    private void initComponents()
    { 
        
        // initialize simulators for components
        components = new Component[numberOfComponents];
        for(int k = 0; k < numberOfComponents; k++)
        {             
            inout.readComponent(k);
            components[k] = new Component(inout.getPerfIdxs(), inout.getTimeHorizon(), inout.getNumberOfSamples(), inout.getNumberOfActions(), inout.getCosts(), utils);
        }
        
        // initialize number of actions for components
        numberOfActions = new int[numberOfComponents];
        for(int k = 0; k < numberOfComponents; k++){
            numberOfActions[k] = components[k].getNumberOfActions();
        }
        
        statesOfAsset = new double[timeHorizon];        
        annualCosts = new double[timeHorizon];        
    }  
    
    
    private void sequentialExecution()
    {
        for(int k = 0; k < numberOfComponents; k++){
            components[k].execute();
        }
        
    }
    
    
    private void parallelExecution() //throws InterruptedException
    {
        // create executor
        threadPool = Executors.newFixedThreadPool(numberOfComponents);
        
        // submit and execute tasts
        for(int k = 0; k < numberOfComponents; k++){
            threadPool.execute(components[k]);
        }
        
        // shut down executor
        threadPool.shutdown();
        
        // Wait until all tasks are finished
        while(!threadPool.isTerminated()) {
            // wait
        }
        
    }
    
    
    // method to eval indiv
    public void evaluate (Solution solution)
    {        
        // set actions to components
        for(int k = 0; k < numberOfComponents; k++){
            components[k].setActions(solution.chrom[k]);
        }
        
        // run components
//        if(settings.execution.equals("parallel")){
            parallelExecution();
//        }
//        else{
//            sequentialExecution();
//        }
        
        // get states
        if(nameOfAsset.equals("ponte"))
        {
            for(int k = 0; k < numberOfComponents; k++)
            {
                for(int j = 0; j < timeHorizon; j++){
                    solution.states[k][j] = components[k].getStateFor(0, j);
                }
            }
        }
        else
        {
            for(int k = 0; k < numberOfStates; k++)
            {
                for(int j = 0; j < timeHorizon; j++){
                    solution.states[k][j] = components[0].getStateFor(k, j);
                }
            }            
        }        
        
        
        // minimize worst state and costs
        
        solution.objectives[0] = performanceFunction(); // calculate first objective
        
        solution.objectives[1] = costFunction(); // calculate second objective
        
        if(numberOfObjectives == 3){
            solution.objectives[2] = homogeneityFunction();
        }
        
        // constraint violation
        solution.cv = calcConstraint();     
        
        solution.cv += calcConstraint2();

    }    
    
 
    // performance is defined by max value
    public double maxState()
    {        
        // declare integral
        double maxValue = statesOfAsset[0];
        
        for(int j = 1; j < timeHorizon; j++)
        {
            if(statesOfAsset[j] > maxValue){
                maxValue = statesOfAsset[j];
            }
        }
        
        return maxValue;
    }    
    
    
    // performance is defined by the sum of states
    public double performanceFunction()
    {        
        // calculate states of asset
        perfFunc.execute();  
        
        // return worst state
        return maxState(); 
    }  
    
    
    // method to return cost with reduction for actions for all the components in the same year
    public double costFunction()
    {
        int j, k;
        
        for(j = 0; j < timeHorizon; j++)
        {
            annualCosts[j] = 0.0;
            for(k = 0; k < numberOfComponents; k++){
                annualCosts[j] += components[k].getCostAt(j);
            }
        }
        
        double budget = 0.0;
        for(j = 0; j < timeHorizon; j++){
            budget += annualCosts[j]/Math.pow(1.0 + discountRate, j);
        }
        
        return budget;
        
    } // getCost method
    
    
    // minimize variance
    private double homogeneityFunction()
    {
        double mean;
        double var;
        double max = Double.NEGATIVE_INFINITY;
        int i, j, k;
        
        for(j = 0; j < timeHorizon; j++)
        {
            mean = 0.0;
            for(k = 0; k < numberOfComponents; k++){
                mean += components[k].getStateFor(0, j);
            }
            mean /= numberOfComponents;
            var = 0.0;
            for(k = 0; k < numberOfComponents; k++){
                var += Math.pow(components[k].getStateFor(0, j) - mean, 2.0);
            }
            var /= numberOfComponents;
            if(var > max){
                max = var;
            }
        }
        
        return max;
    }
    
    
    // calc constr violateion
    private double calcConstraint()
    {
        // declare integral
        double cv = 0.0;
        for(int i = 0; i < timeHorizon-1; i++){
            cv +=  timeInterval*(Math.max(statesOfAsset[i+1]-criticalState, 0.0)+Math.max(statesOfAsset[i]-criticalState, 0.0)) /2.0 ;
        }
        
        return cv;
    }
    
    
    // calc constr violateion to force not perform action in the concequitive periods
    private double calcConstraint2()
    {
        // declare integral
        double cv = 0.0;
        for(int i = 0; i < timeHorizon-1; i++)
        {
            if(annualCosts[i] > 0 && annualCosts[i+1] > 0){
                cv ++;
            }
        }
        
        return cv;
    }
    
    
    public void setCurrentNumberOfSamples(int currentNumberOfSamples)
    {
        this.numberOfSamples = currentNumberOfSamples;
        for(int k = 0; k < numberOfComponents; k++){
            components[k].setCurrentNumberOfSamples(currentNumberOfSamples);
        }
    }
    
    
    public double [] calcStatesForAsset(double [][] states)
    {
        if(nameOfAsset.equals("ponte"))
        {
            for(int k = 0; k < numberOfComponents; k++)
            {
                for(int j = 0; j < timeHorizon; j++){
                    components[k].setState(0, j, states[k][j]);
                }
            }
        }
        else
        {
            for(int k = 0; k < numberOfStates; k++)
            {
                for(int j = 0; j < timeHorizon; j++){
                    components[0].setState(k, j, states[k][j]);
                }
            }
        }
        
        // calculate states of asset
        perfFunc.execute();
        
        return statesOfAsset;
    }
    
    
    public double [] calcCostsForAsset(int [][] actions)
    {        
        int j, k;
        
        for(j = 0; j < timeHorizon; j++)
        {
            annualCosts[j] = 0.0;
            for(k = 0; k < numberOfComponents; k++)
            {
                annualCosts[j] += components[k].costs[actions[k][j]];
            }
        }
        
        return annualCosts;
    }
    
    
} // class Problem
