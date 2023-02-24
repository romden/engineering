package moead;


public class IO
{
    
    Solution [] solutions;
    
    problem.Problem problem;
    
    void set(problem.Problem problem)
    {
        this.problem = problem;
    }
    
    
    // save all results
    public void save(String fileOutput, String type, Solution [] solutions)
    {
        this.solutions = solutions;
        
        String fileName;
        
        finOpen(fileOutput);
        
        fin.next();
        fileName = fin.next();
        if(!fileName.equals("NA")){
            fileName = fileName + type;
            saveActions(fileName);
        }
        
        fin.next();
        fileName = fin.next();
        if(!fileName.equals("NA")){
            fileName = fileName + type;
            saveStates(fileName);
        }
        
        fin.next();
        fileName = fin.next();
        if(!fileName.equals("NA")){
            fileName = fileName + type;
            saveCosts(fileName);
        }
        
        fin.next();
        fileName = fin.next();        
        if(!fileName.equals("NA")){
            fileName = fileName + type;
            saveObjectives(fileName);
        }
        
        fin.next();
        fileName = fin.next();        
        if(!fileName.equals("NA")){
            fileName = fileName + type;
            saveCV(fileName);
        }
        
        finClose();
    }
    
    
    // method to save chrom
    public void saveActions(String fileName)
    {
        int i, j, k;
        
        // FIRST SAVE ACTIONS FOR ASSET
        
        //open file
        foutOpen(fileName);
        // write to file
        for(i = 0; i < solutions.length; i++)
        {
            if(!solutions[i].isActive) continue;
            
            for(j = 0; j < problem.timeHorizon; j++)
            {
                for(k = 0; k < problem.numberOfComponents; k++)
                {
                    fout.format("%s ", solutions[i].chrom[k][j]);
                }
            }
            fout.format("%n");// new line
        }
        // close file
        foutClose();
        
        if(problem.numberOfComponents == 1){
            return;
        }
        
        // THEN SAVE ACTIONS FOR COMPONENTS
        
        //open files
        foutArrayOpen(fileName, problem.numberOfComponents);
        
        // write to files
        for(i = 0; i < solutions.length; i++)
        {
            if(!solutions[i].isActive) continue;
            
            for(k = 0; k < problem.numberOfComponents; k++)
            {
                for(j = 0; j < problem.timeHorizon; j++){
                    foutArray[k].format("%s ", solutions[i].chrom[k][j]);
                }
                foutArray[k].format("%n");// new line
            }
        }
        
        // close files
        foutArrayClose(problem.numberOfComponents);
        
    } // method  saveActions
    
    
    // method to save states
    public void saveStates(String fileName)
    {
        int i, j, k;
        double [] states;
        
        // FIRST SAVE STATES OF ASSET
        
        //open file
        foutOpen(fileName);
        
        for(i = 0; i < solutions.length; i++)
        {
            if(!solutions[i].isActive) continue;
            
            states = problem.calcStatesForAsset(solutions[i].states);
            // save values
            for(j = 0; j < problem.timeHorizon; j++){
                fout.format("%s ", states[j]);
            }
            // new line
            fout.format("%n");
        }
        
        // close file
        foutClose();
        
        if(problem.numberOfStates == 1){
            return;
        }
        
        // THEN SAVE STATES OF COMPONENTS
        
        //open files
        foutArrayOpen(fileName, problem.numberOfStates);
        
        for(i = 0; i < solutions.length; i++)
        {
            if(!solutions[i].isActive) continue;
            
            for(k = 0; k < problem.numberOfStates; k++)
            {
                for(j = 0; j < problem.timeHorizon; j++){
                    foutArray[k].format("%s ", solutions[i].states[k][j]);
                }
                foutArray[k].format("%n");// new line
            }
        }
        
        // close files
        foutArrayClose(problem.numberOfStates);
        
    } // method saveStates
    
    
    // method to save costs
    public void saveCosts(String fileName)
    {
        int i, j, k;
        double [] costs;
        
        // FIRST COSTS FOR ASSET
        
        //open file
        foutOpen(fileName);
        
        for(i = 0; i < solutions.length; i++)
        {
            if(!solutions[i].isActive) continue;
            
            costs = problem.calcCostsForAsset(solutions[i].chrom);
            // save values
            for(j = 0; j < problem.timeHorizon; j++){
                fout.format("%s ", costs[j]);
            }
            // new line
            fout.format("%n");
        }
        
        // close file
        foutClose();
        
        // close file
        foutClose();
        
        if(problem.numberOfComponents == 1){
            return;
        }
        
        // THEN SAVE COSTS FOR COMPONENTS
        
        //open files
        foutArrayOpen(fileName, problem.numberOfComponents);
        
        for(i = 0; i < solutions.length; i++)
        {
            if(!solutions[i].isActive) continue;
            
            for(k = 0; k < problem.numberOfComponents; k++)
            {
                for(j = 0; j < problem.timeHorizon; j++)
                {
                    foutArray[k].format("%s ", problem.components[k].costs[solutions[i].chrom[k][j]]);
                }
                foutArray[k].format("%n");// new line
            }
        }
        
        // close files
        foutArrayClose(problem.numberOfComponents);
        
    } // method  saveActions
    
    
    // method to save objectives
    public void saveObjectives(String fileName)
    {
        //open file
        foutOpen(fileName);
        
        // save values
        int i, j;
        for (i = 0; i < solutions.length; i++)
        {
            if(!solutions[i].isActive) continue;
            
            // save fun values
            for (j = 0; j < problem.numberOfObjectives; j++){
                fout.format("%s ", solutions[i].objectives[j]);
            }
            // new line
            fout.format("%n");
        }
        
        // close file
        foutClose();
        
    } // method saveObjectives
    
    
    // method to save objectives
    public void saveCV(String fileName)
    {
        //open file
        foutOpen(fileName);
        
        for(int i = 0; i < solutions.length; i++)
        {
            if(!solutions[i].isActive) continue;
            
            // save cv values
            fout.format("%s %n", solutions[i].cv);
        }
        
        // close file
        foutClose();
        
    } // method saveObjectives
    
    
    
    public java.util.Formatter fout; // object to write data to file
    
    
    public void foutOpen(String fileName)
    {
        try {
            fout = new java.util.Formatter(fileName);
        }
        catch (Exception e) {
            System.out.println("error opening " + fileName + " : " + e);
        }
    }
    
    public void foutClose()
    {
        fout.close();
    }
    
    public java.util.Scanner fin; // object to read data from file
    
    // method to open file
    public void finOpen(String fileName)
    {
        try{
            fin = new java.util.Scanner(new java.io.File(fileName));
        }
        catch (Exception e){
            System.out.println("error opening " + fileName + " : " + e);
        }
        
    } // openInFile method
    
    
    // method to close file
    public void finClose()
    {
        fin.close();
    } // closeInFile method
    
    
    
    java.util.Formatter [] foutArray;
    
    public void foutArrayOpen(String fileName, int number)
    {
        String fname;
        foutArray = new java.util.Formatter[number];
        
        for(int k = 0; k < number; k++)
        {
            fname = fileName + "." + (k+1);
            try {
                foutArray[k] = new java.util.Formatter(fname);
            }
            catch (Exception e) {
                System.out.println("error opening " + fname + " : " + e);
            }
        }
    }
    
    
    public void foutArrayClose(int number)
    {
        for(int k = 0; k < number; k++){
            foutArray[k].close();
        }
    }
    
}
