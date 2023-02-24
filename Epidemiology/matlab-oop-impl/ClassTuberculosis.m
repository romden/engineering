classdef ClassTuberculosis<handle
    properties
        % parameters
        beta = 75; %(75, 100,150,175) % Transmission coefficient
        mu = 1/70; % Death and birth rate (yr^-1)
        delta = 12; % Rate at which individuals leave L1 (yr^-1)
        phi = 0.05; % Proportion of individuals going to I
        omega = 0.0002; % Rate of endogenous reactivation for persistent latent infections (yr^-1)
        omegaR = 0.00002; % Rate of endogenous reactivation for treated individuals (yr^-1)
        sigma = 0.25; % Factor reducing the risk of infection as a result of acquired immunity to a previous infection for L2
        sigmaR = 0.25; % Rate of exogenous reinfection of treated patients
        tau0 = 2; % Rate of recovery under treatment of active TB (yr^-1)
        tau1 = 2; % Rate of recovery under treatment of latent individuals L1 (yr^-1)
        tau2 = 1; % Rate of recovery under treatment of latent individuals L2 (yr^-1)
        epsilon1 = 0.5; % (0.25, 0.5, 0.75) % Efficacy of treatment of active TB I
        epsilon2 = 0.5; % (0.25, 0.5, 0.75) % Efficacy of treatment of active TB L2
        N = 30000; %(30000,40000,60000) % Total population
        T = 5; % Total simulation duration (yr)
        
        M; % number of time intervals
        h; % length of interval
        t; % set of evenly distributed points
        n; % number of variables
        
        % state variables
        S; % susceptible individuals
        L1; % latent individuals
        I; % infectious individuals
        L2; % persistent latent individuals
        R; % recovered individuals   
        
        funType = 'robust';
                
    end
    methods (Access = public)
        
        % constructor
        function obj = ClassTuberculosis(n)
            % init number of variables
            obj.n = n;
            
            % init params
            obj.M = obj.n/2 - 1; % number of time intervals
            obj.h = obj.T/obj.M; % length of interval
            obj.t = linspace(0, obj.T, obj.M+1);
            
            % initialize state variables
            obj.S = zeros(1, obj.M+1); % susceptible individuals
            obj.L1 = zeros(1, obj.M+1); % latent individuals
            obj.I = zeros(1, obj.M+1); % infectious individuals
            obj.L2 = zeros(1, obj.M+1); % persistent latent individuals
            obj.R = zeros(1, obj.M+1); % recovered individuals
            
            obj.initPFbounds();
        end
        
        % equations function
        function [f1, f2, f3, f4, f5] = equations(obj, u1, u2, S, L1, I, L2, R)
            % calculate functions
            f1 = obj.mu*obj.N - obj.beta/obj.N*I*S - obj.mu*S;
            f2 = obj.beta/obj.N*I*(S + obj.sigma*L2 + obj.sigmaR*R) - (obj.delta + obj.tau1 + obj.mu)*L1;
            f3 = obj.phi*obj.delta*L1 + obj.omega*L2 + obj.omegaR*R - (obj.tau0 + obj.epsilon1*u1 + obj.mu)*I;
            f4 = (1 - obj.phi)*obj.delta*L1 - obj.sigma*obj.beta/obj.N*I*L2 - (obj.omega + obj.epsilon2*u2 + obj.tau2 + obj.mu)*L2;
            f5 = (obj.tau0 + obj.epsilon1*u1)*I + obj.tau1*L1 + (obj.tau2 + obj.epsilon2*u2)*L2 - obj.sigmaR*obj.beta/obj.N*I*R - (obj.omegaR + obj.mu)*R;            
        end
        
        % runge kutta function
        function Runge_Kutta_4(obj, u1, u2)
            
            % inicial conditions
            obj.S(1) = 76/120*obj.N; % Initial number of susceptible individuals
            obj.L1(1) = 37/120*obj.N; % Initial number of early latent L1 individuals
            obj.I(1) = 4/120*obj.N; % Initial number of infectious individuals
            obj.L2(1) = 2/120*obj.N; % Initial number of persistent latent L2 individuals
            obj.R(1) = 1/120*obj.N; % Initial number of recovered individuals
            
            
            % the 4-th order Runge-Kutta method for the system of ODEs
            for i = 1:obj.M
                
                % FIRST ORDER PARAMETERS
                
                % controls
                u1_ = u1(i);
                u2_ = u2(i);
                
                % states
                S_ = obj.S(i);
                L1_ = obj.L1(i);
                I_ = obj.I(i);
                L2_ = obj.L2(i);
                R_ = obj.R(i);
                
                % calculate first order parameters
                [k11, k12, k13, k14, k15] = obj.equations(u1_, u2_, S_, L1_, I_, L2_, R_);
                
                
                % SECOND ORDER PARAMETERS
                
                % controls
                u1_ = 0.5*(u1(i) + u1(i+1));
                u2_ = 0.5*(u2(i) + u2(i+1));
                
                % states
                S_ = obj.S(i) + 0.5*k11*obj.h;
                L1_ = obj.L1(i) + 0.5*k12*obj.h;
                I_ = obj.I(i) + 0.5*k13*obj.h;
                L2_ = obj.L2(i) + 0.5*k14*obj.h;
                R_ = obj.R(i) + 0.5*k15*obj.h;
                
                % calculate second order parameters
                [k21, k22, k23, k24, k25] = obj.equations(u1_, u2_, S_, L1_, I_, L2_, R_);
                
                
                % THIRD ORDER PARAMETERS
                
                % controls
                u1_ = 0.5*(u1(i) + u1(i+1));
                u2_ = 0.5*(u2(i) + u2(i+1));
                
                % states
                S_ = obj.S(i) + 0.5*k21*obj.h;
                L1_ = obj.L1(i) + 0.5*k22*obj.h;
                I_ = obj.I(i) + 0.5*k23*obj.h;
                L2_ = obj.L2(i) + 0.5*k24*obj.h;
                R_ = obj.R(i) + 0.5*k25*obj.h;
                
                % calculate third order parameters
                [k31, k32, k33, k34, k35] = obj.equations(u1_, u2_, S_, L1_, I_, L2_, R_);
                
                
                % FOURS ORDER PARAMETERS
                
                % states
                u1_ = u1(i+1);
                u2_ = u2(i+1);
                
                % controls
                S_ = obj.S(i) + k31*obj.h;
                L1_ = obj.L1(i) + k32*obj.h;
                I_ = obj.I(i) + k33*obj.h;
                L2_ = obj.L2(i) + k34*obj.h;
                R_ = obj.R(i) + k35*obj.h;
                
                % calculate fourth order parameters
                [k41, k42, k43, k44, k45] = obj.equations(u1_, u2_, S_, L1_, I_, L2_, R_);
                
                
                % VALUES OF STATE VARIABLES
                obj.S(i+1) = obj.S(i) + (obj.h/6)*(k11 + 2*k21 + 2*k31 + k41);
                obj.L1(i+1) = obj.L1(i) + (obj.h/6)*(k12 + 2*k22 + 2*k32 + k42);
                obj.I(i+1) = obj.I(i) + (obj.h/6)*(k13 + 2*k23 + 2*k33 + k43);
                obj.L2(i+1) = obj.L2(i) + (obj.h/6)*(k14 + 2*k24 + 2*k34 + k44);
                obj.R(i+1) = obj.R(i) + (obj.h/6)*(k15 + 2*k25 + 2*k35 + k45);
                
            end
        end      
   
        % evaluate function
        function f = evaluate(obj, x)
            
            % solve numerically system of ODEs using 4th order Runge Kutta method
            obj.Runge_Kutta_4(x(1:obj.n/2), x(obj.n/2+1:end));
            
            % calculate objectives
            f = zeros(1, 2);
            f(1) = trapz(obj.t, obj.I+obj.L2);
            f(2) = trapz(obj.t, x(1:obj.n/2).^2+x(obj.n/2+1:end).^2);
        end        
           
    end % methods
    
    properties
        ideal; % ideal point
        nadir; % nadir point
        epsConstraint; % value for epsilon constraint method
        idxFun; % index of obj to be minimized
        idxConst; % indexes of objs considered as constraints
    end
    
    methods 
        function initPFbounds(obj)
            data = [obj.evaluate(zeros(1, obj.n)); obj.evaluate(ones(1, obj.n))];
            obj.ideal = min(data);
            obj.nadir = max(data);
        end
    end
    
end % class