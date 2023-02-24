package problem.asset.general;

import util.Utils;

public class Component implements Runnable
{
    private int timeHorizon; // number of time intervals in the future when the sistem is evaluated
    
    private int numberOfSamples; // number of sample points
    
    private double timeInterval = 1.0; // time interval between points
    
    private int numberOfActions; // number of actions
    
    private int numberOfIndexes; // number of performance indicators for asset
    
    private PerformanceIndex [] performanceIndex;
    
    public double [] costs;
    
    private int [] actions;
    
    private int [] permutation;
    
    private Utils utils;
    
    
    // constructor
    public Component(PerformanceIndex [] performanceIndex, int timeHorizon, int numberOfSamples, int numberOfActions,  double [] costs, Utils utils)
    {
        this.performanceIndex = performanceIndex;
        this.timeHorizon = timeHorizon;
        this.numberOfSamples = numberOfSamples;
        this.numberOfIndexes = performanceIndex.length;
        this.numberOfActions = numberOfActions;
        this.costs = costs;
        this.utils = utils;
        
        // initialize
        for(int i = 0; i < numberOfIndexes; i++){
            performanceIndex[i].initialize(numberOfSamples, timeHorizon);
        }
        
        //actions  = new int[timeHorizon];
        
        permutation = new int[numberOfActions];
        
    } // constructor   
    
    
    
    private void initCurrent()
    {
        for(int i = 0; i < numberOfIndexes; i++){
            performanceIndex[i].initCurrent();
        }
    }
    
    
    private void resetCurrent()
    {
        for(int i = 0; i < numberOfIndexes; i++){
            performanceIndex[i].resetCurrent();
        }
    }
    
    
    private void updateCurrent()
    {
        for(int i = 0; i < numberOfIndexes; i++){
            performanceIndex[i].updateCurrent();
        }
    }
    
    
    private boolean violationAt(int t)
    {
        for(int i = 0; i < numberOfIndexes; i++)
        {
            if(performanceIndex[i].violationAt(t)){
                return true;
            }
        }
        
        return false;
    }
    
    
    // method to calcuçate next state applying MA
    private void simulateAt(int t)
    {
        int i, k;
        
        boolean flag = false; // indicate wether action produces effects
        
        // sample points
        for(k = 0; k < numberOfIndexes; k++)
        {
            for(i = 0; i < numberOfSamples; i++)
            {
                // sample action effects
                performanceIndex[k].sampleEffects(i, t, actions[t]);
                
                // accao corretiva
                performanceIndex[k].improvement(i, t);
                
                // find delta
                performanceIndex[k].findDelta(i, t);
                
                // sample next
                performanceIndex[k].sampleNext(i, t, timeInterval);
                
                // check whether action produces effects
                if(!flag)
                {
                    if (performanceIndex[k].produceEffects(i, t)){
                        flag = true; // effect is produced
                    }
                }
                
            } // for
            
            // calculate index
            performanceIndex[k].calcStates(t);
        }
        
        // mark as no action if affect is not produced
        if(!flag){
            actions[t] = 0;
        }
        
        
    } // maintenance method
    
    
    // method to predict states for all elements using Monte Carlo simulation
    public void execute()
    {
        // initialize samples of current states
        initCurrent();
        
        // calculate states with actions
        for(int t = 0; t < timeHorizon; t++)
        {
            // perform maintainance and calc next state
            simulateAt(t);
            
            if(violationAt(t))
            {
                utils.randomPermutation(permutation, numberOfActions);
                
                for(int a : permutation)
                {
                    actions[t] = a;
                    
                    resetCurrent();
                    
                    // perform maintainance and calc next state
                    simulateAt(t);
                    
                    if(!violationAt(t)){
                        break;
                    }
                    
                }
            }
            
            updateCurrent();
            
        } // for t
        
        
    } // calculate method
    
    
    // set methods -------------------------------------
    public void setCurrentNumberOfSamples(int currentNumberOfSamples)
    {
        this.numberOfSamples = currentNumberOfSamples;
        for(int i = 0; i < numberOfIndexes; i++){
            performanceIndex[i].setCurrentNumberOfSamples(currentNumberOfSamples);
        }
    }
    
    public void setAction(int j, int a)
    {
        actions[j] = a;
    }
    
    public void setActions(int [] actions)
    {
        this.actions = actions;
    }
    
    public void setState(int i, int j, double s)
    {
        performanceIndex[i].setState(j, s);
    }
    // -------------------------------------------------
    
    
    // get methods -------------------------------------
    public int getActionAt(int j)
    {
        return actions[j];
    }
    
    public double getCostAt(int j)
    {
        return costs[actions[j]];
    }
    
    public double getStateFor(int i, int j)
    {
        return performanceIndex[i].getStateAt(j);
    }
    
    public int getNumberOfActions()
    {
        return numberOfActions;
    }
    
    public int getNumberOfIndexes()
    {
        return numberOfIndexes;
    }
    // -------------------------------------------------
    
    
    @Override
    public void run(){
        execute();
    }
}