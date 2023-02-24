


public class WWTP
{
    
    
// PARAMETERS
    
    
//////////////////////////////////////////////////////////////////////
// operational and model parameters
//////////////////////////////////////////////////////////////////////
    
    double u_H = 6.0;
    double Y_H = 0.666;
    double K_S = 20.0;
    double K_OH = 0.2;
    double eta_g = 0.8;
    double K_NO = 0.5;
    double k_h = 3.0;
    double K_X = 0.03;
    double eta_h = 0.4;
    double f_p = 0.08;
    double b_H = 0.62;
    double b_A = 0.04;
    double u_A = 0.8;
    double K_NH = 1.0;
    double K_OA = 0.4;
    double Y_A = 0.24;
    double i_XB = 0.086;
    double k_a = 0.08;
    double i_XP = 0.06;
    double T = 20.0;
    double P_O2 = 0.21;
    double dens = 999.96*(2.29e-2*T)-(5.44e-3*Math.pow(T, 2));
    double beta = 0.95;
    double Henry = (708.0*T)+25700.0;
    double SOST =1777.8*beta*dens*P_O2/Henry;
    double fracO2 = 0.21;
    double alfa = 0.8;
    double eta = 0.07;
    double icv = 1.48;
    double f_BOD = 0.66;
    double IVLD = 150.0;
    double TSSr_max = 1200.0*1000.0/IVLD;
    double TSSr_max_p = (1200.0/IVLD+2.0)*1000.0;
    double SRT = 20.0;
    
////////////////////////////////////////
// effluent quality
////////////////////////////////////////
    
    double beta_TSS = 2.0;
    double beta_COD = 1.0;
    double beta_BOD = 2.0;
    double beta_TKN = 20.0;
    double beta_NO = 20.0;
    
    
////////////////////////
// influent
////////////////////////
    
    double Qinf = 530.0;
    double Q_P = 54.0;
    
//////////////////////////////////////////////
// state variables
//////////////////////////////////////////////
    
    double S_alkinf = 7.0;
    double X_Iinf = 90.0;
    double X_IIinf = 18.3;
    double X_Sinf = 168.75;
    double S_Sinf = 112.5;
    double S_I = 12.5;
    double S_Oinf = 0.0;
    double S_NOinf = 0.0;
    double S_NHinf = 11.7;
    double S_NDinf = 0.63;
    double X_NDinf = 1.251;
    double X_BHinf = 0.0;
    double X_BAinf = 0.0;
    double X_Pinf = 0.0;
    double TKNinf = S_NHinf + S_NDinf + X_NDinf + i_XB * (X_BHinf + X_BAinf) + i_XP * (X_Pinf + X_Iinf);
    double Ninf = TKNinf + S_NOinf;
    double Xinf = X_Iinf + X_Sinf + X_BHinf + X_BAinf + X_Pinf;
    double Sinf = S_I + S_Sinf;
    double CODinf = Xinf + Sinf;
    double VSSinf = Xinf/icv;
    double TSSinf = VSSinf + X_IIinf;
    double BODinf = f_BOD * (S_Sinf + X_Sinf + X_BHinf + X_BAinf);
    
//////////////////////////////////////////////////////////////////
// double exponential parameters
//////////////////////////////////////////////////////////////////
    
    double v1_0 =274.0;
    double v_0 =410.0;
    double r_h =0.0004;
    double f_ns =0.001;
    double r_P =0.0025;
    double ST_t =2000.0;
    
//////////////////////////////////////////////////////////////////
// quality eters
//////////////////////////////////////////////////////////////////
    
