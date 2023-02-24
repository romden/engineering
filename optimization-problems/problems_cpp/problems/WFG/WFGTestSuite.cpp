
#include "WFGTestSuite.h"


namespace WFGTestSuite
{

	int k = 4;  // number of position related parameters

	void WFG1(const double * x, int n, double * f, int m)
	{
		std::vector<double>x_var(x, x+n);
		std::vector<double>y_obj(f, f+m);

		k = m-1;

		y_obj = WFG::Toolkit::Examples::Problems::WFG1(x_var, k, m);

		for(std::vector<double>::iterator it = y_obj.begin(); it != y_obj.end(); ++it){
			f[it - y_obj.begin()] = *it;
		}
	};


	void WFG2(const double * x, int n, double * f, int m)
	{
		std::vector<double>x_var(x, x+n);
		std::vector<double>y_obj(f, f+m);

		k = m-1;

		y_obj = WFG::Toolkit::Examples::Problems::WFG2(x_var, k, m);

		for(std::vector<double>::iterator it = y_obj.begin(); it != y_obj.end(); ++it){
			f[it - y_obj.begin()] = *it;
		}
	};


	void WFG3(const double * x, int n, double * f, int m)
	{
		std::vector<double>x_var(x, x+n);
		std::vector<double>y_obj(f, f+m);

		k = m-1;

		y_obj = WFG::Toolkit::Examples::Problems::WFG3(x_var, k, m);

		for(std::vector<double>::iterator it = y_obj.begin(); it != y_obj.end(); ++it){
			f[it - y_obj.begin()] = *it;
		}
	};


	void WFG4(const double * x, int n, double * f, int m)
	{
		std::vector<double>x_var(x, x+n);
		std::vector<double>y_obj(f, f+m);

		k = m-1;

		y_obj = WFG::Toolkit::Examples::Problems::WFG4(x_var, k, m);

		for(std::vector<double>::iterator it = y_obj.begin(); it != y_obj.end(); ++it){
			f[it - y_obj.begin()] = *it;
		}
	};


	void WFG5(const double * x, int n, double * f, int m)
	{
		std::vector<double>x_var(x, x+n);
		std::vector<double>y_obj(f, f+m);

		k = m-1;

		y_obj = WFG::Toolkit::Examples::Problems::WFG5(x_var, k, m);

		for(std::vector<double>::iterator it = y_obj.begin(); it != y_obj.end(); ++it){
			f[it - y_obj.begin()] = *it;
		}
	};


	void WFG6(const double * x, int n, double * f, int m)
	{
		std::vector<double>x_var(x, x+n);
		std::vector<double>y_obj(f, f+m);

		k = m-1;

		y_obj = WFG::Toolkit::Examples::Problems::WFG6(x_var, k, m);

		for(std::vector<double>::iterator it = y_obj.begin(); it != y_obj.end(); ++it){
			f[it - y_obj.begin()] = *it;
		}
	};


	void WFG7(const double * x, int n, double * f, int m)
	{
		std::vector<double>x_var(x, x+n);
		std::vector<double>y_obj(f, f+m);

		k = m-1;

		y_obj = WFG::Toolkit::Examples::Problems::WFG7(x_var, k, m);

		for(std::vector<double>::iterator it = y_obj.begin(); it != y_obj.end(); ++it){
			f[it - y_obj.begin()] = *it;
		}

	};


	void WFG8(const double * x, int n, double * f, int m)
	{
		std::vector<double>x_var(x, x+n);
		std::vector<double>y_obj(f, f+m);

		k = m-1;

		y_obj = WFG::Toolkit::Examples::Problems::WFG8(x_var, k, m);

		for(std::vector<double>::iterator it = y_obj.begin(); it != y_obj.end(); ++it){
			f[it - y_obj.begin()] = *it;
		}
	};


	void WFG9(const double * x, int n, double * f, int m)
	{
		std::vector<double>x_var(x, x+n);
		std::vector<double>y_obj(f, f+m);

		k = m-1;

		y_obj = WFG::Toolkit::Examples::Problems::WFG9(x_var, k, m);

		for(std::vector<double>::iterator it = y_obj.begin(); it != y_obj.end(); ++it){
			f[it - y_obj.begin()] = *it;
		}
	};

}

