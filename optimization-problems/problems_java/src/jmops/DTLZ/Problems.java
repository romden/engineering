package jmops.DTLZ;


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
        
    
    public static double[] DTLZ1(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        int k = n - m + 1; // number of position parameters
        double g = 0.0; // func
        
        // DTLZ1
        for (int i = n - k; i < n; i++)
            g += (x[i] - 0.5)*(x[i] - 0.5) - Math.cos(20.0 * Math.PI * ( x[i] - 0.5));
        
        g = 100 * (k + g);
        for (int i = 0; i < m; i++)
            f[i] = (1.0 + g) * 0.5;
        
        for (int i = 0; i < m; i++){
            for (int j = 0; j < m - (i + 1); j++)
                f[i] *= x[j];
            if (i != 0){
                int aux = m - (i + 1);
                f[i] *= 1 - x[aux];
            } //if
        }//for
        // end DTLZ1
        
        return f;
    }
    
    public static double[] DTLZ2(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        int k = n - m + 1; // number of position parameters
        double g = 0.0; // func
        
        // DTLZ2
        for (int i = n - k; i < n; i++)
            g += (x[i] - 0.5)*(x[i] - 0.5);
        
        for (int i = 0; i < m; i++)
            f[i] = 1.0 + g;
        
        for (int i = 0; i < m; i++){
            for (int j = 0; j < m - (i + 1); j++)
                f[i] *= Math.cos(x[j]*0.5*Math.PI);
            if (i != 0){
                int aux = m - (i + 1);
                f[i] *= Math.sin(x[aux]*0.5*Math.PI);
            } //if
        } // for
        // end DTLZ2
        
        return f;
    }
    
    public static double[] DTLZ3(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        int k = n - m + 1; // number of position parameters
        double g = 0.0; // func
        
        // DTLZ3
        for (int i = n - k; i < n; i++)
            g += (x[i] - 0.5)*(x[i] - 0.5) - Math.cos(20.0 * Math.PI * (x[i] - 0.5));
        
        g = 100.0 * (k + g);
        for (int i = 0; i < m; i++)
            f[i] = 1.0 + g;
        
        for (int i = 0; i < m; i++){
            for (int j = 0; j < m - (i + 1); j++)
                f[i] *= java.lang.Math.cos(x[j]*0.5*java.lang.Math.PI);
            if (i != 0){
                int aux = m - (i + 1);
                f[i] *= java.lang.Math.sin(x[aux]*0.5*java.lang.Math.PI);
            } // if
        } //for
        // end DTLZ3
        
        return f;
    }
    
    public static double[] DTLZ4(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        int k = n - m + 1; // number of position parameters
        double g = 0.0; // func
        
        // DTLZ4
        double alpha = 100.0;
        for (int i = n - k; i < n; i++)
            g += (x[i] - 0.5)*(x[i] - 0.5);
        
        for (int i = 0; i < m; i++)
            f[i] = 1.0 + g;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m - (i + 1); j++)
                f[i] *= java.lang.Math.cos(java.lang.Math.pow(x[j],alpha)*(java.lang.Math.PI/2.0));
            if (i != 0){
                int aux = m - (i + 1);
                f[i] *= java.lang.Math.sin(java.lang.Math.pow(x[aux],alpha)*(java.lang.Math.PI/2.0));
            } //if
        } // for
        // end DTLZ4
        
        return f;
    }
    
    public static double[] DTLZ5(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        int k = n - m + 1; // number of position parameters
        double g = 0.0; // func
        double [] theta = new double[m-1]; // var for DTLZ5,6
        double t; // var for DTLZ5,6
        
        // DTLZ5
        for (int i = n - k; i < n; i++)
            g += (x[i] - 0.5)*(x[i] - 0.5);
        
        t = java.lang.Math.PI  / (4.0 * (1.0 + g));
        
        theta[0] = x[0] * java.lang.Math.PI / 2.0;
        for (int i = 1; i < (m-1); i++)
            theta[i] = t * (1.0 + 2.0 * g * x[i]);
        
        for (int i = 0; i < m; i++)
            f[i] = 1.0 + g;
        
        for (int i = 0; i < m; i++){
            for (int j = 0; j < m - (i + 1); j++)
                f[i] *= java.lang.Math.cos(theta[j]);
            if (i != 0){
                int aux = m - (i + 1);
                f[i] *= java.lang.Math.sin(theta[aux]);
            } // if
        } //for
        // end DTLZ5
        
        return f;
    }
    
    public static double[] DTLZ6(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        int k = n - m + 1; // number of position parameters
        double g = 0.0; // func
        double [] theta = new double[m-1]; // var for DTLZ5,6
        double t; // var for DTLZ5,6
        
        // DTLZ6
        for (int i = n - k; i < n; i++)
            g += java.lang.Math.pow(x[i],0.1);
        
        t = java.lang.Math.PI  / (4.0 * (1.0 + g));
        
        theta[0] = x[0] * java.lang.Math.PI / 2;
        for (int i = 1; i < (m-1); i++)
            theta[i] = t * (1.0 + 2.0 * g * x[i]);
        
        for (int i = 0; i < m; i++)
            f[i] = 1.0 + g;
        
        for (int i = 0; i < m; i++){
            for (int j = 0; j < m - (i + 1); j++)
                f[i] *= java.lang.Math.cos(theta[j]);
            if (i != 0){
                int aux = m - (i + 1);
                f[i] *= java.lang.Math.sin(theta[aux]);
            } //if
        } // for
        // end DTLZ6
        
        return f;
    }
    
    public static double[] DTLZ7(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        int k = n - m + 1; // number of position parameters
        double g = 0.0; // func
        
        // DTLZ7
        for (int i = n - k; i < n; i++)
            g += x[i] ;
        
        g = 1 + (9.0 * g) / k;
        //Calculate the value of f1,f2,f3,...,fM-1 (take acount of vectors start at 0)
        for (int i = 0; i < m-1; i++)
            f[i] = x[i];
        
        //Calculate fM
        double h = 0.0;
        for (int i = 0; i < m -1; i++)
            h += (f[i]/(1.0 + g))*(1 + Math.sin(3.0 * Math.PI * f[i]));
        
        h = m - h;
        
        f[m-1] = (1 + g) * h;
        // end DTLZ7
        
        return f;
    }
}
