package moead;


public class Archive
{
    
    Solution [] members; // candidate
    Solution candidate;
    
    int available; // index of available slot in archive
    
    int size;
    int capacity;
    
    int numberOfObjectives;
    int numberOfVariables;
    
    double [] ideal;
    double [] nadir;
    
    double [][] objs; // normalized f for hv
    double [] s; // hv contributions
    
    
    HV hv = new HV();
    
    
    public Archive(int capacity, int numberOfObjectives, int numberOfVariables)
    {
        this.capacity = capacity;
        this.numberOfObjectives = numberOfObjectives;
        this.numberOfVariables = numberOfVariables;
        
        members = new Solution[capacity];
        
        // allocate memory for archive members
        for(int i = 0; i < capacity; i++)
        {
            members[i] = new Solution();
            members[i].x = new int[numberOfVariables];
            members[i].f = new double[numberOfObjectives];
            members[i].isActive = false;
        }
        
        ideal = new double[numberOfObjectives];
        nadir = new double[numberOfObjectives];
        
        objs = new double[numberOfObjectives][capacity];
        s = new double[capacity];
        
    }
    
    
    public void add(Solution candidate)
    {
        this.candidate = candidate;
        
        // stop if candidate is dominated
        if(dominance()){
            return;
        }
        
        insert();
        size ++;
        
        if(size == capacity){
            truncate();
            size --;
        }
    }
    
    
    private boolean dominance()
    {
        
        available = -1;
        
        // some vars
        int i, j;
        boolean isDomMember;
        boolean isDomCandidate;
        
        // compare with current archive
        for(i = 0; i < capacity; i++)
        {
            if(!members[i].isActive)
            {
                if(available < 0){
                    available = i; // get available slot
                }
                
                continue;
            }
            
            // dominance based on constraints
            if(members[i].cv < candidate.cv){
                return true; // candidate is dominated
            }
            else if(members[i].cv > candidate.cv){
                members[i].isActive = false; // memebr is removed because dominated
                size--;
                continue;
            }
            
            isDomMember = true; // memebr is dominated
            isDomCandidate = true;  // candiate is dominated
            
            for(j = 0; j < numberOfObjectives; j++)
            {
                if(members[i].f[j] < candidate.f[j]){
                    isDomMember = false;  // archive member is not dominated
                }
                if(candidate.f[j] < members[i].f[j]){
                    isDomCandidate = false; // candidate is not dominated
                }
            }
            
            if(isDomCandidate){
                return true; // candidate is dominated
            }
            else if(isDomMember){
                members[i].isActive = false; // memebr is removed because dominated
                size--;
            }
            
        }
        
        return false;
    } // dominance method
    
    
    private void insert()
    {
        int i, j;
        // objectives
        for(j = 0; j < numberOfObjectives; j++){
            members[available].f[j] = candidate.f[j];
        }
        // chrom
        for(j = 0; j < numberOfVariables; j++)
        {
            members[available].x[j] = candidate.x[j];
        }
        
        members[available].cv = candidate.cv;
        
        members[available].isActive = true;
        
    } // insert method
    
    
    private void update()
    {
        int i, j;
        
        // init ref point
        for(j = 0; j < numberOfObjectives; j++)
        {
            ideal[j] = members[0].f[j];
            nadir[j] = members[0].f[j];
        }
        
        for(i = 1; i < capacity; i++)
        {
            for(j = 0; j < numberOfObjectives; j++)
            {
                if(members[i].f[j] < ideal[j]){
                    ideal[j] = members[i].f[j];
                }
                if(members[i].f[j] > nadir[j]){
                    nadir[j] = members[i].f[j];
                }
            }
        }
    }
    
    
    private void getObjValues()
    {
        int i, j;
        for(i = 0; i < capacity; i++)
        {
            for(j = 0; j < numberOfObjectives; j++)
            {
                objs[j][i] = (members[i].f[j] - ideal[j]) / (nadir[j] - ideal[j]);
            }
        }
    }
    
    
    private int findMinHV()
    {
        int idx = 0;
        double min = s[idx];
        
        for(int i = 1; i < capacity; i++)
        {
            if(s[i] < min)
            {
                idx = i;
                min = s[idx];
            }
        }
        
        return idx;
    }
    
    private void truncate()
    {
        update();
        
        getObjValues();
        
        switch(numberOfObjectives)
        {
            case 2:
                hv.calc_hv_contributions_dim2(objs, numberOfObjectives, capacity, s);
                break;
            case 3:
                hv.calc_hv_contributions_dim3(objs, numberOfObjectives, capacity, s);
                break;
        }
                
        
        members[findMinHV()].isActive = false;
    }    
    
} // class
