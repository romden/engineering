package problem;

import util.VectorUtil;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

import moead.Solution;


public class Problem
{
    public int problemType;
    
    public int m; // number of objectives
    public int n; // number of variable
    public int [] ub;  // number of items in each varible
    
    public double discountRate;
    
    int [] crom; // inidiv cromossoma
    
    public int numberOfAssetTypes;
    public int numberOfAssets;
    public int timeHorizon;
    public Asset [] assets;
    public int [] numberOfAssetsOfEachTypes;
    
    public double [] networkCost;
    public double [] networkPerformance;
    
    public double [] networkPerformanceStd;
    
    public double [][] meanPerformanceForAssetTypes; // mean performance for each type of assets
    public double [][] stdPerformanceForAssetTypes; // std for each type of assets
    
    public double [] weightPerformance;    // weights to combine performance of different assets
    
    VectorUtil vectorUtil = new VectorUtil();
    
    
    public Problem(String problemFile)
    {
        initProblem(problemFile);
    }
    
    
    private void initProblem(String problemFile)
    {
        finOpen(problemFile);
        
        // read type of problem
        fin.next();
        problemType = Integer.parseInt(fin.next());
        
        // read number of objectives
        fin.next();
        m = Integer.parseInt(fin.next());
        
        // read file
        fin.next();
        timeHorizon = Integer.parseInt(fin.next());
        
        // read descount rate
        fin.next();
        discountRate = Double.parseDouble(fin.next());
        
        fin.next();
        numberOfAssetTypes = Integer.parseInt(fin.next());
        
        fin.next();
        numberOfAssets = Integer.parseInt(fin.next());
        
        // init strategies
        assets = new Asset[numberOfAssets];
        
        n = numberOfAssets;
        ub = new int[n];
        networkCost = new double[timeHorizon];
        networkPerformance = new double[timeHorizon];
        
        networkPerformanceStd = new double[timeHorizon];
        
        meanPerformanceForAssetTypes = new double[numberOfAssetTypes][timeHorizon];
        stdPerformanceForAssetTypes = new double[numberOfAssetTypes][timeHorizon];
        
        weightPerformance = new double[numberOfAssetTypes];
        Arrays.fill(weightPerformance, 1.0/numberOfAssetTypes);
        
        numberOfAssetsOfEachTypes = new int[numberOfAssetTypes];
        Arrays.fill(numberOfAssetsOfEachTypes, 0);
        
        String fileCosts, fileStates;
        int numberOfAlternatives, typeOfAsset;
        for(int i = 0; i < numberOfAssets; i++)
        {
            fin.next(); fin.next(); // skip line
            fin.next(); fileCosts = fin.next(); // read name of file containing costs
            fin.next(); fileStates = fin.next(); // read name of file containing states
            fin.next(); numberOfAlternatives = Integer.parseInt(fin.next()); // read numer of alternatives for asset
            fin.next(); typeOfAsset = Integer.parseInt(fin.next()); // read numer of alternatives for asset
            // init asset object
            assets[i] = new Asset(fileCosts, fileStates, numberOfAlternatives, timeHorizon, typeOfAsset);
            ub[i] = numberOfAlternatives;
            numberOfAssetsOfEachTypes[assets[i].getType()-1]++;
        }
        
        // read weights for performance of different assets
        if(fin.hasNext())
        {
            fin.next();
            for(int i = 0; i < numberOfAssetTypes; i++){
                weightPerformance[i] = Double.parseDouble(fin.next());
            }
        }
        
        fin.close();
    }
    
    
    
