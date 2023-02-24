package problem.asset.general;


public class ActionEffects
{
    
    // numero de anos em que a taxa de deterioracao e suprimida (eye(n))
    public double [][] timeOfDelay; 
    
    // fator de deterioracao (delta)
    public double [][] rateOfReduction; 
    
    // numero de anos em que a taxa de deterioracao e reduzida (theta/delta)
    public double [][] timeOfReduction; 
    
    // estado de condicao para qual a estrutura ira melhorar
    public double [][] improvement;    

    
    public void initialize(int numberOfStates)
    {
        timeOfDelay = new double[numberOfStates][];
        rateOfReduction = new double[numberOfStates][];
        timeOfReduction = new double[numberOfStates][];
        improvement = new double[numberOfStates][];
                
        for(int i = 0; i < numberOfStates; i++)
        {
            timeOfDelay[i] = new double[]{0, 0, 0};
            rateOfReduction[i] = new double[]{1, 1, 1};
            timeOfReduction[i] = new double[]{0, 0, 0};
            improvement[i] = new double[]{0, 0, 0};
        }
    }
    
    
    
    public double getTimeOfDelay(int state, double u)
    {
        return triangularDistribution(timeOfDelay[state], u);
    }
    
    
    public double getRateOfReduction(int state, double u)
    {
        return triangularDistribution(rateOfReduction[state], u);
    }
    
    
    public double getTimeOfReduction(int state, double u)
    {
        return triangularDistribution(timeOfReduction[state], u);
    }
    
    
    public double getImprovement(int state, double u)
    {
        return triangularDistribution(improvement[state], u);
    }
   
    
    // method to generate triangular-distributed random number
    public double triangularDistribution(double [] param, double u)
    {
        double a = param[0];
        double c = param[1];
        double b = param[2];
        
        double x = 0; // Triangular-distributed random number
        double f = (c-a)/(b-a);
//        double u = Math.random();
        
        if(a < f){
            x = a + Math.sqrt(u*(b-a)*(c-a));
        }
        else{
            x = b - Math.sqrt((1-u)*(b-a)*(b-c));
        }
        
        return x;
    }
    
}
