package classical;

import problem.Tuberculosis;

import org.apache.commons.math3.analysis.MultivariateFunction;


public class ObjectiveFunction implements MultivariateFunction
{
    Tuberculosis problem = new Tuberculosis();
    
    String type;
    //double [] variables;
    double [] objectives;
    
    double [] weightVector;
    double [] idealPoint;
    double [] nadirPoint;
    
    int p = 2; // p norm for weighted metric
    double constraint;
    double penalty = 5.0;
    
    
//    public ObjectiveFunction(double [] weightVector, double [] idealPoint, double [] nadirPoint)
//    {
//        this.weightVector = weightVector;
//        this.idealPoint = idealPoint;
//        this.nadirPoint = nadirPoint;
//    }
    
    public void setMethodType(String type)
    {
        this.type = type;
    }
    
    public void setWeightVector(double [] weightVector)
    {
        this.weightVector = weightVector;
    }
    
    public void setIdealPoint(double [] idealPoint)
    {
        this.idealPoint = idealPoint;
    }
        
    public void setNadirPoint(double [] nadirPoint)
    {
        this.nadirPoint = nadirPoint;
    }
        
    public void setConstraint(double constraint)
    {
        this.constraint = constraint;
    }    
    
    public void setLp(int p)
    {
        this.p = p;
    }

    
    
    private void normalization()
    {
        for(int j = 0; j < problem.numberOfObjectives; j++){
            objectives[j] = (objectives[j] - idealPoint[j])/(nadirPoint[j] - idealPoint[j]);
        }
    }
    
    
    private double wheightedSum()
    {
        double value = 0.0;
        
        for(int j = 0; j < problem.numberOfObjectives; j++)
        {
            value += objectives[j]*weightVector[j];
        } // for
        
        return value;
    }
    
    private double wheightedMetric()
    {
        double fval = 0.0;
        
        for(int j = 0; j < problem.numberOfObjectives; j++)
        {
            fval += Math.pow(objectives[j], p)*weightVector[j];
        } // for
        
        return Math.pow(fval, 1.0/p);
    }
    
    private double chebyshev()
    {
        double maxValue = Double.NEGATIVE_INFINITY;
        double value;
        
        for(int j = 0; j < problem.numberOfObjectives; j++)
        {
            value = objectives[j]*weightVector[j];
            
            if(value > maxValue) {
                maxValue = value;
            }
        } // for
        
        return maxValue;
    }
    
    
    private double epsConstrained()
    {
        int indexOfObjective = 0;
        int indexOfConstraint = 1;

        double cv =  Math.max(objectives[indexOfConstraint]-constraint, 0.0);
        
        double feval = objectives[indexOfObjective] + penalty*cv;
        
        return feval;
    }    
    
    private double penaltyBoundaryIntersection()
    {        
        double norm = Math.sqrt(scalar(weightVector, weightVector));
        double d1 = scalar(objectives, weightVector)/norm;       
               
        double [] temp = new double[problem.numberOfObjectives];
        for(int j = 0; j < problem.numberOfObjectives; j++)
        {
            temp[j] = objectives[j] - d1*weightVector[j]/norm;
        } // for
        
        double d2 = Math.sqrt(scalar(temp, temp));
        
        return d1 + penalty*d2;       
        
//        return -scalar(weightVector, objectives)/(Math.sqrt(scalar(weightVector, weightVector))*Math.sqrt(scalar(objectives, objectives)));
    }
    
    private double scalar(double [] v1, double [] v2)
    {
        double res = 0.0;
        for(int i = 0; i < v1.length; i++){
            res += v1[i]*v2[i];
        }
        
        return res;
    }
    
    
    @Override
    public double value(double [] point)
    {
        // calc objectives
        objectives = problem.execute(point);
        
        // normalize obj
        normalization();
        
        double fval = 0.0;
        switch(type)
        {
            case "wsum":
                fval = wheightedSum();
                break;
            case "wmetric":
                fval = wheightedMetric();
                break;
            case "chb":
                fval = chebyshev();
                break;
            case "eps":
                fval = epsConstrained();
                break;
            case "pbi":
                fval = penaltyBoundaryIntersection();
                break;
        }
        
        return fval;
    }
    
} //  class ObjFunction  
