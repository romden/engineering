package problem.asset.pfunc;

import problem.asset.general.Component;


public class PavementPerformanceFunction implements AbstractPerformanceFunction
{
    
    util.Utils utils = new util.Utils();

    public Component [] components;
    public double [] statesOfAsset;
    int timeHorizon;    
    
    // pesos para conforto
    double [] weightsComfort = {1.0, 0.7, 0.5};
    
    // pesos seguranca
    double [] weightsSecurity = {0.9, 0.9};
    
    // pesos estrutural
    double [] weightsStructural = {0.6, 0.5, 0.9, 1.0};
    
    // pesos global
    double [] weightsGlobal = {0.7, 1.0, 0.65};
    
    
    double [] PI;
    double [] I;
    double [] CPI;
    
    
    // constructor
    public PavementPerformanceFunction(Component [] components, double [] statesOfAsset)
    {
        this.components = components;
        this.statesOfAsset = statesOfAsset;
        this.timeHorizon = statesOfAsset.length;
        
        // indicadores de desempenho IRI, RUT, CRK, FRI, FWD
        PI = new double[5];
        
        // indicadores de desempenho pesados
        I = new double[4];
        
        // indicadores combinados: conforto (CP1), seguranca (CP2), estrutural (CP3)
        CPI = new double[3];
    }
    
    
    // method to calculate performance for pavement asset
    @Override
    public void execute()
    {
        
        double GPI;        

        int k = 0; // there is single component in pavement
        int i, j;        
        for(j = 0; j < timeHorizon; j++)
        {
            // indicadores de desempenho IRI, RUT, CRK, FRI, FWD
            for(i = 0; i < 5; i++){
                PI[i] = components[k].getStateFor(i, j);
            }
            
            
            // CONFORTO
            
            // indicadores pesados
            for(i = 0; i < 3; i++){
                I[i] = weightsComfort[i]*PI[i];
            }
            
            // sort values
            utils.maxSort(I, 3, 3);
            
            // indicador combinado conforto
            CPI[0] = Math.min(5.0, I[0] + 0.2*(I[1]+I[2])/2.0);            

            
            // SEGURANCA
            
            // indicadores pesados
            I[0] = weightsSecurity[0]*PI[1];
            I[1] = weightsSecurity[1]*PI[3];
            
            // sort values
            utils.maxSort(I, 2, 2);
            
            // indicador combinado seguranca
            CPI[1] = CPI[0] = Math.min(5.0, I[0] + 0.2*I[1]);
            
            
            // ESTRUTURAL
            
            // indicadores pesados
            for(i = 0; i < 3; i++){
                I[i] = weightsStructural[i]*PI[i];
            }
            I[3] = weightsStructural[3]*PI[4];
            
            // sort values
            utils.maxSort(I, 4, 4);
            
            // indicador combinado estrutural
            CPI[2] = Math.min(5.0, I[0] + 0.2*(I[1]+I[2]+I[3])/3.0);
            
            
            // GLOBAL
            
            // indicadores pesados
            for(i = 0; i < 3; i++){
                I[i] = weightsGlobal[i]*CPI[i];
            }
            
            // sort values
            utils.maxSort(I, 3, 3);
            
            // inidicador combinado global
            GPI = Math.min(5.0, I[0] + 0.2*(I[1]+I[2])/2.0);
            
            
            // index of asset
            statesOfAsset[j] = GPI;
        }
    }
}
