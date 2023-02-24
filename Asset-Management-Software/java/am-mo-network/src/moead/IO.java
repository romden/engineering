package moead;


public class IO
{
    
    Solution [] solutions;
    problem.Problem problem;
    
    
    public void set(problem.Problem problem)
    {
        this.problem = problem;
        
    }
    
    
    public void save(String fileOutput, String type, Solution [] solutions)
    {
        this.solutions = solutions;
        
        finOpen(fileOutput); // open file containing output information
        
        String fileName;
        
        // save vars
        fin.next();
        fileName = fin.next();
        if(!fileName.equals("NA")){
            fileName = fileName + type;
            saveVariables(fileName);
        }
        
        // save costs
        fin.next();
        fileName = fin.next();
        if(!fileName.equals("NA")){
            fileName = fileName + type;
            saveCosts(fileName);
        }
        
        // save states
        fin.next();
        fileName = fin.next();
        if(!fileName.equals("NA")){
            fileName = fileName + type;
            saveStates(fileName);
        }
        
        // save objs
        fin.next();
        fileName = fin.next();
        if(!fileName.equals("NA")){
            fileName = fileName + type;
            saveObjectives(fileName);
        }
        
        finClose();
    }
    
    
    // method to save objectives
    public void saveVariables(String fileName)
    {
        //open file
        foutOpen(fileName);
        
        // save values
        int i, j;
        for (i = 0; i < solutions.length; i++)
        {
            if(solutions[i].isActive)
            {
                // save fun values
                for (j = 0; j < solutions[i].x.length; j++){
                    fout.format("%s ", solutions[i].x[j]+1);
                }
                // new line
                fout.format("%n");
            }
        }
        
        // close file
        foutClose();
        
    } // method saveObjectives
    
    
    
    // method to save objectives
    public void saveObjectives(String fileName)
    {
        //open file
        foutOpen(fileName);
        
        // save values
        int i, j;
        for (i = 0; i < solutions.length; i++)
        {
            if(solutions[i].isActive)
            {
                // save fun values
                for (j = 0; j < solutions[i].f.length; j++){
                    fout.format("%s ", solutions[i].f[j]);
                }
                // new line
                fout.format("%n");
            }
        }
        
        // close file
        foutClose();
        
    } // method saveObjectives
    
    
    // method to save states
    public void saveCosts(String fileName)
    {
        //open file
        foutOpen(fileName);
        
        int i, j;
        for(i = 0; i < solutions.length; i++)
        {
            if(solutions[i].isActive)
            {
                
                double [] costs = problem.getCosts(solutions[i].x);
                
                // save values
                for(j = 0; j < problem.timeHorizon; j++){
                    fout.format("%s ", costs[j]);
                }
                // new line
                fout.format("%n");
                
            }
        }
        
        // close file
        foutClose();
        
    } // method saveStates
    
    
    
    //    // method to save states
    public void saveStates(String fileName)
    {
        //open file
        foutOpen(fileName);
        
        int i, j;
        for(i = 0; i < solutions.length; i++)
        {
            if(solutions[i].isActive)
            {
                
                double [] states = problem.getStates(solutions[i].x);
                
                // save values
                for(j = 0; j < problem.timeHorizon; j++){
                    fout.format("%s ", states[j]);
                }
                // new line
                fout.format("%n");
                
            }
        }
        
        // close file
        foutClose();
        
    } // method saveStates
    
    
    
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
    
    private void foutClose()
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
    
}
