package moead;


import problem.Problem;
import util.Utils;


public class MOEAD
{
    
    Problem problem;
    
    Settings settings;
    
    Utils utils;
    
    java.util.Random rand;
    
    Solution [] population; // Stores the population
    
    Solution offspring;
    
    double [][] lambda; // weight vectors
    
    int [][] neighborhood; // Neighborhood
    
    int [] whole; // whole population
    
    int [] matingPool;
    
    double [] ideal; // ideal point
    
    double [] nadir; // nadir point of PF
    
    private double [] cv = new double[2]; // min and max constrant violations
    
    private double [] s = new double[]{0.01, 20}; // penalty parameters
    
    private double tau; // violation threshold    
    
    Archive archive;
    
    IO io = new IO();
    
    
    /**
     * constructor
     * @param fileProblem file containing problem definition
     * @param fileSettings file containing algorithm settings
     */
    public MOEAD(String fileProblem, String fileSettings)
    {
        settings = new Settings(fileSettings);
        
        rand = new java.util.Random(settings.seed);
        
        utils = new Utils(rand);
        
        problem = new Problem(fileProblem, utils);       
        
        population = new Solution[settings.mu]; // Stores the population
        
        offspring = new Solution();
        
        lambda = new double[settings.mu][problem.numberOfObjectives]; // weight vectors
        
        neighborhood = new int[settings.mu][settings.T]; // Neighborhood
        
        whole = new int[settings.mu]; // whole population
        
        ideal = new double[problem.numberOfObjectives]; // ideal point
        
        nadir = new double[problem.numberOfObjectives]; // nadir point of PF
        
        archive = new Archive(settings.mu+1, problem.numberOfObjectives, problem.numberOfComponents, problem.numberOfStates, problem.timeHorizon);
        
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
            // one generation
            for (i = 0; i < settings.mu; i++)
            {
                // select mating pool based on probability
                matingSelection(i);
                
                // variation to produce offspring
                variation();
                
                // update of solutions
                environmentalSelection();
                
            } // for each weight vector
            
        } // for number of generations
        
    } // execute method
    
    
    /**
     * initWeightVectors
     */
    public void initWeightVectors()
    {
        if(problem.numberOfObjectives == 2)
        {
            for (int n = 0; n < settings.mu; n++)
            {
                double a = 1.0 * n / (settings.mu - 1);
                lambda[n][0] = a;
                lambda[n][1] = 1 - a;
            } // for
        }// if
        else{
            getWeightsFromFile();
        }
        
    } // initWeightVectors
    
    
    private void getWeightsFromFile()
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
		
		//finOpen(String.format("%s/%d/m%d_%d.dat", folderWeights, settings.mu, problem.m, settings.mu));
        
        // create scanner to read file
        io.finOpen(file.toString());
        
        // read values from file
        int i, j;
        for (i = 0; i < settings.mu; i++)
        {
            // read from file
            for (j = 0; j < problem.numberOfObjectives; j++){
                lambda[i][j] = Double.parseDouble(io.fin.next());
            }
        }
        
        // close file
        io.fin.close();
        
    } // readWeights
    
    
    /**
     * initNeighborhood
     */
    public void initNeighborhood()
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
    public void initPopulation()
    {
        // init chrom length
        settings.pm = 1.0/(problem.numberOfComponents*problem.timeHorizon);
        
        // temp vars
        int i, j, k;
        
        // init ref point
        for(j = 0; j < problem.numberOfObjectives; j++)
        {
            ideal[j] = Double.POSITIVE_INFINITY; //0.0; //
            nadir[j] = Double.NEGATIVE_INFINITY;
        }
        //ideal[0] = 1.0;
        
        // generate initial population
        for(i = 0; i < settings.mu; i++)
        {
            // initialize population
            population[i] = new Solution();
            
            population[i].chrom = new int[problem.numberOfComponents][problem.timeHorizon];
            population[i].objectives = new double[problem.numberOfObjectives];
            population[i].cv = 0.0;
            population[i].states = new double[problem.numberOfStates][problem.timeHorizon];
            population[i].isActive = true;
            
            // generate idividuals genotype
            for(j = 0; j < problem.timeHorizon; j++)
            {
                for(k = 0; k < problem.numberOfComponents; k++)
                {
                    if(i == 0){
                        population[i].chrom[k][j] = 0;
                    }
                    else{
                        population[i].chrom[k][j] = rand.nextInt(problem.numberOfActions[k]);
                    }
                }
            }
            
            // evaluation
            evaluation(population[i]);
        }
        
        // initialize offspring
        offspring.chrom = new int[problem.numberOfComponents][problem.timeHorizon];
        offspring.objectives = new double[problem.numberOfObjectives];
        offspring.cv = 0.0;
        offspring.states = new double[problem.numberOfStates][problem.timeHorizon];
    }
    
    
    // evaluation procedure
    private void evaluation(Solution individual)
    {
        // evaluate
        problem.evaluate(individual);
        
        // update ref point
        for(int j = 0; j < problem.numberOfObjectives; j++)
        {
            if(individual.objectives[j] < ideal[j]){
                ideal[j] = individual.objectives[j];
            }
            if(individual.objectives[j] > nadir[j]){
                nadir[j] = individual.objectives[j];
            }
        }
        
        archive.add(individual);
    }
    
    
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
        System.arraycopy(offspring.objectives, 0, nadir, 0, problem.numberOfObjectives);
        int i, j;
        for(i = 0; i < settings.mu; i++)
        {
            for(j = 0; j < problem.numberOfObjectives; j++)
            {
                if(population[i].objectives[j] > nadir[j]){
                    nadir[j] = population[i].objectives[j];
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
        int i, j, k;
        double f1, f2;
        
        // perf selection
        for (int idx : indexes)
        {
            k = matingPool[idx];
            
            // calculate the values of objective function regarding the current subproblem
            f1 = fitnessFunction(population[k].objectives, lambda[k]) + penaltyFunction(population[k].cv);
            f2 = fitnessFunction(offspring.objectives, lambda[k]) + penaltyFunction(offspring.cv);
            
            if(f2 < f1)
            {
                // replace individual
                replace(k);
                time++;
            }
            // the maximal number of solutions updated is not allowed to exceed 'limit'
            if (time >= settings.nr){
                break;
            }
        }
        
    } // updateProblem
    
    
    
    // replace population member by offspring
    private void replace(int idx)
    {
        int i, j;
        for(j = 0; j < problem.timeHorizon; j++)
        {
            for(i = 0; i < problem.numberOfComponents; i++){
                population[idx].chrom[i][j] = offspring.chrom[i][j];
            }
            for(i = 0; i < problem.numberOfStates; i++){
                population[idx].states[i][j] = offspring.states[i][j];
            }
        }
        System.arraycopy(offspring.objectives, 0, population[idx].objectives, 0, problem.numberOfObjectives);
        population[idx].cv = offspring.cv;
    }
    
    
    
    /**
     * method to calculate individual's fitness
     * @param objectives
     * @param lambda
     */
    private double fitnessFunction(double [] objectives, double [] lambda)
    {
        // Chebyshev method
        double temp, fitness = Double.NEGATIVE_INFINITY;
        
        for(int j = 0; j < problem.numberOfObjectives; j++)
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
    
    
    public void twoPointCrossover()
    {
        // select two parents from mating pool
        int poolSize = matingPool.length;
        int p1 = matingPool[rand.nextInt(poolSize)];
        int p2 = matingPool[rand.nextInt(poolSize)];
        while(p1 == p2){
            p2 = matingPool[rand.nextInt(poolSize)];
        }
        
        // declare some variables
        int i, j, point1, point2, tempPoint;
        
        // there is no crossover
        for(j = 0; j < problem.timeHorizon; j++)
        {
            for(i = 0; i < problem.numberOfComponents; i++)
            {
                offspring.chrom[i][j] = population[p1].chrom[i][j];
            }
        }
        
        // crossover
        if(rand.nextDouble() <= settings.pc)
        {
            // generate crossover points
            point1 = rand.nextInt(problem.timeHorizon);
            tempPoint = rand.nextInt(problem.timeHorizon);
            while(tempPoint == point1){
                tempPoint = rand.nextInt(problem.timeHorizon);
            }
            if(point1 < tempPoint){
                point2 = tempPoint;
            }
            else{
                point2 = point1;
                point1 = tempPoint;
            }
            
            // for each gene exchange genetic information
            for(j = point1; j <= point2; j++)
            {
                for(i = 0; i < problem.numberOfComponents; i++){
                    offspring.chrom[i][j] = population[p2].chrom[i][j];
                }
            }
        }
        
    }
    
    
    private void mutation()
    {
        // mutation
        int j, k;
        for(j = 0; j < problem.timeHorizon; j++)
        {
            for(k = 0; k < problem.numberOfComponents; k++)
            {
                if(rand.nextDouble() < settings.pm){
                    offspring.chrom[k][j] = rand.nextInt(problem.numberOfActions[k]);
                }
            }
        }
    }
    
    
    // save results
    public void save(String fileOutput)
    {
        io.set(problem);
        
        io.save(fileOutput, ".pop", population);
        
        io.save(fileOutput, ".arc", archive.members);
    }
    
    
} // MOEAD class

