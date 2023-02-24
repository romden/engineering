package perf;




public class Main
{
    private static String matrixFile;
    private static String paramsFile;
    private static String outputFile;
    private static String actionsTimetableFile;
    private static String actionsEffectsFile;
    private static int numberOfSamples;
        
    
    private final static Performance performance = new Performance();
    
    
    public static void main(String[] args)
    {
//        System.out.println("SustIMS - performance - library"); ;
        
        if(args.length == 3)
        {
            // calculate performance using analytic method
            performance.executeAnalytic(args[0], args[1], args[2]); 
        }
        else if(args.length == 4)
        {
            // calculate performance using Monte Carlo method
            performance.executeMonteCarlo(args[0], args[1], args[2], Integer.parseInt(args[3]));
        }
        else if(args.length == 6)
        {
            // calculate performance with maintenance actions using Monte Carlo method
            performance.executeMaintenance(args[0], args[1], args[2], args[3], args[4], Integer.parseInt(args[5]));
        }
        
//      runAll();
        
    } // main method
    
    
    private static void runAll()
    {   
       
        // calculate initial states
        String [] names = getNames();
        for(int i = 0; i < 18; i++)
        {
            matrixFile = "../Assets/IntensityMatrices/" + names[i] + ".dat";
            paramsFile = "../Assets/Parameters/" + names[i] + ".dat";
            outputFile = "../Assets/PerformanceNoActions/" + names[i] + ".dat";            
            
            performance.executeAnalytic(matrixFile, paramsFile, outputFile);
            
//            matrixFile = "../Assets/IntensityMatrices/" + names[i] + ".dat";
//            paramsFile = "../Assets/Parameters/" + names[i] + ".dat";
//            actionsTimetableFile = "../Assets/TimetableActions/" + names[i] + ".dat";
//            actionsEffectsFile = "../Assets/MaintenanceActions/Effects/" + names[i] + ".dat";
//            outputFile = "../Assets/PerformanceWithActions/" + names[i] + ".dat";
//            numberOfSamples = 100;
//            
//            performance.executeMaintenance(matrixFile, paramsFile, actionsTimetableFile, actionsEffectsFile, outputFile, numberOfSamples);
        }
        
    } // initAll
    
    
    private static String [] getNames()
    {
        // names of components
        String [] names =
        {   "obras_aparelhos_apoio",
            "obras_apoios_intermedios_betao_armado",
            "obras_apoios_intermedios_betao_preesforcado",
            "obras_encontros_betao_armado",
            "obras_encontros_betao_preesforcado",
            "obras_guarda_corpos_aco",
            "obras_guarda_corpos_betao_armado",
            "obras_juntas_dilatacao_aco",
            "obras_juntas_dilatacao_outros",
            "obras_tabuleiro_betao_armado",
            "obras_tabuleiro_betao_preesforcado",
            "pavimentos_ckr",
            "pavimentos_fri",
            "pavimentos_fwd",
            "pavimentos_iri",
            "pavimentos_rut",
            "telematica",
            "taludes",
        };
        
        return names;
    }
    
}
