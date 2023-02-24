function [C, Ceq]=constraints(x)

variables;
parameters;

%%%%% CONSTRAINTS %%%%%

C(1)=Q_P-2*A_s;

%%%%%%%%%%%%%%%%%%%%%%%%%%%
% hydraulic retention time
%%%%%%%%%%%%%%%%%%%%%%%%%%%

Ceq(1)=HRT*Q-V_a;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% aeration tank balances (ASM1 + steady state + CSTR) 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%readily biodegradable substrate – S_S
Ceq(2)=-u_H/Y_H*S_S/(K_S+S_S)*(S_O/(K_OH+S_O)+eta_g*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO))*X_BH+k_h*X_BH/(K_X*X_BH+X_S)*(S_O/(K_OH+S_O)+eta_h*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO))*X_S+Q/V_a*(S_Sent-S_S);

% enmeshed slowly biodegradable substrate – X_S 
Ceq(3)=(1-f_p)*b_H*X_BH+(1-f_p)*b_A*X_BA-k_h*X_BH/(K_X*X_BH+X_S)*(S_O/(K_OH+S_O)+eta_h*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO))*X_S+Q/V_a*(X_Sent-X_S);

% active heterotrophic biomass – X_BH
Ceq(4)=u_H*S_S/(K_S+S_S)*(S_O/(K_OH+S_O)+eta_g*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO))*X_BH-b_H*X_BH+Q/V_a*(X_BHent-X_BH);

% active autotrophic biomass – X_BA
Ceq(5)=u_A*S_NH/(K_NH+S_NH)*S_O/(K_OA+S_O)*X_BA-b_A*X_BA+Q/V_a*(X_BAent-X_BA);

% unbiodegradable particulates from cell decay - X_P
Ceq(6)=f_p*b_H*X_BH+f_p*b_A*X_BA+Q/V_a*(X_Pent-X_P);

% nitrite and nitrate nitrogen – S_NO
Ceq(7)=-(1-Y_H)/(2.86*Y_H)*u_H*S_S/(K_S+S_S)*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO)*eta_g*X_BH+u_A/Y_A*S_NH/(K_NH+S_NH)*S_O/(K_OA+S_O)*X_BA+Q/V_a*(S_NOent-S_NO);

% ammonia nitrogen - S_NH
Ceq(8)=-u_H*S_S/(K_S+S_S)*(S_O/(K_OH+S_O)+eta_g*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO))*i_XB*X_BH-u_A*(i_XB+1/Y_A)*S_NH/(K_NH+S_NH)*S_O/(K_OA+S_O)*X_BA+k_a*S_ND*X_BH+Q/V_a*(S_NHent-S_NH);

% soluble biodegradable organic nitrogen - S_ND
Ceq(9)=-k_a*X_BH*S_ND+k_h*X_BH/(K_X*X_BH+X_S)*(S_O/(K_OH+S_O)+eta_h*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO))*X_ND+Q/V_a*(S_NDent-S_ND);

% particulate biodegradable organic nitrogen - X_ND
Ceq(10)=b_H*(i_XB-f_p*i_XP)*X_BH+b_A*(i_XB-f_p*i_XP)*X_BA-k_h*X_BH/(K_X*X_BH+X_S)*(S_O/(K_OH+S_O)+eta_h*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO))*X_ND+Q/V_a*(X_NDent-X_ND);

% alklinity – S_alk
Ceq(11)=-i_XB/14*u_H*(S_S/(K_S+S_S))*(S_O/(K_OH+S_O))*X_BH-((1-Y_H)/(14*2.86*Y_H)+i_XB/14)*u_H*(S_S/(K_S+S_S))*(K_OH/(K_OH+S_O))*(S_NO/(K_NO+S_NO))*eta_g*X_BH-(i_XB/14+1/(7*Y_A))*u_A*S_NH/(K_NH+S_NH)*S_O/(K_OA+S_O)*X_BA+1/14*k_a*S_ND*X_BH+Q/V_a*(S_alkent-S_alk);

%%%%%%%%%%%%%%%%%%%
%% oxygen balance 
%%%%%%%%%%%%%%%%%%

Ceq(12)=KLa-alfa*G_s*fracO2*eta*1333.3/(V_a*SOST)*1.024^(T-20);

Ceq(13)=Q*S_Oent-Q*S_O+KLa*(SOST-S_O)*V_a+((-(1-Y_H)/Y_H)*u_H*(S_S/(K_S+S_S))*(S_O/(K_OH+S_O))*X_BH-(4.57-Y_A)/Y_A*u_A*(S_NH/(K_NH+S_NH))*(S_O/(K_OA+S_O))*X_BA)*V_a;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% composite variables
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% soluble COD
Ceq(14)=Sent-(S_I+S_Sent);
Ceq(15)=S-(S_I+S_S);

