clear
clc

addpath(genpath('fun'));

% copyfile('java/am-mo-asset/dist/am-mo-asset.jar', 'bin/')

%% problem definition

% mandatory fields
problem.name = '';
problem.inputFolder = '';
problem.outputFolder = '';

% optional fields
problem.numberOfObjectives = 2;
problem.discountRate = 0.01;
problem.timeHorizon = 10;
problem.numberOfSamples = 10;
problem.criticalState = 5;




%% optimize ponte

% problem.name = 'ponte';  % must be lower case
% problem.inputFolder = 'data/input/ponte_1';
% problem.outputFolder = 'data/output/ponte_1';
% 
% opt_asset(problem);


%% optimize pavimento

% problem.name = 'pavimento'; % must be lower case
% problem.inputFolder = 'data/input/pavimento';
% problem.outputFolder = 'data/output/pavimento';
% 
% opt_asset(problem);


%% optimize muro

problem.name = 'muro'; % must be lower case
problem.inputFolder = 'data/optimization/muro_1/input';
problem.outputFolder = 'data/optimization/muro_1/output';

opt_asset(problem);


%% optimize talude

% problem.name = 'talude'; % must be lower case
% problem.inputFolder = 'data/input/talude_1';
% problem.outputFolder = 'data/output/talude_1';
% 
% opt_asset(problem);


%% optimize telematica

% problem.name = 'telematica'; % must be lower case
% problem.inputFolder = 'data/input/telematica_1';
% problem.outputFolder = 'data/output/telematica_1';
% 
% opt_asset(problem);





