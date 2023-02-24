/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package jmops.LZ09;


/**
 *
 * @author Administrator
 */

public class Problems
{
    public static double[] getLB(int n, int numProb)
    {
        double [] lb = new double[n];
        
        switch(numProb)
        {
            case 1:
                java.util.Arrays.fill(lb, 0.0);
                break;
            case 2:
                java.util.Arrays.fill(lb, -1.0); lb[0] = 0.0;
                break;
            case 3:
                java.util.Arrays.fill(lb, -1.0); lb[0] = 0.0;
                break;
            case 4:
                java.util.Arrays.fill(lb, -1.0); lb[0] = 0.0;
                break;
            case 5:
                java.util.Arrays.fill(lb, -1.0); lb[0] = 0.0;
                break;
            case 6:
                java.util.Arrays.fill(lb, -2.0); lb[0] = 0.0; lb[1] = 0.0;
                break;
            case 7:
                java.util.Arrays.fill(lb, 0.0);
                break;
            case 8:
                java.util.Arrays.fill(lb, 0.0);
                break;
            case 9:
                java.util.Arrays.fill(lb, -1.0); lb[0] = 0.0;
                break;
        }
        
        return lb;
    }
    
    public static double[] getUB(int n, int numProb)
    {
        double [] ub = new double[n];
        
        switch(numProb)
        {
            case 1:
                java.util.Arrays.fill(ub, 1.0);
                break;
            case 2:
                java.util.Arrays.fill(ub, 1.0);
                break;
            case 3:
                java.util.Arrays.fill(ub, 1.0);
                break;
            case 4:
                java.util.Arrays.fill(ub, 1.0);
                break;
            case 5:
                java.util.Arrays.fill(ub, 1.0);
                break;
            case 6:
                java.util.Arrays.fill(ub, 2.0); ub[0] = 1.0; ub[1] = 1.0;
                break;
            case 7:
                java.util.Arrays.fill(ub, 1.0);
                break;
            case 8:
                java.util.Arrays.fill(ub, 1.0);
                break;
            case 9:
                java.util.Arrays.fill(ub, 1.0);
                break;
        }
        
        return ub;
    }
    
    
    public static double[] LZ09_F1(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LZ09_F1
        int j, count1, count2;
        double sum1, sum2, yj;
        
        sum1 = sum2 = 0.0;
        count1 = count2 = 0;
        
        for ( j = 2; j <= n; j++ )
        {
            yj = x[j - 1] - Math.pow(x[0],  0.5*(1.0 + 3.0*(j - 2.0)/(n - 2.0) ));
            yj = yj * yj;
            if ( j % 2 == 0 )
            {
                sum2 += yj;
                count2++;
            }
            else
            {
                sum1 += yj;
                count1++;
            }
        }
        
        f[0] = x[0] + 2.0 * sum1 / ( double )count1;
        f[1] = 1.0 - Math.sqrt( x[0] ) + 2.0 * sum2 / ( double )count2;
        // end LZ09_F1
        
        return f;
    }
    
    
    public static double[] LZ09_F2(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LZ09_F2
        int j, count1, count2;
        double sum1, sum2, yj;
        
        sum1 = sum2 = 0.0;
        count1 = count2 = 0;
        
        for ( j = 2; j <= n; j++ )
        {
            yj = x[j - 1] - Math.sin( 6.0 * Math.PI * x[0] + j * Math.PI / n );
            yj = yj * yj;
            if ( j % 2 == 0 )
            {
                sum2 += yj;
                count2++;
            }
            else
            {
                sum1 += yj;
                count1++;
            }
        }
        f[0] = x[0] + 2.0 * sum1 / ( double )count1;
        f[1] = 1.0 - Math.sqrt( x[0] ) + 2.0 * sum2 / ( double )count2;
        // end LZ09_F2
        
        return f;
    }
    
    
    public static double[] LZ09_F3(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LZ09_F3
        int j, count1, count2;
        double sum1, sum2, yj;
        
        sum1 = sum2 = 0.0;
        count1 = count2 = 0;
        
        for ( j = 2; j <= n; j++ )
        {
            if ( j % 2 == 0 )
            {
                yj = x[j - 1] - 0.8*x[0]*Math.sin( 6.0 * Math.PI * x[0] + j * Math.PI / n );
                sum2 += yj * yj;
                count2++;
            }
            else
            {
                yj = x[j - 1] - 0.8*x[0]*Math.cos( 6.0 * Math.PI * x[0] + j * Math.PI / n );
                sum1 += yj * yj;
                count1++;
            }
        }
        f[0] = x[0] + 2.0 * sum1 / ( double )count1;
        f[1] = 1.0 - Math.sqrt( x[0] ) + 2.0 * sum2 / ( double )count2;
        // end LZ09_F3
        
        return f;
    }
    
    
    public static double[] LZ09_F4(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LZ09_F4
        int j, count1, count2;
        double sum1, sum2, yj;
        
        sum1 = sum2 = 0.0;
        count1 = count2 = 0;
        
        for ( j = 2; j <= n; j++ )
        {
            if ( j % 2 == 0 )
            {
                yj = x[j - 1] - 0.8*x[0]*Math.sin( 6.0 * Math.PI * x[0] + j * Math.PI / n );
                sum2 += yj * yj;
                count2++;
            }
            else
            {
                yj = x[j - 1] - 0.8*x[0]*Math.cos( (6.0 * Math.PI * x[0] + j * Math.PI / n)/3 );
                sum1 += yj * yj;
                count1++;
            }
        }
        f[0] = x[0] + 2.0 * sum1 / ( double )count1;
        f[1] = 1.0 - Math.sqrt( x[0] ) + 2.0 * sum2 / ( double )count2;
        // end LZ09_F4
        
        return f;
    }
    
    
    public static double[] LZ09_F5(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LZ09_F5
        int j, count1, count2;
        double sum1, sum2, yj;
        
        sum1 = sum2 = 0.0;
        count1 = count2 = 0;
        
        for ( j = 2; j <= n; j++ )
        {
            if ( j % 2 == 0 )
            {
                yj = x[j - 1] - 0.3 * x[0] * ( x[0] * Math.cos( 24.0 * Math.PI * x[0] + 4.0 * j * Math.PI/n ) + 2.0 )
                        * Math.sin( 6.0 * Math.PI * x[0] + j * Math.PI/n );
                sum2 += yj * yj;
                count2++;
            }
            else
            {
                yj = x[j - 1] - 0.3 * x[0] * ( x[0] * Math.cos( 24.0 * Math.PI * x[0] + 4.0 * j * Math.PI/n ) + 2.0 )
                        * Math.cos( 6.0 * Math.PI * x[0] + j * Math.PI/n );
                sum1 += yj * yj;
                count1++;
            }
        }
        f[0] = x[0] + 2.0 * sum1 / ( double )count1;
        f[1] = 1.0 - Math.sqrt( x[0] ) + 2.0 * sum2 / ( double )count2;
        // end LZ09_F5
        
        return f;
    }
    
    
    public static double[] LZ09_F6(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LZ09_F6
        int j, count1, count2, count3;
        double sum1, sum2, sum3, yj;
        
        sum1 = sum2 = sum3 = 0.0;
        count1 = count2 = count3 = 0;
        for ( j = 3; j <= n; j++ )
        {
            yj = x[j - 1] - 2.0 * x[1] * Math.sin( 2.0 * Math.PI * x[0] + j * Math.PI / n );
            if ( j % 3 == 1 )
            {
                sum1 += yj * yj;
                count1++;
            }
            else if ( j % 3 == 2 )
            {
                sum2 += yj * yj;
                count2++;
            }
            else
            {
                sum3 += yj * yj;
                count3++;
            }
        }
        f[0] = Math.cos( 0.5 * Math.PI * x[0] ) * Math.cos( 0.5 * Math.PI * x[1] ) + 2.0 * sum1 / ( double )count1;
        f[1] = Math.cos( 0.5 * Math.PI * x[0] ) * Math.sin( 0.5 * Math.PI * x[1] ) + 2.0 * sum2 / ( double )count2;
        f[2] = Math.sin( 0.5 * Math.PI * x[0] ) + 2.0 * sum3 / ( double )count3;
        // end LZ09_F6
        
        return f;
    }
    
    
    public static double[] LZ09_F7(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LZ09_F7
        int j, count1, count2;
        double sum1, sum2, prod1, prod2, yj, pj;
        
        sum1 = sum2 = 0.0;
        count1 = count2 = 0;
        prod1 = prod2 = 1.0;
        for ( j = 2; j <= n; j++ )
        {
            yj = x[j - 1] - Math.pow( x[0], 0.5 * ( 1.0 + 3.0 * ( j - 2.0 ) / ( n - 2.0 ) ) );
            pj = Math.cos( 8.0 * yj * Math.PI );
            if ( j % 2 == 0 )
            {
                sum2 += 4.0*yj * yj - pj +1.0;
                count2++;
            }
            else
            {
                sum1 += 4.0*yj * yj - pj +1.0;
                count1++;
            }
        }
        f[0] = x[0] + 2.0 * sum1 / ( double )count1;
        f[1] = 1.0 - Math.sqrt( x[0] ) + 2.0 * sum2 / ( double )count2;
        // end LZ09_F7
        
        return f;
    }
    
    
    public static double[] LZ09_F8(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LZ09_F8
        int j, count1, count2;
        double sum1, sum2, prod1, prod2, yj, pj;
        
        sum1 = sum2 = 0.0;
        count1 = count2 = 0;
        prod1 = prod2 = 1.0;
        for ( j = 2; j <= n; j++ )
        {
            yj = x[j - 1] - Math.pow( x[0], 0.5 * ( 1.0 + 3.0 * ( j - 2.0 ) / ( n - 2.0 ) ) );
            pj = Math.cos( 20.0 * yj * Math.PI / Math.sqrt( j + 0.0 ) );
            if ( j % 2 == 0 )
            {
                sum2 += yj * yj;
                prod2 *= pj;
                count2++;
            }
            else
            {
                sum1 += yj * yj;
                prod1 *= pj;
                count1++;
            }
        }
        f[0] = x[0] + 2.0 * ( 4.0 * sum1 - 2.0 * prod1 + 2.0 ) / ( double )count1;
        f[1] = 1.0 - Math.sqrt( x[0] ) + 2.0 * ( 4.0 * sum2 - 2.0 * prod2 + 2.0 ) / ( double )count2;
        // end LZ09_F8
        
        return f;
    }
    
    
    public static double[] LZ09_F9(double [] x, int m)
    {
        // declare
        int n = x.length;
        double [] f = new double[m]; // obj values
        
        // LZ09_F9
        int j, count1, count2;
        double sum1, sum2, yj;
        
        sum1 = sum2 = 0.0;
        count1 = count2 = 0;
        
        for ( j = 2; j <= n; j++ )
        {
            yj = x[j - 1] - Math.sin( 6.0 * Math.PI * x[0] + j * Math.PI / n );
            yj = yj * yj;
            if ( j % 2 == 0 )
            {
                sum2 += yj;
                count2++;
            }
            else
            {
                sum1 += yj;
                count1++;
            }
        }
        f[0] = x[0] + 2.0 * sum1 / ( double )count1;
        f[1] = 1.0 - x[0]*x[0] + 2.0 * sum2 / ( double )count2;
        // end LZ09_F9
        
        return f;
    }
    
    
} // class