% particulate COD
Ceq(16)=Xent-(X_I+X_Sent+X_BHent+X_BAent+X_Pent);
Ceq(17)=X-(X_I+X_S+X_BH+X_BA+X_P);
Ceq(18)=Xr-(X_Ir+X_Sr+X_BHr+X_BAr+X_Pr);
Ceq(19)=Xef-(X_Ief+X_Sef+X_BHef+X_BAef+X_Pef);

% total COD
Ceq(20)=CODent-(Xent+Sent);
Ceq(21)=COD-(X+S);
Ceq(22)=CODr-(Xr+S);
Ceq(23)=CODef-(Xef+S);

% volatile suspended solids
Ceq(24)=VSSent-Xent*1/icv;
Ceq(25)=VSS-X*1/icv;
Ceq(26)=VSSr-Xr*1/icv;
Ceq(27)=VSSef-Xef*1/icv;

% total suspended solids
Ceq(28)=TSSent-(VSSent+SSI);
Ceq(29)=TSS-(VSS+SSI);
Ceq(30)=TSSr-(VSSr+SSIr);
Ceq(31)=TSSef-(VSSef+SSIef);

% BOD5
Ceq(32)=BODent-f_BOD*(S_Sent+X_Sent+X_BHent+X_BAent);
Ceq(33)=BOD-f_BOD*(S_S+X_S+X_BH+X_BA);
Ceq(34)=BODr-f_BOD*(S_S+X_Sr+X_BHr+X_BAr);
Ceq(35)=BODef-f_BOD*(S_S+X_Sef+X_BHef+X_BAef);

% TKN notrogen
Ceq(36)=TKNent-(S_NHent+S_NDent+X_NDent+i_XB*(X_BHent+X_BAent)+i_XP*(X_Pent+X_I));
Ceq(37)=TKN-(S_NH+S_ND+X_ND+i_XB*(X_BH+X_BA)+i_XP*(X_P+X_I));
Ceq(38)=TKNr-(S_NH+S_ND+X_NDr+i_XB*(X_BHr+X_BAr)+i_XP*(X_Pr+X_Ir));
Ceq(39)=TKNef-(S_NH+S_ND+X_NDef+i_XB*(X_BHef+X_BAef)+i_XP*(X_Pef+X_Ief));

% total nitrogen
Ceq(40)=Nent-(TKNent+S_NOent);
Ceq(41)=N-(TKN+S_NO);
Ceq(42)=Nr-(TKNr+S_NO);
Ceq(43)=Nef-(TKNef+S_NO);

%%%%%%%%%%%%%%
% definitions
%%%%%%%%%%%%%%

Ceq(44)=(Qw*Xr)*SRT-V_a*X;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% suspended matter balances
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

Ceq(45)=(1+r)*Qinf*Xent-(Qinf*Xinf+(1+r)*Qinf*X-V_a*X/(SRT*Xr)*(Xr-Xef)-Qinf*Xef);
Ceq(46)=(1+r)*Qinf*SSI-(Qinf*TSSinf*0.2+(1+r)*Qinf*SSI-V_a*SSI/(SRT*Xr)*(SSIr-SSIef)-Qinf*SSIef);
Ceq(47)=(1+r)*Qinf*X_NDent-(Qinf*X_NDinf+(1+r)*Qinf*X_ND-V_a*X/(SRT*Xr)*(X_NDr-X_NDef)-Qinf*X_NDef);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% dissolved matter balances
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

Ceq(48)=(1+r)*Qinf*S_Sent-(Qinf*S_Sinf+r*Qinf*S_S);
Ceq(49)=(1+r)*Qinf*S_Oent-(Qinf*S_Oinf+r*Qinf*S_O);
Ceq(50)=(1+r)*Qinf*S_NOent-(Qinf*S_NOinf+r*Qinf*S_NO);
Ceq(51)=(1+r)*Qinf*S_NHent-(Qinf*S_NHinf+r*Qinf*S_NH);
Ceq(52)=(1+r)*Qinf*S_NDent-(Qinf*S_NDinf+r*Qinf*S_ND);
Ceq(53)=(1+r)*Qinf*S_alkent-(Qinf*S_alkinf+r*Qinf*S_alk);

%%%%%%%%%%%%%%%%%%%
% flow balances
%%%%%%%%%%%%%%%%%%%

