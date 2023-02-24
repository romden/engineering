function [f] = dengue(u)

T = 84; % period of time
M = numel(u)-1; % number of intervals
h = T/M; % interval
h2 = h/2; % half of interval

% constant parameters
Nh=480000; % total human population
B=1; % average daily biting (per day)
betamh=0.375; % transmission probability from Im (per bite)
betahm=0.375; % transmission probability from Ih (per bite)
muh=1/(71*365); % average lifespan of humans (in days)
etah=1/3; % mean viremic period (in days)
etam=1/11; % average lifespan of adult mosquitoes (in days)
phi=6; % number of eggs at each deposit per capita (per day)
muA=1/4; % natural mortality of larvae (per day)
etaA=0.08; % maturation rate from larvae to adult (per day)
mum=1/11; % extrinsic incubation period (in days)
nuh=1/4; % intrinsic incubation period (in days)
m=6; % female mosquitoes per human
k=3; % number of larvae per human

% initialize state variables
Sh=zeros(1,M+1); % susceptible human
Eh=zeros(1,M+1); % exposed human
Ih=zeros(1,M+1); % infected human
Rh=zeros(1,M+1); % resistant human
Am=zeros(1,M+1); % aquatic phase female mosquitoes
Sm=zeros(1,M+1); % susceptible female mosquitoes
Em=zeros(1,M+1); % exposed female mosquitoes
Im=zeros(1,M+1); % infected female mosquitoes

% inicial conditions
% Sh(1)=479350;
% Eh(1)=216;
% Ih(1)=434;
% Rh(1)=0;
% Am(1)=k*Nh;
% Sm(1)=m*Nh;
% Em(1)=0;
% Im(1)=0;
% normalized
Sh(1)=0.99865;
Eh(1)=0.00035;
Ih(1)=0.001;
Rh(1)=0;
Am(1)=1;
Sm(1)=1;
Em(1)=0;
Im(1)=0;

