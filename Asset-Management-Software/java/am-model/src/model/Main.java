package model;




class Main
{
    
    private static String dataFile;
    private static int minState;
    private static int maxState;
    private static int timeSpan;
    private static String outputFile;
    
    
    public static void main(String[] args)
    {
        
        if(args.length == 5)
        {
            dataFile = args[0];
            minState = Integer.parseInt(args[1]);
            maxState = Integer.parseInt(args[2]);
            timeSpan = Integer.parseInt(args[3]);
            outputFile = args[4];
            
            (new Model(dataFile, minState, maxState, timeSpan, outputFile)).build();
        }
        
//        runAll();
        
    }
    
    
    
    private static void runAll()
    {
        // calculate initial states
        String [] names = getNames();

        for(int i = 0; i < names.length; i++)
        {
            dataFile = "../Assets/Inspections/" + names[i] + ".dat";
            minState = 1;
            maxState = 5;
            if(names[i].equals("telematica")){
                timeSpan = 30; // in month
            }
            else{
                timeSpan = 365; //in years
            }
            outputFile = "../Assets/IntensityMatrices/" + names[i] + ".dat";
            
            (new Model(dataFile, minState, maxState, timeSpan, outputFile)).build();
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
