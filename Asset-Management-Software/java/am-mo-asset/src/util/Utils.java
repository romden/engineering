//  Utils.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package util;

/**
 * Utilities methods to used by MOEA/D
 */
public class Utils {
    
    java.util.Random rand;
    
    
    public Utils()
    {
        rand = new java.util.Random();
    }
    
    
    public Utils(java.util.Random rand)
    {
        this.rand = rand;
    }
    

  public double distVector(double[] vector1, double[] vector2) {
    int dim = vector1.length;
    double sum = 0;
    for (int n = 0; n < dim; n++) {
      sum += (vector1[n] - vector2[n]) * (vector1[n] - vector2[n]);
    }
    return Math.sqrt(sum);
  } // distVector

  public void minFastSort(double x[], int idx[], int n, int m) {
    for (int i = 0; i < m; i++) {
      for (int j = i + 1; j < n; j++) {
        if (x[i] > x[j]) {
          double temp = x[i];
          x[i] = x[j];
          x[j] = temp;
          int id = idx[i];
          idx[i] = idx[j];
          idx[j] = id;
        } // if
      }
    } // for

  } // minFastSort

  public void randomPermutation(int[] perm, int size) {
    int[] index = new int[size];
    boolean[] flag = new boolean[size];

    for (int n = 0; n < size; n++) {
      index[n] = n;
      flag[n] = true;
    }

    int num = 0;
    while (num < size) {
      int start = rand.nextInt(size);
      //int start = int(size*nd_uni(&rnd_uni_init));
      while (true) {
        if (flag[start]) {
          perm[num] = index[start];
          flag[start] = false;
          num++;
          break;
        }
        if (start == (size - 1)) {
          start = 0;
        } else {
          start++;
        }
      }
    } // while
  } // randomPermutation
  
  
  public void maxSort(double x[], int n, int m) 
  {
    double temp;
    for (int i = 0; i < m; i++) 
    {
      for (int j = i + 1; j < n; j++) 
      {
        if (x[i] < x[j])
        {
          temp = x[i];
          x[i] = x[j];
          x[j] = temp;
        }
//          int id = idx[i];
//          idx[i] = idx[j];
//          idx[j] = id;
      }
    } // for

  } // maxSort
  
  
} // class