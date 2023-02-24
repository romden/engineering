package moead;

import problem.*;
import util.*;


public class Variation
{
    
    // instance variables
    Tuberculosis problem = new Tuberculosis();
//   LZ09F problem = new LZ09F();
   
    Settings settings = new Settings();
    Random rand = new Random();
    Matrix mx = new Matrix();
    
    Solution [] population;
    Solution offspring;
    int [] matingPool;
    
    
    // constructor
    public void Variation()
    {
        
        
    } // constructor
    
    
    // method to perform variation
    public void perform(Solution [] population, Solution offspring, int [] matingPool, int current)
    {
        this.population = population;
        this.offspring = offspring;
        this.matingPool = matingPool;
        
        // recombination
        switch(settings.variationType)
        {
            case "DE": // differential evolution
                differentialEvolution(current);
                break;
            case "PM": // probabilistic model based operator
                probabilisticModel(current);
                break;
            case "GM": // guided mutation
                guidedMutation(current);
                break;
            case "GA": // genetic algorithm
                geneticAlgorithm();
                break;
        }
        
        // random bounds
        bounds(current);
        
        // mutation and bounds
        polynomialMutation();
        
        // projection bounds
        bounds();
        
        // evaluation
        problem.evaluate(offspring);
        
    } // perform method
    
    
    // method to apply differential evolution operator
    public void differentialEvolution(int current)
    {
        int poolSize = matingPool.length;
        // select two parents from mating pool
        int p1 = matingPool[rand.nextInt(poolSize)];
        while(p1 == current){
            p1 = matingPool[rand.nextInt(poolSize)];
        }
        int p2 = matingPool[rand.nextInt(poolSize)];
        while((p1 == p2) || (current == p2)){
            p2 = matingPool[rand.nextInt(poolSize)];
        }
        
        int jr;
        // apply DE operator
        for(int j = 0; j < problem.numberOfVariables; j++)
        {
            jr = rand.nextInt(problem.numberOfVariables);
            // appy DE operator
            if((rand.nextDouble() <= settings.CR) || (j == jr)){
                offspring.variables[j] = population[current].variables[j] + settings.F*(population[p1].variables[j] - population[p2].variables[j]);
            }
            else{
                offspring.variables[j] = population[current].variables[j];
            }
            
        } // for
        
        
    } // differentialEvolution method
    
    
    // method to produce offspring using probabilistic model
    private void probabilisticModel(int current)
    {
        int poolSize = matingPool.length;       
        
        // initialize deta matrix
        double [][] data = new double[poolSize][problem.numberOfVariables];
        for(int i = 0; i < poolSize; i++){
            System.arraycopy(population[matingPool[i]].variables, 0, data[i], 0, problem.numberOfVariables);
        }
        
        data = mx.centerRows(data); // substract mean values
        
        double [][] C = mx.product(mx.product(mx.transpose(data), data), 1/(poolSize-1)); // covariance matrix
        
        double [][] D = new double[problem.numberOfVariables][problem.numberOfVariables]; // eigen velues
        double [][] V = new double[problem.numberOfVariables][problem.numberOfVariables]; // eigen vectors
        
        mx.eigen(C, D, V); // eigen decomposition
        
        // generate random vector
        double [] v = mx.product(D, mx.product(V, rand.nextGaussian(problem.numberOfVariables)));
        
        // generate offspring
        for(int j = 0; j < problem.numberOfVariables; j++){
            offspring.variables[j] = population[current].variables[j] + v[j];
        }
        
    } // probabilisticModel method
    
    
    // method to produce offspring using guided mutation operator
    private void guidedMutation(int current)
    {        
        int poolSize = matingPool.length;
        
        // select parent from mating pool
        int r = matingPool[rand.nextInt(poolSize)];
        while(r == current){
            r = matingPool[rand.nextInt(poolSize)];
        }
        
        double [] t = new double[problem.numberOfVariables];
        System.arraycopy(population[current].variables, 0, t, 0, problem.numberOfVariables);
        
        double [] x = new double[problem.numberOfVariables];
        System.arraycopy(population[r].variables, 0, x, 0, problem.numberOfVariables);
        
        double [] H = new double[problem.numberOfVariables];
        for(int j = 0; j < problem.numberOfVariables; j++)
        {
            H[j] = 0.0;
            if(rand.nextDouble() <= settings.pm){
                H[j] = rand.nextGaussian();
            }
        }
        
        double mu = 0.005;
        
        double norm1 = 0;
        for(int j = 0; j < problem.numberOfVariables; j++){
            norm1 += Math.pow(t[j] - x[j], 2);
        }
        norm1 = Math.sqrt(norm1);
        
        double R = Math.max(0.1*norm1, mu);
        
        // produce child
        double randn = rand.nextGaussian();
        
        for(int j = 0; j < problem.numberOfVariables; j++){
            offspring.variables[j] = x[j] + 0.5*(t[j] - x[j])*randn + R*H[j];
        }
        
    } // guidedMutation method
    
    
    // method to produce offspring using genetic algorithm operator
    private void geneticAlgorithm()
    {
        
        int poolSize = matingPool.length;
        // select parents from mating pool
        int idx1 = matingPool[rand.nextInt(poolSize)];
        int idx2 = matingPool[rand.nextInt(poolSize)];
        while(idx2 == idx1){
            idx2 = matingPool[rand.nextInt(poolSize)];
        }
        
        double u, beta, temp;
        
        double [] child1 = new double[problem.numberOfVariables];
        System.arraycopy(population[idx1].variables, 0, child1, 0, problem.numberOfVariables);
        double [] child2 = new double[problem.numberOfVariables];
        System.arraycopy(population[idx2].variables, 0, child2, 0, problem.numberOfVariables);
        
        if(Math.random() <= settings.pc)
        {
            // for each variable
            for(int j = 0; j < problem.numberOfVariables; j++)
            {
                u = Math.random();
                if(u <= 0.5){
                    beta = Math.pow(2.0*u, 1.0/(settings.etac + 1.0));
                }
                else{
                    beta = Math.pow(1.0/(2.0*(1.0 - u)), 1.0/(settings.etac + 1.0));
                }
                
                child1[j] = 0.5*((1.0 - beta)*population[idx1].variables[j] + (1.0 + beta)*population[idx2].variables[j]);
                child2[j] = 0.5*((1.0 + beta)*population[idx1].variables[j] + (1.0 - beta)*population[idx2].variables[j]);
                
                // swap gens with probability 0.5
                if(Math.random() <= 0.5)
                {
                    temp = child2[j];
                    child2[j] = child1[j];
                    child1[j] = temp;
                }
                
            } // for
        }
        
        
        if(Math.random() <= 0.5){
            System.arraycopy(child1, 0, offspring.variables, 0, problem.numberOfVariables);
        }
        else{
            System.arraycopy(child2, 0, offspring.variables, 0, problem.numberOfVariables);
        }
        
    } // geneticAlgorithm method
    
    
    // apply polynomial mutation on offspring
    private void polynomialMutation()
    {
        double u, delta;
        
        for(int j = 0; j < problem.numberOfVariables; j++)
        {
            // apply polinomial mutation
            if(rand.nextDouble() <= settings.pm)
            {
                u = rand.nextDouble();
                
                if(u <= 0.5){
                    delta = Math.pow(2.0*u, 1.0/(settings.etam + 1.0)) - 1.0;
                }
                else{
                    delta = 1.0 - Math.pow(2.0*(1.0 - u), 1.0/(settings.etam + 1.0));
                }
                
                offspring.variables[j] += (problem.upperLimit[j] - problem.lowerLimit[j])*delta;
            }
        }
        
    } // polynomialMutation method
    
    
    // method to ensure feasibility randomly generated values with the bounds
    private void bounds(int current)
    {
        for(int j = 0; j < problem.numberOfVariables; j++)
        {
            // bounds
            if(offspring.variables[j] < problem.lowerLimit[j]){
                offspring.variables[j] = problem.lowerLimit[j] + rand.nextDouble()*(population[current].variables[j] - problem.lowerLimit[j]);
            }
            if(offspring.variables[j] > problem.upperLimit[j]){
                offspring.variables[j] = problem.upperLimit[j] - rand.nextDouble()*(problem.upperLimit[j] - population[current].variables[j]);
            }
        }
        
    } // bounds method
    
    
    // method to ensure feasibility applying projection
    private void bounds()
    {
        for(int j = 0; j < problem.numberOfVariables; j++)
        {
            // bounds
            if(offspring.variables[j] < problem.lowerLimit[j]){
                offspring.variables[j] = problem.lowerLimit[j];
            }
            if(offspring.variables[j] > problem.upperLimit[j]){
                offspring.variables[j] = problem.upperLimit[j];
            }
        }
        
    } // bounds method
    
    
} // Variantion class
