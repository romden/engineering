clear
clc

[f, S, L_T, I_T, R, I_H, A, C_H, L_TH, I_TH, R_H, A_T] = tuberculosisHIV(rand(180,1))

N = S + L_T + I_T + R + I_H + A + C_H + L_TH + I_TH + R_H + A_T
