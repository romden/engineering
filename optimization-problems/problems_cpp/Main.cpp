#include <iostream>
#include <fstream>
#include <string>

#include "Util.h"

#include "problems\ZDT\ZDT.h"
#include "problems\DTLZ\DTLZ.h"
#include "problems\WFG\WFGTestSuite.h"
#include "problems\LZ09\LZ09.h"
#include "problems\CEC2009\CEC2009.h"
#include "problems\LGZ\LGZ.h"
#include "problems\CEC2007\CEC2007.h"




void initialization(void);
void finalization(void);

void (*feval)(const double *, int, double *, int);

std::string name;
int m;
int n;
double * lb;
double * ub;

double ** x;
double ** f;

int mu = 300;

CUtil util;




void finalization(void)
{
	// open files to output
	std::ofstream fout1, fout2;
	fout1.open("VAR.dat");
	fout2.open("FUN.dat");

	// declare
	int i, j;

	// write to file
	for(i = 0; i < mu; i++)
	{
		// save variables
		for(j = 0; j < n; j++){
			fout1  << " " << x[i][j];
		}
		fout1 << "\n";

		// save function values
		for(j = 0; j < m; j++){
			fout2 << " " << f[i][j];
		}
		fout2 << "\n";		
	}

	// close file
	fout1.close();
	fout2.close();

	delete [] lb;
	delete [] ub;

	for(int i=0; i<mu; i++)
	{
		delete [] x[i];
		delete [] f[i];
	}
	delete [] x;
	delete [] f;
}


