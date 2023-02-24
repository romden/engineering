package util;


public class VectorUtil
{
    
    public VectorUtil(){
        
    }
    
    
    public double std(double [] vector)
    {
        return Math.sqrt(variance(vector));
    }
    
    
    public double variance(double [] vector)
    {
        double meanValue = mean(vector);
        double varianceValue = 0.0;
        int size = vector.length;
        
        for(int j = 0; j < size; j++){
            varianceValue += Math.pow(meanValue - vector[j], 2);
        }
        
        return (varianceValue/size);
    }
    
    
    // performance is defined by max value
    public double mean(double [] vector)
    {
        return (sum(vector)/vector.length);
    }
    
    
    // performance is defined by max value
    public double sum(double [] vector)
    {
        // declare integral
        double sumValue = vector[0];
        
        for(int j = 1; j < vector.length; j++){
            sumValue += vector[j];
        }
        
        return sumValue;
    }
    
    
    // performance is defined by max value
    public double max(double [] vector)
    {
        // declare integral
        double maxValue = vector[0];
        
        for(int j = 1; j < vector.length; j++)
        {
            if(vector[j] > maxValue){
                maxValue = vector[j];
            }
        }
        
        return maxValue;
    }
    
    public double min(double [] vector)
    {
        // declare integral
        double minValue = vector[0];
        
        for(int j = 1; j < vector.length; j++)
        {
            if(vector[j] < minValue){
                minValue = vector[j];
            }
        }
        
        return minValue;
    }
}
