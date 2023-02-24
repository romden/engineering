clear
clc

addpath(genpath('fun'));



%% calcular matriz de intensidade

% Na base de dadados, uma data pode ter um dos seguintes formatos: 
% dd/mm/yyyy
% dd-mm-yyyy
% yyyy/mm/dd
% yyyy-mm-dd

% % ficheiro com base de dados (registo de inspe��es)
% dataFile = 'data/inspections/database.xlsx';
% 
% % calcular matriz
% Q = build_model(dataFile)



%% calcular desempenho sem a��es de manuten��o

% matrix de intensidade
% Q = [   -0.0934    0.0934    0         0         0;
%          0   -0.1133    0.1133         0         0;
%          0         0   -0.1642    0.1642         0;
%          0         0         0   -0.1019    0.1019;
%          0         0         0         0         0];
% 
% % estado de condi��o inicial
% c0 = 3;
% 
% % tempo horizonte
% th = 100;
% 
% % calcular o desempenho futuro
% s = calc_performance(Q, c0, th);
% 
% % construir gr�fico
% plot(1:numel(s), s, 'b.');
% axis([0 numel(s) 0 5]);
% xlabel('tempo');
% ylabel('estado');



%% calcular desempenho com a��es de manuten��o

% % matrix de intensidade
% Q = [-0.35536621072355506 0.35536621072355506 0.0 0.0 0.0;
%      0.0 -0.26264072614532066 0.26264072614532066 0.0 0.0;
%      0.0 0.0 -0.38035073667554344 0.38035073667554344 0.0;
%      0.0 0.0 0.0 -0.09810154865555702 0.09810154865555702;
%      0.0 0.0 0.0 0.0 0.0];
%  
% % estado de condi��o inicial
% c0 = 1;
% 
% % estrategia de manuten��o
% % estrategiaManutencao = zeros(1, 30);
% % % estrategiaManutencao = [0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0];
% % estrategiaManutencao(10:10:30) = 5;
% estrategiaManutencao = randi(5, 1,30);
% % 
% 
% % ficheiro com acoes de manutencao
% efeitosFile = 'data/maintenance/effects.xlsx'; 
% 
% % calcular o desempenho futuro
% s = calc_performance(Q, c0, estrategiaManutencao, efeitosFile);
% 
% % construir gr�fico
% plot(1:numel(s), s, 'b.');
% axis([0 numel(s) 0 5]);
% xlabel('tempo');
% ylabel('estado');



%% multiobjective optimization

% problem.name = 'asset'; % nome do ativo (nome do ficheiro xlsx com input)
% problem.inputFolder = 'data/optimization/input'; % caminho para pasta com ficheiro xlsx
% problem.outputFolder = 'data/optimization/output'; % caminho para gravar resultado da otimiza��o
% 
% problem.discountRate = 0.05; % taxa de desconto
% problem.timeHorizon = 10; % tempo horizonte
% 
% % executar algoritmo de otimiza��o, ficheiro "problem.name" deve ser fechado !!!
% opt_asset(problem);