% system of differential equations
for i = 1:M
    
    % first order parameters
    m11 = muh-(B*betamh*Im(i)*m+muh)*Sh(i);
    m12 = B*betamh*Im(i)*m*Sh(i)-(nuh+muh)*Eh(i);
    m13 = nuh*Eh(i)-(etah+muh)*Ih(i);
    m14 = etah*Ih(i)-muh*Rh(i);
    m15 = phi*(1-Am(i))*(Sm(i)+Em(i)+Im(i))*m/k-(etaA+muA)*Am(i);
    m16 = etaA*Am(i)*k/m-(B*betahm*Ih(i)+mum+u(i))*Sm(i);
    m17 = B*betahm*Ih(i)*Sm(i)-(mum+etam+u(i))*Em(i);
    m18 = etam*Em(i)-(mum+u(i))*Im(i);
    
    % second order parameters
    m21 = muh-(B*betamh*(Im(i)+h2*m18)*m+muh)*(Sh(i)+h2*m11);
    m22 = B*betamh*(Im(i)+h2*m18)*m*(Sh(i)+h2*m11)-(nuh+muh)*(Eh(i)+h2*m12);
    m23 = nuh*(Eh(i)+h2*m12)-(etah+muh)*(Ih(i)+h2*m13);
    m24 = etah*(Ih(i)+h2*m13)-muh*(Rh(i)+h2*m14);
    m25 = phi*(1-(Am(i)+h2*m15))*((Sm(i)+h2*m16)+(Em(i)+h2*m17)+(Im(i)+h2*m18))*m/k-(etaA+muA)*(Am(i)+h2*m15);
    m26 = etaA*(Am(i)+h2*m15)*k/m-(B*betahm*(Ih(i)+h2*m13)+mum+0.5*(u(i)+u(i+1)))*(Sm(i)+h2*m16);
    m27 = B*betahm*(Ih(i)+h2*m13)*(Sm(i)+h2*m16)-(mum+etam+0.5*(u(i)+u(i+1)))*(Em(i)+h2*m17);
    m28 = etam*(Em(i)+h2*m17)-(mum+0.5*(u(i)+u(i+1)))*(Im(i)+h2*m18);
    
    % third order parameters
    m31 = muh-(B*betamh*(Im(i)+h2*m28)*m+muh)*(Sh(i)+h2*m21);
    m32 = B*betamh*(Im(i)+h2*m28)*m*(Sh(i)+h2*m21)-(nuh+muh)*(Eh(i)+h2*m22);
    m33 = nuh*(Eh(i)+h2*m22)-(etah+muh)*(Ih(i)+h2*m23);
    m34 = etah*(Ih(i)+h2*m23)-muh*(Rh(i)+h2*m24);
    m35 = phi*(1-(Am(i)+h2*m25))*((Sm(i)+h2*m26)+(Em(i)+h2*m27)+(Im(i)+h2*m28))*m/k-(etaA+muA)*(Am(i)+h2*m25);
    m36 = etaA*(Am(i)+h2*m25)*k/m-(B*betahm*(Ih(i)+h2*m23)+mum+0.5*(u(i)+u(i+1)))*(Sm(i)+h2*m26);
    m37 = B*betahm*(Ih(i)+h2*m23)*(Sm(i)+h2*m26)-(mum+etam+0.5*(u(i)+u(i+1)))*(Em(i)+h2*m27);
    m38 = etam*(Em(i)+h2*m27)-(mum+0.5*(u(i)+u(i+1)))*(Im(i)+h2*m28);
    
    % fourth order parameters
    m41 = muh-(B*betamh*(Im(i)+h2*m38)*m+muh)*(Sh(i)+h2*m31);
    m42 = B*betamh*(Im(i)+h2*m38)*m*(Sh(i)+h2*m31)-(nuh+muh)*(Eh(i)+h2*m32);
    m43 = nuh*(Eh(i)+h2*m32)-(etah+muh)*(Ih(i)+h2*m33);
    m44 = etah*(Ih(i)+h2*m33)-muh*(Rh(i)+h2*m34);
    m45 = phi*(1-(Am(i)+h2*m35))*((Sm(i)+h2*m36)+(Em(i)+h2*m37)+(Im(i)+h2*m38))*m/k-(etaA+muA)*(Am(i)+h2*m35);
    m46 = etaA*(Am(i)+h2*m35)*k/m-(B*betahm*(Ih(i)+h2*m33)+mum+u(i+1))*(Sm(i)+h2*m36);
    m47 = B*betahm*(Ih(i)+h2*m33)*(Sm(i)+h2*m36)-(mum+etam+u(i+1))*(Em(i)+h2*m37);
    m48 = etam*(Em(i)+h2*m37)-(mum+u(i+1))*(Im(i)+h2*m38);
    
    % values of state variables
    Sh(i+1) = Sh(i) + (h/6)*(m11 + 2*m21 + 2*m31 + m41);
    Eh(i+1) = Eh(i) + (h/6)*(m12 + 2*m22 + 2*m32 + m42);
    Ih(i+1) = Ih(i) + (h/6)*(m13 + 2*m23 + 2*m33 + m43);
    Rh(i+1) = Rh(i) + (h/6)*(m14 + 2*m24 + 2*m34 + m44);
    Am(i+1) = Am(i) + (h/6)*(m15 + 2*m25 + 2*m35 + m45);
    Sm(i+1) = Sm(i) + (h/6)*(m16 + 2*m26 + 2*m36 + m46);
    Em(i+1) = Em(i) + (h/6)*(m17 + 2*m27 + 2*m37 + m47);
    Im(i+1) = Im(i) + (h/6)*(m18 + 2*m28 + 2*m38 + m48);
    
end

% objective function
f=zeros(1,2);

% set of evenly distributed points
t=linspace(0,T,M+1);

% total of infected human
f(1)=trapz(t,Ih);
% f(1)=trapz(t,Im);

% total control
f(2)=trapz(t,u);

return


