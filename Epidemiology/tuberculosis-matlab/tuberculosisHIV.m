function [f, S, L_T, I_T, R, I_H, A, C_H, L_TH, I_TH, R_H, A_T] = tuberculosisHIV(x)

global beta_1 beta_2 mu d_N gamma_1 gamma_2 eta_C eta_A k_1 k_2 tau_1 tau_2 k_3 delta phi psi rho_1 rho_2 alpha_1 alpha_2 d_T d_A d_TA r omega_1 omega_2 

% initialize parameters
T = 50; % considered time in years  (T = 10, when d_T=d_A=d_TA=10 no death induced)
N0 = 30000; % initial population size

gamma_1 = 0.9; % Modification parameter
gamma_2 = 1.1; % Modification parameter
eta_C = 0.9; % Modification parameter
eta_A = 1.05; % Modification parameter
delta = 1.03; % Modification parameter
psi = 1.07; % Modification parameter

beta_1 = 0.6; % TB transmission rate
beta_2 = 0.1; % HIV transmission rate

mu = 430.0; % Recruitment rate
k_1 = 1.0/2.0; % Rate at which individuals leave L_T class by becoming infectious
k_2 = 1.3*k_1; % Rate at which individuals leave L_TH class by becoming TB infectious
k_3 = 2.0; % Rate at which individuals leave L_TH class
rho_1 = 0.1; % Rate at which individuals leave I_H class to A
rho_2 = 1.0; % Rate at which individuals leave I_TH class
omega_1 = 0.09; % Rate at which individuals leave C_H class
omega_2 = 0.15; % Rate at which individuals leave R_H class

tau_1 = 2.0; % TB treatment rate for L_T individuals
tau_2 = 1.0; % TB treatment rate for I_T individuals
phi = 1.0; % HIV treatment rate for I_H individuals
alpha_1 = 0.33; % AIDS treatment rate
alpha_2 = 0.33; % HIV treatment rate for A_T individuals
r = 0.3; % Fraction of L_TH individuals that take HIV and TB treatment

d_N = 1.0/70.0; % Natural death rate
d_T = 1.0/10.0; % TB induced death rate
d_A = 0.3; % AIDS induced death rate
d_TA = 0.33; % AIDS-TB induced death rate

n = numel(x); % number of variables
M = n/2 - 1; % number of time intervals
h = T/M; % length of interval

% initialize controls
u1 = x(1:n/2); % first control
u2 = x(n/2+1:end); % second control


% SIOLVE NUMERICALLY SYSTEM OF ODEs USING 4th ORDER RUNGE KUTTA METHOD

% initialize state variables
S = zeros(1,M+1); % susceptible individuals
L_T = zeros(1,M+1); % TB-latently infected individuals who have no symptoms of TB disease and are not infectious
I_T = zeros(1,M+1); % TB-infected individuals, who have active TB disease and are infectious
R = zeros(1,M+1); % TB-recovered individuals
I_H = zeros(1,M+1); % HIV-infected individuals with no clinical symptoms of AIDS
A = zeros(1,M+1); % HIV-infected individuals with AIDS clinical symptoms 
C_H = zeros(1,M+1); % HIV-infected individuals under treatment for HIV infection
L_TH = zeros(1,M+1); % TB-latent individuals co-infected with HIV (pre-AIDS)
I_TH = zeros(1,M+1); % HIV-infected individuals (pre-AIDS) co-infected with active TB disease
R_H = zeros(1,M+1); % TB-recovered individuals with HIV-infection without AIDS symptoms
A_T = zeros(1,M+1); % HIV-infected individuals with AIDS symptoms co-infected with active    

% inicial conditions
S(1) = 66*N0/120;
L_T(1) = 37*N0/120;
I_T(1) = 5*N0/120;
R(1) = 2*N0/120;
I_H(1) = 2*N0/120;
A(1) = N0/120;
C_H(1) = N0/120;
L_TH(1) = 2*N0/120;
I_TH(1) = 2*N0/120;
R_H(1) = N0/120;
A_T(1) = N0/120;


