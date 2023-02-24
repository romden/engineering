function [f, Sh, Eh, Ih, Rh, Am, Sm, Em, Im] = dengue(u)

% read parameters
[T] = parameters();

% initialize control variables and time intervals
n = numel(u); % number of variables
M = n - 1; % number of time intervals
h = T/M; % length of interval

% solve numerically system of ODEs using 4th order Runge Kutta method
[Sh, Eh, Ih, Rh, Am, Sm, Em, Im] = Runge_Kutta_4(u, h, M);

% set of evenly distributed points
t = linspace(0, T, M+1);

% calculate objectives
type = 'Ih-u2';
f = zeros(2, 1);
switch type
    case 'Ih-u'
        f(1) = trapz(t, Ih);
        f(2) = trapz(t, u);
    case 'Ih-u2'
        f(1) = trapz(t, Ih);
        f(2) = trapz(t, u.^2);
end

return


function [T, Nh, B, betamh, betahm, muh, etah, etam, phi, muA, etaA, mum, nuh, m, k] = parameters()

% parameters
T = 84; % period of time
Nh = 480000; % total human population
B = 1; % average daily biting (per day)
betamh = 0.375; % transmission probability from Im (per bite)
betahm = 0.375; % transmission probability from Ih (per bite)
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

return


function [f1, f2, f3, f4, f5, f6, f7, f8] = equations(u, Sh, Eh, Ih, Rh, Am, Sm, Em, Im)

% read parameters
[~, ~, B, betamh, betahm, muh, etah, etam, phi, muA, etaA, mum, nuh, m, k] = parameters();

% calculate functions
f1 = muh - (B*betamh*m*Im + muh)*Sh;
f2 = B*betamh*m*Im*Sh - (nuh + muh)*Eh;
f3 = nuh*Eh - (etah + muh)*Ih;
f4 = etah*Ih - muh*Rh;
f5 = phi*m/k*(1 - Am)*(Sm + Em + Im) - (etaA + muA)*Am;
f6 = etaA*k/m*Am - (B*betahm*Ih + mum)*Sm - u*Sm;
f7 = B*betahm*Ih*Sm - (mum + etam)*Em - u*Em;
f8 = etam*Em - mum*Im - u*Im;

return


function [Sh, Eh, Ih, Rh, Am, Sm, Em, Im] = Runge_Kutta_4(u, h, M)

% read parameters
% [T, Nh, B, betamh, betahm, muh, etah, etam, phi, muA, etaA, mum, nuh, m, k] = parameters();

% initialize state variables
Sh = zeros(1,M+1); % susceptible human
Eh = zeros(1,M+1); % exposed human
Ih = zeros(1,M+1); % infected human
Rh = zeros(1,M+1); % resistant human
Am = zeros(1,M+1); % aquatic phase female mosquitoes
Sm = zeros(1,M+1); % susceptible female mosquitoes
Em = zeros(1,M+1); % exposed female mosquitoes
Im = zeros(1,M+1); % infected female mosquitoes

% inicial conditions (normalized)
Sh(1) = 0.99865;
Eh(1) = 0.00035;
Ih(1) = 0.001;
Rh(1) = 0;
Am(1) = 1;
Sm(1) = 1;
Em(1) = 0;
Im(1) = 0;


% the 4-th order Runge-Kutta method for the system of ODEs
for i = 1:M
    
    % FIRST ORDER PARAMETERS
    
    % control
    u_ = u(i);
    
    % states
    Sh_ = Sh(i);
    Eh_ = Eh(i);
    Ih_ = Ih(i);
    Rh_ = Rh(i);
    Am_ = Am(i);
    Sm_ = Sm(i);
    Em_ = Em(i);
    Im_ = Im(i);
    
    % calculate first order parameters
    [k11, k12, k13, k14, k15, k16, k17, k18] = equations(u_, Sh_, Eh_, Ih_, Rh_, Am_, Sm_, Em_, Im_);
    
    
    % SECOND ORDER PARAMETERS
    
    % control
    u_ = 0.5*(u(i) + u(i+1));
     
    % states
    Sh_ = Sh(i) + 0.5*k11*h;
    Eh_ = Eh(i) + 0.5*k12*h;
    Ih_ = Ih(i) + 0.5*k13*h;
    Rh_ = Rh(i) + 0.5*k14*h;
    Am_ = Am(i) + 0.5*k15*h;
    Sm_ = Sm(i) + 0.5*k16*h;
    Em_ = Em(i) + 0.5*k17*h;
    Im_ = Im(i) + 0.5*k18*h;
    
    % calculate second order parameters
    [k21, k22, k23, k24, k25, k26, k27, k28] = equations(u_, Sh_, Eh_, Ih_, Rh_, Am_, Sm_, Em_, Im_);
    
    
    % THIRD ORDER PARAMETERS
    
    % controls
    u_ = 0.5*(u(i) + u(i+1));
    
    % states
    Sh_ = Sh(i) + 0.5*k21*h;
    Eh_ = Eh(i) + 0.5*k22*h;
    Ih_ = Ih(i) + 0.5*k23*h;
    Rh_ = Rh(i) + 0.5*k24*h;
    Am_ = Am(i) + 0.5*k25*h;
    Sm_ = Sm(i) + 0.5*k26*h;
    Em_ = Em(i) + 0.5*k27*h;
    Im_ = Im(i) + 0.5*k28*h;
    
    % calculate third order parameters
    [k31, k32, k33, k34, k35, k36, k37, k38] = equations(u_, Sh_, Eh_, Ih_, Rh_, Am_, Sm_, Em_, Im_);
    
    
    % FOURS ORDER PARAMETERS
    
    % states
    u_ = u(i+1);
    
    % controls
    Sh_ = Sh(i) + k31*h;
    Eh_ = Eh(i) + k32*h;
    Ih_ = Ih(i) + k33*h;
    Rh_ = Rh(i) + k34*h;
    Am_ = Am(i) + k35*h;
    Sm_ = Sm(i) + k36*h;
    Em_ = Em(i) + k37*h;
    Im_ = Im(i) + k38*h;
    
    % calculate fourth order parameters
    [k41, k42, k43, k44, k45, k46, k47, k48] = equations(u_, Sh_, Eh_, Ih_, Rh_, Am_, Sm_, Em_, Im_);
    
    
    % VALUES OF STATE VARIABLES
    Sh(i+1) = Sh(i) + (h/6)*(k11 + 2*k21 + 2*k31 + k41);
    Eh(i+1) = Eh(i) + (h/6)*(k12 + 2*k22 + 2*k32 + k42);
    Ih(i+1) = Ih(i) + (h/6)*(k13 + 2*k23 + 2*k33 + k43);
    Rh(i+1) = Rh(i) + (h/6)*(k14 + 2*k24 + 2*k34 + k44);
    Am(i+1) = Am(i) + (h/6)*(k15 + 2*k25 + 2*k35 + k45);    
    Sm(i+1) = Sm(i) + (h/6)*(k16 + 2*k26 + 2*k36 + k46);    
    Em(i+1) = Em(i) + (h/6)*(k17 + 2*k27 + 2*k37 + k47);
    Im(i+1) = Im(i) + (h/6)*(k18 + 2*k28 + 2*k38 + k48);
    
end

return



