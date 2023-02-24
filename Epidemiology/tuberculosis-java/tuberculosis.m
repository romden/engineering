function [f] = tuberculosis(x, type)

% read parameters
[beta, mu, delta, phi, omega, omegaR, sigma, sigmaR, tau0, tau1, tau2, epsilon1, epsilon2, N, T] = parameters();

% initialize control variables and time intervals
n = numel(x); % number of variables
u1 = x(1:n/2); % first control
u2 = x(n/2+1:end); % second control
M = n/2 - 1; % number of time intervals
h = T/M; % length of interval

% solve numerically system of ODEs using 4th order Runge Kutta method
[S, L1, I, L2, R] = Runge_Kutta_4(u1, u2, h, M);

% set of evenly distributed points
t = linspace(0, T, M+1);

% calculate objectives
switch type
    case 'IL2-u1u2'
        f = zeros(2, 1);
        f(1) = trapz(t, I+L2);
        f(2) = trapz(t, u1.^2+u2.^2);
    case 'I-L2-u1u2'
        f = zeros(3, 1);
        f(1) = trapz(t, I);
        f(2) = trapz(t, L2);
        f(3) = trapz(t, u1+u2);
    case 'IL2-u1-u2'
        f = zeros(3, 1);
        f(1) = trapz(t, I+L2);
        f(2) = trapz(t, u1);
        f(3) = trapz(t, u2);
    case 'I-L2-u1-u2'
        f = zeros(4, 1);
        f(1) = trapz(t, I);
        f(2) = trapz(t, L2);
        f(3) = trapz(t, u1);
        f(4) = trapz(t, u2);
end

return


function [beta, mu, delta, phi, omega, omegaR, sigma, sigmaR, tau0, tau1, tau2, epsilon1, epsilon2, N, T] = parameters()

% parameters
beta = 75; %(100,150,175) % Transmission coefficient
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
epsilon1 = 0.25; % (0.5, 0.75) % Efficacy of treatment of active TB I
epsilon2 = 0.25; % (0.5, 0.75) % Efficacy of treatment of active TB L2
N = 30000; %(40000,60000) % Total population
T = 5; % Total simulation duration (yr)

return


function [f1, f2, f3, f4, f5] = equations(u1, u2, S, L1, I, L2, R)

% read parameters
[beta, mu, delta, phi, omega, omegaR, sigma, sigmaR, tau0, tau1, tau2, epsilon1, epsilon2, N, T] = parameters();

% calculate functions
f1 = mu*N - beta/N*I*S - mu*S;
f2 = beta/N*I*(S + sigma*L2 + sigmaR*R) - (delta + tau1 + mu)*L1;
f3 = phi*delta*L1 + omega*L2 + omegaR*R - (tau0 + epsilon1*u1 + mu)*I;
f4 = (1 - phi)*delta*L1 - sigma*beta/N*I*L2 - (omega + epsilon2*u2 + tau2 + mu)*L2;
f5 = (tau0 + epsilon1*u1)*I + tau1*L1 + (tau2 + epsilon2*u2)*L2 - sigmaR*beta/N*I*R - (omegaR + mu)*R;

return


function [S, L1, I, L2, R] = Runge_Kutta_4(u1, u2, h, M)

% read parameters
[beta, mu, delta, phi, omega, omegaR, sigma, sigmaR, tau0, tau1, tau2, epsilon1, epsilon2, N, T] = parameters();

% initialize state variables
S = zeros(1,M+1); % susceptible individuals
L1 = zeros(1,M+1); % latent individuals
I = zeros(1,M+1); % infectious individuals
L2 = zeros(1,M+1); % persistent latent individuals
R = zeros(1,M+1); % recovered individuals

% inicial conditions
S(1) = 76/120*N; % Initial number of susceptible individuals
L1(1) = 37/120*N; % Initial number of early latent L1 individuals
I(1) = 4/120*N; % Initial number of infectious individuals
L2(1) = 2/120*N; % Initial number of persistent latent L2 individuals
R(1) = 1/120*N; % Initial number of recovered individuals


% the 4-th order Runge-Kutta method for the system of ODEs
for i = 1:M
    
    % FIRST ORDER PARAMETERS
    
    % controls
    u1_ = u1(i);
    u2_ = u2(i);
    
    % states
    S_ = S(i);
    L1_ = L1(i);
    I_ = I(i);
    L2_ = L2(i);
    R_ = R(i);
    
    % calculate first order parameters
    [k11, k12, k13, k14, k15] = equations(u1_, u2_, S_, L1_, I_, L2_, R_);
    
    
    % SECOND ORDER PARAMETERS
    
    % controls
    u1_ = 0.5*(u1(i) + u1(i+1));
    u2_ = 0.5*(u2(i) + u2(i+1));
    
    % states
    S_ = S(i) + 0.5*k11*h;
    L1_ = L1(i) + 0.5*k12*h;
    I_ = I(i) + 0.5*k13*h;
    L2_ = L2(i) + 0.5*k14*h;
    R_ = R(i) + 0.5*k15*h;
    
    % calculate second order parameters
    [k21, k22, k23, k24, k25] = equations(u1_, u2_, S_, L1_, I_, L2_, R_);
    
    
    % THIRD ORDER PARAMETERS
    
    % controls
    u1_ = 0.5*(u1(i) + u1(i+1));
    u2_ = 0.5*(u2(i) + u2(i+1));
    
    % states
    S_ = S(i) + 0.5*k21*h;
    L1_ = L1(i) + 0.5*k22*h;
    I_ = I(i) + 0.5*k23*h;
    L2_ = L2(i) + 0.5*k24*h;
    R_ = R(i) + 0.5*k25*h;
    
    % calculate third order parameters
    [k31, k32, k33, k34, k35] = equations(u1_, u2_, S_, L1_, I_, L2_, R_);
    
    
    % FOURS ORDER PARAMETERS
    
    % states
    u1_ = u1(i+1);
    u2_ = u2(i+1);
    
    % controls
    S_ = S(i) + k31*h;
    L1_ = L1(i) + k32*h;
    I_ = I(i) + k33*h;
    L2_ = L2(i) + k34*h;
    R_ = R(i) + k35*h;
    
    % calculate fourth order parameters
    [k41, k42, k43, k44, k45] = equations(u1_, u2_, S_, L1_, I_, L2_, R_);
    
    
    % VALUES OF STATE VARIABLES
    S(i+1) = S(i) + (h/6)*(k11 + 2*k21 + 2*k31 + k41);
    L1(i+1) = L1(i) + (h/6)*(k12 + 2*k22 + 2*k32 + k42);
    I(i+1) = I(i) + (h/6)*(k13 + 2*k23 + 2*k33 + k43);
    L2(i+1) = L2(i) + (h/6)*(k14 + 2*k24 + 2*k34 + k44);
    R(i+1) = R(i) + (h/6)*(k15 + 2*k25 + 2*k35 + k45);
    
end

return

