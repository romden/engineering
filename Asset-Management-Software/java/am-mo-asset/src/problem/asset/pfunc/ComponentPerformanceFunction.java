package problem.asset.pfunc;

import problem.asset.general.Component;


public class ComponentPerformanceFunction implements AbstractPerformanceFunction
{
    
    util.Utils utils = new util.Utils();
    
    public Component [] components;
    public double [] statesOfAsset;
    int timeHorizon;
    
    
    // constructor
    public ComponentPerformanceFunction(Component [] components, double [] statesOfAsset)
    {
        this.components = components;
        this.statesOfAsset = statesOfAsset;
        this.timeHorizon = statesOfAsset.length;
    }
    
    
    // method to calculate performance for pavement asset
    @Override
    public void execute()
    {
        int k = 0; // there is single component
        int i, j;
        for(j = 0; j < timeHorizon; j++)
        {
            statesOfAsset[j] = 0.0;
            for(i = 0; i < components[k].getNumberOfIndexes(); i++){
                statesOfAsset[j] += components[k].getStateFor(i, j);
            }
            statesOfAsset[j] /= components[k].getNumberOfIndexes();
        }
    }
    
} // class
