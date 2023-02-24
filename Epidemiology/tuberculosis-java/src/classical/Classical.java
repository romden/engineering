package classical;


import problem.Tuberculosis;

import java.util.Arrays;
import java.util.Formatter;
import java.util.Scanner;

import org.apache.commons.math3.optimization.PointValuePair;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.direct.BOBYQAOptimizer;


public class Classical
{
    
    Tuberculosis problem = new Tuberculosis();
    
    ObjectiveFunction objFunction = new ObjectiveFunction();
    
    
    int numberOfSubproblems = 100;
    
    double [][] weights = new double[numberOfSubproblems][problem.numberOfObjectives]; // weight vectors
    double [] ideal = new double[problem.numberOfObjectives]; // ideal point
    double [] nadir = new double[problem.numberOfObjectives]; // nadir point of PF
    
    double [] epsConstraints;
    
    double [][] xx = new double[numberOfSubproblems][]; // approx x
    double [][] yy = new double[numberOfSubproblems][]; // approx y
    
    private Scanner fin;
    private Formatter fout;
    
    void initComponents()
    {
        initWeightVectors();
        
        epsConstraints = linspace(0.0, 1.0, 100);
        
        // init ideal and nadir points
        double [][] temp = new double[2][];
        temp[0] = problem.execute(problem.lowerLimit);
        temp[1] = problem.execute(problem.upperLimit);
        for(int j = 0; j < problem.numberOfObjectives; j++)
        {
            ideal[j] = Double.POSITIVE_INFINITY;
            nadir[j] = Double.NEGATIVE_INFINITY;
            for(int i = 0; i < 2; i++)
            {
                if(ideal[j] > temp[i][j]){
                    ideal[j] = temp[i][j];
                }
                if(nadir[j] < temp[i][j]){
                    nadir[j] = temp[i][j];
                }
            }
        }
        
        
        for(int i = 0; i < numberOfSubproblems; i++)
        {
            xx[i] = new double[problem.numberOfVariables];
            yy[i] = new double[problem.numberOfVariables];
            for(int j = 0; j < problem.numberOfVariables; j++){
                xx[i][j] = 0.0;
            }
            for(int j = 0; j < problem.numberOfObjectives; j++){
                yy[i][j] = 0.0;
            }
        }   
        
    }
    
    // method to adjust intensity matrix using single-objective optimization
    public void execute()
    {
        
        initComponents();
        
        // parameter settings for solver
        int numberOfInterpolationPoints = problem.numberOfVariables+2;//problem.numberOfVariables+2;//2*problem.numberOfVariables+1;
        double initialTrustRegionRadius = 0.016;//0.1;
        double stoppingTrustRegionRadius = 1e-4;//1e-8;
        int  maxEval = 50000; // 50000       
        
        // create object
        BOBYQAOptimizer babyqa = new BOBYQAOptimizer(numberOfInterpolationPoints,
                initialTrustRegionRadius,
                stoppingTrustRegionRadius);      
        
        double [] x0 = new double[problem.numberOfVariables]; // initizal point
        
        // first
        xx[0] = new double[problem.numberOfVariables];
        Arrays.fill(xx[0], 0.0);
        for(int i = 1; i < numberOfSubproblems-1; i++) // for(int i = 1; i < 2; i++) // 
        {
            System.out.println(i);
            // objective function
            objFunction.setMethodType("wsum");
            objFunction.setIdealPoint(ideal);
            objFunction.setNadirPoint(nadir);
            objFunction.setWeightVector(weights[i]);
//            objFunction.setWeightVector(weights[numberOfSubproblems-(i+1)]); // for pbi
//            objFunction.setConstraint(epsConstraints[i]);
//            objFunction.setLp(2);
            
            // initial point
            System.arraycopy(xx[i-1], 0, x0, 0, problem.numberOfVariables);
            // perform optimization
            PointValuePair result = babyqa.optimize(maxEval, objFunction, GoalType.MINIMIZE, x0, problem.lowerLimit, problem.upperLimit);
            // get results
            xx[i] = result.getPoint();            
        }
        // last
        xx[numberOfSubproblems-1] = new double[problem.numberOfVariables];
        Arrays.fill(xx[numberOfSubproblems-1], 1.0);
        
        // recalc obj
        for(int i = 0; i < numberOfSubproblems; i++){
            yy[i] = problem.execute(xx[i]);
        }
        
        // save results
        saveObjectives();
        saveVariables();
        
    } // method adjust
    
    
    
