package moead;


import problem.*;
import util.*;


public class MOEAD
{
    
    Tuberculosis problem = new Tuberculosis();
//    LZ09F problem = new LZ09F();
    
    Settings settings = new Settings();
    
    Initialization initialization = new Initialization();
    
    Variation variation = new Variation();
    
    Outcome outcome = new Outcome();
    
    Utils utils = new Utils();
    
    Random rand = new Random();
    
    Solution [] population = new Solution[settings.mu]; // Stores the population
    
    Solution offspring = new Solution();
    
    double [][] lambda = new double[settings.mu][problem.numberOfObjectives]; // weight vectors
    
    int [][] neighborhood = new int[settings.mu][settings.T]; // Neighborhood
    
    int [] whole = new int[settings.mu]; // whole population
    
    int [] matingPool;
    
    double [] ideal = new double[problem.numberOfObjectives]; // ideal point
    
    double [] nadir = new double[problem.numberOfObjectives]; // nadir point of PF
    
    boolean update; // to make update norm obj
    
    java.util.Scanner fin;
    
    /**
     * constructor
     */
    public MOEAD()
    {
        
        
    } // constructor
    
    
    // method to run solver
    public void run()
    {
        
        // Initialize population
        initialization.perform(population, offspring);
        
        // generate weigt vectors
        initWeightVectors();
        
        // compute euclidean distances between weight vectors and find T
        initNeighborhood();
        
        // initialize z
        initReferencePoint();
        
        // EVOLUTIONARY PROCESS
        int g, i;
        for(g = 1; g <= settings.maxGen; g++)
        {
            
            // one generation
            for(i = 0; i < settings.mu; i++)
            {
                
                // select mating pool based on probability
                matingSelection(i);
                
                // variation to produce offspring
                variation.perform(population, offspring, matingPool, i);
                
                // update z
                updateReferencePoint();
                
                // normalize objectives
                normalization();
                
                // update of solutions
                environmentalSelection();
                
            } // for
            
//            if(g%50 == 0){
//                outcome.save(population, g);
//            }
        }
        
        // save results
        outcome.save(population);
        
    } // run
    
    
    /**
     * initUniformWeight
     */
    public void initWeightVectors()
    {
        if(problem.numberOfObjectives == 2)
        {
            for (int n = 0; n < settings.mu; n++)
            {
                double a = 1.0 * n / (settings.mu - 1.0);
                lambda[n][0] = a;
                lambda[n][1] = 1.0 - a;
            } // for
        }// if
        else
        {
            // file containing set of points
            StringBuilder file = new StringBuilder("weights/");
            file.append(settings.mu);
            file.append("/");
            file.append("m");
            file.append(problem.numberOfObjectives);
            file.append("_");
            file.append(settings.mu);
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
            for (i = 0; i < settings.mu; i++)
            {
                // read hyperplane from file
                for (j = 0; j < problem.numberOfObjectives; j++)
                    lambda[i][j] = Double.parseDouble(fin.next());
            }
            
            // close file
            fin.close();
        }
        
    } // initUniformWeight
    
    
    /**
     *initNeighborhood
     */
    private void initNeighborhood()
    {
        double[] x = new double[settings.mu];
        int[] idx = new int[settings.mu];
        
        for (int i = 0; i < settings.mu; i++)
        {
            // calculate the distances based on weight vectors
            for (int j = 0; j < settings.mu; j++)
            {
                x[j] = utils.distVector(lambda[i], lambda[j]);
                idx[j] = j;
            } // for
            
            // find 'niche' nearest neighboring subproblems
            utils.minFastSort(x, idx, settings.mu, settings.T);
            
            for(int k = 0; k < settings.T; k++){
                neighborhood[i][k] = idx[k];
            }
        } // for
        
        
        // initialize pool representing whole population
        for(int i = 0; i < settings.mu; i++){
            whole[i] = i;
        }
        
    } // initNeighborhood
    
    
    