    // performance is defined by the sum of states
    public void evaluate(Solution individual)
    {
        this.crom = individual.x;
        
        // calc network values
        updateArrays();
        calcNetworkMeanValues();
        calcNetworkPerformanceStd();

        // calc objs depending on ptoblem type
        switch(problemType)
        {
            case 1: // minimize mean network performance and cost
                individual.f[0] = vectorUtil.mean(networkPerformance);
                individual.f[1] = vectorUtil.mean(networkCost);
                break;
            case 2: // minimize mean and std network cost
                individual.f[0] = vectorUtil.mean(networkCost);
                individual.f[1] = vectorUtil.std(networkCost);
                break;
            case 3: // minimize mean network cost and std network performance
                individual.f[0] = vectorUtil.mean(networkCost);
                individual.f[1] = vectorUtil.mean(networkPerformanceStd); // vectorUtil.std(networkPerformance);
                break;
            case 4: // minimize mean and std network performance
                individual.f[0] = vectorUtil.mean(networkPerformance);
                individual.f[1] = vectorUtil.mean(networkPerformanceStd); // vectorUtil.std(networkPerformance);
                break;
            case 5: // minimize mean network performance and cost and std values of cost
                individual.f[0] = vectorUtil.mean(networkPerformance);
                individual.f[1] = vectorUtil.mean(networkCost);
                individual.f[2] = vectorUtil.std(networkCost);
                break;
            case 6: // minimize mean network performance and cost and std values of performance
                individual.f[0] = vectorUtil.mean(networkPerformance);
                individual.f[1] = vectorUtil.mean(networkCost);
                individual.f[2] = vectorUtil.mean(networkPerformanceStd);
                break;
            case 7: // minimize all the objectives
                individual.f[0] = vectorUtil.mean(networkPerformance);
                individual.f[1] = vectorUtil.mean(networkCost);
                individual.f[2] = vectorUtil.mean(networkPerformanceStd);
                individual.f[3] = vectorUtil.std(networkCost);
                break;
                
            case 8: // minimize the number of years with actions and costs                
                individual.f[0] = (double)calcNumberOfYearsWithActions();
                individual.f[1] = vectorUtil.sum(networkCost); // mimimize total cost
                break;
                
            case 10: // minimize the number of years with actions and costs
                individual.f[0] = (double)calcNumberOfYearsWithActions();                
                individual.f[1] = vectorUtil.sum(networkCost); // mimimize total cost
                individual.f[2] = vectorUtil.max(networkPerformanceStd); // minimize max variation of asset states
                break;
        }
        
        individual.cv = 0.0;
    }    
    
    
    // method to calc number of yeas with actions
    public int calcNumberOfYearsWithActions()
    {
        int result = timeHorizon;
        for(int j = 0; j < timeHorizon; j++)
        {
            if(networkCost[j] == 0.0){
                result--; // reduce if there is no maintenace (minimize years with maintenance)
            }
        }
        
        return result;
    }
    
    
    // mathod to update arrays
    private void updateArrays()
    {
        int i, j;
        
        for(j = 0; j < timeHorizon; j++)
        {
            networkCost[j] = 0.0;
            networkPerformance[j] = 0.0;
            networkPerformanceStd[j] = 0.0;
        }
        
        for(i = 0; i < numberOfAssetTypes; i++)
        {
            for(j = 0; j < timeHorizon; j++)
            {
                meanPerformanceForAssetTypes[i][j] = 0.0;
                stdPerformanceForAssetTypes[i][j] = 0.0;
            }
        }
    }
    
    
//    // method to calc mean network performance and cost
//    private void calcNetworkMeanValues()
//    {
//        int i, j;
//        
//        for(j = 0; j < timeHorizon; j++)
//        {
//            for(i = 0; i < numberOfAssets; i++)
//            {
//                networkCost[j] += assets[i].getCostFor(crom[i], j);
//                
//                meanPerformanceForAssetTypes[assets[i].getType()-1][j] += assets[i].getStateFor(crom[i], j);
//            }
//            
//            networkCost[j] = networkCost[j]/Math.pow(1+discountRate, j);
//            
//            for(i = 0; i < numberOfAssetTypes; i++)
//            {
//                meanPerformanceForAssetTypes[i][j] /= numberOfAssetsOfEachTypes[i];
//                
//                networkPerformance[j] += weightPerformance[i]*meanPerformanceForAssetTypes[i][j];
//            }
//        }
//        
//    } // method calcNetorkPerformance
    
    
    // method to calc mean network performance and cost (particular case for 5 bridges)
    private void calcNetworkMeanValues()
    {
        int i, j, k, idx;
        
        double reductionRate = 0.2;
        
        // 1, 2, 5 - principal components
        java.util.ArrayList<Integer> indexes = new java.util.ArrayList();
        indexes.add(1); indexes.add(2); indexes.add(6);
        double temp;
        
        int numComp = 6;
        boolean [] flag = new boolean[numComp];        
        
        int numBridges = 5;
        double [] bridgeCS = new double[numBridges];        
        
        
        for(j = 0; j < timeHorizon; j++)
        {
            // init vars for j-th year
            java.util.Arrays.fill(flag, true);
            java.util.Arrays.fill(bridgeCS, 0.0);
            
            // bridges perf
            idx = -1;
            for(i = 0; i < numBridges; i++)
            {
                temp = 0.0;
                for(k = 0; k < numComp; k++)
                {
                    // index
                    idx++;
                    
                    // init bridge perf by computing average
                    bridgeCS[i] += assets[idx].getStateFor(crom[idx], j)/numComp;
                    
                    // check wether there is maintenance for same components
                    if(assets[idx].getCostFor(crom[idx], j) == 0.0) flag[k] = false;  
                    
                    // find worst CS of principal component
                    if(indexes.contains(k)) temp = Math.max(temp, assets[idx].getStateFor(crom[idx], j));
                }    
                
                // perf of bridge considering principal components
                bridgeCS[i] = Math.max(temp, bridgeCS[i]);
            }
            
            // bridges cost
            for(k = 0; k < numComp; k++)
            {
                temp = 0.0;
                for(i = 0; i < numBridges; i++)
                {
                    idx = k + i*numComp;
                    
                    temp += assets[idx].getCostFor(crom[idx], j);
                }
                
                if(flag[k]) temp *= (1.0 - reductionRate); // reduce cost
                
                networkCost[j] += temp; // network cost
            }            
            
            for(i = 0; i < numBridges; i++) networkPerformance[j] += (bridgeCS[i]/numBridges); // network perf
            
        }
        
    } // method calcNetorkPerformance
    
    
    