    double COD_law = 125.0;
    double TSS_law = 35.0;
    double N_law = 15.0;
    
    
    
    
    // VARIABLES
    
    
    double Q;
    double Qw;
    double Qr;
    double Qef;
    double Qr_p;
    double V_a; // Inteira
    double A_s; // Inteira
    double h3;
    double h4;
    double r_p;
    double X_I;
    double X_Ir;
    double X_Ief;
    double S_Sent;
    double S_S;
    double S_Oent;
    double S_NOent;
    double S_NO;
    double X_BHent;
    double X_BH;
    double X_BHr;
    double X_BHef;
    double X_Sent;
    double X_S;
    double X_Sr;
    double X_Sef;
    double X_BAent;
    double X_BA;
    double X_BAr;
    double X_BAef;
    double S_NHent;
    double S_NH;
    double X_Pent;
    double X_P;
    double X_Pr;
    double X_Pef;
    double S_NDent;
    double S_ND;
    double X_NDent;
    double X_ND;
    double X_NDr;
    double X_NDef;
    double G_s;
    double S_alkent;
    double S_alk;
    double SSI;
    double SSIef;
    double SSIr;
    double [] ST = new double[10];
    double v_dn;
    double v_up;
    double [] v_s = new double[10];
    double [] J = new double[10];
    double HRT;
    double KLa;
    double r;
    double Sent;
    double S;
    double Xent;
    double X;
    double Xr;
    double Xef;
    double CODent;
    double COD;
    double CODr;
    double CODef;
    double VSSent;
    double VSS;
    double VSSr;
    double VSSef;
    double TSSent;
    double TSS;
    double TSSr;
    double TSSef;
    double BODent;
    double BOD;
    double BODr;
    double BODef;
    double TKNent;
    double TKN;
    double TKNr;
    double TKNef;
    double Nent;
    double N;
    double Nr;
    double Nef;
    double h;
    double S_O;
    
    
    public WWTP()
    {
        
    }
    
    
    
    private void variables(double [] x)
    {
        
        // VARIABLES
        
        
        Q	= 	x[1-1];
        Qw	= 	x[2-1];
        Qr	=	x[3-1];
        Qef	=	x[4-1];
        Qr_p	=	x[5-1];
        V_a	=	x[6-1]; // Inteira
        A_s	=	x[7-1]; // Inteira
        h3	=	x[8-1];
        h4	=	x[9-1];
        r_p	=	x[10-1];
        X_I	=	x[11-1];
        X_Ir	=	x[12-1];
        X_Ief	=	x[13-1];
        S_Sent	=	x[14-1];
        S_S	=	x[15-1];
        S_Oent	=	x[16-1];
        S_NOent	=	x[17-1];
        S_NO	=	x[18-1];
        X_BHent	=	x[19-1];
        X_BH	=	x[20-1];
        X_BHr	=	x[21-1];
        X_BHef	=	x[22-1];
        X_Sent	=	x[23-1];
        X_S	=	x[24-1];
        X_Sr	=	x[25-1];
        X_Sef	=	x[26-1];
        X_BAent	=	x[27-1];
        X_BA	=	x[28-1];
        X_BAr	=	x[29-1];
        X_BAef	=	x[30-1];
        S_NHent	=	x[31-1];
        S_NH	=	x[32-1];
        X_Pent	=	x[33-1];
        X_P	=	x[34-1];
        X_Pr	=	x[35-1];
        X_Pef	=	x[36-1];
        S_NDent	=	x[37-1];
        S_ND	=	x[38-1];
        X_NDent	=	x[39-1];
        X_ND	=	x[40-1];
        X_NDr	=	x[41-1];
        X_NDef	=	x[42-1];
        G_s	=	x[43-1];
        S_alkent	=	x[44-1];
        S_alk	=	x[45-1];
        SSI	=	x[46-1];
        SSIef	=	x[47-1];
        SSIr	=	x[48-1];
        ST[0]	=	x[49-1];
        ST[1]	=	x[50-1];
        ST[2]	=	x[51-1];
        ST[3]	=	x[52-1];
        ST[4]	=	x[53-1];
        ST[5]	=	x[54-1];
        ST[6]	=	x[55-1];
        ST[7]	=	x[56-1];
        ST[8]	=	x[57-1];
        ST[9]	=	x[58-1];
        v_dn	=	x[59-1];
        v_up	=	x[60-1];
        v_s[0]	=	x[61-1];
        v_s[1]	=	x[62-1];
        v_s[2]	=	x[63-1];
        v_s[3]	=	x[64-1];
        v_s[4]	=	x[65-1];
        v_s[5]	=	x[66-1];
        v_s[6]	=	x[67-1];
        v_s[7]	=	x[68-1];
        v_s[8]	=	x[69-1];
        v_s[9]	=	x[70-1];
        J[0]	=	x[71-1];
        J[1]	=	x[72-1];
        J[2]	=	x[73-1];
        J[3]	=	x[74-1];
        J[4]	=	x[75-1];
        J[5]	=	x[76-1];
        J[6]	=	x[77-1];
        J[7]	=	x[78-1];
        J[8]	=	x[79-1];
        J[9]	=	x[80-1];
        HRT	=	x[81-1];
        KLa	=	x[82-1];
        r	=	x[83-1];
        Sent	=	x[84-1];
        S	=	x[85-1];
        Xent	=	x[86-1];
        X	=	x[87-1];
        Xr	=	x[88-1];
        Xef	=	x[89-1];
        CODent	=	x[90-1];
        COD	=	x[91-1];
        CODr	=	x[92-1];
        CODef	=	x[93-1];
        VSSent	=	x[94-1];
        VSS	=	x[95-1];
        VSSr	=	x[96-1];
        VSSef	=	x[97-1];
        TSSent	=	x[98-1];
        TSS	=	x[99-1];
        TSSr	=	x[100-1];
        TSSef	=	x[101-1];
        BODent	=	x[102-1];
        BOD	=	x[103-1];
        BODr	=	x[104-1];
        BODef	=	x[105-1];
        TKNent	=	x[106-1];
        TKN	=	x[107-1];
        TKNr	=	x[108-1];
        TKNef	=	x[109-1];
        Nent	=	x[110-1];
        N	=	x[111-1];
        Nr	=	x[112-1];
        Nef	=	x[113-1];
        h = x[114-1];
        S_O = x[115-1];
        
    }
    
    
    