    /**
     * method to initialize reference point
     */
    private void initReferencePoint()
    {
        int i, j;
        for (j = 0; j < problem.numberOfObjectives; j++){
            ideal[j] = population[0].objectives[j];
            nadir[j] = population[0].objectives[j];
        }
        
        for(i = 1; i < settings.mu; i++)
        {
            for(j = 0; j < problem.numberOfObjectives; j++)
            {
                if (population[i].objectives[j] < ideal[j]){
                    ideal[j] = population[i].objectives[j];
                }
                if (population[i].objectives[j] > nadir[j]){
                    nadir[j] = population[i].objectives[j];
                }
            }
        }
        
        update = true;
        
    } // initIdealPoint
    
    
    /**
     * select mating pool
     * @param i : index of subproblem
     */
    public void matingSelection(int i)
    {
        // mating selection based on probability
        if (rand.nextDouble() < settings.delta){
            matingPool = neighborhood[i]; // neighborhood
        }
        else{
            matingPool = whole; // whole population
        }
        
    } // matingSelection
    
    
    
    /**
     * update reference point
     */
    private void updateReferencePoint()
    {
        for(int i = 0; i < problem.numberOfObjectives; i++)
        {
            if (offspring.objectives[i] < ideal[i]){
                ideal[i] = offspring.objectives[i];
                update = true;
            }
            if (offspring.objectives[i] > nadir[i]){
                nadir[i] = offspring.objectives[i];
                update = true;
            }
        }
    } // updateReference
    
    
    /**
     * normalization of objective values
     */
    private void normalization()
    {
        int i, j;
        
        // normalize pop members
        if(update)
        {
            for(i = 0; i < settings.mu; i++)
            {
                for(j = 0; j < problem.numberOfObjectives; j++){
                    population[i].nf[j] = (population[i].objectives[j] - ideal[j])/(nadir[j] - ideal[j]);
                }
            }
        }
        
        // normalize offspring
        for(j = 0; j < problem.numberOfObjectives; j++){
            offspring.nf[j] = (offspring.objectives[j] - ideal[j])/(nadir[j] - ideal[j]);
        }
        
        update = false;
    }
    
    
    
    
    /**
     * environmental selection
     */
    private void environmentalSelection()
    {
        // some vars
        int size = matingPool.length;
        int [] indexes = new int[size];
        utils.randomPermutation(indexes, size);
        int time = 0;
        int i, j, k;
        double f1, f2;
        
        // perf selection
        for(int idx : indexes)
        {
            k = matingPool[idx];
            
            // calculate the values of objective function regarding the current subproblem
            f1 = fitnessFunction(population[k].nf, lambda[k]);
            f2 = fitnessFunction(offspring.nf, lambda[k]);
            
            if(f2 < f1)
            {
                // check wether components of replaced are in nadir point
                if(!update)
                {
                    for(j = 0; j < problem.numberOfObjectives; j++)
                    {
                        if(nadir[j] == population[k].objectives[j])
                        {
                            update = true;
                            break;
                        }
                    }
                }
                // replace individual
                System.arraycopy(offspring.variables, 0, population[k].variables, 0, problem.numberOfVariables);
                System.arraycopy(offspring.objectives, 0, population[k].objectives, 0, problem.numberOfObjectives);
                System.arraycopy(offspring.nf, 0, population[k].nf, 0, problem.numberOfObjectives);
                time++;
            }
            // the maximal number of solutions updated is not allowed to exceed 'limit'
            if (time >= settings.nr){
                break;
            }
        }
        
        // update nadir point
        if(update)
        {
            System.arraycopy(population[0].objectives, 0, nadir, 0, problem.numberOfObjectives);
            
            for(i = 1; i < settings.mu; i++)
            {
                for(j = 0; j < problem.numberOfObjectives; j++)
                {
                    if(population[i].objectives[j] > nadir[j]){
                        nadir[j] = population[i].objectives[j];
                    }
                }
            }
        }
        
    } // updateProblem
    
    
    /**
     * method to calculate individual's fitness
     * @param objectives
     * @param lambda
     */
    private double fitnessFunction(double [] objectives, double[] lambda)
    {
        
        double fitness = 0.0, diff;
        
        if (settings.functionType.equals("CHB"))
        {
            double maxFun = Double.NEGATIVE_INFINITY;
            
            for(int n = 0; n < problem.numberOfObjectives; n++)
            {
                diff = objectives[n];
                
                double feval;
                if (lambda[n] == 0){
                    feval = 0.0001 * diff;
                }
                else{
                    feval = diff * lambda[n];
                }
                if(feval > maxFun) {
                    maxFun = feval;
                }
            } // for
            
            fitness = maxFun;
        } // if
        
        return fitness;
    } // fitnessEvaluation
    
    
} // MOEAD class
