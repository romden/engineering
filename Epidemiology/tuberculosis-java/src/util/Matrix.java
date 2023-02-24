package util;



import java.io.File;
import java.util.Formatter;
import java.util.Scanner;

import org.jblas.*;


public class Matrix
{
    
    private MatrixFunctions matFun = new MatrixFunctions();
//    private Eigen eigen = new Eigen();
//    private Geometry geometry = new Geometry();
    
    private DoubleMatrix mx;
    
    private Scanner fin;
    private Formatter fout;
    
    
    public Matrix()
    {
        
    }
    
    
    // method to read from file
    public double [][] input(String fileName, int n, int m)
    {
        
        // open file containing data
        try{
            fin = new Scanner(new File(fileName));
        }
        catch (Exception e){
            System.out.println("error opening " + fileName + " : " + e);
        }
        
        // create
        double [][] data = new double[n][m];
        
        // read data
        int i, j;
        for (i = 0; i < n; i++)
        {
            for (j = 0; j < m; j++)
                data[i][j] = Double.parseDouble(fin.next());
        }
        
        // close file
        fin.close();
        
        return data;
        
    } // method input
    
    
    // method to save data storing in a matrix to a file
    public void output(String fileName, double [][] data)
    {
        //open file
        try {
            fout = new Formatter(fileName);
        }
        catch (Exception e) {
            System.out.println("error opening " + fileName + " : " + e);
        }
        
        // save data
        int i, j;
        for (i = 0; i < data.length; i++)
        {
            for (j = 0; j < data[i].length; j++)
                fout.format("%s ", data[i][j]);
            fout.format("%n");
        }
        
        // close file
        fout.close();
        
    } // method output
    
    
    // method to multiply matrix by scalar
    public double [][] product(double [][] A, double b)
    {
        // get sizes
        int n = A.length;
        int m = A[0].length;
        
        // declare result
        double [][] C = new double[n][m];
        
        // calculate product
        int i, j;
        for (i = 0; i < n; i++)
        {
            for (j = 0; j < m; j++)
                C[i][j] = A[i][j]*b;
        }
        
        return C;
        
    } // method scalar
    
    
    // method to compute matrix product
    public double [][] product(double [][] A, double [][] B)
    {
        // get sizes
        int n = A.length;
        int m = B.length;
        int p = B[0].length;
        
        // declare result
        double [][] C = new double[n][p];
        
        // calculate product
        int i, j, k;
        for (i = 0; i < n; i++)
        {
            for (j = 0; j < p; j++)
            {
                C[i][j] = 0;
                for(k = 0; k < m; k++)
                    C[i][j] += A[i][k]*B[k][j];
            }            
        }
        
        return C;
        
    } // method product
    
    
    // method to compute matrix product
    public double [] product(double [] A, double [][] B)
    {
        // get sizes
        int n = A.length;
        
        // declare result
        double [] C = new double[n];
        
        // calculate product
        int i, j;
        for (i = 0; i < n; i++)
        {
            C[i] = 0;
            for (j = 0; j < n; j++)
                C[i] += A[j]*B[j][i];
        }
        
        return C;
        
    } // method product
    
    
        // method to compute matrix product
    public double [] product(double [][] A, double [] B)
    {
        // get sizes
        int n = A.length;
        
        // declare result
        double [] C = new double[n];
        
        // calculate product
        int i, j;
        for(i = 0; i < n; i++){
            C[i] = 0;
            for (j = 0; j < n; j++){
                C[i] += A[i][j]*B[j];
            }
        }
        
        return C;
        
    } // method product
    
    
//    public double [][] centerRows(double [][] A)
//    {
//        return unwrap(Geometry.centerRows(wrap(A)));
//    }
    
    public double [][] centerRows(double [][] data)
    {
        int n = data.length;
        int m = data[0].length;
        double [] mean = new double[m];
        for (int j = 0; j < m; j++){
            mean[j] = 0.0;
        }
        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                mean[j] += data[i][j];
            }
        }
        for (int j = 0; j < m; j++){
            mean[j] /= n;
        }
        
        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                data[i][j] -= mean[j];
            }
        }
        
        return data;
    }
    
    
    public double [][] transpose(double [][] A)
    {        
        return unwrap(wrap(A).transpose());
    }
    
    
    // method to 
    public void eigen(double [][] A, double [][] D, double [][] V)
    {
        DoubleMatrix [] temp = Eigen.symmetricEigenvectors(wrap(A));
        
        // eigen vectors
        V = unwrap(temp[0]);
        
        // eigen velues
        D = unwrap(temp[1]);
        
    } // method eye
    
    
    // method to generate identity matrix
    public double [][] eye(int n)
    {
        // wrap, calculate, and return
        return unwrap(mx.eye(n));
        
    } // method eye
    
    
    // method to compute matrix exponential
    public double [][] expm(double [][] A)
    {
        // wrap, calculate, and return
        return unwrap(matFun.expm(wrap(A)));
        
    } // method expm
    
    
    // method to wrap data
    private DoubleMatrix wrap(double [][] inArray)
    {
        // get sizes
        int n = inArray.length;
        int m = inArray[0].length;
        
        DoubleMatrix M = new DoubleMatrix(n, m);
        
        int i, j;
        for (i = 0; i < n; i++)
        {
            for (j = 0; j < m; j++)
                M.put(i, j, inArray[i][j]);
        }
        
        return M;
        
    } // method wrap
    
    
    // method to unwrap data
    private double [][] unwrap(DoubleMatrix M)
    {
        // get sizes
        int n = M.rows;
        int m = M.columns;
        
        double [][] outArray = new double[n][m];
        
        int i, j;
        for (i = 0; i < n; i++)
        {
            for (j = 0; j < m; j++)
                outArray[i][j] = M.get(i,j);
        }
        
        return outArray;
        
    } // method unwrap
    
    
} // class Matrix