    public double objectives(double [] x)
    {
        
        variables(x);
        
        double f = 174.2214*Math.pow(V_a, 1.0699)+12486.713*Math.pow(G_s, 0.6216)+114.8094*G_s+955.5*Math.pow(A_s, 0.9633)+41.2706*Math.pow((A_s*(1+h3+h4)), 1.0699);
        
        return f;
    }
    
    public double [][] constraints(double [] x)
    {
        
        variables(x);
        
        double [] C = new double[1];
        double [] Ceq = new double[99];
        ////////// CONSTRAINTS //////////
        
        C[0] = Q_P-2*A_s;
        
//////////////////////////////////////////////////////
// hydraulic retention time
//////////////////////////////////////////////////////
        
        Ceq[1-1]=HRT*Q-V_a;
        
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// aeration tank balances (ASM1 + steady state + CSTR)
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
//readily biodegradable substrate – S_S
        Ceq[2-1]=-u_H/Y_H*S_S/(K_S+S_S)*(S_O/(K_OH+S_O)+eta_g*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO))*X_BH+k_h*X_BH/(K_X*X_BH+X_S)*(S_O/(K_OH+S_O)+eta_h*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO))*X_S+Q/V_a*(S_Sent-S_S);
        
// enmeshed slowly biodegradable substrate – X_S
        Ceq[3-1]=(1-f_p)*b_H*X_BH+(1-f_p)*b_A*X_BA-k_h*X_BH/(K_X*X_BH+X_S)*(S_O/(K_OH+S_O)+eta_h*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO))*X_S+Q/V_a*(X_Sent-X_S);
        
// active heterotrophic biomass – X_BH
        Ceq[4-1]=u_H*S_S/(K_S+S_S)*(S_O/(K_OH+S_O)+eta_g*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO))*X_BH-b_H*X_BH+Q/V_a*(X_BHent-X_BH);
        
// active autotrophic biomass – X_BA
        Ceq[5-1]=u_A*S_NH/(K_NH+S_NH)*S_O/(K_OA+S_O)*X_BA-b_A*X_BA+Q/V_a*(X_BAent-X_BA);
        
// unbiodegradable particulates from cell decay - X_P
        Ceq[6-1]=f_p*b_H*X_BH+f_p*b_A*X_BA+Q/V_a*(X_Pent-X_P);
        
// nitrite and nitrate nitrogen – S_NO
        Ceq[7-1]=-(1-Y_H)/(2.86*Y_H)*u_H*S_S/(K_S+S_S)*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO)*eta_g*X_BH+u_A/Y_A*S_NH/(K_NH+S_NH)*S_O/(K_OA+S_O)*X_BA+Q/V_a*(S_NOent-S_NO);
        
// ammonia nitrogen - S_NH
        Ceq[8-1]=-u_H*S_S/(K_S+S_S)*(S_O/(K_OH+S_O)+eta_g*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO))*i_XB*X_BH-u_A*(i_XB+1/Y_A)*S_NH/(K_NH+S_NH)*S_O/(K_OA+S_O)*X_BA+k_a*S_ND*X_BH+Q/V_a*(S_NHent-S_NH);
        
