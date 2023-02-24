// cec09.h
//
// C++ source codes of the test instances for CEC 2009 MOO competition
//
// If the source codes are not consistant with the report, please use the version in the report
//
// History
// 	  v1   Sept.8  2008
// 	  v1.1 Sept.22 2008: add R2_DTLZ2_M5 R3_DTLZ3_M5 WFG1_M5
//    v1.2 Oct.2  2008: fix the bugs in CF1-CF4, CF6-CF10, thank Qu Boyang for finding the bugs
//    v1.3 Oct.8  2008: fix a bug in R2_DTLZ2_M5, thank Santosh Tiwari

#ifndef CEC2009_H
#define CEC2009_H

#include <cmath>


namespace CEC2009
{
	void UF1(const double *x, int n, double *f, int m);
	void UF2(const double *x, int n, double *f, int m);
	void UF3(const double *x, int n, double *f, int m);
	void UF4(const double *x, int n, double *f, int m);
	void UF5(const double *x, int n, double *f, int m);
	void UF6(const double *x, int n, double *f, int m);
	void UF7(const double *x, int n, double *f, int m);
	void UF8(const double *x, int n, double *f, int m);
	void UF9(const double *x, int n, double *f, int m);
	void UF10(const double *x, int n, double *f, int m);

	void R2_DTLZ2_M5(const double *x,  int n, double *f, int m);
	void R2_DTLZ3_M5(const double *x,  int n, double *f, int m);
	void WFG1_M5(const double *x,  int n, double *f, int m);

	//void CF1(double *x, double *f, double *c, const unsigned int nx);
	//void CF2(double *x, double *f, double *c, const unsigned int nx);
	//void CF3(double *x, double *f, double *c, const unsigned int nx);
	//void CF4(double *x, double *f, double *c, const unsigned int nx);
	//void CF5(double *x, double *f, double *c, const unsigned int nx);
	//void CF6(double *x, double *f, double *c, const unsigned int nx);
	//void CF7(double *x, double *f, double *c, const unsigned int nx);
	//void CF8(double *x, double *f, double *c, const unsigned int nx);
	//void CF9(double *x, double *f, double *c, const unsigned int nx);
	//void CF10(double *x, double *f, double *c, const unsigned int nx);
}
#endif //CEC09_H










// second version - defferent from matlab implementations

//#ifndef CEC2009_H
//#define CEC2009_H
//
//#include <cmath>
//
//
//namespace CEC2009
//{
//	void UF1(const double *x, int nx, double *f, int m);
//	void UF2(const double *x, int nx, double *f, int m);
//	void UF3(const double *x, int nx, double *f, int m);
//	void UF4(const double *x, int nx, double *f, int m);
//	void UF5(const double *x, int nx, double *f, int m);
//	void UF6(const double *x, int nx, double *f, int m);
//	void UF7(const double *x, int nx, double *f, int m);
//	void UF8(const double *x, int nx, double *f, int m);
//	void UF9(const double *x, int nx, double *f, int m);
//	void UF10(const double *x, int nx, double *f, int m);
//
//	void CF1(double *x, double *f, double *c, const unsigned int nx);
//	void CF2(double *x, double *f, double *c, const unsigned int nx);
//	void CF3(double *x, double *f, double *c, const unsigned int nx);
//	void CF4(double *x, double *f, double *c, const unsigned int nx);
//	void CF5(double *x, double *f, double *c, const unsigned int nx);
//	void CF6(double *x, double *f, double *c, const unsigned int nx);
//	void CF7(double *x, double *f, double *c, const unsigned int nx);
//	void CF8(double *x, double *f, double *c, const unsigned int nx);
//	void CF9(double *x, double *f, double *c, const unsigned int nx);
//	void CF10(double *x, double *f, double *c, const unsigned int nx);
//
//	void R2_DTLZ2_M5(const double *x,  int nx, double *f, int n_obj);
//	void R3_DTLZ3_M5(const double *x,  int nx, double *f, int n_obj);
//	void WFG1_M5(const double *z, int nx, double *f, const unsigned int M);
//}
//#endif //CEC09_H
