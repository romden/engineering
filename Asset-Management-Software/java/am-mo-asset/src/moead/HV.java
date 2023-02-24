package moead;


public class HV
{
    void min_sort(double [] x, int [] idx, int n, int m)
    {
        int i, j, id;
        double temp;
        
        for (i = 0; i < m; i++)
        {
            for (j = i + 1; j < n; j++)
            {
                if (x[i] > x[j])
                {
                    temp = x[i];
                    x[i] = x[j];
                    x[j] = temp;
                    id = idx[i];
                    idx[i] = idx[j];
                    idx[j] = id;
                } // if
            }
        } // for
        
    } // minFastSort
    
    
    
    void calc_hv_contributions_dim2(double [][] points, int dim, int size, double [] s)
    {
        // f is a matrix of size (dim x size), each row represents obj vector
        
        // init indexes
        double [] values = new double[size];
        int [] idx = new int[size];
        int i;
        
        for(i = 0; i < size; i++)
        {
            values[i] = points[0][i];
            idx[i] = i;
        }
        
        // sort
        min_sort(values, idx, size, size);
        
        
        s[idx[0]] = 1.0;
        for(i = 1; i < size-1; i++){
            s[idx[i]] = (points[0][idx[i+1]] - points[0][idx[i]])*(points[1][idx[i-1]] - points[1][idx[i]]);
        }
        s[idx[size-1]] = 1.0;
        
    }
    
    
    void calc_hv_contributions_dim3(double [][] points, int dim, int size, double [] s)
    {
        // f is a matrix of size (dim x size), each row represents obj vector
        
        // init vars
        int i, j, k;
        
        double [] a = new double[size+1];
        double [] b = new double[size+1];
        
        int [] idx_a = new int[size+1];
        int [] idx_b = new int[size+1];
        
        double [][] best1_f3 = new double[size][size];
        double [][] best2_f3 = new double[size][size];
        
        for(i = 0; i < size; i++)
        {
            s[i] = 0.0; // init S metric
            
            a[i] = points[0][i];
            b[i] = points[1][i];
            
            idx_a[i] = i;
            idx_b[i] = i;
            
            for(j = 0; j < size; j++)
            {
                best1_f3[i][j] = 1.1;
                best2_f3[i][j] = 1.1;
            }
        }
        a[size] = 1.1;
        b[size] = 1.1;
        idx_a[size] = size;
        idx_b[size] = size;
        
        // sort
        min_sort(a, idx_a, size, size);
        min_sort(b, idx_b, size, size);
        
        
        for(i = 0; i < size; i++)
        {
            for(j = 0; j < size; j++)
            {
                for(k = 0; k < size; k++)
                {
                    if(points[0][k] <= a[i] && points[1][k] <= b[j])
                    {
                        // s_k dominates cell (i,j) conc. f1, f2
                        if(points[2][k] < best1_f3[i][j])
                        {
                            best2_f3[i][j] = best1_f3[i][j];
                            best1_f3[i][j] = points[2][k];
                        }
                        else if(points[2][k] < best2_f3[i][j] && points[2][k] > best1_f3[i][j])
                        {
                            best2_f3[i][j] = points[2][k];
                        }
                    }
                }
            }
        }
        
        
        for(i = 0; i < size; i++)
        {
            for(j = 0; j < size; j++)
            {
                int ownerNumber = 0;
                int owner = 0;
                for(k = 0; k < size; k++)
                {
                    if(points[0][k] <= a[i] && points[1][k] <= b[j] && best1_f3[i][j] == points[2][k])
                    {
                        // s_k dominates cell (i,j) conc. f1, f2
                        ownerNumber++;
                        owner = k;
                    }
                }
                if(ownerNumber == 1)
                {
                    // cell (i,j) is dominated disjoint
                    s[owner] += (a[i+1] - a[i]) * (b[j+1] - b[j]) * (best2_f3[i][j] - best1_f3[i][j]);
                }
            }
        }
    }
}
