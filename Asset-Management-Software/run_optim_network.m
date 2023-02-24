clear
clc

addpath(genpath('fun'));

% copyfile('java/sustims-mo-network/dist/sustims-mo-network.jar', 'bin/')

%% defined input files, storing results of optimizing individual assets

% inputData - is a cell array containing path to input folder and type of asset

inputData = {'data/output/muro_1/muro.xlsx', 1;
            'data/output/talude_1/talude.xlsx', 2;
%          'data/output/talude_2/talude.xlsx', 2;
%          'data/output/talude_3/talude.xlsx', 2;
%          'data/output/talude_4/talude.xlsx', 2;
%          'data/output/talude_5/talude.xlsx', 2;
%          'data/output/talude_6/talude.xlsx', 2;
%          'data/output/talude_7/talude.xlsx', 2;
%          'data/output/talude_8/talude.xlsx', 2;
%          'data/output/talude_9/talude.xlsx', 2;
%          'data/output/talude_10/talude.xlsx', 2;
%          'data/output/talude_11/talude.xlsx', 2;
%          'data/output/talude_12/talude.xlsx', 2;
%          'data/output/talude_13/talude.xlsx', 2;
         'data/output/ponte_1/ponte.xlsx', 3};


 %% import data from xlsx to txt and mat files
%  xlsx_export_results(inputData)
 
 
 
 %% perform network optimization
 problem.type = 10;
 switch problem.type
     case 5
         problem.numberOfObjectives = 3;
     case 6
         problem.numberOfObjectives = 3;
     case 7
         problem.numberOfObjectives = 4;
     case 10
         problem.numberOfObjectives = 3;
     otherwise
         problem.numberOfObjectives = 2;
 end
problem.discountRate = 0.01;
problem.timeHorizon = 30;
problem.numberOfAssetTypes = 3; % number of different types of assets (types: muros, taludes, etc)
problem.numberOfAssets = 3; % total number of assets

problem.outputFolder = 'data/output/network_problem_10';


opt_network(problem)
 
 
 
 
 
 

%% save individual solutions
% folder = 'data/output/network';
% output_solution(1, folder);


%% 
% pesos
w = [0 1; 0.1 0.9; 0.5 0.5; 0.9 0.1; 1 0];

% data file
fileXLSX = sprintf('%s/network.xlsx', problem.outputFolder);

% objetivos
y = xlsread(fileXLSX, 4, 'B2:C101');

for i = 1:size(w, 1)
    
    % indice da solucao
    idx = valueFunction(y, w(i,:), 'CHB');
    
    % save res for indiv sol
    output_solution(idx, problem.outputFolder);
end