void initialization(void)
{
	// READ PROBLEM DEFINITION FROM FILE

	std::ifstream fin; // create ifstream object called fin	
	fin.open("param_problem.dat"); // opens the file

	std::string temp; // temp string
	fin >> temp >> name; // read problem name
	fin >> temp >> m; // read number of objectives
	fin >> temp >> n; // read number of variables

	fin.close(); // close the file

	// INITIALIZE LB, UB AND FEVAL
	lb = new double[n];
	ub = new double[n];

	// ZDT test suite
	if(name.compare("ZDT1") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = ZDT::ZDT1;
	}
	if(name.compare("ZDT2") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = ZDT::ZDT2;
	}
	if(name.compare("ZDT3") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = ZDT::ZDT3;
	}
	if(name.compare("ZDT4") == 0)
	{
		std::fill(lb, lb+n, -5.0); lb[0] = 0.0;
		std::fill(ub, ub+n, 5.0);  ub[0] = 1.0;
		feval = ZDT::ZDT4;
	}
	if(name.compare("ZDT6") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = ZDT::ZDT6;
	}
	// DTLZ test suite
	if(name.compare("DTLZ1") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = DTLZ::DTLZ1;
	}
	if(name.compare("DTLZ2") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = DTLZ::DTLZ2;
	}
	if(name.compare("DTLZ3") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = DTLZ::DTLZ3;
	}
	if(name.compare("DTLZ4") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = DTLZ::DTLZ4;
	}
	if(name.compare("DTLZ5") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = DTLZ::DTLZ5;
	}
	if(name.compare("DTLZ6") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = DTLZ::DTLZ6;
	}
	if(name.compare("DTLZ7") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = DTLZ::DTLZ7;
	}
	// WFG test suite
	if(name.compare("WFG1") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		for(int i = 0; i < n; i++){ub[i]=(double)2*(i+1);}
		feval = WFGTestSuite::WFG1;
	}
	if(name.compare("WFG2") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		for(int i = 0; i < n; i++){ub[i]=(double)2*(i+1);}
		feval = WFGTestSuite::WFG2;
	}
	if(name.compare("WFG3") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		for(int i = 0; i < n; i++){ub[i]=(double)2*(i+1);}
		feval = WFGTestSuite::WFG3;
	}
	if(name.compare("WFG4") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		for(int i = 0; i < n; i++){ub[i]=(double)2*(i+1);}
		feval = WFGTestSuite::WFG4;
	}
	if(name.compare("WFG5") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		for(int i = 0; i < n; i++){ub[i]=(double)2*(i+1);}
		feval = WFGTestSuite::WFG5;
	}
	if(name.compare("WFG6") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		for(int i = 0; i < n; i++){ub[i]=(double)2*(i+1);}
		feval = WFGTestSuite::WFG6;
	}
	if(name.compare("WFG7") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		for(int i = 0; i < n; i++){ub[i]=(double)2*(i+1);}
		feval = WFGTestSuite::WFG7;
	}
	if(name.compare("WFG8") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		for(int i = 0; i < n; i++){ub[i]=(double)2*(i+1);}
		feval = WFGTestSuite::WFG8;
	}
	if(name.compare("WFG9") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		for(int i = 0; i < n; i++){ub[i]=(double)2*(i+1);}
		feval = WFGTestSuite::WFG9;
	}
	// LZ09 test suite
	if(name.compare("LZ09_F1") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = LZ09::F1;
	}
	if(name.compare("LZ09_F2") == 0)
	{
		std::fill(lb, lb+n, -1.0); lb[0]=0.0;
		std::fill(ub, ub+n, 1.0);
		feval = LZ09::F2;
	}
	if(name.compare("LZ09_F3") == 0)
	{
		std::fill(lb, lb+n, -1.0); lb[0]=0.0;
		std::fill(ub, ub+n, 1.0);
		feval = LZ09::F3;
	}
	if(name.compare("LZ09_F4") == 0)
	{
		std::fill(lb, lb+n, -1.0); lb[0]=0.0;
		std::fill(ub, ub+n, 1.0);
		feval = LZ09::F4;
	}
	if(name.compare("LZ09_F5") == 0)
	{
		std::fill(lb, lb+n, -1.0); lb[0]=0.0;
		std::fill(ub, ub+n, 1.0);
		feval = LZ09::F5;
	}
	if(name.compare("LZ09_F6") == 0)
	{
		std::fill(lb, lb+n, -2.0); lb[0]=0.0; lb[1]=0.0;
		std::fill(ub, ub+n, 2.0); ub[0]=1.0; ub[1]=1.0;
		feval = LZ09::F6;
	}
	if(name.compare("LZ09_F7") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = LZ09::F7;
	}
	if(name.compare("LZ09_F8") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = LZ09::F8;
	}
	if(name.compare("LZ09_F9") == 0)
	{
		std::fill(lb, lb+n, -1.0); lb[0]=0.0;
		std::fill(ub, ub+n, 1.0);
		feval = LZ09::F9;
	}
	// CEC2009 test suite
	if(name.compare("UF1") == 0)
	{
		std::fill(lb, lb+n, -1.0); lb[0]=0.0;
		std::fill(ub, ub+n, 1.0);
		feval = CEC2009::UF1;
	}
	if(name.compare("UF2") == 0)
	{
		std::fill(lb, lb+n, -1.0); lb[0]=0.0;
		std::fill(ub, ub+n, 1.0);
		feval = CEC2009::UF2;
	}
	if(name.compare("UF3") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = CEC2009::UF3;
	}
	if(name.compare("UF4") == 0)
	{
		std::fill(lb, lb+n, -2.0); lb[0]=0.0;
		std::fill(ub, ub+n, 2.0); ub[0]=1.0;
		feval = CEC2009::UF4;
	}
	if(name.compare("UF5") == 0)
	{
		std::fill(lb, lb+n, -1.0); lb[0]=0.0;
		std::fill(ub, ub+n, 1.0);
		feval = CEC2009::UF5;
	}
	if(name.compare("UF6") == 0)
	{
		std::fill(lb, lb+n, -1.0); lb[0]=0.0;
		std::fill(ub, ub+n, 1.0);
		feval = CEC2009::UF6;
	}
	if(name.compare("UF7") == 0)
	{
		std::fill(lb, lb+n, -1.0); lb[0]=0.0;
		std::fill(ub, ub+n, 1.0);
		feval = CEC2009::UF7;
	}
	if(name.compare("UF8") == 0)
	{
		std::fill(lb, lb+n, -2.0); lb[0]=0.0; lb[1]=0.0;
		std::fill(ub, ub+n, 2.0); ub[0]=1.0; ub[1]=1.0;
		feval = CEC2009::UF8;
	}
	if(name.compare("UF9") == 0)
	{
		std::fill(lb, lb+n, -2.0); lb[0]=0.0; lb[1]=0.0;
		std::fill(ub, ub+n, 2.0); ub[0]=1.0; ub[1]=1.0;
		feval = CEC2009::UF9;	
	}
	if(name.compare("UF10") == 0)
	{
		std::fill(lb, lb+n, -2.0); lb[0]=0.0; lb[1]=0.0;
		std::fill(ub, ub+n, 2.0); ub[0]=1.0; ub[1]=1.0;
		feval = CEC2009::UF10;
	}
	if(name.compare("R2_DTLZ2_M5") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = CEC2009::R2_DTLZ2_M5;
	}
	if(name.compare("R2_DTLZ3_M5") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = CEC2009::R2_DTLZ3_M5;
	}
	if(name.compare("WFG1_M5") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		for(int i = 0; i < n; i++){ub[i]=(double)2*(i+1);}
		feval = CEC2009::WFG1_M5;
	}
	if(name.compare("LGZ1") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = LGZ::F1;
	}
	if(name.compare("LGZ2") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = LGZ::F2;
	}
	if(name.compare("LGZ3") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = LGZ::F3;
	}
	if(name.compare("LGZ4") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = LGZ::F4;
	}
	if(name.compare("LGZ5") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = LGZ::F5;
	}
	if(name.compare("LGZ6") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = LGZ::F6;
	}
	if(name.compare("LGZ7") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = LGZ::F7;
	}
	if(name.compare("CEC2007_OKA2") == 0)
	{
		lb[0] = -3.1415926535897932384626433832795; lb[1] = -5.0, lb[2]= -5.0;
		ub[0] = 3.1415926535897932384626433832795; ub[1] = 5.0, ub[2]= 5.0;
		feval = CEC2007::OKA2;
	}
	if(name.compare("CEC2007_S_ZDT1") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = CEC2007::S_ZDT1;
	}
	if(name.compare("CEC2007_S_ZDT2") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = CEC2007::S_ZDT2;
	}
	if(name.compare("CEC2007_S_ZDT4") == 0)
	{
		std::fill(lb, lb+n, -5.0); lb[0] = 0.0;
		std::fill(ub, ub+n, 5.0);  ub[0] = 1.0;
		feval = CEC2007::S_ZDT4;
	}
	if(name.compare("CEC2007_R_ZDT4") == 0)
	{
		std::fill(lb, lb+n, -5.0); lb[0] = 0.0;
		std::fill(ub, ub+n, 5.0);  ub[0] = 1.0;
		feval = CEC2007::R_ZDT4;
	}
	if(name.compare("CEC2007_S_ZDT6") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = CEC2007::S_ZDT6;
	}
	if(name.compare("CEC2007_S_DTLZ2") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = CEC2007::S_DTLZ2;
	}
	if(name.compare("CEC2007_R_DTLZ2") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = CEC2007::R_DTLZ2;
	}
	if(name.compare("CEC2007_S_DTLZ3") == 0)
	{
		std::fill(lb, lb+n, 0.0);
		std::fill(ub, ub+n, 1.0);
		feval = CEC2007::S_DTLZ3;
	}


	x = new double*[mu];
	f = new double*[mu];
	for(int i=0; i<mu; i++)
	{
		x[i]=new double[n];
		f[i]=new double[m];
	}
}







int main()
{

	initialization();

	int i,j;
	for(i=0; i<mu; i++)
	{
		for(j=0; j<n; j++){
			x[i][j] = lb[j] + util.rand()*(ub[j] - lb[j]);
		}
		feval(x[i], n, f[i], m);
	}


	finalization();


	//system("PAUSE");
	return EXIT_SUCCESS;
}