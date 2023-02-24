package problem.asset.general;



public class PerformanceIndex
{
    private int timeHorizon;
    
    private int numberOfSamples;
    
    private int minState;
    
    private  int maxState;
    
    private  int numberOfStates;
    
    private double criticalState; // worst allowable condition state
    
    private double lastState; // last inspection
    
    private double elapsedTime; // initial states
    
    private double [][] Q; // intensity matrix
    
    private double [][] P; // transition matrix
    
    private ActionEffects [] actionEffects;
    
    private double [] states;
    
    private double [] next;
    
    private double [] current;
    
    private double [] temp;
    
    private double [] delta;
    
    
    private RandomSample [][] rand; // sample of random numbers
    
    private Sample [][] applied; // sample of action effects
    
    private util.Matrix matrix = new util.Matrix(); // object for matrix calculations
    
    private java.util.Random [] random = new java.util.Random[5]; // for random numbers
    
    
    public void initialize(int numberOfSamples, int timeHorizon)
    {
        this.timeHorizon = timeHorizon;
        this.numberOfSamples = numberOfSamples;
        
        // initialize
        states = new double[timeHorizon+1];
        next = new double[numberOfSamples];
        current = new double[numberOfSamples];
        temp = new double[numberOfSamples];
        delta = new double[numberOfSamples];
        
        // create samples
        rand = new RandomSample[numberOfSamples][timeHorizon];
        applied = new Sample[numberOfSamples][timeHorizon];      
        
        // initialize
        initRandomSample();
    }
    
    
    // modififed using seed
    public void initRandomSample()
    {
        int i, j;   
        
        // set seed for random numbers
        for(i = 0; i < 5; i++){
            random[i] = new java.util.Random((i+1)*1000);
        }  
   
        for(j = 0; j < timeHorizon; j++)
        {
            for(i = 0; i < numberOfSamples; i++)
            {
                applied[i][j] = new Sample();
                rand[i][j] = new RandomSample();
                rand[i][j].generate = random[0].nextDouble();
                rand[i][j].timeOfDelay = random[1].nextDouble();
                rand[i][j].improvement = random[2].nextDouble();
                rand[i][j].timeOfReduction = random[3].nextDouble();
                rand[i][j].rateOfReduction = random[4].nextDouble();
            }
        }        
    }
    
    
    private void calcTransMatrix(double timeInterval)
    {
        P = matrix.expm(matrix.product(Q, timeInterval));
    }
    
    
    // gen state method
    private int generateState(int idx, double r)
    {
        int i, j;
        double A;
        
        for(i = 0; i < numberOfStates; i++)
        {
            A = 0.0;
            // comulative distribution
            for(j = 0; j <= i; j++){
                A += P[idx][j];
            }
            
            // state
            if(r < A){
                break;
            }
        }
        
        return i;
    }
    
    
    public void initCurrent()
    {
        calcTransMatrix(elapsedTime);
        
        for(int i = 0; i < numberOfSamples; i++)
        {
            current[i] = Math.max((double)generateState((int)lastState, rand[i][0].generate), lastState);
            temp[i] = current[i];
        }
    }
    
    
    public void resetCurrent()
    {
        for(int i = 0; i < numberOfSamples; i++){
            current[i] = temp[i];
        }
    }
    
    
    public void updateCurrent()
    {
        for(int i = 0; i < numberOfSamples; i++)
        {
            current[i] = next[i];
            temp[i] = current[i];
        }
    }
    
    
    public void sampleEffects(int i, int t, int a)
    {
        int s = (int)Math.round(current[i]);
        applied[i][t].timeOfDelay = actionEffects[a].getTimeOfDelay(s, rand[i][t].timeOfDelay);
        applied[i][t].improvement = actionEffects[a].getImprovement(s, rand[i][t].improvement);
        applied[i][t].timeOfReduction = actionEffects[a].getTimeOfReduction(s, rand[i][t].timeOfReduction);
        applied[i][t].rateOfReduction = actionEffects[a].getRateOfReduction(s, rand[i][t].rateOfReduction);
    }    
    
    
    public void sampleNext(int i, int t, double timeInterval)
    {
        calcTransMatrix(timeInterval*delta[i]);
        
        next[i] = (double)generateState((int)current[i], rand[i][t].generate);
        
        if(next[i] < current[i]){
            next[i] = current[i];
        }
    }
    
    
    public void improvement(int i, int t)
    {    
        // accao corretiva
        if(applied[i][t].improvement > 0){
            current[i] = Math.max(current[i], Math.round(current[i]))- applied[i][t].improvement;
        }  
    }
    
    
    public void findDelta(int i, int t)
    {
        delta[i] = 1.0;
        
        // compute rate of reduction
        for(int t0 = 0; t0 <= t; t0++)
        {
            if(applied[i][t0].timeOfReduction-(t-t0) > 0 && applied[i][t0].rateOfReduction < delta[i]){
                delta[i] = applied[i][t0].rateOfReduction;
            }
            
            if(applied[i][t0].timeOfDelay-(t-t0) > 0){
                delta[i] = 0.0; // there is suppression (P - is identity matrix)
                break;
            }
        }
    }
    
    
    public void calcStates(int t)
    {
        states[t] = 0.0;
        states[t+1] = 0.0;
        for(int i = 0; i < numberOfSamples; i++)
        {
            states[t] += current[i];
            states[t+1] += next[i];
        }
        states[t] /= numberOfSamples;
        states[t+1] /= numberOfSamples;
    }
    
    
    public boolean violationAt(int t)
    {
        if(states[t+1] > criticalState){
            return true;
        }
        else{
            return false;
        }
    }
    
    // get methods ------------------------
    public double getStateAt(int j)
    {
        return states[j] + minState;
    }
    //--------------------------------------
    
    // set methods ------------------------
    public void setMinState(int minState)
    {
        this.minState = minState;
    }
    
    public void setMaxState(int maxState)
    {
        this.maxState = maxState;
    }
    
    public void setNumberOfStates(int numberOfStates)
    {
        this.numberOfStates = numberOfStates;
    }
    
    public void setCriticalState(double criticalState)
    {
        this.criticalState = criticalState;
    }
    
    public void setLastState(double lastState)
    {
        this.lastState = lastState;
    }
    
    public void setState(int j, double s) // is used, before print values into file
    {
        this.states[j] = s - minState;
    }
    
    public void setElapsedTime(double elapsedTime)
    {
        this.elapsedTime = elapsedTime;
    }
    
    public void setQ(double [][] Q)
    {
        this.Q = Q;
    }
    
    public void setActionEffects(ActionEffects [] actionEffects)
    {
        this.actionEffects = actionEffects;
    }
    
    public void setCurrentNumberOfSamples(int currentNumberOfSamples)
    {
        this.numberOfSamples = currentNumberOfSamples;
    }
    //--------------------------------------
    
    
    public boolean produceEffects(int i, int t)
    {        
        if(applied[i][t].timeOfDelay > 0 || applied[i][t].improvement > 0 || applied[i][t].timeOfReduction > 0){
            return true;
        }else{
            return false;
        }
    }
    
} // class
