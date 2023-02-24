package model;



import java.util.Arrays;
//import java.io.File;


import org.apache.commons.math3.optimization.PointValuePair;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.direct.BOBYQAOptimizer;
import org.apache.commons.math3.analysis.MultivariateFunction;


public class Model
{
    
    String dataFile; // name of the part
    
    int minState; // min state of asset
    
    int numberOfStates; // number of states
    
    String outputFile; // file for saving output
    
    double [] theta0; // before optimization
    
    double [] theta; // after optimization
    
    double eps = 0.01; // error fo numerical stability of theta
    
    DataBase dataBase; // database
    
    Likelihood likelihood; // likelihood object for optimization process
    
    ObjFunction objFunction = new ObjFunction();
    
    Matrix matrix = new Matrix(); // for matrix calculations

    
    // constructor
    public Model(String dataFile, int minState, int maxState, int timeSpan, String outputFile)
    {
        // INITIALIZE VARIABLES
        
        this.dataFile = dataFile;
        
        this.minState = minState;
        
        this.numberOfStates = maxState - minState + 1;
        
        this.outputFile = outputFile;
        
        
        // INITIALIZE OBJECTS
        
        dataBase = new DataBase(dataFile, minState, timeSpan);
        
        likelihood = new Likelihood(dataBase, numberOfStates);
        
    } // constructor
    
    
    // method to build a probabilistic model based on Markov process
    public void build()
    {
        // calculate Q based on the data
        calculate();
        
        // adjust the model built
        adjust();
        
        // save values to file
        save();
        
    } // method build
    
    
    // method to calculate intensity matrix
    public void calculate()
    {
        
        // intitialize theta
        theta0 = new double[numberOfStates-1];
        Arrays.fill(theta0, eps);
        
        // numero de elementos que transitou do estado i para j
        int [] n = new int[numberOfStates-1];
        Arrays.fill(n, 0);
        
        // o sumatorio dos intervalos de tempo entre observações, cujo estado inicial e i
        double [] sumDeltaT = new double[numberOfStates-1];
        Arrays.fill(sumDeltaT, 0.0);
        
        
        int i, j;
        for(i = 0; i < dataBase.numberOfElements; i++)
        {
            for(j = 1; j < dataBase.numberOfInspections[i]; j++)
            {
                if(dataBase.inspection[i][j-1].c < numberOfStates-1)
                {
                    // update sum of interavals
                    sumDeltaT[dataBase.inspection[i][j-1].c] += dataBase.inspection[i][j].deltaT;
                    
                    // if there was transition from the j-th state to the (j+1)-th state
                    if(dataBase.inspection[i][j-1].c + 1 == dataBase.inspection[i][j].c){
                        n[dataBase.inspection[i][j-1].c]++;                        
                    }
                    
                }
            }
        }
        
        // calculate theta
        for(i = 0; i < numberOfStates-1; i++)
        {
            if(sumDeltaT[i] > 0){
                theta0[i] = n[i] / sumDeltaT[i];
            }
        }
        
    } // method culculateIntensityMatrix
    
    
    // method to adjust intensity matrix using single-objective optimization
    public void adjust ()
    {
        
        // lower and upper bounds
        double [] lb = new double[numberOfStates-1];
        double [] ub = new double[numberOfStates-1];
        Arrays.fill(lb, eps);
        Arrays.fill(ub, 1.0-eps);
        
        // check bounds
        for(int i = 0; i < theta0.length; i++)
        {
            if(theta0[i] < lb[i]){
                theta0[i] = lb[i];
            }
            if(theta0[i] > ub[i]){
                theta0[i] = ub[i];
            }
        }
        
        // parameter settings for solver
        int numberOfInterpolationPoints = 2*theta0.length+1;
        double initialTrustRegionRadius = 0.5;
        double stoppingTrustRegionRadius = 1e-8;
        int  maxEval = 5000;
        
        // create object
        BOBYQAOptimizer babyao = new BOBYQAOptimizer(numberOfInterpolationPoints,
                initialTrustRegionRadius,
                stoppingTrustRegionRadius);
        
        // perform optimization
        PointValuePair result = babyao.optimize(maxEval, objFunction, GoalType.MINIMIZE, theta0, lb, ub);
        
        // get results
        theta = result.getPoint();
        
    } // method adjust
    
    
    // method to save intensity matrix to file
    public void save()
    {
        // build intensity matrix
        double [][] Q = new double[numberOfStates][numberOfStates];
        int i, j;
        for(i = 0; i < numberOfStates; i++)
        {
            for(j = 0; j < numberOfStates; j++)
                Q[i][j] = 0.0;
        }
        
        for(i = 0; i < numberOfStates-1; i++)
        {
            Q[i][i] = -theta[i];
            Q[i][i+1] = theta[i];
        }
        
        // save results
        matrix.output(outputFile, Q);
        
    }// method save
    
    
    // class defining obj fucntion
    class ObjFunction implements MultivariateFunction
    {
        
        public ObjFunction()
        {
            
        }
        
        @Override
        public double value(double[] point)
        {
            return likelihood.evaluate(point);
        }
    }
    
    
} // end class





        // show values to screen
//        print();
//    // method to print results to screen
//    public void print()
//    {
//        System.out.println("original theta \t theta after optimization ");
//        for(int i = 0; i < numberOfStates-1; i++)
//            System.out.println(theta0[i] + " \t " + theta[i]);
//        
//        System.out.println("verosimilhança ");
//        
//        System.out.println(likelihood.evaluate(theta0) + " \t " + likelihood.evaluate(theta));
//        
//    } // method print    
//    
//    
//    public double [] getTheta0()
//    {
//        return theta0;
//    }
//    
//    public double [] getTheta()
//    {
//        return theta;
//    }
//    
//    public double getV0()
//    {
//        return likelihood.evaluate(theta0);
//    }
//    
//    public double getV()
//    {
//        return likelihood.evaluate(theta);
//    }

