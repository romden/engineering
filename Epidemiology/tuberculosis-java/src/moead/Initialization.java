package moead;

import problem.*;



public class Initialization
{
    
    // instance variables
    Tuberculosis problem = new Tuberculosis();
//    LZ09F problem = new LZ09F();
    Settings settings = new Settings();
    
    public Initialization()
    {
        
    } // constructor
    
    
    // generate initial population method
//    public void perform(Solution [] population, Solution offspring)
//    {     
//
//        int i, j;   
//        // generate initial population
//        for(i = 0; i < settings.mu; i++)
//        {            
//            // initialize variables
//            population[i] = new Solution();
//            population[i].variables = new double[problem.numberOfVariables];
//            population[i].objectives = new double[problem.numberOfObjectives];
//            
//            // generate idividual in the decision space
//            for(j=0; j < problem.numberOfVariables; j++){
//                population[i].variables[j] = problem.lowerLimit[j] + Math.random()*(problem.upperLimit[j] - problem.lowerLimit[j]);
//            }
//            
//            // evaluation
//            problem.evaluate(population[i]);
//        
//        }        
//        
//        // initialize offspring
//        offspring.variables = new double[problem.numberOfVariables];
//        offspring.objectives = new double[problem.numberOfObjectives];
//        
//    } // generate method end
//    
    
    // generate initial population method
    public void perform(Solution [] population, Solution offspring)
    {
        
        int i, j;
        
        double [] cube = new double[problem.numberOfVariables];
        
        for(j=0; j < problem.numberOfVariables; j++){
            cube[j] = (problem.upperLimit[j] - problem.lowerLimit[j])/settings.mu;
        }
        
        
        // generate initial population
        for(i = 0; i < settings.mu; i++)
        {
            // initialize variables
            population[i] = new Solution();
            population[i].variables = new double[problem.numberOfVariables];
            population[i].objectives = new double[problem.numberOfObjectives];
            population[i].nf = new double[problem.numberOfObjectives];
            
            // generate idividual in the decision space
            if(i == 0){
                for(j = 0; j < problem.numberOfVariables; j++){
                    population[i].variables[j] = 1.0;
                }
            }
            else if(i == settings.mu-1){
                for(j = 0; j < problem.numberOfVariables; j++){
                    population[i].variables[j] = 0.0;
                }
            }
            else{
                for(j = 0; j < problem.numberOfVariables; j++){
//                    population[i].variables[j] = problem.upperLimit[j] - Math.random()*cube[j]*(i+1);
                    population[i].variables[j] = problem.upperLimit[j] - Math.random()*1.0;
                }
            }
            
            
            // evaluation
            problem.evaluate(population[i]);
            
        }
        
        // initialize offspring
        offspring.variables = new double[problem.numberOfVariables];
        offspring.objectives = new double[problem.numberOfObjectives];
        offspring.nf = new double[problem.numberOfObjectives];
        
    } // generate method end
    
}// Initialpopulation class end
