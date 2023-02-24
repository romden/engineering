
#ifndef ZDT_H
#define ZDT_H


#include <math.h>

namespace ZDT
{
	void ZDT1(const double * x_var,  int nvar, double * y_obj, int nobj);
	void ZDT2(const double * x_var,  int nvar, double * y_obj, int nobj);
	void ZDT3(const double * x_var,  int nvar, double * y_obj, int nobj);
	void ZDT4(const double * x_var,  int nvar, double * y_obj, int nobj);
	void ZDT6(const double * x_var,  int nvar, double * y_obj, int nobj);
}
#endif
