package problem;

import java.util.Arrays;
import java.util.Scanner;
import moead.Solution;


public class Tuberculosis
{
    // problem parameters
    public int numberOfVariables ;
    public int numberOfObjectives ;
    public double [] lowerLimit ;
    public double [] upperLimit ;
    public String problemName ;
    
    // parameters
    double beta = 75.0; //(100,150,175) // Transmission coefficient
    double mu = 1.0/70.0; // Death and birth rate (yr^-1)
    double delta = 12.0; // Rate at which individuals leave L1 (yr^-1)
    double phi = 0.05; // Proportion of individuals going to I
    double omega = 0.0002; // Rate of endogenous reactivation for persistent latent infections (yr^-1)
    double omegaR = 0.00002; // Rate of endogenous reactivation for treated individuals (yr^-1)
    double sigma = 0.25; // Factor reducing the risk of infection as a result of acquired immunity to a previous infection for L2
    double sigmaR = 0.25; // Rate of exogenous reinfection of treated patients
    double tau0 = 2.0; // Rate of recovery under treatment of active TB (yr^-1)
    double tau1 = 2.0; // Rate of recovery under treatment of latent individuals L1 (yr^-1)
    double tau2 = 1.0; // Rate of recovery under treatment of latent individuals L2 (yr^-1)
    double epsilon1 = 0.25; // (0.5, 0.75) // Efficacy of treatment of active TB I
    double epsilon2 = 0.25; // (0.5, 0.75) // Efficacy of treatment of active TB L2
    int N = 30000; //(40000,60000) // Total population
    int T = 5; // Total simulation duration (yr)
    
    // controls
    double [] u1;
    double [] u2;
    
    // state variables
    double [] S; // susceptible individuals
    double [] L1; // latent individuals
    double [] I; // infectious individuals
    double [] L2; // persistent latent individuals
    double [] R; // recovered individuals
    
