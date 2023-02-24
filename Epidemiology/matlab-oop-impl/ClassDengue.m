classdef ClassDengue<handle
    properties
        % parameters
        T = 84; % period of time
        Nh = 480000; % total human population [380000, 480000, 580000]
        B = 1; % average daily biting (per day)
        betamh = 0.375; % transmission probability from Im (per bite) [0.3 0.375 0.45]
        betahm = 0.375; % transmission probability from Ih (per bite) [0.3 0.375 0.45]
        muh = 1/(71*365); % average lifespan of humans (in days)
        etah = 1/3; % mean viremic period (in days)
        etam = 1/11; % average lifespan of adult mosquitoes (in days)
        phi = 6; % number of eggs at each deposit per capita (per day)
        muA = 1/4; % natural mortality of larvae (per day)
        etaA = 0.08; % maturation rate from larvae to adult (per day)
        mum = 1/11; % extrinsic incubation period (in days)
        nuh = 1/4; % intrinsic incubation period (in days)
        m = 6; % female mosquitoes per human
        k = 3; % number of larvae per human
        
        M; % number of time intervals
        h; % length of interval
        t; % set of evenly distributed points
        n; % number of variables
        
        % state variables
        Sh; % susceptible human
        Eh; % exposed human
        Ih; % infected human
        Rh; % resistant human
        Am; % aquatic phase female mosquitoes
        Sm; % susceptible female mosquitoes
        Em; % exposed female mosquitoes
        Im; % infected female mosquitoes
        
        funType = 'Ih-u';
        
    end
    methods (Access = public)
        
        % constructor
        function obj = ClassDengue(n)
            % init number of variables
            obj.n = n;
            
            % init params
            obj.M = obj.n - 1; % number of time intervals
            obj.h = obj.T/obj.M; % length of interval
            obj.t = linspace(0, obj.T, obj.M+1);
            
            % initialize state variables
            obj.Sh = zeros(1,obj.M+1); % susceptible human
            obj.Eh = zeros(1,obj.M+1); % exposed human
            obj.Ih = zeros(1,obj.M+1); % infected human
            obj.Rh = zeros(1,obj.M+1); % resistant human
            obj.Am = zeros(1,obj.M+1); % aquatic phase female mosquitoes
            obj.Sm = zeros(1,obj.M+1); % susceptible female mosquitoes
            obj.Em = zeros(1,obj.M+1); % exposed female mosquitoes
            obj.Im = zeros(1,obj.M+1); % infected female mosquitoes
            
            % init bounds of Pareto front
            obj.initPFbounds();
        end
        
        % equations function
        function [f1, f2, f3, f4, f5, f6, f7, f8] = equations(obj, u_, Sh_, Eh_, Ih_, Rh_, Am_, Sm_, Em_, Im_)
            % calculate functions
            f1 = obj.muh - (obj.B*obj.betamh*obj.m*Im_ + obj.muh)*Sh_;
            f2 = obj.B*obj.betamh*obj.m*Im_*Sh_ - (obj.nuh + obj.muh)*Eh_;
            f3 = obj.nuh*Eh_ - (obj.etah + obj.muh)*Ih_;
            f4 = obj.etah*Ih_ - obj.muh*Rh_;
            f5 = obj.phi*obj.m/obj.k*(1 - Am_)*(Sm_ + Em_ + Im_) - (obj.etaA + obj.muA)*Am_;
            f6 = obj.etaA*obj.k/obj.m*Am_ - (obj.B*obj.betahm*Ih_ + obj.mum)*Sm_ - u_*Sm_;
            f7 = obj.B*obj.betahm*Ih_*Sm_ - (obj.mum + obj.etam)*Em_ - u_*Em_;
            f8 = obj.etam*Em_ - obj.mum*Im_ - u_*Im_;
        end
        
        % runge kutta function
        function Runge_Kutta_4(obj, u)
            
            % inicial conditions (normalized)
            obj.Sh(1) = 0.99865;
            obj.Eh(1) = 0.00035;
            obj.Ih(1) = 0.001;
            obj.Rh(1) = 0;
            obj.Am(1) = 1;
            obj.Sm(1) = 1;
            obj.Em(1) = 0;
            obj.Im(1) = 0;
            
            
            % the 4-th order Runge-Kutta method for the system of ODEs
            for i = 1:obj.M
                
                % FIRST ORDER PARAMETERS
                
                % control
                u_ = u(i);
                
                % states
                Sh_ = obj.Sh(i);
                Eh_ = obj.Eh(i);
                Ih_ = obj.Ih(i);
                Rh_ = obj.Rh(i);
                Am_ = obj.Am(i);
                Sm_ = obj.Sm(i);
                Em_ = obj.Em(i);
                Im_ = obj.Im(i);
                
                % calculate first order parameters
                [k11, k12, k13, k14, k15, k16, k17, k18] = obj.equations(u_, Sh_, Eh_, Ih_, Rh_, Am_, Sm_, Em_, Im_);
                
                
                % SECOND ORDER PARAMETERS
                
                % control
                u_ = 0.5*(u(i) + u(i+1));
                
                % states
                Sh_ = obj.Sh(i) + 0.5*k11*obj.h;
                Eh_ = obj.Eh(i) + 0.5*k12*obj.h;
                Ih_ = obj.Ih(i) + 0.5*k13*obj.h;
                Rh_ = obj.Rh(i) + 0.5*k14*obj.h;
                Am_ = obj.Am(i) + 0.5*k15*obj.h;
                Sm_ = obj.Sm(i) + 0.5*k16*obj.h;
                Em_ = obj.Em(i) + 0.5*k17*obj.h;
                Im_ = obj.Im(i) + 0.5*k18*obj.h;
                
                % calculate second order parameters
                [k21, k22, k23, k24, k25, k26, k27, k28] = obj.equations(u_, Sh_, Eh_, Ih_, Rh_, Am_, Sm_, Em_, Im_);
                
                
                % THIRD ORDER PARAMETERS
                
                % controls
                u_ = 0.5*(u(i) + u(i+1));
                
                % states
                Sh_ = obj.Sh(i) + 0.5*k21*obj.h;
                Eh_ = obj.Eh(i) + 0.5*k22*obj.h;
                Ih_ = obj.Ih(i) + 0.5*k23*obj.h;
                Rh_ = obj.Rh(i) + 0.5*k24*obj.h;
                Am_ = obj.Am(i) + 0.5*k25*obj.h;
                Sm_ = obj.Sm(i) + 0.5*k26*obj.h;
                Em_ = obj.Em(i) + 0.5*k27*obj.h;
                Im_ = obj.Im(i) + 0.5*k28*obj.h;
                
                % calculate third order parameters
                [k31, k32, k33, k34, k35, k36, k37, k38] = obj.equations(u_, Sh_, Eh_, Ih_, Rh_, Am_, Sm_, Em_, Im_);
                
                
                % FOURS ORDER PARAMETERS
                
                % states
                u_ = u(i+1);
                
                % controls
                Sh_ = obj.Sh(i) + k31*obj.h;
                Eh_ = obj.Eh(i) + k32*obj.h;
                Ih_ = obj.Ih(i) + k33*obj.h;
                Rh_ = obj.Rh(i) + k34*obj.h;
                Am_ = obj.Am(i) + k35*obj.h;
                Sm_ = obj.Sm(i) + k36*obj.h;
                Em_ = obj.Em(i) + k37*obj.h;
                Im_ = obj.Im(i) + k38*obj.h;
                
                % calculate fourth order parameters
                [k41, k42, k43, k44, k45, k46, k47, k48] = obj.equations(u_, Sh_, Eh_, Ih_, Rh_, Am_, Sm_, Em_, Im_);
                
                
                % VALUES OF STATE VARIABLES
                obj.Sh(i+1) = obj.Sh(i) + (obj.h/6)*(k11 + 2*k21 + 2*k31 + k41);
                obj.Eh(i+1) = obj.Eh(i) + (obj.h/6)*(k12 + 2*k22 + 2*k32 + k42);
                obj.Ih(i+1) = obj.Ih(i) + (obj.h/6)*(k13 + 2*k23 + 2*k33 + k43);
                obj.Rh(i+1) = obj.Rh(i) + (obj.h/6)*(k14 + 2*k24 + 2*k34 + k44);
                obj.Am(i+1) = obj.Am(i) + (obj.h/6)*(k15 + 2*k25 + 2*k35 + k45);
                obj.Sm(i+1) = obj.Sm(i) + (obj.h/6)*(k16 + 2*k26 + 2*k36 + k46);
                obj.Em(i+1) = obj.Em(i) + (obj.h/6)*(k17 + 2*k27 + 2*k37 + k47);
                obj.Im(i+1) = obj.Im(i) + (obj.h/6)*(k18 + 2*k28 + 2*k38 + k48);
                
            end
        end
        
        % evaluate function
        function f = evaluate(obj, u)
            
            % solve numerically system of ODEs using 4th order Runge Kutta method
            obj.Runge_Kutta_4(u);
            
            % calculate objectives
            f = zeros(1, 2);
            switch obj.funType
                case 'Ih-u'
                    f(1) = trapz(obj.t, obj.Ih);
                    f(2) = trapz(obj.t, u);
                case 'Ih2-u2'
                    f(1) = trapz(obj.t, obj.Ih.^2);
                    f(2) = trapz(obj.t, u.^2);
                case 'Ih-u2'
                    f(1) = trapz(obj.t, obj.Ih);
                    f(2) = trapz(obj.t, u.^2);
            end
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