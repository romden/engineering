package moead;

import problem.Problem;
import util.Utils;



public class MOEAD
{
    
    private Problem problem;
    
    private Settings settings;
    
    private Utils utils;
    
    private java.util.Random rand;
    
    private Solution [] population; // Stores the population
    
    private Solution offspring;
    
    private double [][] lambda; // weight vectors
    
    private int [][] neighborhood; // Neighborhood
    
    private int [] whole; // whole population
    
    private int [] matingPool;
    
    private double [] ideal; // ideal point
    
    private double [] nadir; // nadir point of PF
    
    private double [] cv = new double[2]; // min and max constrant violations
    
    private double [] s = new double[]{0.01, 20}; // penalty parameters
    
    private double tau; // violation threshold
    
    String outputFile;
    String folderWeights;
    
    Archive archive;
    
    IO io = new IO();
    
    
    // constructor
    public MOEAD(){
        
    }
    
    // constructor
    public MOEAD(String problemFile, String settingsFile, String outputFile, String folderWeights)
    {
        this.outputFile = outputFile;
        
        this.folderWeights = folderWeights;
        
        problem = new Problem(problemFile);
        
        settings = new Settings(settingsFile);
        
        rand = new java.util.Random(settings.seed);
        
        utils = new Utils(rand);
        
        population = new Solution[settings.mu]; // Stores the population
        
        offspring = new Solution();
        
        lambda = new double[settings.mu][problem.m]; // weight vectors
        
        neighborhood = new int[settings.mu][settings.T]; // Neighborhood
        
        whole = new int[settings.mu]; // whole population
        
        ideal = new double[problem.m]; // ideal point
        
        nadir = new double[problem.m]; // nadir point of PF
        
        archive = new Archive(settings.mu+1, problem.m, problem.n);
        
    } // constructor
    
    
    // method to run solver
    public void execute()
    {
        
        // generate weigt vectors
        initWeightVectors();
        
        // compute euclidean distances between weight vectors and find T
        initNeighborhood();
        
        // Initialize population
        initPopulation();
        
        // EVOLUTIONARY PROCESS
        int g, i;
        for(g = 1; g <= settings.maxGen; g++)
        {
            //System.out.println("gen " + g);
            // one generation
            for (i = 0; i < settings.mu; i++)
            {
                // select mating pool based on probability
                matingSelection(i);
                
                // variation to produce offspring
                variation();
                
                // update of solutions
                environmentalSelection();
                
            } // for i
            
        } // for g
        
        // save results
        save();
        
    } // run
    
    
    private void initWeightVectors()
    {
        if(problem.m == 2)
        {
            for (int n = 0; n < settings.mu; n++)
            {
                double a = 1.0 * n / (settings.mu - 1.0);
                lambda[n][0] = a;
                lambda[n][1] = 1.0 - a;
            } // for
        }
        else
        {
            // open file containing set of points
            io.finOpen(String.format("%s/%d/m%d_%d.dat", folderWeights, settings.mu, problem.m, settings.mu));;
            
            // read values from file
            int i, j;
            for (i = 0; i < settings.mu; i++)
            {
                // read hyperplane from file
                for (j = 0; j < problem.m; j++){
                    lambda[i][j] = Double.parseDouble(io.fin.next());
                }
            }
            
            io.finClose(); // close file
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
    
    
    // method to perform initialization
    private void initPopulation()
    {
        // temp vars
        int i, j;
        
        // generate initial population
        for(i = 0; i < settings.mu; i++)
        {
            // initialize population
            population[i] = new Solution();
            population[i].x = new int[problem.n];
            population[i].f = new double[problem.m];
            population[i].cv = 0.0;
            
            // generate idividual in the decision space
            if(i == 0){
                for(j = 0; j < problem.n; j++){
                    population[i].x[j] = 0;
                }
            }
            else{
//                if(problem.problemType == 1){
//                    java.util.Arrays.fill(population[i].x, i-1);
//                }
//                else{
                for(j = 0; j < problem.n; j++){
                    population[i].x[j] = rand.nextInt(problem.ub[j]);
                }
//                }
            }
            
            // evaluation
            evaluation(population[i]);
        }
        
        // initialize population
        offspring = new Solution();
        offspring.x = new int[problem.n];
        offspring.f = new double[problem.m];
        offspring.cv = 0.0;
    }
    
    
    // evaluation procedure
    private void evaluation(Solution individual)
    {
        // evaluate
        problem.evaluate(individual);
        
        // update ref point
        for(int j = 0; j < problem.m; j++)
        {
            if(individual.f[j] < ideal[j]){
                ideal[j] = individual.f[j];
            }
            if(individual.f[j] > nadir[j]){
                nadir[j] = individual.f[j];
            }
        }
        
        archive.add(individual);
    }
    
    
    /**
     * select mating pool
     * @param i : index of subproblem
     */
    private void matingSelection(int i)
    {
        // mating selection based on probability
        if (rand.nextDouble() < settings.delta){
            matingPool = neighborhood[i]; // neighborhood
        }
        else{
            matingPool = whole; // whole population
        }
        
    } // matingSelection
    
    
    private void calcConstraintParameters()
    {
        cv[0] = Double.POSITIVE_INFINITY;
        cv[1] = Double.NEGATIVE_INFINITY;
        
        for(int idx : matingPool)
        {
            if(population[idx].cv < cv[0]){
                cv[0] = population[idx].cv;
            }
            if(population[idx].cv > cv[1]){
                cv[1] = population[idx].cv;
            }
        }
        
        tau = s[0] + 0.3*(s[1] - s[0]);
    }
    
    
    private double penaltyFunction(double cv)
    {
        double penalty;
        if(cv < tau){
            penalty = s[0]*(cv*cv);
        }
        else{
            penalty = s[0]*(tau*tau) + s[1]*(cv - tau);
        }
        
        return penalty;
    }
    
    
    // update nadir point
    private void updateNadir()
    {
        System.arraycopy(offspring.f, 0, nadir, 0, problem.m);
        int i, j;
        for(i = 0; i < settings.mu; i++)
        {
            for(j = 0; j < problem.m; j++)
            {
                if(population[i].f[j] > nadir[j]){
                    nadir[j] = population[i].f[j];
                }
            }
        }
    }
    
    /**
     * environmental selection
     */
    private void environmentalSelection()
    {
        //updateNadir();
        
        // first calculate  cv min max and tau in the mating pool (params for penalty function)
        calcConstraintParameters();
        
        // some vars
        int size = matingPool.length;
        int [] indexes = new int[size];
        utils.randomPermutation(indexes, size);
        int time = 0;
        int k;
        double f1, f2;
        
        // perf selection
        for(int idx : indexes)
        {
            k = matingPool[idx];
            
            // calculate the values of objective function regarding the current subproblem
            f1 = fitnessFunction(population[k].f, lambda[k]) + penaltyFunction(population[k].cv);
            f2 = fitnessFunction(offspring.f, lambda[k]) + penaltyFunction(offspring.cv);
            
            if(f2 < f1)
            {
                // replace individual
                System.arraycopy(offspring.x, 0, population[k].x, 0, problem.n);
                System.arraycopy(offspring.f, 0, population[k].f, 0, problem.m);
                population[k].cv = offspring.cv;
                time++;
            }
            // the maximal number of solutions updated is not allowed to exceed 'limit'
            if (time >= settings.nr){
                break;
            }
        }
        
    } // updateProblem
    
    
    
    /**
     * method to calculate individual's fitness
     * @param objectives
     * @param lambda
     */
    private double fitnessFunction(double [] objectives, double [] lambda)
    {
        // Chebyshev method
        double temp, fitness = Double.NEGATIVE_INFINITY;
        
        for(int j = 0; j < problem.m; j++)
        {
            temp = lambda[j] * (objectives[j] - ideal[j])/(nadir[j] - ideal[j]);
            
            if(temp > fitness){
                fitness = temp;
            }
        } // for
        
        
        return fitness;
    } // fitnessEvaluation
    
    
    
    // method to perform variation
    public void variation()
    {
        // recombination
        twoPointCrossover();
        
        // mutation
        mutation();
        
        // evaluate offspring
        evaluation(offspring);
        
    } // perform method
    
    
    // metod to recombine parent
    private void twoPointCrossover()
    {
        // select two parents from mating pool
        int poolSize = matingPool.length;
        int p1 = matingPool[rand.nextInt(poolSize)];
        int p2 = matingPool[rand.nextInt(poolSize)];
        while(p1 == p2){
            p2 = matingPool[rand.nextInt(poolSize)];
        }
        
        // declare some variables
        int i, point1, point2, tempPoint;
        
        // if there is no crossover
        System.arraycopy(population[p1].x, 0, offspring.x, 0, problem.n);
        
        // if crossover
        if(Math.random() <= settings.pc)
        {
            // generate crossover points
            point1 = rand.nextInt(problem.n);
            tempPoint = rand.nextInt(problem.n);
            while(tempPoint == point1){
                tempPoint = rand.nextInt(problem.n);
            }
            if(point1 < tempPoint){
                point2 = tempPoint;
            }
            else{
                point2 = point1;
                point1 = tempPoint;
            }
            
            // for each gene exchange genetic information
            for(i = point1; i <= point2; i++){
                offspring.x[i] = population[p2].x[i];
            }
        }
    }
    
    
    // method to mutate offspring
    private void mutation()
    {
        // mutation
        for(int i = 0; i < problem.n; i++)
        {
            if(Math.random() < settings.pm){
                offspring.x[i] = rand.nextInt(problem.ub[i]);
            }
        }
    }
    
    
    // save results
    public void save()
    {
        io.set(problem);
        
        io.save(outputFile, ".pop", population);
        
        io.save(outputFile, ".arc", archive.members);
    }  
    
    
} // MOEAD class









//    // method to perform variation using one-point crossover and mutation
//    public void onePointCrossover()
//    {
//        // declare some variables
//        int i, p1, p2, point;
//
//        // select two parents from mating pool
//        p1 = matingPool[rand.nextInt(matingPool.length)];
//        p2 = matingPool[rand.nextInt(matingPool.length)];
//        while(p1 == p2){
//            p2 = matingPool[rand.nextInt(matingPool.length)];
//        }
//
//        // crossover with probability pc
//        if(Math.random() < settings.pc)
//        {
//            // generate crossover point
//            point = rand.nextInt(problem.timeHorizon);
//
//            // for each gene exchange genetic information
//            for(i = 0; i < problem.timeHorizon; i++)
//            {
//                // perform one-point crossover
//                if(i < point){
//                    offspring.actions[i] = population[p1].actions[i];
//                }
//                else{
//                    offspring.actions[i] = population[p2].actions[i];
//                }
//            }
//        }
//        else{
//            // there is no crossover
//            System.arraycopy(population[p1].actions, 0, offspring.actions, 0, problem.timeHorizon);
//        }
//
//        // mutation
//        for(i = 0; i < problem.timeHorizon; i++)
//        {
//            if(Math.random() < settings.pm){
//                offspring.actions[i] = rand.nextInt(problem.numberOfActions);
//            }
//        }
//
//        // evaluate offspring
//        problem.evaluate(offspring);
//
//
//    } // onePointCrossover method