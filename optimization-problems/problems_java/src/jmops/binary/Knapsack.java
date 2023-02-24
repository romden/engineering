/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package jmops.binary;

/**
 *
 * @author Administrator
 */
public class Knapsack {
    
    public int numKnapsacks; // number of knapsacks
    public int numItems; // number of items
    
    public double [][] itemWeight;  // weight of each item
    public double [][] itemProfit; // profit of each item for each knapsack
    public double [] capacities; // knapsacks capacities
    
    public double [] weights; // weights of knapsacks for given individual
    public double [] objectives; // objectives representing profits of knapsacks
    
    public double [] variables;
    
    // auxiliary variables
    private double [] ratio; // profit weight ration
    private int [] indexes; // indexes of iitems
    
    private java.util.Scanner [] fin;
    
    
    public Knapsack()
    {
        
    }
    
    public Knapsack(int numKnapsacks, int numItems, String folder)
    {
        init(numKnapsacks, numItems, folder);
        
        calcProfitWeightRation();
    }
    
    
    private void initArrays()
    {
        // initialzie arrays
        capacities = new double[numKnapsacks];
        itemWeight = new double[numKnapsacks][numItems];
        itemProfit = new double[numKnapsacks][numItems];
        
        weights = new double[numKnapsacks];
        objectives = new double[numKnapsacks];
        
        ratio = new double[numItems]; // array storing profit/weight ratio
        indexes = new int[numItems]; // vector of item indexes for repair procedure
    }
    
    // initialization method from file
    public void init(int numKnapsacks, int numItems, String folder)
    {
        this.numKnapsacks = numKnapsacks;
        this.numItems = numItems;
        
        initArrays();
        
        StringBuilder file;
        
        String [] names = new String[]{"/profit", "/weight", "/capacity"};
        fin = new java.util.Scanner[3];
        
        // open files
        for(int i = 0; i < 3; i++)
        {
            file = new StringBuilder(folder).append(names[i]);
            
            try{
                fin[i] = new java.util.Scanner(new java.io.File(file.toString()));
            }
            catch (Exception e){
                System.out.println("Error openning: " + e);
            }
        }
        
        int i, j;
        
        // read items profit and weight
        for(j = 0; j < numItems; j++)
        {
            for(i = 0; i < numKnapsacks; i++)
            {
                itemProfit[i][j] = Double.parseDouble(fin[0].next());
                itemWeight[i][j] = Double.parseDouble(fin[1].next());
            }
        }
        
        // read item capacity
        for(i = 0; i < numKnapsacks; i++){
            capacities[i] = Double.parseDouble(fin[2].next());
        }        
        
        // close files
        for(i = 0; i < 3; i++){
            fin[i].close();
        }
        
    }
    
    
    private void calcProfitWeightRation()
    {
        // some variables
        int i, j;
        double temp;
        
        // perform greedy repair method to ensure feasibility
        for(j = 0; j < numItems; j++)
        {
            indexes[j] = j;
            ratio[j] = Double.NEGATIVE_INFINITY;
            for(i = 0; i < numKnapsacks; i++)
            {
                temp = itemProfit[i][j]/itemWeight[i][j];
                if(temp > ratio[j])
                    ratio[j] = temp;
            }
        }
        
        // sort in increasing order, the lowest profit per weight unit are removed first
        minFastSort(ratio, indexes, numItems, numItems); //maxFastSort(ratio, indexes, numItems, numItems);
    }
    
    
    // method to calculate knapsack weights and profits
    private void calcKnapsacks()
    {        
        int i, j;
        
        for(i = 0; i < numKnapsacks; i++)
        {
            objectives[i] = 0.0;
            weights[i] = 0.0;
            for(j = 0; j < numItems; j++)
            {
                objectives[i] +=  variables[j] * itemProfit[i][j];
                weights[i] += variables[j] * itemWeight[i][j];
            }
        }
    }
    
    
    
    // method to evaluate individual
    public double [] evaluate(double [] x)
    {
        this.variables = x;
         
        // calculate knapsack weights and profits
        calcKnapsacks();
        
        // repair individual while infeasible
        while(!isFeasible())
        {
            repair();  // repair chromosome
            
            calcKnapsacks(); // calculate knapsack weights and profits
        }
        
        for(int i = 0; i < numKnapsacks; i++){
            objectives[i] *= -1.0;
        }
        
        return objectives;
        
    } // evaluate method
    
    
    public double [] getVariables()
    {
        return variables;
    }
    
    public double [] getObjectives()
    {
        return objectives;
    }
    
    
    private void repair()
    {
        int idx;
        
        for(int i = 0; i < numItems; i++)
        {
            idx = indexes[i];
            if(variables[idx] > 0.0)
            {
                variables[idx] = 0.0; // remove item
                return;
            }
        }
    }
    
    
    // method to check fiseability
    private boolean isFeasible()
    {
        for(int i = 0; i < numKnapsacks; i++)
        {
            if(weights[i] > capacities[i]){
                return false;
            }
        }
        return true;
    } // feasibility method
	
	
	
	public void minFastSort(double [] x, int [] idx, int n, int m)
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
    
    
    
    public void maxFastSort(double [] x, int [] idx, int n, int m)
    {
        int i, j, id;
        double temp;
        
        for (i = 0; i < m; i++)
        {
            for (j = i + 1; j < n; j++)
            {
                if (x[i] < x[j])
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
        
    } // maxFastSort
    
    
} // class

