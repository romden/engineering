/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package jmops.LGZ;

/**
 *
 * @author Administrator
 */

public class Problems
{
    
    public static double[] getLB(int n, int numProb)
    {
        double [] lb = new double[n];
        
        for(int i = 0; i < n; i++){
            lb[i] = 0.0;
        }
        
        return lb;
    }
    
    public static double[] getUB(int n, int numProb)
    {
        double [] ub = new double[n];
        
        for(int i = 0; i < n; i++){
            ub[i] = 1.0;
        }
        
        return ub;
    }
    
    
    public static double[] LGZ1(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LGZ1
        double pi = 3.1415926535897932384626433832795;
        double tj;
        double g = 0.0;
        
        for(int j = 1; j < n; j++)
        {
            tj = x[j] - Math.sin(0.5*pi*x[0]);
            g+= 2.0*Math.sin(pi*x[0])*(-0.9*tj*tj + Math.pow(Math.abs(tj), 0.6));
        }
        
        f[0] = (1.0 + g)*x[0];
        f[1] = (1.0 + g)*(1- Math.sqrt(x[0]));
        // end LGZ1
        
        return f;
    }
    
    
    public static double[] LGZ2(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LGZ2
        double pi = 3.1415926535897932384626433832795;
        double tj;
        double g = 0.0;
        
        for(int j = 1; j < n; j++)
        {
            tj = x[j] - Math.sin(0.5*pi*x[0]);
            g+= 10.0*Math.sin(pi*x[0])*(Math.abs(tj) / (1+Math.exp(5.0*Math.abs(tj) ) ) );
        }
        
        f[0] = (1.0 + g)*x[0];
        f[1] = (1.0 + g)*(1- x[0]*x[0]);
        // end LGZ2
        
        return f;
    }
    
    
    public static double[] LGZ3(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LGZ3
        double pi = 3.1415926535897932384626433832795;
        double tj;
        double g = 0.0;
        
        for(int j = 1; j < n; j++)
        {
            tj = x[j] - Math.sin(0.5*pi*x[0]);
            g+= 10.0*Math.sin(pi*x[0]/2.0)*(Math.abs(tj) / (1+Math.exp(5.0*Math.abs(tj) ) ) );
        }
        
        f[0] = (1.0 + g)*Math.cos(pi*x[0]/2.0);
        f[1] = (1.0 + g)*Math.sin(pi*x[0]/2.0);
        // end LGZ3
        
        return f;
    }
    
    
    public static double[] LGZ4(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LGZ4
        double pi = 3.1415926535897932384626433832795;
        double tj;
        double g = 0.0;
        
        for(int j = 1; j < n; j++)
        {
            tj = x[j] - Math.sin(0.5*pi*x[0]);
            g+= 10.0*Math.sin(pi*x[0])*(Math.abs(tj) / (1+Math.exp(5.0*Math.abs(tj) ) ) );
        }
        //g+= 1.0;
        
        f[0] = (1.0 + g)*x[0];
        f[1] = (1.0 + g)*(1.0 - Math.pow(x[0], 0.5)*Math.cos(2*pi*x[0])*Math.cos(2*pi*x[0]));
        // end LGZ4
        
        return f;
    }
    
    
    public static double[] LGZ5(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LGZ5
        double pi = 3.1415926535897932384626433832795;
        double tj;
        double g = 0.0;
        
        for(int j = 1; j < n; j++)
        {
            tj = x[j] - Math.sin(0.5*pi*x[0]);
            g+= 2.0*Math.abs(Math.cos(pi*x[0]))*(-0.9*tj*tj + Math.pow(Math.abs(tj), 0.6));
        }
        
        f[0] = (1.0 + g)*x[0];
        f[1] = (1.0 + g)*(1- Math.sqrt(x[0]));
        // end LGZ5
        
        return f;
    }
    
    
    public static double[] LGZ6(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LGZ6
        double pi = 3.1415926535897932384626433832795;
        double tj;
        double g = 0.0;
        
        for(int j = 2; j < n; j++)
        {
            tj = x[j] - x[0]*x[1];
            g+= 2.0*Math.sin(pi*x[0])*(-0.9*tj*tj + Math.pow(Math.abs(tj), 0.6));
        }
        
        f[0] = (1.0 + g)*x[0]*x[1];
        f[1] = (1.0 + g)*x[0]*(1- x[1]);
        f[2] = (1.0 + g)*(1- x[0]);
        // end LGZ6
        
        return f;
    }
    
    
    public static double[] LGZ7(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LGZ7
        double pi = 3.1415926535897932384626433832795;
        double tj;
        double g = 0.0;
        
        for(int j = 2; j < n; j++)
        {
            tj = x[j] - x[0]*x[1];
            g+= 2.0*Math.sin(pi*x[0])*(-0.9*tj*tj + Math.pow(Math.abs(tj), 0.6));
        }
        
        f[0] = (1.0 + g)*Math.cos(pi*x[0]/2.0)*Math.cos(pi*x[1]/2.0);
        f[1] = (1.0 + g)*Math.cos(pi*x[0]/2.0)*Math.sin(pi*x[1]/2.0);
        f[2] = (1.0 + g)*Math.sin(pi*x[0]/2.0);
        // end LGZ7
        
        return f;
    }
}
