package moead;

import java.util.Arrays;


public class LZ09F
{
    
    // problem parameters
    public int numberOfVariables ;
    public int numberOfObjectives ;
    public double [] lowerLimit ;
    public double [] upperLimit ;
    public String problemName ;
    
    public LZ09F()
    {
        numberOfVariables = 30;
        numberOfObjectives = 2;
        
        // initialize bounds
        lowerLimit = new double[numberOfVariables];
        Arrays.fill(lowerLimit, -1.0);
        lowerLimit[0] = 0.0;
        upperLimit = new double[numberOfVariables];
        Arrays.fill(upperLimit, 1.0);
        
    }
    
    
    public void evaluate(Solution solution)
    {
        
        double sum1 = 0;
        double sum2 = 0;
        int J1 = 0;
        int J2 = 0;
        
        for(int j = 1; j < numberOfVariables; j++)
        {
            if(j%2==0){
                sum2 += Math.pow(solution.variables[j] - Math.sin(6.0*Math.PI*solution.variables[0] + j*Math.PI/numberOfVariables), 2.0);
                J2++;
            }
            else{
                sum1 += Math.pow(solution.variables[j] - Math.sin(6.0*Math.PI*solution.variables[0]+j*Math.PI/numberOfVariables), 2.0);
                J1++;
            }
        }
        solution.objectives[0] = solution.variables[0] + 2.0*sum1/J1;
        solution.objectives[1] = 1.0 - Math.sqrt(solution.variables[0]) + 2.0*sum2/J2;
        
    }
    
}
