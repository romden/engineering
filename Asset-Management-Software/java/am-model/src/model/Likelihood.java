package model;




public class Likelihood
{
    
    DataBase dataBase; // database
    
    Matrix matrix = new Matrix(); // for matrix calculations
    
    int numberOfStates; // number of states
    
    double eps = 1e-16; // error for numerical stability
    
    double [][] Q; // intensity matrix
    
    double [][] P; // transition matrix
    
    
    // constructor
    public Likelihood(DataBase dataBase, int numberOfStates)
    {
        
        this.dataBase = dataBase;
        this.numberOfStates = numberOfStates;
        
        
        // initialize matrix Q
        Q = new double[numberOfStates][numberOfStates];
        int i, j;
        for(i = 0; i < numberOfStates; i++)
        {
            for(j = 0; j < numberOfStates; j++){
                Q[i][j] = 0.0;
            }
        }
        
    }  // constructor
    
    
    // method to calculate verisimilitude
    public double evaluate(double [] theta)
    {
        // temp vars
        int i, j;
        
        // build matrix Q
        for(i = 0; i < numberOfStates-1; i++)
        {
            Q[i][i] = -theta[i];
            Q[i][i+1] = theta[i];
        }
        
        // calculate V
        double V = 0.0;
        for(i = 0; i < dataBase.numberOfElements; i++)
        {
            for(j = 1; j < dataBase.numberOfInspections[i]; j++)
            {
                // calculate transition matrix
                P = matrix.expm(matrix.scalar(Q, dataBase.inspection[i][j].deltaT));
                V -= Math.log(Math.max(P[dataBase.inspection[i][j-1].c][dataBase.inspection[i][j].c], eps));
            }
        }
        
        return V;
        
    } // method  evaluate
    
    
} // end class
