package problem.asset.pfunc;


import problem.asset.general.Component;


public class BridgePerformanceFunction implements AbstractPerformanceFunction
{
    
    util.Utils utils = new util.Utils();
    
    public Component [] components;
    public double [] statesOfAsset;
    int timeHorizon;
    int numberOfComponents;
    int [] criticalElements;
    
    // constructor
    public BridgePerformanceFunction(Component [] components, double [] statesOfAsset, int [] criticalElements)
    {
        this.components = components;
        this.statesOfAsset = statesOfAsset;
        this.timeHorizon = statesOfAsset.length;
        this.numberOfComponents = components.length;
        this.criticalElements = criticalElements;
    }
    
    
    // method to calculate performance for pavement asset
    @Override
    public void execute()
    {
        int j, k;
        for(j = 0; j < timeHorizon; j++)
        {
            statesOfAsset[j] = 0.0;
            for(k = 0; k < numberOfComponents; k++){
                statesOfAsset[j] += components[k].getStateFor(0, j);
            }
            // state for the bridge: MAX ( média (IC (componentes)); MAX(IC (apoios intermedios); IC (tabuleiro); IC (encontros) )
            statesOfAsset[j] /= numberOfComponents;
            for(int i : criticalElements)
            {
                if(statesOfAsset[j] < components[i].getStateFor(0, j)){
                    statesOfAsset[j] = components[i].getStateFor(0, j);
                }
            }
        }
    }
    
} // class