% the 4-th order Runge-Kutta method for the system of ODEs
for i = 1:M
    
    % FIRST ORDER PARAMETERS
    
    % controls
    u1_ = u1(i);
    u2_ = u2(i);
    
    % states
    S_ = S(i);
    L_T_ = L_T(i);
    I_T_ = I_T(i);
    R_ = R(i);
    I_H_ = I_H(i);
    A_ = A(i);
    C_H_ = C_H(i);
    L_TH_ = L_TH(i);
    I_TH_ = I_TH(i);
    R_H_ = R_H(i);
    A_T_ = A_T(i);
    
    % calculate first order parameters
    [eq1_1, eq2_1, eq3_1, eq4_1, eq5_1, eq6_1, eq7_1, eq8_1, eq9_1, eq10_1, eq11_1] = equations(u1_, u2_, S_, L_T_, I_T_, R_, I_H_, A_, C_H_, L_TH_, I_TH_, R_H_, A_T_);
    
    
    % SECOND ORDER PARAMETERS
    
    % controls
    u1_ = 0.5*(u1(i) + u1(i+1));
    u2_ = 0.5*(u2(i) + u2(i+1));
    
    % states
    S_ = S(i) + 0.5*eq1_1*h;
    L_T_ = L_T(i) + 0.5*eq2_1*h;
    I_T_ = I_T(i) + 0.5*eq3_1*h;
    R_ = R(i) + 0.5*eq4_1*h;
    I_H_ = I_H(i) + 0.5*eq5_1*h;
    A_ = A(i) + 0.5*eq6_1*h;
    C_H_ = C_H(i) + 0.5*eq7_1*h;
    L_TH_ = L_TH(i) + 0.5*eq8_1*h;
    I_TH_ = I_TH(i) + 0.5*eq9_1*h;
    R_H_ = R_H(i) + 0.5*eq10_1*h;
    A_T_ = A_T(i) + 0.5*eq11_1*h;
    
    % calculate second order parameters
    [eq1_2, eq2_2, eq3_2, eq4_2, eq5_2, eq6_2, eq7_2, eq8_2, eq9_2, eq10_2, eq11_2] = equations(u1_, u2_, S_, L_T_, I_T_, R_, I_H_, A_, C_H_, L_TH_, I_TH_, R_H_, A_T_);
    
    
    % THIRD ORDER PARAMETERS
    
    % controls
    u1_ = 0.5*(u1(i) + u1(i+1));
    u2_ = 0.5*(u2(i) + u2(i+1));
    
    % states
    S_ = S(i) + 0.5*eq1_2*h;
    L_T_ = L_T(i) + 0.5*eq2_2*h;
    I_T_ = I_T(i) + 0.5*eq3_2*h;
    R_ = R(i) + 0.5*eq4_2*h;
    I_H_ = I_H(i) + 0.5*eq5_2*h;
    A_ = A(i) + 0.5*eq6_2*h;
    C_H_ = C_H(i) + 0.5*eq7_2*h;
    L_TH_ = L_TH(i) + 0.5*eq8_2*h;
    I_TH_ = I_TH(i) + 0.5*eq9_2*h;
    R_H_ = R_H(i) + 0.5*eq10_2*h;
    A_T_ = A_T(i) + 0.5*eq11_2*h;
    
    % calculate third order parameters
    [eq1_3, eq2_3, eq3_3, eq4_3, eq5_3, eq6_3, eq7_3, eq8_3, eq9_3, eq10_3, eq11_3] = equations(u1_, u2_, S_, L_T_, I_T_, R_, I_H_, A_, C_H_, L_TH_, I_TH_, R_H_, A_T_);
    
    
    % FOURS ORDER PARAMETERS
    
    % controls
    u1_ = u1(i+1);
    u2_ = u2(i+1);
    
    % states
    S_ = S(i) + eq1_3*h;
    L_T_ = L_T(i) + eq2_3*h;
    I_T_ = I_T(i) + eq3_3*h;
    R_ = R(i) + eq4_3*h;
    I_H_ = I_H(i) + eq5_3*h;
    A_ = A(i) + eq6_3*h;
    C_H_ = C_H(i) + eq7_3*h;
    L_TH_ = L_TH(i) + eq8_3*h;
    I_TH_ = I_TH(i) + eq9_3*h;
    R_H_ = R_H(i) + eq10_3*h;
    A_T_ = A_T(i) + eq11_3*h;
    
    % calculate fourth order parameters
    [eq1_4, eq2_4, eq3_4, eq4_4, eq5_4, eq6_4, eq7_4, eq8_4, eq9_4, eq10_4, eq11_4] = equations(u1_, u2_, S_, L_T_, I_T_, R_, I_H_, A_, C_H_, L_TH_, I_TH_, R_H_, A_T_);
    
    
    % VALUES OF STATE VARIABLES
    S(i+1) = S(i) + (h/6)*(eq1_1 + 2*eq1_2 + 2*eq1_3 + eq1_4);
    L_T(i+1) = L_T(i) + (h/6)*(eq2_1 + 2*eq2_2 + 2*eq2_3 + eq2_4);
    I_T(i+1) = I_T(i) + (h/6)*(eq3_1 + 2*eq3_2 + 2*eq3_3 + eq3_4);
    R(i+1) = R(i) + (h/6)*(eq4_1 + 2*eq4_2 + 2*eq4_3 + eq4_4);
    I_H(i+1) = I_H(i) + (h/6)*(eq5_1 + 2*eq5_2 + 2*eq5_3 + eq5_4);
    A(i+1) = A(i) + (h/6)*(eq6_1 + 2*eq6_2 + 2*eq6_3 + eq6_4);
    C_H(i+1) = C_H(i) + (h/6)*(eq7_1 + 2*eq7_2 + 2*eq7_3 + eq7_4);
    L_TH(i+1) = L_TH(i) + (h/6)*(eq8_1 + 2*eq8_2 + 2*eq8_3 + eq8_4);
    I_TH(i+1) = I_TH(i) + (h/6)*(eq9_1 + 2*eq9_2 + 2*eq9_3 + eq9_4);
    R_H(i+1) = R_H(i) + (h/6)*(eq10_1 + 2*eq10_2 + 2*eq10_3 + eq10_4);
    A_T(i+1) = A_T(i) + (h/6)*(eq11_1 + 2*eq11_2 + 2*eq11_3 + eq11_4);
    
