package perf;


import util.Matrix;



public class Simulation
{    
    
    int numberOfStates; // number of possible states    
    
    double [][] P; // transision matrix
    
    double [] states;  
    
    Matrix matrix = new Matrix(); // for matrix calculations
    
    
    // constructor
    public Simulation()
    {
        
        
    } // constructor
    
    
    // method to predict states for all elements using deterministic method
    public double [] analytic(int initialState, double elapsedTime, int timeHorizon, double [][] Q)
    {
        
        numberOfStates = Q.length;
        states = new double[timeHorizon];
        
        double [] previousState = new double[numberOfStates];
        double [] currentState;
        
        int i, j;
        double dt = 1.0;
        
        // state at the beginning (initial state)
        j = 0;
        
        // convert initial state to probability vector
        for(i = 0; i < numberOfStates; i++){
            previousState[i] = 0.0;
        }
        previousState[initialState] = 1.0;
        
        // calulate first state
        currentState = matrix.product(previousState, matrix.expm(matrix.product(Q, elapsedTime)));
        
        states[j] = 0.0;
        for(i = 0; i < numberOfStates; i++)
        {
            states[j] += currentState[i]*i;
            previousState[i] = currentState[i];
        }
        
        // calculate state for other time points
        for(j = 1; j < timeHorizon; j++)
        {
            // calculate next probabilistic state
            currentState = matrix.product(previousState, matrix.expm(matrix.product(Q, dt)));
            
            // convert to scalar state
            states[j] = 0.0;
            for(i = 0; i < numberOfStates; i++)
            {
                states[j] += currentState[i]*i;
                previousState[i] = currentState[i];
            }
        }
        
        return states;
        
    } // method deterministic
    
    
    // method to predict states for all elements using Monte Carlo simulation
    public double [] monteCarlo(int initialState, double elapsedTime, int timeHorizon, double [][] Q, int numberOfSamples)
    {
        
        this.numberOfStates = Q.length;
        this.states = new double[timeHorizon];
        
        int [][] sample  = new int[numberOfSamples][timeHorizon];
        
        int i, j, idx;
        double dt = 1.0;
        
        // calulate first state
        P = matrix.expm(matrix.product(Q, elapsedTime));
        for(i = 0; i < numberOfSamples; i++){
            sample[i][0] = generateState(initialState, Math.random());
        }
        
        // sample for time horizon
        for(j = 1; j < timeHorizon; j++)
        {
            // calculate transition matrix
            P = matrix.expm(matrix.product(Q, dt));
            
            for(i = 0; i < numberOfSamples; i++)
            {
                // index corresponding to the previous state
                idx = sample[i][j-1];
                
                // calculate state
                sample[i][j] = generateState(idx, Math.random());
            }
        }
        
        // calculate mean states
        for(j = 0; j < timeHorizon; j++)
        {
            states[j] = 0.0;
            for(i = 0; i < numberOfSamples; i++){
                states[j] += sample[i][j];
            }
            states[j] /=  numberOfSamples;
        }
        
        return states;
        
    } // method monte carlo
    
    
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
    
    
    
//    // calculate perf using monte carlo method
//    public double [] maintenance(double lastState,
//            double elapsedTime,
//            int timeHorizon,
//            int numberOfSamples,
//            double [][] Q,
//            int [] actions,
//            ActionEffects [] actionEffects)
//    {
//        
//        
//        numberOfStates = Q.length;
//        states = new double[timeHorizon];
//        Sample [][] sample = new Sample[numberOfSamples][timeHorizon];
//        
//        int i, j;
//        int action, s, t;
//        double delta, timeInterval = 1.0;
//        
//        // init current state        
//        for(j = 0; j < timeHorizon; j++)
//        {
//            for(i = 0; i < numberOfSamples; i++){
//                sample[i][j] = new Sample();
//            }
//        }      
//        
//        P = matrix.expm(matrix.product(Q, elapsedTime));
//        for(i = 0; i < numberOfSamples; i++){
//            sample[i][0].state = Math.max(generateState((int)lastState, Math.random()), lastState);
//        } 
//
//        
//        // calculate states with actions
//        for(j = 0; j < timeHorizon; j++)
//        {
//            states[j] = 0.0;
//            
//            action = actions[j];
//            
//            // sample points
//            for(i = 0; i < numberOfSamples; i++)
//            {
//                // reduction factor
//                delta = 1.0;
//                
//                // state integer
//                s = (int)Math.round(sample[i][j].state); // current state
//                
//                // get effects for current state
//                sample[i][j].timeOfDelay = actionEffects[action].getTimeOfDelay(s, Math.random());
//                sample[i][j].improvement = actionEffects[action].getImprovement(s, Math.random());
//                sample[i][j].timeOfReduction = actionEffects[action].getTimeOfReduction(s, Math.random());
//                sample[i][j].rateOfReduction = actionEffects[action].getRateOfReduction(s, Math.random());
//                
//                // accao corretiva
//                if(sample[i][j].improvement > 0){
//                    sample[i][j].state = Math.max(s, sample[i][j].state) - sample[i][j].improvement;
//                }                
//                
//                if(j < timeHorizon-1)
//                {                    
//                    // compute rate of reduction
//                    for(t = 0; t <= j; t++)
//                    {
//                        if(sample[i][t].timeOfReduction-(j-t) > 0 && sample[i][t].rateOfReduction < delta){
//                            delta = sample[i][t].rateOfReduction;
//                        }
//                        
//                        if(sample[i][t].timeOfDelay-(j-t) > 0){
//                            delta = 0.0; // there is suppression (P - is identity matrix)
//                            break;
//                        }
//                    }
//                    
//                    // compute transition matrix
//                    P = matrix.expm(matrix.product(Q, timeInterval*delta));
//                    
//                    // calculate next state
//                    sample[i][j+1].state = Math.max(generateState((int)sample[i][j].state, Math.random()), sample[i][j].state);
//                }
//                
//                // add
//                states[j] += sample[i][j].state;                
//            }
//            
//            // calculate state at time point j
//            states[j] /= numberOfSamples;            
//        }
//        
//        return states;
//    }
    
    
     // calculate perf using monte carlo method (Using random fixed seed)
    public double [] maintenance(double lastState,
            double elapsedTime,
            int timeHorizon,
            int numberOfSamples,
            double [][] Q,
            int [] actions,
            ActionEffects [] actionEffects)
    {
        
        
        java.util.Random [] random = new java.util.Random[5]; // for random numbers
        
                // set seed for random numbers
        for(int k = 0; k < 5; k++){
            random[k] = new java.util.Random((k+1)*1000);
        }  
        
        numberOfStates = Q.length;
        states = new double[timeHorizon];
        Sample [][] sample = new Sample[numberOfSamples][timeHorizon];
        
        int i, j;
        int action, s, t;
        double delta, timeInterval = 1.0;
        
        // init current state        
        for(j = 0; j < timeHorizon; j++)
        {
            for(i = 0; i < numberOfSamples; i++){
                sample[i][j] = new Sample();
            }
        }      
        
        P = matrix.expm(matrix.product(Q, elapsedTime));
        for(i = 0; i < numberOfSamples; i++){
            sample[i][0].state = Math.max(generateState((int)lastState, random[0].nextDouble()), lastState);
        } 

        
        // calculate states with actions
        for(j = 0; j < timeHorizon; j++)
        {
            states[j] = 0.0;
            
            action = actions[j];
            
            // sample points
            for(i = 0; i < numberOfSamples; i++)
            {
                // reduction factor
                delta = 1.0;
                
                // state integer
                s = (int)Math.round(sample[i][j].state); // current state
                
                // get effects for current state
                sample[i][j].timeOfDelay = actionEffects[action].getTimeOfDelay(s, random[1].nextDouble());
                sample[i][j].improvement = actionEffects[action].getImprovement(s, random[2].nextDouble());
                sample[i][j].timeOfReduction = actionEffects[action].getTimeOfReduction(s, random[3].nextDouble());
                sample[i][j].rateOfReduction = actionEffects[action].getRateOfReduction(s, random[4].nextDouble());
                
                // accao corretiva
                if(sample[i][j].improvement > 0){
                    sample[i][j].state = Math.max(s, sample[i][j].state) - sample[i][j].improvement;
                }                
                
                if(j < timeHorizon-1)
                {                    
                    // compute rate of reduction
                    for(t = 0; t <= j; t++)
                    {
                        if(sample[i][t].timeOfReduction-(j-t) > 0 && sample[i][t].rateOfReduction < delta){
                            delta = sample[i][t].rateOfReduction;
                        }
                        
                        if(sample[i][t].timeOfDelay-(j-t) > 0){
                            delta = 0.0; // there is suppression (P - is identity matrix)
                            break;
                        }
                    }
                    
                    // compute transition matrix
                    P = matrix.expm(matrix.product(Q, timeInterval*delta));
                    
                    // calculate next state
                    sample[i][j+1].state = Math.max(generateState((int)sample[i][j].state, random[0].nextDouble()), sample[i][j].state);
                }
                
                // add
                states[j] += sample[i][j].state;                
            }
            
            // calculate state at time point j
            states[j] /= numberOfSamples;            
        }
        
        return states;
    }
    
    
} // class Prediction


//System.out.println(numberOfStates);
