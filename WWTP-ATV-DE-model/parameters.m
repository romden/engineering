%%%%%%%%%%%%%%%
% parameters  %
%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% operational and model parameters  
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

u_H =6;
Y_H =0.666;
K_S =20;
K_OH =0.2;
eta_g =0.8;
K_NO =0.5;
k_h =3;
K_X =0.03;
eta_h =0.4;
f_p =0.08;
b_H =0.62;
b_A =0.04;
u_A =0.8;
K_NH =1;
K_OA =0.4;
Y_A =0.24;
i_XB =0.086;
k_a =0.08;
i_XP =0.06;
T =20;
P_O2 =0.21;
dens =999.96*(2.29e-2*T)-(5.44e-3*T^2);
beta =0.95;
Henry =(708*T)+25700;
SOST =1777.8*beta*dens*P_O2/Henry;
fracO2 =0.21;
alfa =0.8;
eta =0.07;
icv =1.48;
f_BOD =0.66;
IVLD =150;
TSSr_max =1200*1000/IVLD;
TSSr_max_p =(1200/IVLD+2)*1000;
SRT =20;

%%%%%%%%%%%%%%%%%%%%
% effluent quality
%%%%%%%%%%%%%%%%%%%%

beta_TSS =2;
beta_COD =1;
beta_BOD =2;
beta_TKN =20;
beta_NO =20;


%%%%%%%%%%%%
% influent 
%%%%%%%%%%%% 

Qinf =530;
Q_P =54;

%%%%%%%%%%%%%%%%%%%%%%%
% state variables
%%%%%%%%%%%%%%%%%%%%%%%

S_alkinf =7;
X_Iinf =90;
X_IIinf =18.3;
X_Sinf =168.75;
S_Sinf =112.5;
S_I =12.5;
S_Oinf =0;
S_NOinf =0;
S_NHinf =11.7;
S_NDinf =0.63;
X_NDinf =1.251;
X_BHinf =0;
X_BAinf =0;
X_Pinf =0;
TKNinf = S_NHinf + S_NDinf + X_NDinf + i_XB * (X_BHinf + X_BAinf) + i_XP * (X_Pinf + X_Iinf);
Ninf =TKNinf + S_NOinf;
Xinf = X_Iinf + X_Sinf + X_BHinf + X_BAinf + X_Pinf;
Sinf = S_I + S_Sinf;
CODinf = Xinf + Sinf;
VSSinf = Xinf/icv;
TSSinf = VSSinf + X_IIinf;
BODinf = f_BOD * (S_Sinf + X_Sinf + X_BHinf + X_BAinf);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% double exponential parameters
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

v1_0 =274;
v_0 =410;
r_h =0.0004;
f_ns =0.001;
r_P =0.0025;
ST_t =2000;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% quality eters
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

 COD_law =125;
 TSS_law =35;
 N_law =15;
