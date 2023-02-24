function opt_network()

global problem

%% define some data for xlsx files
global columns names headings
initParamsXLSX();


%% export costs from xlsx to dat
if exist('Temp','dir') ~= 7; mkdir('Temp'); end

xlRange = sprintf('A2:%s%d', columns{problem.timeHorizon}, problem.numberOfStrategies+1);

k = 0; % index of assset
for i = 1:numel(problem.namesOfAssets)
    
    % define data structures
    switch problem.namesOfAssets{i}
        case 'Muros'
            temp_str = {'actions' 'states' 'costs'};
            problem.(lower(problem.namesOfAssets{i})).(lower(problem.namesOfAssets{i}(1:end-1))) = repmat(struct('actions', [], 'states', [], 'costs', []), problem.numbersOfAssets(i), 1);
        case 'Taludes'
            temp_str = {'actions' 'states' 'costs'};
            problem.(lower(problem.namesOfAssets{i})).(lower(problem.namesOfAssets{i}(1:end-1))) = repmat(struct('actions', [], 'states', [], 'costs', []), problem.numbersOfAssets(i), 1);
        case 'Pavimentos'
            temp_str = {'actions' 'states' 'states_iri' 'states_rut' 'states_crk' 'states_fri' 'states_fwd' 'costs'};
            problem.(lower(problem.namesOfAssets{i})).(lower(problem.namesOfAssets{i}(1:end-1))) = repmat(struct('actions', [], 'states', [], 'states_iri', [], 'states_rut', [], 'states_crk', [], 'states_fri', [], 'states_fwd', [], 'costs', []), problem.numbersOfAssets(i), 1);
    end
    
    % export data from xlsx and save in txt files
    for indexOfAsset = 1:problem.numbersOfAssets(i)
        
        % define xlsx file name
        fileName = sprintf('Results/%s/%s_%d/%s_%d_anos_%d.xlsx',...
                           problem.namesOfAssets{i}, problem.namesOfAssets{i}(1:end-1), indexOfAsset, lower(problem.namesOfAssets{i}(1:end-1)), indexOfAsset, problem.timeHorizon);
        
        % update index of asset
        k = k + 1;
        
        % for each component of result        
        for j = 1:numel(temp_str)
            % read xlsx data
            problem.(lower(problem.namesOfAssets{i})).(lower(problem.namesOfAssets{i}(1:end-1)))(indexOfAsset).(temp_str{j}) = xlsread(fileName, j, xlRange);
            
            % save to txt
%             fid = fopen(sprintf('Temp/%s_%d_%s.dat', lower(problem.namesOfAssets{i}(1:end-1)), indexOfAsset, temp_str{j}), 'w');
%             for ii = 1:problem.numberOfStrategies
%                 for jj = 1:problem.timeHorizon
%                     if j == 1
%                         fprintf(fid, '%d ', problem.(lower(problem.namesOfAssets{i})).(lower(problem.namesOfAssets{i}(1:end-1)))(indexOfAsset).(temp_str{j})(ii, jj));
%                     else
%                         fprintf(fid, '%.4f ', problem.(lower(problem.namesOfAssets{i})).(lower(problem.namesOfAssets{i}(1:end-1)))(indexOfAsset).(temp_str{j})(ii, jj));
%                     end
%                 end
%                 fprintf(fid, '\n');
%             end
%             fclose(fid);
        end
        
    end
    
end

% save assets assets

%% run mo selection

