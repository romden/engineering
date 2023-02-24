package moead;



public class Outcome
{
    
    private java.util.Formatter fout; // object for saving data
    
    
    // constructor
    public void Outcome()
    {
        
    }// constructor
    
    
    // method to save objectives of the current generation
    public void save(Solution [] population, int g)
    {
        //open file
        openOutFile("gen_" + g + ".dat");
        
        // save fun values
        for(int i = 0; i < population.length; i++)
        {
            for (int j = 0; j < population[i].objectives.length; j++){
                fout.format("%s ", population[i].objectives[j]);
            }
            fout.format("%n");
        }
        
        // close file
        closeOutFile();
        
    } // save method
    
    
    // method to save approximation
    public void save(Solution [] population)
    {
        // SAVE VARIABLES
        
        //open file
        openOutFile("VAR.dat");
        
        // save values
        for (int i = 0; i < population.length; i++)
        {
            // save var values
            for (int j = 0; j < population[i].variables.length; j++){
                fout.format("%s ", population[i].variables[j]);
            }
            fout.format("%n");
        }
        
        // close file
        closeOutFile();
        
        // SAVE OBJECTIVES
        
        //open file
        openOutFile("FUN.dat");
        
        // save fun values
        for(int i = 0; i < population.length; i++)
        {
            for (int j = 0; j < population[i].objectives.length; j++){
                fout.format("%s ", population[i].objectives[j]);
            }
            fout.format("%n");
        }
        
        // close file
        closeOutFile();
        
    } // save method
    
    
    // method to open file
    private void openOutFile(String fileName)
    {
        try {
            fout = new java.util.Formatter(fileName);
        }
        catch (Exception e) {
            System.out.println("error opening " + fileName + " :" + e);
        }
        
    } // openOutFile method
    
    
    // method to clase file
    private void closeOutFile()
    {
        fout.close();
        
    } // closeOutFile method
    
    
} // end Outcome class