end


% calculate objectives
f = [trapz(A+A_T) trapz(u1.^2+u2.^2)];


return


function [eq1, eq2, eq3, eq4, eq5, eq6, eq7, eq8, eq9, eq10, eq11] = equations(u_1, u_2, S, L_T, I_T, R, I_H, A, C_H, L_TH, I_TH, R_H, A_T)

global beta_1 beta_2 mu d_N gamma_1 gamma_2 eta_C eta_A k_1 k_2 tau_1 tau_2 k_3 delta phi psi rho_1 rho_2 alpha_1 alpha_2 d_T d_A d_TA r omega_1 omega_2 

% total population at time t
N = S + L_T + I_T + R + I_H + A + C_H + L_TH + I_TH + R_H + A_T;

lambda_T = beta_1*(I_T + I_TH + A_T)/N;
lambda_H = beta_2*(I_H + I_TH + L_TH + R_H + eta_C*C_H + eta_A*(A + A_T))/N;

% calculate functions
eq1 = mu - lambda_T*S - lambda_H*S - d_N*S; % S(t)
eq2 = lambda_T*S + gamma_1*lambda_T*R - (k_1 + tau_1 + d_N)*L_T; % L_T(t)
eq3 = k_1*L_T - (tau_2 + d_T + d_N + delta*lambda_H)*I_T; % I_T(t)
eq4 = tau_1*L_T + tau_2*I_T - (gamma_1*lambda_T + lambda_H + d_N)*R; % R(t)
eq5 = lambda_H*S - (rho_1 + phi + psi*lambda_T + d_N)*I_H + alpha_1*A + lambda_H*R + omega_1*C_H; % I_H
eq6 = rho_1*I_H + omega_2*R_H - alpha_1*A - (d_N + d_A)*A; % A(t)
eq7 = phi*I_H + u_1*rho_2*I_TH + r*k_3*L_TH - (omega_1 + d_N)*C_H; % C_H(t)
eq8 = gamma_2*lambda_T*R_H - (k_2 + k_3 + d_N)*L_TH; % L_TH(t)
eq9 = delta*lambda_H*I_T + psi*lambda_T*I_H + alpha_2*A_T + k_2*L_TH - (rho_2 + d_N + d_T)*I_TH; % I_TH(t)
eq10 = u_2*rho_2*I_TH + (1 - r)*k_3*L_TH - (gamma_2*lambda_T + omega_2 + d_N)*R_H; % R_H(t)
eq11 = (1 - (u_1 + u_2))*rho_2*I_TH - (alpha_2 + d_N + d_TA)*A_T; % A_T(t)

return