    private Scanner scanner;
    
    
    // constructor
    public Tuberculosis()
    {
        // initialize problem parameters
        initComponents();
        
        // controls
        u1 = new double[numberOfVariables/2];
        u2 = new double[numberOfVariables/2];
        
        // initialize state variables
        S = new double[numberOfVariables/2]; // susceptible individuals
        L1 = new double[numberOfVariables/2]; // latent individuals
        I = new double[numberOfVariables/2]; // infectious individuals
        L2 = new double[numberOfVariables/2]; // persistent latent individuals
        R = new double[numberOfVariables/2]; // recovered individuals
        
    }// constructor
    
    
    private void initComponents()
    {
        // create scanner to read file
        try{
            scanner = new Scanner(new java.io.File("param_problem.dat"));
        }
        catch (Exception e){
            System.out.println("Error reading param_problem.dat : " + e);
        }
        
        // read problem name
        scanner.next();
        problemName = scanner.next();
        
        // read number of objectives
        scanner.next();
        numberOfObjectives = Integer.parseInt(scanner.next());
        
        // read number of variables
        scanner.next();
        numberOfVariables = Integer.parseInt(scanner.next());
        
        // close file
        scanner.close();        
        
        // initialize bounds
        lowerLimit = new double[numberOfVariables];
        Arrays.fill(lowerLimit, 0.0);
        upperLimit = new double[numberOfVariables];
        Arrays.fill(upperLimit, 1.0);        
    }
    
    
    public double [] equations(double u1, double u2, double S, double L1, double I, double L2, double R)
    {
        double [] f = new double[5];
        
        // calculate functions
        f[0] = mu*N - beta/N*I*S - mu*S;
        f[1] = beta/N*I*(S + sigma*L2 + sigmaR*R) - (delta + tau1 + mu)*L1;
        f[2] = phi*delta*L1 + omega*L2 + omegaR*R - (tau0 + epsilon1*u1 + mu)*I;
        f[3] = (1 - phi)*delta*L1 - sigma*beta/N*I*L2 - (omega + epsilon2*u2 + tau2 + mu)*L2;
        f[4] = (tau0 + epsilon1*u1)*I + tau1*L1 + (tau2 + epsilon2*u2)*L2 - sigmaR*beta/N*I*R - (omegaR + mu)*R;
        
        return f;
    }
    
    
    public void  RungeKutta4(double [] u1, double [] u2, double h, int M)
    {
        // inicial conditions
        S[0] = 76.0/120.0*N; // Initial number of susceptible individuals
        L1[0] = 37.0/120.0*N; // Initial number of early latent L1 individuals
        I[0] = 4.0/120.0*N; // Initial number of infectious individuals
        L2[0] = 2.0/120.0*N; // Initial number of persistent latent L2 individuals
        R[0] = 1.0/120.0*N; // Initial number of recovered individuals
        
        // auxiliary variables
        double u1_;
        double u2_;
        double S_;
        double L1_;
        double I_;
        double L2_;
        double R_;
        
        double [] k1;
        double [] k2;
        double [] k3;
        double [] k4;
        
        // the 4-th order Runge-Kutta method for the system of ODEs
        for(int i = 0; i < M; i++)
        {
            
            // FIRST ORDER PARAMETERS
            
            // controls
            u1_ = u1[i];
            u2_ = u2[i];
            
            // states
            S_ = S[i];
            L1_ = L1[i];
            I_ = I[i];
            L2_ = L2[i];
            R_ = R[i];
            
            // calculate first order parameters
            k1 = equations(u1_, u2_, S_, L1_, I_, L2_, R_);
            
            
            // SECOND ORDER PARAMETERS
            
            // controls
            u1_ = 0.5*(u1[i] + u1[i+1]);
            u2_ = 0.5*(u2[i] + u2[i+1]);
            
            // states
            S_ = S[i] + 0.5*k1[0]*h;
            L1_ = L1[i] + 0.5*k1[1]*h;
            I_ = I[i] + 0.5*k1[2]*h;
            L2_ = L2[i] + 0.5*k1[3]*h;
            R_ = R[i] + 0.5*k1[4]*h;
            
            // calculate second order parameters
            k2 = equations(u1_, u2_, S_, L1_, I_, L2_, R_);
            
            
            // THIRD ORDER PARAMETERS
            
            // controls
            u1_ = 0.5*(u1[i] + u1[i+1]);
            u2_ = 0.5*(u2[i] + u2[i+1]);
            
            // states
            S_ = S[i] + 0.5*k2[0]*h;
            L1_ = L1[i] + 0.5*k2[1]*h;
            I_ = I[i] + 0.5*k2[2]*h;
            L2_ = L2[i] + 0.5*k2[3]*h;
            R_ = R[i] + 0.5*k2[4]*h;
            
            // calculate third order parameters
            k3 = equations(u1_, u2_, S_, L1_, I_, L2_, R_);
            
            
            // FOURS ORDER PARAMETERS
            
            // states
            u1_ = u1[i+1];
            u2_ = u2[i+1];
            
            // controls
            S_ = S[i] + k3[0]*h;
            L1_ = L1[i] + k3[1]*h;
            I_ = I[i] + k3[2]*h;
            L2_ = L2[i] + k3[3]*h;
            R_ = R[i] + k3[4]*h;
            
            // calculate fourth order parameters
            k4 = equations(u1_, u2_, S_, L1_, I_, L2_, R_);
            
            
            // VALUES OF STATE VARIABLES
            S[i+1] = S[i] + (h/6)*(k1[0] + 2*k2[0] + 2*k3[0] + k4[0]);
            L1[i+1] = L1[i] + (h/6)*(k1[1] + 2*k2[1] + 2*k3[1] + k4[1]);
            I[i+1] = I[i] + (h/6)*(k1[2] + 2*k2[2] + 2*k3[2] + k4[2]);
            L2[i+1] = L2[i] + (h/6)*(k1[3] + 2*k2[3] + 2*k3[3] + k4[3]);
            R[i+1] = R[i] + (h/6)*(k1[4] + 2*k2[4] + 2*k3[4] + k4[4]);
        }
        
    }
    
    
    public double [] execute(double [] variables)
    {
        for(int i = 0; i < numberOfVariables/2; i++)
        {
//            u1[i] = variables[2*i]; // first control
//            u2[i] = variables[2*i+1]; // second control
        
            u1[i] = variables[i]; // first control
            u2[i] = variables[i+numberOfVariables/2]; // second control
        }
        
        int M = numberOfVariables/2 - 1; // number of time intervals
        double h = (double)T/M; // length of interval
        
        // solve numerically system of ODEs using 4th order Runge Kutta method
        RungeKutta4(u1, u2, h, M);
        
        // set of evenly distributed points
        double [] t = linspace(0, T, M+1);
        
        
        // calculate objectives
        double [] objectives = new double[]{0, 0};
        objectives[0] = trapz(t, addVectors(I, L2));
        objectives[1] = trapz(t, addVectors2(u1, u2));
        
        return objectives;
    }
    
    
    
