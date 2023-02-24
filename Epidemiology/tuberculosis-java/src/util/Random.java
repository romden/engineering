package util;



public class Random
{
    
    private java.util.Random rand = new java.util.Random();
    
    public void seed(long newSeed)
    {
        rand = new java.util.Random(newSeed);
    }
    
    public double nextDouble()
    {
        return rand.nextDouble();
    }
    
    public double nextGaussian()
    {
        return rand.nextGaussian();
    }
    
    public double [] nextGaussian(int size)
    {
        double [] v = new double[size];
        
        for(int i = 0; i < size; i++){
            v[i] = rand.nextGaussian();
        }
        return v;
    }
    
    public int nextInt(int max)
    {
        return rand.nextInt(max);
    }
    
    public byte nextByte(int max)
    {
        return ((byte)Math.min(rand.nextInt(max), 127));
    }
    
} // class
