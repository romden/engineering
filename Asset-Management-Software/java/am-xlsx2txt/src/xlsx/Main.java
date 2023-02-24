package xlsx;


public class Main
{
    
    public static XLSXAccess xlsx = new XLSXAccess();
    private static String inputFile;
    private static String outputFile;
    
    
    public static void main(String[] args)
    {
        
        if(args.length == 2){
            (new XLSXAccess()).xlsx2txtEffects(args[0], args[1], 0);
        }
        else if(args.length == 3){
            (new XLSXAccess()).xlsx2txtEffects(args[0], args[1], Integer.parseInt(args[2])-1);
        } 
        
//        if(args.length == 2){
//            (new XLSXAccess()).xlsx2datInspections(args[0], args[1]);
//        }

    }
    
    
}
