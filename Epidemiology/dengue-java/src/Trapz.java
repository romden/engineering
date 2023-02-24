// class for calculating integral
public class Trapz {
	public double getIntegal(double[] x, double[] y) {

		// declare integral
		double I = 0;

		for (int i = 0; i < 1000; i++)
			I += (x[i + 1] - x[i]) * (y[i + 1] + y[i]) / 2;

		return I;
	}
}