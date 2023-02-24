/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package jmops.ZDT;

/**
 *
 * @author Administrator
 */

public class Problems
{
    
    public static double[] getLB(int n, int numProb)
    {
        double [] lb = new double[n];
        
        if(numProb == 4){
            java.util.Arrays.fill(lb, -5.0); lb[0] = 0.0;
        }
        else{
            java.util.Arrays.fill(lb, 0.0);
        }
        
        return lb;
    }
    
    public static double[] getUB(int n, int numProb)
    {
        double [] ub = new double[n];
        
        if(numProb == 4){
            java.util.Arrays.fill(ub, 5.0); ub[0] = 1.0;
        }
        else{
            java.util.Arrays.fill(ub, 1.0);
        }
        
        return ub;
    }
    
    
    public static double[] ZDT1(double [] x_var, int m)
    {
        // declare
        int nvar = x_var.length;
        double [] y_obj = new double[2]; // obj values
        
        // ZDT1
        double pi = 3.1415926535897932384626433832795;
        double g = 0;
        for(int n=1;n<nvar;n++)
            g+= x_var[n];
        g = 1 + 9*g/(nvar-1);
        
        y_obj[0] = x_var[0];
        y_obj[1] = g*(1 - Math.sqrt(y_obj[0]/g));
        // end ZDT1
        
        return y_obj;
    }
    
    public static double[] ZDT2(double [] x_var, int m)
    {
        // declare
        int nvar = x_var.length;
        double [] y_obj = new double[2]; // obj values
        
        // ZDT2
        double pi = 3.1415926535897932384626433832795;
        double g = 0;
        for(int n=1;n<nvar;n++)
            g+= x_var[n];
        g = 1 + 9*g/(nvar-1);
        y_obj[0] = x_var[0];
        y_obj[1] = g*(1 - Math.pow(y_obj[0]/g,2));
        // end ZDT2
        
        return y_obj;
    }
    
    public static double[] ZDT3(double [] x_var, int m)
    {
        // declare
        int nvar = x_var.length;
        double [] y_obj = new double[2]; // obj values
        
        // ZDT3
        double pi = 3.1415926535897932384626433832795;
        double g = 0;
        for(int n=1;n<nvar;n++)
            g+= x_var[n];
        g = 1 + 9*g/(nvar-1);
        
        y_obj[0] = x_var[0];
        y_obj[1] = g*(1 - Math.sqrt(x_var[0]/g) - x_var[0]*Math.sin(10*pi*x_var[0])/g);
        // end ZDT3
        
        return y_obj;
    }
    
    public static double[] ZDT4(double [] x_var, int m)
    {
        // declare
        int nvar = x_var.length;
        double [] y_obj = new double[2]; // obj values
        
        // ZDT4
        double pi = 3.1415926535897932384626433832795;
        double g = 1.0 + 10.0*(nvar-1.0);
        
        for(int n=1;n<nvar;n++){
            g+= x_var[n]*x_var[n] - 10*Math.cos(4*pi*x_var[n]);
        }
        
        y_obj[0] = x_var[0];
        y_obj[1] = g*(1- Math.sqrt(y_obj[0]/g));
        // end ZDT4
        
        return y_obj;
    }
    
    public static double[] ZDT6(double [] x_var, int m)
    {
        // declare
        int nvar = x_var.length;
        double [] y_obj = new double[2]; // obj values
        
        // ZDT6
        double pi = 3.1415926535897932384626433832795;
        double g = 0;
        for(int n=1;n<nvar;n++)
            g+= x_var[n]/(nvar - 1);
        g = 1 + 9*Math.pow(g,0.25) ;
        
        y_obj[0] = 1 - Math.exp(-4*x_var[0])*Math.pow(Math.sin(6*pi*x_var[0]),6);
        y_obj[1] = g*(1- Math.pow(y_obj[0]/g,2));
        // end ZDT6
        
        return y_obj;
    }
}

