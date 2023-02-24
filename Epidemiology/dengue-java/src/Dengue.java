public class Dengue {
	public double[] objValues(double[] x) {

		// declare control variable
		double[] u = new double[1001];
        
		// initialize control variable
		for (int ii = 0; ii < x.length; ii++)
			u[ii] = x[ii];

	    // some problem values
	    double T = 84.0 ; // period of time
	    int M = 1000 ; // number of intervals
	    double h = T / M ; // interval length
	    double h2 = h / 2 ; // half of interval length


	    // constant parameters
	    int B = 1 ; // average daily biting (per day)
	    double betamh = 0.375 ; // transmission probability from Im (per bite)
	    double betahm = 0.375 ; // transmission probability from Ih (per bite)
	    double muh = 1.0/(71.0*365.0) ; // average lifespan of humans (in days)
	    double etah = 1.0/3.0 ; // mean viremic period (in days)
	    double etam = 1.0/11.0 ; // average lifespan of adult mosquitoes (in days)
	    double phi = 6.0 ; // number of eggs at each deposit per capita (per day)
	    double muA = 0.25 ; // natural mortality of larvae (per day)
	    double etaA = 0.08 ; // maturation rate from larvae to adult (per day)
	    double mum = 1.0/11.0 ; // extrinsic incubation period (in days)
	    double nuh = 1.0/4.0 ; // intrinsic incubation period (in days)
	    int m = 6 ; // female mosquitoes per human
	    int k = 3 ; // number of larvae per human

		// initialize arrays
		double[] Sh = new double[M + 1]; // susceptible human
		double[] Eh = new double[M + 1]; // exposed human
		double[] Ih = new double[M + 1]; // infected human
		double[] Rh = new double[M + 1]; // resistant human
		double[] Am = new double[M + 1]; // aquatic phase female mosquitoes
		double[] Sm = new double[M + 1]; // susceptible female mosquitoes
		double[] Em = new double[M + 1]; // exposed female mosquitoes
		double[] Im = new double[M + 1]; // infected female mosquitoes

		// initial conditions
		Sh[0] = 0.99865;
		Eh[0] = 0.00035;
		Ih[0] = 0.001;
		Rh[0] = 0;
		Am[0] = 1;
		Sm[0] = 1;
		Em[0] = 0;
		Im[0] = 0;

		// initialize auxiliary parameters

		//first order parameters
		double m11, m12, m13, m14, m15, m16, m17, m18;

		//second order parameters
		double m21, m22, m23, m24, m25, m26, m27, m28;
	
		//third order parameters
		double m31, m32, m33, m34, m35, m36, m37, m38;

		//fourth order parameters
		double m41, m42, m43, m44, m45, m46, m47, m48;
		
		
		// compute system of differential equations
		for (int i = 0; i < M; i++) {
			
			// first order parameters
			m11 = muh - (B * betamh * Im[i] * m + muh) * Sh[i];
			m12 = B * betamh * Im[i] * m * Sh[i] - (nuh + muh) * Eh[i];
			m13 = nuh * Eh[i] - (etah + muh) * Ih[i];
			m14 = etah * Ih[i] - muh * Rh[i];
			m15 = phi * (1 - Am[i]) * (Sm[i] + Em[i] + Im[i]) * m / k - (etaA + muA) * Am[i];
			m16 = etaA * Am[i] * k / m - (B * betahm * Ih[i] + mum + u[i]) * Sm[i];
			m17 = B * betahm * Ih[i] * Sm[i] - (mum + etam + u[i]) * Em[i];
			m18 = etam * Em[i] - (mum + u[i]) * Im[i];

			// second order parameters
			m21 = muh - (B * betamh * (Im[i] + h2 * m18) * m + muh)	* (Sh[i] + h2 * m11);
			m22 = B * betamh * (Im[i] + h2 * m18) * m * (Sh[i] + h2 * m11) - (nuh + muh) * (Eh[i] + h2 * m12);
			m23 = nuh * (Eh[i] + h2 * m12) - (etah + muh) * (Ih[i] + h2 * m13);
			m24 = etah * (Ih[i] + h2 * m13) - muh * (Rh[i] + h2 * m14);
			m25 = phi * (1 - (Am[i] + h2 * m15)) * ((Sm[i] + h2 * m16) + (Em[i] + h2 * m17) + (Im[i] + h2 * m18)) * m / k - (etaA + muA) * (Am[i] + h2 * m15);
			m26 = etaA * (Am[i] + h2 * m15) * k / m - (B * betahm * (Ih[i] + h2 * m13) + mum + 0.5 * (u[i] + u[i + 1])) * (Sm[i] + h2 * m16);
			m27 = B * betahm * (Ih[i] + h2 * m13) * (Sm[i] + h2 * m16) - (mum + etam + 0.5 * (u[i] + u[i + 1])) * (Em[i] + h2 * m17);
			m28 = etam * (Em[i] + h2 * m17) - (mum + 0.5 * (u[i] + u[i + 1])) * (Im[i] + h2 * m18);

			// third order parameters
			m31 = muh - (B * betamh * (Im[i] + h2 * m28) * m + muh) * (Sh[i] + h2 * m21);
			m32 = B * betamh * (Im[i] + h2 * m28) * m * (Sh[i] + h2 * m21) - (nuh + muh) * (Eh[i] + h2 * m22);
			m33 = nuh * (Eh[i] + h2 * m22) - (etah + muh) * (Ih[i] + h2 * m23);
			m34 = etah * (Ih[i] + h2 * m23) - muh * (Rh[i] + h2 * m24);
			m35 = phi * (1 - (Am[i] + h2 * m25)) * ((Sm[i] + h2 * m26) + (Em[i] + h2 * m27) + (Im[i] + h2 * m28)) * m / k - (etaA + muA) * (Am[i] + h2 * m25);
			m36 = etaA * (Am[i] + h2 * m25) * k / m - (B * betahm * (Ih[i] + h2 * m23) + mum + 0.5 * (u[i] + u[i + 1])) * (Sm[i] + h2 * m26);
			m37 = B * betahm * (Ih[i] + h2 * m23) * (Sm[i] + h2 * m26) - (mum + etam + 0.5 * (u[i] + u[i + 1])) * (Em[i] + h2 * m27);
			m38 = etam * (Em[i] + h2 * m27) - (mum + 0.5 * (u[i] + u[i + 1])) * (Im[i] + h2 * m28);

			// fourth order parameters
			m41 = muh - (B * betamh * (Im[i] + h2 * m38) * m + muh) * (Sh[i] + h2 * m31);
			m42 = B * betamh * (Im[i] + h2 * m38) * m * (Sh[i] + h2 * m31) - (nuh + muh) * (Eh[i] + h2 * m32);
			m43 = nuh * (Eh[i] + h2 * m32) - (etah + muh) * (Ih[i] + h2 * m33); 
			m44 = etah * (Ih[i] + h2 * m33) - muh * (Rh[i] + h2 * m34);
			m45 = phi * (1 - (Am[i] + h2 * m35)) * ((Sm[i] + h2 * m36) + (Em[i] + h2 * m37) + (Im[i] + h2 * m38)) * m / k - (etaA + muA) * (Am[i] + h2 * m35);
			m46 = etaA * (Am[i] + h2 * m35) * k / m - (B * betahm * (Ih[i] + h2 * m33) + mum + u[i + 1]) * (Sm[i] + h2 * m36);
			m47 = B * betahm * (Ih[i] + h2 * m33) * (Sm[i] + h2 * m36) - (mum + etam + u[i + 1]) * (Em[i] + h2 * m37);
			m48 = etam * (Em[i] + h2 * m37) - (mum + u[i + 1]) * (Im[i] + h2 * m38);

			// compute values of state variables
			Sh[i + 1] = Sh[i] + (h / 6) * (m11 + 2 * m21 + 2 * m31 + m41);
			Eh[i + 1] = Eh[i] + (h / 6) * (m12 + 2 * m22 + 2 * m32 + m42);
			Ih[i + 1] = Ih[i] + (h / 6) * (m13 + 2 * m23 + 2 * m33 + m43);
			Rh[i + 1] = Rh[i] + (h / 6) * (m14 + 2 * m24 + 2 * m34 + m44);
			Am[i + 1] = Am[i] + (h / 6) * (m15 + 2 * m25 + 2 * m35 + m45);
			Sm[i + 1] = Sm[i] + (h / 6) * (m16 + 2 * m26 + 2 * m36 + m46);
			Em[i + 1] = Em[i] + (h / 6) * (m17 + 2 * m27 + 2 * m37 + m47);
			Im[i + 1] = Im[i] + (h / 6) * (m18 + 2 * m28 + 2 * m38 + m48);
			
			// print values to check
			// System.out.println(Ih[i]);
			//System.out.println(m11+" "+m12+" "+m13+" "+m14);
			//System.out.println(Sh[i]+" "+Eh[i]+" "+Ih[i]+" "+Rh[i]+" "+Am[i]+" "+Sm[i]+" "+Em[i]+" "+Im[i]);
			

		} // end system of diff eq
		
		
		
		// get a set of evenly distributed points
		double[] t = new double[M + 1];
		Linspace objectLinspace = new Linspace();
		t = objectLinspace.getValues();

		// calculate integral

		// create trapz object
		Trapz objectTrapz = new Trapz();
		
		// declare obj values
		double [] f = new double[2];

		// calculate integral for human infected
		f[0] = objectTrapz.getIntegal(t, Ih);

		// calculate integral for control
		f[1] = objectTrapz.getIntegal(t, u);

		// evaluate
		
		return f;
	}



}
