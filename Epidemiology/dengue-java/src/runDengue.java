import java.util.Formatter;
import java.util.Scanner;
import java.io.*;
import java.lang.*;


public class runDengue {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// declare variables
		double[] x = new double[1001];
		double[] y = new double[2];
		
		// create instance of main class first
		runDengue outer = new runDengue();

		// create instance of inner class
		readFromFile objectReadFromFile = outer.new readFromFile();
		//readFromFile objectReadFromFile = new readFromFile();
		// read data from file
		objectReadFromFile.openFile();
		x = objectReadFromFile.getValues();
		objectReadFromFile.closeFile();

		// create object
		Dengue objectDengue = new Dengue();
		// calculate values
		y = objectDengue.objValues(x);

		// create instance of inner class
		writeToFile objectWriteToFile = outer.new writeToFile();
		//writeToFile objectWriteToFile = new writeToFile();
		// write data to file
		objectWriteToFile.openFile();
		objectWriteToFile.giveValues(y);
		objectWriteToFile.closeFile();
		
		
		// -------------------------check integral------------------------
//		// create object
//		double[] t = new double[1001];
//		Linspace objectLinspace = new Linspace();
//		// calculate values
//		t = objectLinspace.getValues();
//		
//		Trapz objectTrapz = new Trapz();
//		System.out.println(objectTrapz.getIntegal(t,x));

	}

	// read values from file
	public class readFromFile {

		// declare
		private Scanner inData;

		// open file
		void openFile() {
			try {
				inData = new Scanner(new File("xData.dat"));
			} catch (Exception e) {
				System.out.println("File doesn't exists");
			}
		}

		double[] getValues() {

			// declare
			double[] x = new double[1001];
			String valueStr = null;

			// read values
			for (int i = 0; i < 1001; i++) {
				valueStr = inData.next();
				x[i] = Double.parseDouble(valueStr);
			}

			return x;

		}

		// close file
		void closeFile() {
			inData.close();
		}

	} // end readFromFile

	// write values to file
	public class writeToFile {

		// declare
		private Formatter outData;

		// open file
		void openFile() {
			try {
				outData = new Formatter("yData.dat");
			} catch (Exception e) {
				System.out.println("You have an error");
			}
		}

		// write to file
		void giveValues(double[] y) {
			for (int i = 0; i < y.length; i++)
				outData.format("%s ", y[i]);

		}

		// close file
		void closeFile() {
			outData.close();
		}

	} // writeToFile end

} // END
