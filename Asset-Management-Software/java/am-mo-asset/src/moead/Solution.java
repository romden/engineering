package moead;


public class Solution
{   
    public double [] objectives; // individuals phenotype
    
    public double cv; // constraint violation
    
    public int [][] chrom; // individuals genotype
    
    public double [][] states; // states of components
    
    boolean isActive;
}