    public void evaluate(Solution solution)
    {
        
        // initialize control variables and time intervals
//        for(int i = 0; i < numberOfVariables/2; i++)
//        {
//            u1[i] = solution.variables[i]; // first control
//            u2[i] = solution.variables[i+numberOfVariables/2]; // second control
//        }
        for(int i = 0; i < numberOfVariables/2; i++)
        {
            u1[i] = solution.variables[2*i]; // first control
            u2[i] = solution.variables[2*i+1]; // second control
        }

        int M = numberOfVariables/2 - 1; // number of time intervals
        double h = (double)T/M; // length of interval
        
        // solve numerically system of ODEs using 4th order Runge Kutta method
        RungeKutta4(u1, u2, h, M);
        
        // set of evenly distributed points
        double [] t = linspace(0, T, M+1);
        
        
        // calculate objectives
        switch(problemName)
        {
            case "IL2-u1u2":
                solution.objectives[0] = trapz(t, addVectors(I, L2));
                solution.objectives[1] = trapz(t, addVectors2(u1, u2));
                break;
            case "I-L2-u1u2":
                solution.objectives[0] = trapz(t, I);
                solution.objectives[1] = trapz(t, L2);
                solution.objectives[2] = trapz(t, addVectors(u1, u2));
                break;
            case "IL2-u1-u2":
                solution.objectives[0] = trapz(t, addVectors(I, L2));
                solution.objectives[1] = trapz(t, u1);
                solution.objectives[2] = trapz(t, u2);
                break;
            case "I-L2-u1-u2":
                solution.objectives[0] = trapz(t, I);
                solution.objectives[1] = trapz(t, L2);
                solution.objectives[2] = trapz(t, u1);
                solution.objectives[3] = trapz(t, u2);
                break;
        }
        
    }
    
    
    private double [] addVectors(double [] a, double [] b)
    {
        double [] c = new double[a.length];
        
        for(int i = 0; i < a.length; i++){
            c[i] = a[i] + b[i];
        }
        
        return c;
    }
    
        private double [] addVectors2(double [] a, double [] b)
    {
        double [] c = new double[a.length];
        
        for(int i = 0; i < a.length; i++){
            c[i] = a[i]*a[i] + b[i]*b[i];
        }
        
        return c;
    }
    
    private double [] linspace(double p1, double p2, int np)
    {
        double space = (p2 - p1) / (np-1);
        double [] vect = new double[np];
        
        vect[0] = p1;
        for(int i = 1; i < np-1; i++){
            vect[i] = vect[i-1] + space;
        }
        vect[np-1] = p2;
        
        return vect;
    }
    
    
    // method to numerically calculate integral
    private double trapz(double [] x, double [] y)
    {
        // declare integral
        double I = 0 ;
        
        for(int i = 0; i < x.length-1; i++)
            I +=  (x[i+1]-x[i]) * (y[i+1]+y[i]) /2 ;
        
        return I;
        
    } // trapz method
    
}