% save file with problem definition
problemFile = 'Temp/param_problem.dat';
fid = fopen(problemFile, 'w');
fprintf(fid, '%s %d \n', 'problem_type', problem.type);
fprintf(fid, '%s %d \n', 'number_of_objectives', 2);
fprintf(fid, '%s %d \n', 'time_horizon', problem.timeHorizon);
fprintf(fid, '%s %f \n', 'discount_rate', 0.01);
fprintf(fid, '%s %d \n', 'number_of_asset_types', numel(problem.namesOfAssets));
fprintf(fid, '%s %d \n', 'number_of_assets', sum(problem.numbersOfAssets));
k = 0;
for i = 1:numel(problem.namesOfAssets)
    for indexOfAsset = 1:problem.numbersOfAssets(i)
        k = k + 1;
        fprintf(fid, '\n');
        fprintf(fid, '#%d %s \n', k, 'asset');
        fprintf(fid, '%s %s \n', 'file_containing_costs', sprintf('Temp/%s_%d_costs.dat', lower(problem.namesOfAssets{i}(1:end-1)), indexOfAsset));
        fprintf(fid, '%s %s \n', 'file_containing_states', sprintf('Temp/%s_%d_states.dat', lower(problem.namesOfAssets{i}(1:end-1)), indexOfAsset));
        fprintf(fid, '%s %d \n', 'number_of_alternatives', problem.numberOfStrategies);
        fprintf(fid, '%s %d \n', 'asset_type', i);
    end
end
fclose(fid);

% save file with parameter settings for solver
settingsFile = 'Temp/param_solver.dat';
fid = fopen(settingsFile, 'w');
fprintf(fid, '%s %f \n', 'crossover_probability_CR', 1.000000);
fprintf(fid, '%s %f \n', 'mutation_probability_pm', 1/sum(problem.numbersOfAssets));
fprintf(fid, '%s %f \n', 'probability_for_mating_pool_delta', 0.800000);
fprintf(fid, '%s %d \n', 'neighborhood_size_T', 20);
fprintf(fid, '%s %d \n', 'neighborhood_size_nr', 2);
fprintf(fid, '%s %d \n', 'population_size_mu', 300);
fprintf(fid, '%s %d \n', 'stopping_criterion_maxGen', 1000);
fclose(fid);

% save file for output
outputFile = 'Temp/param_output.dat';
variablesFile = 'Temp/vars.dat';
objectivesFile = 'Temp/objs.dat';
fid = fopen(outputFile, 'w');
fprintf(fid, '%s %s \n', 'file_for_saving_variables', variablesFile);
fprintf(fid, '%s %s \n', 'file_for_saving_objectives', objectivesFile);
fclose(fid);

% execute java package
system(sprintf('java -jar dist/sustims-mo-network.jar %s %s %s', problemFile, settingsFile, outputFile));

% read results
problem.vars = importdata(variablesFile);
problem.objs = importdata(objectivesFile);

% define folder for output
problem.outputFolder = sprintf('Results/Network/ProblemType_%d/TimeHorizon_%d', problem.type, problem.timeHorizon);
if exist(problem.outputFolder,'dir') ~= 7; mkdir(problem.outputFolder); end

% save approximation set into xlsx
saveParetoSetXLSX();

