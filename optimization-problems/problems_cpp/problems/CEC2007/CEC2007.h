#ifndef CEC2007_H
#define CEC2007_H


#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

namespace CEC2007
{
	void OKA2(const double * x,  int n, double * f, int m);
	//void SYMPART(const double * x,  int n, double * f, int m);
	void S_ZDT1(const double * x,  int n, double * f, int m);
	void S_ZDT2(const double * x,  int n, double * f, int m);
	void S_ZDT4(const double * x,  int n, double * f, int m);
	void R_ZDT4(const double * x,  int n, double * f, int m);
	void S_ZDT6(const double * x,  int n, double * f, int m);
	void S_DTLZ2(const double * x,  int n, double * f, int m);
	void R_DTLZ2(const double * x,  int n, double * f, int m);
	void S_DTLZ3(const double * x,  int n, double * f, int m);
}
#endif