    private void calcNetworkPerformanceStd()
    {
        int i, j;
        // calc std perf
        for(j = 0; j < timeHorizon; j++)
        {
            // calc std for different assets
            for(i = 0; i < numberOfAssets; i++){
                stdPerformanceForAssetTypes[assets[i].getType()-1][j] += Math.pow(meanPerformanceForAssetTypes[assets[i].getType()-1][j]-assets[i].getStateFor(crom[i], j), 2);
            }
            
            // combine std for different asset
            for(i = 0; i < numberOfAssetTypes; i++){
                stdPerformanceForAssetTypes[i][j] = Math.sqrt(stdPerformanceForAssetTypes[i][j]/numberOfAssetsOfEachTypes[i]);
                networkPerformanceStd[j] += weightPerformance[i]*stdPerformanceForAssetTypes[i][j];
            }
        }
        
    }
    
    
    public double [] getCosts(int [] crom)
    {
        this.crom = crom;
        updateArrays();
        calcNetworkMeanValues();
        
        return networkCost;
    }
    
    public double [] getStates(int [] x)
    {
        this.crom = crom;
        updateArrays();
        calcNetworkMeanValues();
        
        return networkPerformance;
    }
    
    
    Scanner fin; // object to read data from file
    
    // method to open file
    protected void finOpen(String fileName)
    {
        try{
            fin = new Scanner(new File(fileName));
        }
        catch (Exception e){
            System.out.println("error opening " + fileName + " : " + e);
        }
        
    } // openInFile method
    
    // method to close file
    protected void finClose()
    {
        fin.close();
    } // closeInFile method
    
}






//        // calc perf func
//        double value = 0.0;
//        switch(performanceFunType)
//        {
//            case 1: // commulative performance
//                value = sumState();
//                break;
//            case 2: // worst (max) value
//                value = maxState();
//                break;
//        }



//    public void evaluate(Solution individual)
//    {
//        int i, j;
//
//        Arrays.fill(networkCost, 0.0);
//        double mean = 0.0;
//        for(j = 0; j < timeHorizon; j++)
//        {
//            for(i = 0; i < numberOfAssets; i++){
//                networkCost[j] += assets[i].getCostFor(individual.x[i], j);
//            }
//            mean += networkCost[j];
//        }
//        mean /= timeHorizon;
//
//        double variance = 0.0;
//        for(j = 0; j < timeHorizon; j++){
//            variance += Math.pow(mean - networkCost[j], 2);
//        }
//        variance /= timeHorizon;
//
//        individual.f[0] = mean;
//        individual.f[1] = Math.sqrt(variance);
//
//    }