    // method to save objectives
    public void saveObjectives()
    {
        //open file
        foutOpen("FUN.dat");
        
        // save values
        int i, j;
        for (i = 0; i < numberOfSubproblems; i++)
        {
            // save fun values
            for (j = 0; j < yy[i].length; j++){
                fout.format("%s ", yy[i][j]);
            }
            // new line
            fout.format("%n");
        }
        
        // close file
        foutClose();
        
    } // method saveObjectives
    
    
    // method to save objectives
    public void saveVariables()
    {
        //open file
        foutOpen("VAR.dat");
        
        // save values
        int i, j;
        for (i = 0; i < numberOfSubproblems; i++)
        {
            // save fun values
            for (j = 0; j < xx[i].length; j++){
                fout.format("%s ", xx[i][j]);
            }
            // new line
            fout.format("%n");
        }
        
        // close file
        foutClose();
        
    } // method saveObjectives
    
    
    private void foutOpen(String fileName)
    {
        try {
            fout = new Formatter(fileName);
        }
        catch (Exception e) {
            System.out.println("error opening " + fileName + " : " + e);
        }
    }
    
    private void foutClose()
    {
        fout.close();
    }
    
    
    private double [] linspace(double p1, double p2, int np)
    {
        double space = (p2 - p1) / (np-1);
        double [] vect = new double[np];
        
        vect[0] = p1;
        for(int i = 1; i < np-1; i++){
            vect[i] = vect[i-1] + space;
        }
        vect[np-1] = p2;
        
        return vect;
    }
    
    
    
    /**
     * initUniformWeight
     */
    public void initWeightVectors()
    {
        if(problem.numberOfObjectives == 2)
        {
            for(int n = 0; n < numberOfSubproblems; n++)
            {
                double a = 1.0 * n / (numberOfSubproblems - 1.0);
                weights[n][0] = a;
                weights[n][1] = 1.0 - a;
            } // for
        }// if
        else
        {
            // file containing set of points
            StringBuilder file = new StringBuilder("weights/");
            file.append(numberOfSubproblems);
            file.append("/");
            file.append("m");
            file.append(problem.numberOfObjectives);
            file.append("_");
            file.append(numberOfSubproblems);
            file.append(".dat");
            
            // create scanner to read file
            try{
                fin = new java.util.Scanner(new java.io.File(file.toString()));
            }
            catch (Exception e){
                System.out.println("rrror openning " + file.toString() + " : " + e);
            }
            
            // read values from file
            int i, j;
            for (i = 0; i < numberOfSubproblems; i++)
            {
                // read hyperplane from file
                for (j = 0; j < problem.numberOfObjectives; j++)
                    weights[i][j] = Double.parseDouble(fin.next());
            }
            
            // close file
            fin.close();
        }
        
    } // initUniformWeight
    
    
} // class




//        ideal = new double[]{2.468503858671811e+04, 0};//problem.execute(problem.upperLimit);
//        nadir = new double[]{3.178602648208461e+04, 10};//problem.execute(problem.lowerLimit);
//

//        for(int i = 0; i < numberOfSubproblems; i++)
//        {
//            for(int j = 0; j < problem.numberOfVariables; j++){
//                xx[i][j] = 0.0;
//            }
//            for(int j = 0; j < problem.numberOfObjectives; j++){
//                yy[i][j] = 0.0;
//            }
//        }   