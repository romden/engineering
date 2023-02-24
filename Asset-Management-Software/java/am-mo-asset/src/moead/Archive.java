package moead;


public class Archive
{
    
    Solution [] members; // candidate
    Solution candidate;
    
    int available; // index of available slot in archive
    
    int size;
    int capacity;
    int numberOfObjectives;
    int numberOfComponents;
    int numberOfStates;
    int timeHorizon;
    
    double [] ideal;
    double [] nadir;
    
    double [][] objs; // normalized objectives for hv
    double [] s; // hv contributions
    
    
    HV hv = new HV();
    
    
    public Archive(int capacity, int numberOfObjectives, int numberOfComponents, int numberOfStates, int timeHorizon)
    {
        this.capacity = capacity;
        this.numberOfObjectives = numberOfObjectives;
        this.numberOfComponents = numberOfComponents;
        this.numberOfStates = numberOfStates;
        this.timeHorizon = timeHorizon;
        
        members = new Solution[capacity];
        
        // allocate memory for archive members
        for(int i = 0; i < capacity; i++)
        {
            members[i] = new Solution();
            members[i].chrom = new int[numberOfComponents][timeHorizon];
            members[i].objectives = new double[numberOfObjectives];
            members[i].cv = 0.0;
            members[i].states = new double[numberOfStates][timeHorizon];
            
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
                if(members[i].objectives[j] < candidate.objectives[j]){
                    isDomMember = false;  // archive member is not dominated
                }
                if(candidate.objectives[j] < members[i].objectives[j]){
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
            members[available].objectives[j] = candidate.objectives[j];
        }
        // chrom
        for(j = 0; j < timeHorizon; j++)
        {
            for(i = 0; i < numberOfComponents; i++){
                members[available].chrom[i][j] = candidate.chrom[i][j];
            }
            for(i = 0; i < numberOfStates; i++){
                members[available].states[i][j] = candidate.states[i][j];
            }
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
            ideal[j] = members[0].objectives[j];
            nadir[j] = members[0].objectives[j];
        }
        
        for(i = 1; i < capacity; i++)
        {
            for(j = 0; j < numberOfObjectives; j++)
            {
                if(members[i].objectives[j] < ideal[j]){
                    ideal[j] = members[i].objectives[j];
                }
                if(members[i].objectives[j] > nadir[j]){
                    nadir[j] = members[i].objectives[j];
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
                objs[j][i] = (members[i].objectives[j] - ideal[j]) / (nadir[j] - ideal[j]);
            }
        }
    }
    
    
    private int findToRemove()
    {
        // calculate hv contributions
        switch(numberOfObjectives)
        {
            case 2:
                hv.calc_hv_contributions_dim2(objs, numberOfObjectives, capacity, s);
                break;
            case 3:
                hv.calc_hv_contributions_dim3(objs, numberOfObjectives, capacity, s);
                break;
        }
        
        // find one having smallest contribution
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
        
        int idx = findToRemove();
        
        members[idx].isActive = false;
    }
    
    
} // class
