package xlsx;


import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


public class XLSXAccess
{
    String fileName;
    int indexOfAsset;
    
    InputStream fin;
    FileOutputStream fout;
    
    XSSFWorkbook wb;
    XSSFSheet sheet;
    XSSFRow row;
    XSSFCell cell;
    CellStyle cellStyle;
    
    Iterator rows, cells;
    
    
    Formatter foutTxt; // object to save data to file
    
    public void xlsx2txtEffects(String inputFile, String outputFile, int sheetIndex)
    {
        
        openOutFile(outputFile);
        
        int numberOfStates = 5;
        
        try {
            // read matrix Q
            fin = new FileInputStream(inputFile);
            wb = new XSSFWorkbook(fin);
            
            sheet = wb.getSheetAt(sheetIndex);
            
            int numberOfActions = sheet.getLastRowNum()-2;
            numberOfActions++;
            
            
            rows = sheet.rowIterator();
            
            rows.next(); // skip first row
            rows.next(); // skip first row
            rows.next(); // skip first row
            for(int k = 1; k < numberOfActions; k++)
            {
                row = (XSSFRow) rows.next();
                cells = row.cellIterator();
                
                cells.next(); // skip id
                cells.next(); // skip description
                
                // read time of delay
                cells.next();
                for(int ii = 0; ii < numberOfStates; ii++)
                {
                    for(int j = 0; j < 3; j++)
                    {
                        cell = (XSSFCell)cells.next();
                        if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
                            foutTxt.format("%s\t", cell.getNumericCellValue());
                        }
                        else{
                            foutTxt.format("-\t");
                        }
                    }
                }
                
                // read improved state
                cells.next();
                for(int ii = 0; ii < numberOfStates; ii++)
                {
                    for(int j = 0; j < 3; j++)
                    {
                        cell = (XSSFCell)cells.next();
                        if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
                            foutTxt.format("%s\t", cell.getNumericCellValue());
                        }
                        else{
                            foutTxt.format("-\t");
                        }
                    }
                }
                
                
                // read time of reduction
                cells.next();
                for(int ii = 0; ii < numberOfStates; ii++)
                {
                    for(int j = 0; j < 3; j++)
                    {
                        cell = (XSSFCell)cells.next();
                        if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
                            foutTxt.format("%s\t", cell.getNumericCellValue());
                        }
                        else{
                            foutTxt.format("-\t");
                        }
                    }
                }
                
                // read rate of reduction
//                    cells.next();
                for(int ii = 0; ii < numberOfStates; ii++)
                {
                    for(int j = 0; j < 3; j++)
                    {
                        cell = (XSSFCell)cells.next();
                        if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
                            foutTxt.format("%s\t", cell.getNumericCellValue());
                        }
                        else{
                            foutTxt.format("-\t");
                        }
                    }
                }
                
                foutTxt.format("%n");
            }
            
            fin.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        closeOutFile();
        
    }
    
    
    
    public void xlsx2datInspections(String inputFile, String outputFile)
    {
        
        openOutFile(outputFile);
        
        try {
            
            fin = new FileInputStream(inputFile);
            wb = new XSSFWorkbook(fin);
            
            sheet = wb.getSheetAt(0);
            
            rows = sheet.rowIterator();
            
            while(rows.hasNext())
            {
                row = (XSSFRow) rows.next();
                cells = row.cellIterator();
                while(cells.hasNext())
                {
                    cell = (XSSFCell)cells.next();
                    
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    foutTxt.format("%s;", cell.getStringCellValue().toString());
                   
                }
                foutTxt.format("%n");
            }
            
            
            fin.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        closeOutFile();
        
    }
    
    
    // method to open file
    private void openOutFile(String fileName)
    {
        // open file
        try {
            foutTxt = new Formatter(fileName);
        }
        catch (Exception e) {
            System.out.println("error opening " + fileName + " : " + e);
        }
    }
    
    
    // method to close file
    private void closeOutFile()
    {
        foutTxt.close();
    }
    
}






                    
//                    switch (cell.getCellType())
//                    {
//                        case XSSFCell.CELL_TYPE_STRING:
//                            foutTxt.format("%s;", cell.getStringCellValue());
//                            break;
//                        case XSSFCell.CELL_TYPE_NUMERIC:
//                            foutTxt.format("%s;", (int)cell.getNumericCellValue());
//                            break;
//                    }
                    
//                    if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
//                        foutTxt.format("%s;", cell.getStringCellValue());
//                    }
//                    
//                    if(org.apache.poi.hssf.usermodel.HSSFDateUtil.isCellDateFormatted(cell)){
//                        foutTxt.format("%s;", (String)cell.getCellStyle().getDataFormatString());
//                    }                    
//
//                    if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
//                        foutTxt.format("%s;", (int)cell.getNumericCellValue());
//                    }