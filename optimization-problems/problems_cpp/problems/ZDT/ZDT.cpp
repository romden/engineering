#include "ZDT.h"

namespace ZDT
{

	void ZDT1(const double * x_var,  int nvar, double * y_obj, int nobj)
	{
		double pi = 3.1415926535897932384626433832795;
		double g = 0;
		for(int n=1;n<nvar;n++)
			g+= x_var[n];
		g = 1 + 9*g/(nvar-1);

		y_obj[0] = x_var[0];
		y_obj[1] = g*(1 - sqrt(y_obj[0]/g));
	}


	void ZDT2(const double * x_var,  int nvar, double * y_obj, int nobj)
	{
		double pi = 3.1415926535897932384626433832795;
		double g = 0;
		for(int n=1;n<nvar;n++)
			g+= x_var[n];
		g = 1 + 9*g/(nvar-1);
		y_obj[0] = x_var[0];
		y_obj[1] = g*(1 - pow(y_obj[0]/g,2));
	}


	void ZDT3(const double * x_var,  int nvar, double * y_obj, int nobj)
	{
		double pi = 3.1415926535897932384626433832795;
		double g = 0;
		for(int n=1;n<nvar;n++)
			g+= x_var[n];
		g = 1 + 9*g/(nvar-1);

		y_obj[0] = x_var[0];
		y_obj[1] = g*(1 - sqrt(x_var[0]/g) - x_var[0]*sin(10*pi*x_var[0])/g);
	}


	void ZDT4(const double * x_var,  int nvar, double * y_obj, int nobj)
	{
		double pi = 3.1415926535897932384626433832795;
		double g = 1.0 + 10.0*(nvar-1.0);
		double x;

		for(int n=1;n<nvar;n++){
			g+= x_var[n]*x_var[n] - 10*cos(4*pi*x_var[n]);
		}

		y_obj[0] = x_var[0];
		y_obj[1] = g*(1- sqrt(y_obj[0]/g));
	}


	void ZDT6(const double * x_var,  int nvar, double * y_obj, int nobj)
	{
		double pi = 3.1415926535897932384626433832795;
		double g = 0;
		for(int n=1;n<nvar;n++)
			g+= x_var[n]/(nvar - 1);
		g = 1 + 9*pow(g,0.25) ;

		y_obj[0] = 1 - exp(-4*x_var[0])*pow(sin(6*pi*x_var[0]),6);
		y_obj[1] = g*(1- pow(y_obj[0]/g,2));
	}


}