% save individual solutions into xlsx
for i = [1 size(problem.vars,1)] %[1 30:size(x,1)]
    saveSolutionXLSX(sprintf('%s/solution_%d.xlsx', problem.outputFolder, i), problem.vars(i,:)');
end

return


%% difine some data for xlsx files
function initParamsXLSX()

global problem columns names headings

% init columns
columns = defineColumnIdentifiesXLSX(100);

% init names of assets
names = cell(sum(problem.numbersOfAssets), 1);
k = 0;
for i = 1:numel(problem.namesOfAssets)
    for j = 1:problem.numbersOfAssets(i)
        k = k+1;
        names{k} = sprintf('%s %d', problem.namesOfAssets{i}(1:end-1), j);
    end
end

% init headings of time intervals
headings = cell(1, problem.timeHorizon+2);
headings(1:2) = {'nome' 'estrategia Nº'};
for j = 3:numel(headings) 
    headings{j} = sprintf('ano %d', j-2); 
end

return


%% define columns identifies for xlsx
function columns = defineColumnIdentifiesXLSX(numberOfColumns)

alphabet = {'A' 'B' 'C' 'D' 'E' 'F' 'G' 'H' 'I' 'J' 'K' 'L' 'M' 'N' 'O' 'P' 'Q' 'R' 'S' 'T' 'U' 'V' 'W' 'X' 'Y' 'Z'};
% numberOfColumns = 100;
columns = cell(1, numberOfColumns);
columns(1:numel(alphabet)) = alphabet;
k = numel(alphabet);
for i = 1:numel(alphabet)
    for j = 1:numel(alphabet)
        k = k+1;
        columns{k} = sprintf('%s%s', alphabet{i}, alphabet{j});
        if k == numberOfColumns
            break;
        end
    end
    if k == numberOfColumns
        break;
    end
end

return


%% rename sheet names in xlsx file
function renameWorkSheets(fileXLSX, sheetNumbers, sheetNames)

% rename work sheets, using ActiveX
currentFolder = pwd; % get current folder path
currentFolder(currentFolder=='\') = '/';
for j = 1:numel(sheetNumbers)
    idx = sheetNumbers(j);
    e = actxserver('Excel.Application'); % # open Activex server
    ewb = e.Workbooks.Open([currentFolder '/' fileXLSX]); % # open file (enter full path!)
    ewb.Worksheets.Item(idx).Name = sheetNames{j}; % # rename j-th sheet
    ewb.Save; % # save to the same file
    ewb.Close(false);
    e.Quit;
end

return


%% save vars and objs in xlsx file
function saveParetoSetXLSX()

global problem columns names headings

% define xlsx file
fileXLSX = sprintf('%s/pareto_set.xlsx', problem.outputFolder);

% save vars
xlswrite(fileXLSX, names', 1, 'A1');
xlswrite(fileXLSX, problem.vars, 1, 'A2');

% save objs
m = size(problem.objs, 2);
str_objs = cell(1, m);
for i = 1:m
    str_objs{i} = sprintf('obj%d', i);
end
xlswrite(fileXLSX, str_objs, 2, 'A1');
xlswrite(fileXLSX, problem.objs, 2, 'A2');

% rename sheets
renameWorkSheets(fileXLSX, [1 2], {'vars', 'objs'});

return


%% save solution to separate xlsx file
function saveSolutionXLSX(fileXLSX, idx)

global problem columns names headings

persistent actions states costs
if size(actions, 2) < problem.timeHorizon
    % allocate arrays
    actions = zeros(sum(problem.numbersOfAssets), problem.timeHorizon);
    states = zeros(sum(problem.numbersOfAssets), problem.timeHorizon);
    costs = zeros(sum(problem.numbersOfAssets), problem.timeHorizon);
end

% make headings in each sheet
for j=1:3; xlswrite(fileXLSX, headings, j,'A1'); end

renameWorkSheets(fileXLSX, [1 2 3], {'acoes', 'estados',  'custos'});

% get seleted data
k = 0;
for i = 1:numel(problem.namesOfAssets)
    for indexOfAsset = 1:problem.numbersOfAssets(i)
        k = k+1;
        actions(k,:) = problem.(lower(problem.namesOfAssets{i})).(lower(problem.namesOfAssets{i}(1:end-1)))(indexOfAsset).actions(idx(k), :);
        states(k,:) = problem.(lower(problem.namesOfAssets{i})).(lower(problem.namesOfAssets{i}(1:end-1)))(indexOfAsset).states(idx(k), :);
        costs(k,:) = problem.(lower(problem.namesOfAssets{i})).(lower(problem.namesOfAssets{i}(1:end-1)))(indexOfAsset).costs(idx(k), :);
    end
end

% insert data into final xlsx
for j = 1:3; xlswrite(fileXLSX, names, j, 'A2'); end
xlswrite(fileXLSX, [idx actions], 1, 'B2');
xlswrite(fileXLSX, [idx states], 2, 'B2');
xlswrite(fileXLSX, [idx costs], 3, 'B2');

xlswrite(fileXLSX, {'budget'; 'mean'; 'std'}, 3, sprintf('B%d', sum(problem.numbersOfAssets)+4));
xlswrite(fileXLSX, sum(costs), 3, sprintf('C%d', sum(problem.numbersOfAssets)+4));
xlswrite(fileXLSX, mean(sum(costs)), 3, sprintf('C%d', sum(problem.numbersOfAssets)+5));
xlswrite(fileXLSX, std(sum(costs)), 3, sprintf('C%d', sum(problem.numbersOfAssets)+6));

return