// soluble biodegradable organic nitrogen - S_ND
        Ceq[9-1]=-k_a*X_BH*S_ND+k_h*X_BH/(K_X*X_BH+X_S)*(S_O/(K_OH+S_O)+eta_h*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO))*X_ND+Q/V_a*(S_NDent-S_ND);
        
// particulate biodegradable organic nitrogen - X_ND
        Ceq[10-1]=b_H*(i_XB-f_p*i_XP)*X_BH+b_A*(i_XB-f_p*i_XP)*X_BA-k_h*X_BH/(K_X*X_BH+X_S)*(S_O/(K_OH+S_O)+eta_h*K_OH/(K_OH+S_O)*S_NO/(K_NO+S_NO))*X_ND+Q/V_a*(X_NDent-X_ND);
        
// alklinity – S_alk
        Ceq[11-1]=-i_XB/14*u_H*(S_S/(K_S+S_S))*(S_O/(K_OH+S_O))*X_BH-((1-Y_H)/(14*2.86*Y_H)+i_XB/14)*u_H*(S_S/(K_S+S_S))*(K_OH/(K_OH+S_O))*(S_NO/(K_NO+S_NO))*eta_g*X_BH-(i_XB/14+1/(7*Y_A))*u_A*S_NH/(K_NH+S_NH)*S_O/(K_OA+S_O)*X_BA+1/14*k_a*S_ND*X_BH+Q/V_a*(S_alkent-S_alk);
        
//////////////////////////////////////
//// oxygen balance
////////////////////////////////////
        
        Ceq[12-1]=KLa-alfa*G_s*fracO2*eta*1333.3/(V_a*SOST)*Math.pow(1.024, (T-20));
        
        Ceq[13-1]=Q*S_Oent-Q*S_O+KLa*(SOST-S_O)*V_a+((-(1-Y_H)/Y_H)*u_H*(S_S/(K_S+S_S))*(S_O/(K_OH+S_O))*X_BH-(4.57-Y_A)/Y_A*u_A*(S_NH/(K_NH+S_NH))*(S_O/(K_OA+S_O))*X_BA)*V_a;
        
////////////////////////////////////////////////////////////////////////////
//// composite variables
////////////////////////////////////////////////////////////////////////////
        
// soluble COD
        Ceq[14-1]=Sent-(S_I+S_Sent);
        Ceq[15-1]=S-(S_I+S_S);
        
// particulate COD
        Ceq[16-1]=Xent-(X_I+X_Sent+X_BHent+X_BAent+X_Pent);
        Ceq[17-1]=X-(X_I+X_S+X_BH+X_BA+X_P);
        Ceq[18-1]=Xr-(X_Ir+X_Sr+X_BHr+X_BAr+X_Pr);
        Ceq[19-1]=Xef-(X_Ief+X_Sef+X_BHef+X_BAef+X_Pef);
        
// total COD
        Ceq[20-1]=CODent-(Xent+Sent);
        Ceq[21-1]=COD-(X+S);
        Ceq[22-1]=CODr-(Xr+S);
        Ceq[23-1]=CODef-(Xef+S);
        
// volatile suspended solids
        Ceq[24-1]=VSSent-Xent*1/icv;
        Ceq[25-1]=VSS-X*1/icv;
        Ceq[26-1]=VSSr-Xr*1/icv;
        Ceq[27-1]=VSSef-Xef*1/icv;
        
// total suspended solids
        Ceq[28-1]=TSSent-(VSSent+SSI);
        Ceq[29-1]=TSS-(VSS+SSI);
        Ceq[30-1]=TSSr-(VSSr+SSIr);
        Ceq[31-1]=TSSef-(VSSef+SSIef);
        
// BOD5
        Ceq[32-1]=BODent-f_BOD*(S_Sent+X_Sent+X_BHent+X_BAent);
        Ceq[33-1]=BOD-f_BOD*(S_S+X_S+X_BH+X_BA);
        Ceq[34-1]=BODr-f_BOD*(S_S+X_Sr+X_BHr+X_BAr);
        Ceq[35-1]=BODef-f_BOD*(S_S+X_Sef+X_BHef+X_BAef);
        
