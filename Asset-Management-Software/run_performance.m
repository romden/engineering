clear
clc

addpath(genpath('mat'));

% copyfile('java/sustims-performance/dist/sustims-performance.jar', 'bin/')


%% com a��es de manuten��o telematica

% matrix telematica 1
Q = [-0.260475344550011 0.260475344550011 0.0 0.0 0.0;
     0.0 -0.08607011099589662 0.08607011099589662 0.0 0.0; 
     0.0 0.0 -0.04784790816560298 0.04784790816560298 0.0;
     0.0 0.0 0.0 -0.01 0.01;
     0.0 0.0 0.0 0.0 0.0;
    ];
 
% % matrix telematica 2
%  Q = [-0.166180925416566 0.166180925416566 0.0 0.0 0.0;
%      0.0 -0.0674830965140494 0.0674830965140494 0.0 0.0;
%      0.0 0.0 -0.0397730975162873 0.0397730975162873 0.0;
%      0.0 0.0 0.0 -0.0010 0.0010;
%      0.0 0.0 0.0 0.0 0.0];
 
% estado de condi��o inicial
c0 = 1.35;

% estrategia de manuten��o
% estrategiaManutencao = zeros(1, 20);
estrategiaManutencao = [0	0	0	0	0	0	0	0	4	0	0	0	0	0	0	0	0	6	0	0	0	0	0	0	4	0	0	0	0	0];

% ficheiro com efeitos das a��es
efeitosFile = 'data/input/telematica_1/telematica.xlsx';

% calcular o desempenho futuro
s = calc_performance(Q, c0, estrategiaManutencao, efeitosFile);

% construir gr�fico
plot(1:numel(s), s, 'b.');
axis([0 numel(s) 0 5]);
xlabel('tempo');
ylabel('estado');

s0 =[1 1.2	1.5	1.7	1.9	1.9	2	2.1	1.7	1.8	1.8	1.8	1.8	1.8	1.8	1.9	2.1	1.7	1.7	1.8	1.9	1.9	1.9	2	1.9	1.9	1.9	1.9	2	2.1];

hold on; plot(1:numel(s), s, 'ro');




