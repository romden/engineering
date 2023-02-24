function opt_network(problem_)

if exist('temp','dir') ~= 7; mkdir('temp'); end

%% define problem struct
global problem
problem = problem_;


%% run mo selection

problemFile = 'temp/opt_network_problem.dat'; % save file with problem definition
settingsFile = 'temp/opt_network_solver.dat'; % save file with parameter settings for solver
outputFile = 'temp/opt_network_output.dat'; % save file for output

saveProblemFile(problemFile);
saveSettingsFile(settingsFile);
saveOutputFile(outputFile);


% execute java package
system(sprintf('java -jar bin/am-mo-network.jar %s %s %s', problemFile, settingsFile, outputFile));

% save approximation set into xlsx
saveResultsForNetworkIntoXLSX();

return


%%
function xlsxAuxiliaryNetwork()

global problem


[numberOfSolutions, numberOfAssets] = size(problem.output.vars);

% for sol indexes
problem.xlsx.solIdx = cell(numberOfSolutions, 1);
for i = 1:numberOfSolutions
    problem.xlsx.solIdx{i} = sprintf('%s_%d', 'sol', i);
end

% init headers
problem.xlsx.headers = cell(1, 4);

% vars
temp_header = cell(1, numberOfAssets);
for i = 1:numberOfAssets
    temp_header{i} = sprintf('%s_%d', 'idx', i);
end
problem.xlsx.headers{1} = temp_header;

% costs and sates
timeHorizon = size(problem.output.costs, 2);
temp_header = cell(1, timeHorizon);
for i = 1:timeHorizon
    temp_header{i} = sprintf('%s_%d', 't', i);
end
problem.xlsx.headers{2} = temp_header;
problem.xlsx.headers{3} = temp_header;

% obj
temp_header = cell(1, problem.numberOfObjectives);
for i = 1:problem.numberOfObjectives
    temp_header{i} = sprintf('%s_%d', 'obj', i);
end
problem.xlsx.headers{4} = temp_header;

return



%% save vars and objs in xlsx file
function saveResultsForNetworkIntoXLSX()

global problem

% define folder for output
if exist(problem.outputFolder,'dir') ~= 7; mkdir(problem.outputFolder); end

components = {'vars' 'costs' 'states' 'objs'};

% read results
problem.output.vars = importdata('temp/variables.arc');
problem.output.costs = importdata('temp/costs.arc');
problem.output.states = importdata('temp/states.arc');
problem.output.objs = importdata('temp/objectives.arc');

% sort
[~, idx] = sort(problem.output.objs(:,1), 'descend');

problem.output.vars = problem.output.vars(idx,:);
problem.output.costs = problem.output.costs(idx,:);
problem.output.states = problem.output.states(idx,:);
problem.output.objs = problem.output.objs(idx,:);

% define headers for xlsx file
xlsxAuxiliaryNetwork();

% define xlsx file
fileXLSX = sprintf('%s/network.xlsx', problem.outputFolder);

% delete old file, if it exists
if exist(fileXLSX, 'file') > 0; delete(fileXLSX); end

% output into xlsx file
for j = 1:numel(components)    
    xlswrite(fileXLSX, problem.xlsx.solIdx, j, 'A2'); % solution indexes
    xlswrite(fileXLSX, problem.xlsx.headers{j}, j, 'B1'); % header identical to states worksheet
    xlswrite(fileXLSX, problem.output.(components{j}), j, 'B2'); % insert data into final xlsx
    xlsx_rename_worksheet(fileXLSX, j, components(j)); % define worksheet name
end

return


%%
function saveProblemFile(problemFile)

global problem

load('temp/assets.mat'); % load mat file

fid = fopen(problemFile, 'w');
fprintf(fid, '%s %d \n', 'problem_type', problem.type);
fprintf(fid, '%s %d \n', 'number_of_objectives', problem.numberOfObjectives);
fprintf(fid, '%s %d \n', 'time_horizon', problem.timeHorizon);
fprintf(fid, '%s %f \n', 'discount_rate', problem.discountRate);
fprintf(fid, '%s %d \n', 'number_of_asset_types', problem.numberOfAssetTypes);
fprintf(fid, '%s %d \n', 'number_of_assets', problem.numberOfAssets);


for k = 1:problem.numberOfAssets
    fprintf(fid, '\n');
    fprintf(fid, '#%d %s \n', k, 'asset');
    fprintf(fid, '%s %s \n', 'file_containing_costs', sprintf('temp/asset_%d_costs', k));
    fprintf(fid, '%s %s \n', 'file_containing_states', sprintf('temp/asset_%d_states', k));
    fprintf(fid, '%s %d \n', 'number_of_alternatives', assets(k).numberOfStrategies);
    fprintf(fid, '%s %d \n', 'asset_type', assets(k).type);
end

fclose(fid);

return


%%
function saveSettingsFile(settingsFile)

global problem

fid = fopen(settingsFile, 'w');
fprintf(fid, '%s %f \n', 'crossover_probability_CR', 1.000000);
fprintf(fid, '%s %f \n', 'mutation_probability_pm', 1/sum(problem.numberOfAssets));
fprintf(fid, '%s %f \n', 'probability_for_mating_pool_delta', 0.800000);
fprintf(fid, '%s %d \n', 'neighborhood_size_T', 20);
fprintf(fid, '%s %d \n', 'neighborhood_size_nr', 2);
fprintf(fid, '%s %d \n', 'population_size_mu', 300);
fprintf(fid, '%s %d \n', 'stopping_criterion_maxGen', 1000);
fprintf(fid, '%s %d \n', 'random_seed', 10000);
fclose(fid);

return


%%
function saveOutputFile(outputFile)

fid = fopen(outputFile, 'w');
fprintf(fid, '%s %s \n', 'file_for_varriables', 'temp/variables');
fprintf(fid, '%s %s \n', 'file_for_costs', 'temp/costs');
fprintf(fid, '%s %s \n', 'file_for_states', 'temp/states');
fprintf(fid, '%s %s \n', 'file_for_objectives', 'temp/objectives');
% fprintf(fid, '%s %s \n', 'ficheiro_output_violacao_restricoes', 'temp/cv');
fclose(fid);

return