// TKN notrogen
        Ceq[36-1]=TKNent-(S_NHent+S_NDent+X_NDent+i_XB*(X_BHent+X_BAent)+i_XP*(X_Pent+X_I));
        Ceq[37-1]=TKN-(S_NH+S_ND+X_ND+i_XB*(X_BH+X_BA)+i_XP*(X_P+X_I));
        Ceq[38-1]=TKNr-(S_NH+S_ND+X_NDr+i_XB*(X_BHr+X_BAr)+i_XP*(X_Pr+X_Ir));
        Ceq[39-1]=TKNef-(S_NH+S_ND+X_NDef+i_XB*(X_BHef+X_BAef)+i_XP*(X_Pef+X_Ief));
        
// total nitrogen
        Ceq[40-1]=Nent-(TKNent+S_NOent);
        Ceq[41-1]=N-(TKN+S_NO);
        Ceq[42-1]=Nr-(TKNr+S_NO);
        Ceq[43-1]=Nef-(TKNef+S_NO);
        
////////////////////////////
// definitions
////////////////////////////
        
        Ceq[44-1]=(Qw*Xr)*SRT-V_a*X;
        
////////////////////////////////////////////////////////////////
//// suspended matter balances
////////////////////////////////////////////////////////////////
        
        Ceq[45-1]=(1+r)*Qinf*Xent-(Qinf*Xinf+(1+r)*Qinf*X-V_a*X/(SRT*Xr)*(Xr-Xef)-Qinf*Xef);
        Ceq[46-1]=(1+r)*Qinf*SSI-(Qinf*TSSinf*0.2+(1+r)*Qinf*SSI-V_a*SSI/(SRT*Xr)*(SSIr-SSIef)-Qinf*SSIef);
        Ceq[47-1]=(1+r)*Qinf*X_NDent-(Qinf*X_NDinf+(1+r)*Qinf*X_ND-V_a*X/(SRT*Xr)*(X_NDr-X_NDef)-Qinf*X_NDef);
        
////////////////////////////////////////////////////////////////////
//// dissolved matter balances
////////////////////////////////////////////////////////////////////
        
        Ceq[48-1]=(1+r)*Qinf*S_Sent-(Qinf*S_Sinf+r*Qinf*S_S);
        Ceq[49-1]=(1+r)*Qinf*S_Oent-(Qinf*S_Oinf+r*Qinf*S_O);
        Ceq[50-1]=(1+r)*Qinf*S_NOent-(Qinf*S_NOinf+r*Qinf*S_NO);
        Ceq[51-1]=(1+r)*Qinf*S_NHent-(Qinf*S_NHinf+r*Qinf*S_NH);
        Ceq[52-1]=(1+r)*Qinf*S_NDent-(Qinf*S_NDinf+r*Qinf*S_ND);
        Ceq[53-1]=(1+r)*Qinf*S_alkent-(Qinf*S_alkinf+r*Qinf*S_alk);
        
//////////////////////////////////////
// flow balances
//////////////////////////////////////
        
        Ceq[54-1]=r*Qinf-Qr;
        Ceq[55-1]=Q-(Qinf+Qr);
        Ceq[56-1]=Q-(Qef+Qr+Qw);
        
//////////////////////////////////////////////////////////////
// secondary settler ATV model
//////////////////////////////////////////////////////////////
        
        Ceq[57-1]=Q_P-2400*A_s*Math.pow(0.7*TSS/1000*IVLD, (-1.34));
        Ceq[58-1]=h3-0.3*TSS/1000*V_a*IVLD/(480*A_s);
        Ceq[59-1]=h4-0.7*TSS/1000*IVLD/1000;
        Ceq[60-1]=r*(TSSr_max-TSS)-TSS;
        Ceq[61-1]=r_p*(TSSr_max_p-0.7*TSS)-0.7*TSS;
        Ceq[62-1]=Qr_p-r_p*Q_P;
        Ceq[63-1]=VSS-0.7*TSS;
        Ceq[64-1]=VSSef-0.7*TSSef;
        
