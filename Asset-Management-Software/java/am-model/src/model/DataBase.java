package model;



import java.io.File;
import java.util.Scanner;


public class DataBase
{
    String dataFile; // name of the modul
    
    int minState;
    
    int timeSpan;
    
    public int numberOfElements; // number of elements in database
    
    public int [] numberOfInspections; // number of inspections for each element in database
    
    public Inspection [][] inspection; // matrix of inspections
    
    //  jan 31   feb 28/29   mar 31   apr 30   may 31   jun 30   jul 31   aug 31   sep 30   oct 31   nov 30   dec 31
    int [] daysPerMonth = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; // number of days in each month    
       
    Scanner fin; // object to read data from file
    
    
    // constructor 
    public DataBase(String dataFile, int minState, int timeSpan)
    {
        this.dataFile = dataFile;
        this.minState = minState;
        this.timeSpan = timeSpan;
        
        //initialize database
        
        getNumberOfElements();
        
        getNumberOfInspections();
        
        getData();
        
        
    }  // method initialize
    
    
    // method to open file
    private void openInFile(String fileName)
    {
        try{
            fin = new Scanner(new File(fileName));
        }
        catch (Exception e){
            System.out.println("error opening " + fileName + " : " + e);
        }
        
    } // openInFile method
    
    
    // method to clse file
    private void closeInFile()
    {
        fin.close();        
    } // closeInFile method
    
    
    // method to get number of elements in database
    private void getNumberOfElements()
    {
        // open file containing data
        openInFile(dataFile);
        
        // initialize
        numberOfElements = 0;
        
        // calculate number of elements
        while(fin.hasNext())
        {
            fin.nextLine();
            numberOfElements++;
        }
        
        // close file
        closeInFile();
        
    } // method getNumberOfElements
    
    
    // method to get number of inspections for each element
    private void getNumberOfInspections()
    {
        // initialize number of inspections
        numberOfInspections = new int[numberOfElements];
        
        // open file containing data
        openInFile(dataFile);
        
        // set delimiter
        fin.useDelimiter(";");
        
        // skip first line
        String token;
        
        // calculate number of elements
        for(int i = 0; i < numberOfElements; i++)
        {
            numberOfInspections[i] = 0;
            
            for(;;)
            {
                token =  fin.next();
                if(!token.equals("NA"))
                {
                    fin.next();
                    numberOfInspections[i]++;
                }
                else
                {
                    fin.nextLine();
                    break;
                }
            }
        }
        
        // close file
        closeInFile();
        
    } // method getNumberOfInspections
    
    
    // method to get data from database
    private void getData()
    {
        int i, j; // some vars
        
        // initialize database
        inspection = new Inspection[numberOfElements][];
        for(i = 0; i < numberOfElements; i++){
            inspection[i] = new Inspection[numberOfInspections[i]];
        }
        
        // open file containing data
        openInFile(dataFile);
        
        // set delimiter
        fin.useDelimiter(";");
        
        // read data and initialize database
        for(i = 0; i < numberOfElements; i++)
        {
            
            for(j = 0; j < numberOfInspections[i]; j++)
            {
                inspection[i][j] = new Inspection();
                
                // read date of inspection
                inspection[i][j].date = fin.next();
                
                // read state condition at the time of inspection (convert to 0 be the best condition state)
                inspection[i][j].c = Integer.parseInt(fin.next()) - minState;
                
                if(j == 0){
                    inspection[i][j].deltaT = 0.0;
                }
                else{
                    inspection[i][j].deltaT = timeDiff(inspection[i][j-1].date, inspection[i][j].date);
                }

            }
            fin.nextLine(); // skip the rest of the line
        }
        
        // close file
        closeInFile();
        
    } // method to getData
    
    
    // method to calculate and return difference between two dates in years
    private double timeDiff(String date1, String date2)
    {
        int years1 = 0, months1 = 0, days1 = 0, years2 = 0, months2 = 0, days2 = 0;
        
        if(date1.substring(2,3).equals("/") || date1.substring(2,3).equals("-"))
        {
            // first date
            years1 = Integer.parseInt(date1.substring(6));
            months1 = Integer.parseInt(date1.substring(3, 5));
            days1 = Integer.parseInt(date1.substring(0, 2));
             
            // second date
            years2 = Integer.parseInt(date2.substring(6));
            months2 = Integer.parseInt(date2.substring(3, 5));
            days2 = Integer.parseInt(date2.substring(0, 2));
        }
        else if(date1.substring(4,5).equals("/") || date1.substring(4,5).equals("-"))
        {
            // first date
            years1 = Integer.parseInt(date1.substring(0, 4));
            months1 = Integer.parseInt(date1.substring(5, 7));
            days1 = Integer.parseInt(date1.substring(8));
            
            // second date
            years2 = Integer.parseInt(date2.substring(0, 4));
            months2 = Integer.parseInt(date2.substring(5, 7));
            days2 = Integer.parseInt(date2.substring(8));
        }
        
        // calculate number of dates from the begining of the year
        for(int i = 1; i <= 12; i++)
        {
            if(months1 >= i){
                days1 += daysPerMonth[i-1];
            }
            if(months2 >= i){
                days2 += daysPerMonth[i-1];
            }
        }
        
        // calculate and return difference between two dates 
        return (double)((years2-years1)*365 + days2 - days1)/timeSpan;
        
    } // method timeDiff
    
    
} // end class