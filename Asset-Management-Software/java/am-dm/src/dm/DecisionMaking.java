package dm;

import java.io.File;
import java.util.Formatter;
import java.util.Scanner;


public class DecisionMaking
{
    
    int size;
    int dim;
    
    double [] ideal; // ideal point
    double [] nadir; // nadir point of PF
    
    double [][] objs;
    
    double [] metric;
    
    //String type = "WCM";
    
    // constructor
    public DecisionMaking()
    {
        
    }
    
    
    public void execute(String inputFile, String outputFile)
    {
        int i, j;
        
        // GET INPUT DATA
        
        // open file
        finOpen(inputFile);
        
        fin.next(); // data size
        size = Integer.parseInt(fin.next());
        
        fin.next(); // data dimension
        dim = Integer.parseInt(fin.next());
        
        fin.next(); // weights
        double [] w = new double[dim];
        for(j = 0; j < dim; j++){
            w[j] = Double.parseDouble(fin.next());
        }
        
        fin.next(); // objectives (data)
        double [][] data = new double[size][dim];
        for(i = 0; i < size; i++)
        {
            for(j = 0; j < dim; j++){
                data[i][j] = Double.parseDouble(fin.next());
            }
        }
        
        // close file
        finClose();
        
        // CALCULATE AND SAVE RESULT
        
        // open file for saving result
        foutOpen(outputFile);
        
        // save result
        fout.format("%s", execute(data, w, "WCM"));
        
        // close file for saving result
        foutClose();
    }
    
    
    public int execute(double [][] data, double [] w, String type)
    {
        
        this.objs = data;
        
        initialization();
        
        bounds();
        
        normalization();
        
        // calculate metric values
        switch (type)
        {
            case "WCM": weightedChebyshevMethod(w);
            break;
            case "WSFM": weightedStressMethod(w);
            break;
        }
        
        
        return (findMinIdx(metric)+1);
    }
    
    
    // calc metric values using weighted Chebyshev method
    private void weightedChebyshevMethod(double [] w)
    {
        int i, j;
        double value;
        
        for(i = 0; i < size; i++)
        {
            for(j = 0; j < dim; j++)
            {
                value = objs[i][j]*w[j];
                if (value > metric[i]){
                    metric[i] = value;
                }
            }
        }
    }
    
    
    private void weightedStressMethod(double [] w)
    {
        // init params
        double delta1 = 0.001;
        double delta2 = 0.001;
        
        // values
        double xi, phi, psi, omega;
        double [] sigma = new double[w.length];
        
        int i, j;
        
        // for minimization problem, only for 2 obj !!!
        for(j = 0; j < dim; j++){
            w[j] = 1.0-w[j];
        }
        
        // calc metric values
        for(i = 0; i < size; i++)
        {
            for(j = 0; j < dim; j++)
            {
                xi = -1/Math.tan(-Math.PI/(2+2*delta2))*Math.tan(Math.PI*(w[j]-0.5)/(1+delta2)) + 1;
                phi = 3/4*Math.pow(1-w[j], 2) + 2*(1-w[j])+delta1;
                psi = phi + 4*w[j] - 2;
                omega = -xi*psi/(Math.tan(Math.PI/phi*(w[j]-1)+delta1)*phi);
                if(objs[i][j]<=w[j]){
                    sigma[j] = omega*Math.tan(-Math.PI/psi*(objs[i][j]-w[j])) + xi;
                }
                else{
                    sigma[j] = -xi/Math.tan(Math.PI/phi*(w[j]-1))*Math.tan(-Math.PI/phi*(objs[i][j]-w[j])) + xi;
                }
            }
            metric[i] =  findMaxValue(sigma);
        }
        
    }
    
    
    private void normalization()
    {
        int i, j;
        
        // normalize pop members
        for(i = 0; i < size; i++)
        {
            for(j = 0; j < dim; j++){
                objs[i][j] = (objs[i][j] - ideal[j])/(nadir[j] - ideal[j]);
            }
        }
    }
    
    
    private void bounds()
    {
        int i, j;
        for (j = 0; j < dim; j++){
            ideal[j] = objs[0][j];
            nadir[j] = objs[0][j];
        }
        
        for(i = 1; i < size; i++)
        {
            for(j = 0; j < dim; j++)
            {
                if (objs[i][j] < ideal[j]){
                    ideal[j] = objs[i][j];
                }
                if (objs[i][j] > nadir[j]){
                    nadir[j] = objs[i][j];
                }
            }
        }
    }
    
    
    private void initialization()
    {
        size = objs.length;
        dim = objs[0].length;
        
        metric = new double[size];
        java.util.Arrays.fill(metric, 0.0);
        
        ideal = new double[dim]; // ideal point
        nadir = new double[dim]; // nadir point of PF
    }
    
    
    public int findMinIdx(double [] vector)
    {
        double minValue = vector[0];
        int idx = 0;
        
        for(int j = 1; j < vector.length; j++)
        {
            if(vector[j] < minValue){
                minValue = vector[j];
                idx = j;
            }
        }
        
        return idx;
    }
    
    public double findMaxValue(double [] vector)
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
    
    
    private Scanner fin; // object to read data from file
    private Formatter fout; // object to save data to file
    
    // method to open file
    private void finOpen(String fileName)
    {
        try{
            fin = new Scanner(new File(fileName));
        }
        catch (Exception e){
            System.out.println("error opening " + fileName + " : " + e);
        }
        
    } // finOpen method
    
    // method to close file
    private void finClose()
    {
        fin.close();
    } // finClose method
    
    
    
    // method to open file
    private void foutOpen(String fileName)
    {
        // open file
        try {
            fout = new Formatter(fileName);
        }
        catch (Exception e) {
            System.out.println("error opening " + fileName + " : " + e);
        }
    }
    
    // method to close file
    private void foutClose()
    {
        fout.close();
    }
}