//////////////////////////////////////////////////////////////////////////////////////////////
// secondary settler double exponential model
//////////////////////////////////////////////////////////////////////////////////////////////
        
        Ceq[65-1]=v_up*A_s-Qef;
        Ceq[66-1]=v_dn*A_s-(Qr+Qw);
        
        Ceq[67-1]=v_s[0]-Math.max(0,Math.min(v1_0,v_0*(Math.exp(-r_h*(ST[0]-f_ns*TSS))-Math.exp(-r_P*(ST[0]-f_ns*TSS)))));
        Ceq[68-1]=v_s[1]-Math.max(0,Math.min(v1_0,v_0*(Math.exp(-r_h*(ST[1]-f_ns*TSS))-Math.exp(-r_P*(ST[1]-f_ns*TSS)))));
        Ceq[69-1]=v_s[2]-Math.max(0,Math.min(v1_0,v_0*(Math.exp(-r_h*(ST[2]-f_ns*TSS))-Math.exp(-r_P*(ST[2]-f_ns*TSS)))));
        Ceq[70-1]=v_s[3]-Math.max(0,Math.min(v1_0,v_0*(Math.exp(-r_h*(ST[3]-f_ns*TSS))-Math.exp(-r_P*(ST[3]-f_ns*TSS)))));
        Ceq[71-1]=v_s[4]-Math.max(0,Math.min(v1_0,v_0*(Math.exp(-r_h*(ST[4]-f_ns*TSS))-Math.exp(-r_P*(ST[4]-f_ns*TSS)))));
        Ceq[72-1]=v_s[5]-Math.max(0,Math.min(v1_0,v_0*(Math.exp(-r_h*(ST[5]-f_ns*TSS))-Math.exp(-r_P*(ST[5]-f_ns*TSS)))));
        Ceq[73-1]=v_s[6]-Math.max(0,Math.min(v1_0,v_0*(Math.exp(-r_h*(ST[6]-f_ns*TSS))-Math.exp(-r_P*(ST[6]-f_ns*TSS)))));
        Ceq[74-1]=v_s[7]-Math.max(0,Math.min(v1_0,v_0*(Math.exp(-r_h*(ST[7]-f_ns*TSS))-Math.exp(-r_P*(ST[7]-f_ns*TSS)))));
        Ceq[75-1]=v_s[8]-Math.max(0,Math.min(v1_0,v_0*(Math.exp(-r_h*(ST[8]-f_ns*TSS))-Math.exp(-r_P*(ST[8]-f_ns*TSS)))));
        Ceq[76-1]=v_s[9]-Math.max(0,Math.min(v1_0,v_0*(Math.exp(-r_h*(ST[9]-f_ns*TSS))-Math.exp(-r_P*(ST[9]-f_ns*TSS)))));
        
        for(int j = 1; j < 7; j++)
        {
            if(ST[j+1-1] <= ST_t){
                Ceq[76+j-1] = J[j-1] - v_s[j-1]*ST[j-1];
            }
            else{
                Ceq[76+j-1] = J[j-1] - Math.min(v_s[j-1]*ST[j-1], v_s[j+1-1]*ST[j+1-1]);
            }
        }
        
        for(int j = 7; j < 10; j++){
            Ceq[76+j-1]=J[j-1] - v_s[j-1]*ST[j-1];
        }
        
// feed layer (m=7)
        
        int j = 7;
        Ceq[87-1]=((Q*TSS)/A_s+J[j-1-1]-(v_up+v_dn)*ST[j-1]-Math.min(J[j-1],J[j-1+1]))/(h/10);
        
// intermediate layers below the feed layer (m=8 e m=9)
        
        j=8;
        Ceq[88-1]=(v_dn*(ST[j-1-1]-ST[j-1])+Math.min(J[j-1],J[j-1-1])-Math.min(J[j-1],J[j-1+1]))/(h/10);
        j=9;
        Ceq[89-1]=(v_dn*(ST[j-1-1]-ST[j-1])+Math.min(J[j-1],J[j-1-1])-Math.min(J[j-1],J[j-1+1]))/(h/10);
        
// lower layer (m=10)
        
        j=10;
        Ceq[90-1]=(v_dn*(ST[j-1-1]-ST[j-1])+Math.min(J[j-1-1], J[j-1]))/(h/10);
        
// intermediate layers above the feed layer (m=2 a 6)
        
        for(j = 2; j < 6; j++){
            Ceq[89+j-1]=(v_up*(ST[j-1+1]-ST[j-1])+J[j-1-1]-J[j-1])/(h/10);
        }
        
// upper layer (m=1)
        
        j=1;
        Ceq[96-1]=(v_up*(ST[j-1+1]-ST[j-1])-J[j-1])/(h/10);
        
        Ceq[97-1]=ST[0]-TSSef;
        
        Ceq[98-1]=ST[9]-TSSr;
        
        Ceq[99-1]=h-(h3+h4+1);
        
        
        double [][] constr = new double[2][];
        constr[0] = C;
        constr[1] = Ceq;
        
        
        return constr;
        
    }    
    
}