Ceq(54)=r*Qinf-Qr;
Ceq(55)=Q-(Qinf+Qr);
Ceq(56)=Q-(Qef+Qr+Qw);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% secondary settler ATV model
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

Ceq(57)=Q_P-2400*A_s*(0.7*TSS/1000*IVLD)^(-1.34);
Ceq(58)=h3-0.3*TSS/1000*V_a*IVLD/(480*A_s);
Ceq(59)=h4-0.7*TSS/1000*IVLD/1000;
Ceq(60)=r*(TSSr_max-TSS)-TSS;
Ceq(61)=r_p*(TSSr_max_p-0.7*TSS)-0.7*TSS;
Ceq(62)=Qr_p-r_p*Q_P;
Ceq(63)=VSS-0.7*TSS;
Ceq(64)=VSSef-0.7*TSSef;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% secondary settler double exponential model
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

Ceq(65)=v_up*A_s-Qef;
Ceq(66)=v_dn*A_s-(Qr+Qw);

Ceq(67)=v_s(1)-max(0,min(v1_0,v_0*(exp(-r_h*(ST(1)-f_ns*TSS))-exp(-r_P*(ST(1)-f_ns*TSS)))));
Ceq(68)=v_s(2)-max(0,min(v1_0,v_0*(exp(-r_h*(ST(2)-f_ns*TSS))-exp(-r_P*(ST(2)-f_ns*TSS)))));
Ceq(69)=v_s(3)-max(0,min(v1_0,v_0*(exp(-r_h*(ST(3)-f_ns*TSS))-exp(-r_P*(ST(3)-f_ns*TSS)))));
Ceq(70)=v_s(4)-max(0,min(v1_0,v_0*(exp(-r_h*(ST(4)-f_ns*TSS))-exp(-r_P*(ST(4)-f_ns*TSS)))));
Ceq(71)=v_s(5)-max(0,min(v1_0,v_0*(exp(-r_h*(ST(5)-f_ns*TSS))-exp(-r_P*(ST(5)-f_ns*TSS)))));
Ceq(72)=v_s(6)-max(0,min(v1_0,v_0*(exp(-r_h*(ST(6)-f_ns*TSS))-exp(-r_P*(ST(6)-f_ns*TSS)))));
Ceq(73)=v_s(7)-max(0,min(v1_0,v_0*(exp(-r_h*(ST(7)-f_ns*TSS))-exp(-r_P*(ST(7)-f_ns*TSS)))));
Ceq(74)=v_s(8)-max(0,min(v1_0,v_0*(exp(-r_h*(ST(8)-f_ns*TSS))-exp(-r_P*(ST(8)-f_ns*TSS)))));
Ceq(75)=v_s(9)-max(0,min(v1_0,v_0*(exp(-r_h*(ST(9)-f_ns*TSS))-exp(-r_P*(ST(9)-f_ns*TSS)))));
Ceq(76)=v_s(10)-max(0,min(v1_0,v_0*(exp(-r_h*(ST(10)-f_ns*TSS))-exp(-r_P*(ST(10)-f_ns*TSS)))));

for j=1:7
    if ST(j+1)<=ST_t
        Ceq(76+j)=J(j)-v_s(j)*ST(j); 
    else
        Ceq(76+j)=J(j)-min(v_s(j)*ST(j),v_s(j+1)*ST(j+1));
    end
end
for j=7:10
    Ceq(76+j)=J(j)-v_s(j)*ST(j);
end

% feed layer (m=7)

j=7;
Ceq(87)=((Q*TSS)/A_s+J(j-1)-(v_up+v_dn)*ST(j)-min(J(j),J(j+1)))/(h/10);

% intermediate layers below the feed layer (m=8 e m=9)

j=8;
Ceq(88)=(v_dn*(ST(j-1)-ST(j))+min(J(j),J(j-1))-min(J(j),J(j+1)))/(h/10);
j=9;
Ceq(89)=(v_dn*(ST(j-1)-ST(j))+min(J(j),J(j-1))-min(J(j),J(j+1)))/(h/10);

% lower layer (m=10)

j=10;
Ceq(90)=(v_dn*(ST(j-1)-ST(j))+min(J(j-1),J(j)))/(h/10);

% intermediate layers above the feed layer (m=2 a 6)

for j=2:6
 Ceq(89+j)=(v_up*(ST(j+1)-ST(j))+J(j-1)-J(j))/(h/10);   
end

% upper layer (m=1)

j=1;
Ceq(96)=(v_up*(ST(j+1)-ST(j))-J(j))/(h/10);

Ceq(97)=ST(1)-TSSef;

Ceq(98)=ST(10)-TSSr;

Ceq(99)=h-(h3+h4+1);


