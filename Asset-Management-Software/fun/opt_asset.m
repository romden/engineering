function opt_asset(problem)


%% execute otimization

% make folder if not exists
if exist('temp','dir') ~= 7; mkdir('temp'); end

% init asset structure
initAsset(problem, problem.inputFolder);

% strings for file path
problemFile = 'temp/opt_asset_problem.dat';
settingsFile = 'temp/opt_asset_solver.dat';
outputFile = 'temp/opt_asset_output.dat';

saveProblemFile(problemFile); % save file with problem definition
saveSettingsFile(settingsFile); % save parameter settings for moea
saveOutputFile(outputFile) % save file containing output data

% run java package
system(sprintf('java -jar bin/am-mo-asset.jar %s %s %s', problemFile, settingsFile, outputFile));

% read opt results
getResults();

% save results into xlsx
xlsxSaveResults(problem.outputFolder);

return


%% 
function initAsset(problem, inputFolder)

global asset

% init asset structure
asset.name = '';
asset.numberOfPrincipal = 1;
asset.indexesOfPrincipal = 1;
asset.numberOfObjectives = 2;
asset.discountRate = 0.01;
asset.timeHorizon = 10;
asset.numberOfSamples = 20;
asset.criticalState = 5;
asset.numberOfComponents = 1;

% assign values fom fields of problem structure
namesOfFields = fieldnames(problem);
for i = 1:numel(namesOfFields)
    asset.(namesOfFields{i}) = problem.(namesOfFields{i});
end

% init structure of output for asset
switch asset.name
    case 'pavimento'
        asset.output = struct('actions', [], 'costs', [], 'states', [], 'iri', [], 'rut', [], 'crk', [], 'fri', [], 'fwd', [], 'objectives', []);
    otherwise
        asset.output = struct('actions', [], 'costs', [], 'states', [], 'objectives', []);
end

% init components
asset.components = struct('name', [], 'numberOfActions', [], 'numberOfIndexes', [], 'critical', [], 'lastState', [], 'elapsedTime', [], 'fileQ', [], 'fileEffects', [], 'fileCosts', []);


switch asset.name
    case 'ponte'        
        
        asset.criticalState = importdata(sprintf('%s/constraint.dat', inputFolder));
        
        obra = importdata(sprintf('%s/components.dat', inputFolder));
        
        asset.numberOfPrincipal = sum(obra.data);
        asset.indexesOfPrincipal = find(obra.data);
        asset.numberOfComponents = numel(obra.textdata);
        asset.components = repmat(asset.components, asset.numberOfComponents, 1);
        
        for i = 1:asset.numberOfComponents
            xlsxFile = sprintf('%s/%s.xlsx', inputFolder, obra.textdata{i});
            
            asset.components(i).name = obra.textdata{i};
            asset.components(i).numberOfActions = xlsread(xlsxFile, 4, 'A1'); % read number of actions
            asset.components(i).numberOfIndexes = 1;
            asset.components(i).critical = xlsread(xlsxFile, 4, 'A2'); % read constraint
            asset.components(i).lastState = xlsread(xlsxFile, 4, 'A3'); % read last state
            asset.components(i).elapsedTime = 0; % time elapsed
            asset.components(i).fileQ = {sprintf('%s/%s_Q.dat', 'temp', asset.components(i).name)};
            asset.components(i).fileEffects = {sprintf('%s/%s_effects.dat', 'temp', asset.components(i).name)};
            asset.components(i).fileCosts = sprintf('%s/%s_costs.dat', 'temp', asset.components(i).name);
            
            convert_xlsx2dat(xlsxFile, asset.components(i).fileEffects{1}, asset.components(i).fileCosts, asset.components(i).fileQ{1});
            
            asset.components(i).output = struct('actions', [], 'states', [], 'costs', []);
        end
        
    case 'pavimento'
        
        asset.components.name = asset.name;
        asset.components.numberOfActions = xlsread(sprintf('%s/%s.xlsx', inputFolder, 'actions'), 6, 'B1');
        asset.components.numberOfIndexes = 5;
        asset.components.critical = xlsread(sprintf('%s/%s.xlsx', inputFolder, 'params'), 1, 'A2:E2'); % constraint
        asset.components.lastState = xlsread(sprintf('%s/%s.xlsx', inputFolder, 'params'), 1, 'A3:E3');
        asset.components.elapsedTime = [0 0 0 0 0];
        asset.components.fileQ = {'temp/iri_Q.dat' 'temp/rut_Q.dat' 'temp/crk_Q.dat' 'temp/fri_Q.dat' 'temp/fwd_Q.dat'};
        asset.components.fileEffects = {'temp/iri_effects.dat' 'temp/rut_effects.dat' 'temp/crk_effects.dat' 'temp/fri_effects.dat' 'temp/fwd_effects.dat'};
        asset.components.fileCosts = 'temp/pav_costs.dat';
        
        convert_xlsx2dat_pav(inputFolder);
        
    otherwise  %  muro, talude, telematica, and general asset
        xlsxFile = sprintf('%s/%s.xlsx', inputFolder, asset.name);
        
        asset.components.name = asset.name;
        asset.components.numberOfActions = xlsread(xlsxFile, 4, 'A1');
        asset.components.numberOfIndexes = 1;
        asset.components.critical = xlsread(xlsxFile, 4, 'A2'); % constraint
        asset.components.lastState = xlsread(xlsxFile, 4, 'A3');
        asset.components.elapsedTime = 0; %asset.components.elapsedTime = (datenum(startingDate, 'dd/mm/yy') - datenum(xlsread(fileName, 4, 'A4'), 'dd/mm/yy'))/365;
        asset.components.fileQ = {sprintf('%s/%s_Q.dat', 'temp', asset.components.name)};
        asset.components.fileEffects = {sprintf('%s/%s_effects.dat', 'temp', asset.components.name)};
        asset.components.fileCosts = sprintf('%s/%s_costs.dat', 'temp', asset.components.name);
        
        convert_xlsx2dat(xlsxFile, asset.components.fileEffects{1}, asset.components.fileCosts, asset.components.fileQ{1});
end

return


%% convert xlsx into dat
function convert_xlsx2dat(xlsxFile, fileEffects, fileCosts, fileQ)

% save effects
system(sprintf('java -jar bin/am-xlsx2txt.jar %s %s', xlsxFile, fileEffects));

% save costs
costs = xlsread(xlsxFile, 2, sprintf('A1:A%d', xlsread(xlsxFile, 4, 'A1')));
fid = fopen(fileCosts, 'w');
for i = 1:numel(costs)
    fprintf(fid, ' %f \n', costs(i));
end
fclose(fid);

Q = xlsread(xlsxFile, 3, 'A1:E5');
fid = fopen(fileQ, 'w');
for i = 1:5
    for j = 1:5
        fprintf(fid, ' %f', Q(i,j));
    end
    fprintf(fid, ' \n');
end
fclose(fid);

return


%% convert xlsx into dat
function convert_xlsx2dat_pav(inputFolder)

global asset

% save Q
xlsxFile = sprintf('%s/%s.xlsx', inputFolder, 'Q');
for i = 1:5    
    Q = xlsread(xlsxFile, i, 'A1:E5');
    fid = fopen(asset.components.fileQ{i}, 'w');
    for ii = 1:5
        for jj = 1:5
            fprintf(fid, ' %f', Q(ii,jj));
        end
        fprintf(fid, ' \n');
    end
    fclose(fid);
end
 
% save effects
for i = 1:5    
    system(sprintf('java -jar bin/am-xlsx2txt.jar %s %s %d', sprintf('%s/%s.xlsx', inputFolder, 'actions'), asset.components.fileEffects{i}, i));
end

% save costs
costs = xlsread(sprintf('%s/%s.xlsx', inputFolder, 'actions'), 6, sprintf('A1:A%d', asset.components.numberOfActions));
fid = fopen(asset.components.fileCosts, 'w');
for jj = 1:numel(costs)
    fprintf(fid, '%f \n', costs(jj));
end
fclose(fid);

return


%% 
function getResults()

global asset

for field = {'actions' 'costs' 'states' 'objectives'}
    asset.output.(cell2mat(field)) = importdata(sprintf('temp/%s.arc', cell2mat(field))); % get results from archive
end

% sort according to second obj (cost function)
[~, idx] = sort(asset.output.objectives(:,2));
for field = {'actions' 'costs' 'states' 'objectives'}
    asset.output.(cell2mat(field)) = asset.output.(cell2mat(field))(idx,:);
end

% save('asset','asset');
switch asset.name
    case 'ponte'
       
        % read results
        for i = 1:asset.numberOfComponents 
            asset.components(i).output.costs = importdata(sprintf('temp/costs.arc.%d', i));
            asset.components(i).output.actions = importdata(sprintf('temp/actions.arc.%d', i));
            asset.components(i).output.states = importdata(sprintf('temp/states.arc.%d', i));
        end
        
        % sort
        for i = 1:asset.numberOfComponents
            asset.components(i).output.costs = asset.components(i).output.costs(idx,:);
            asset.components(i).output.actions = asset.components(i).output.actions(idx,:);
            asset.components(i).output.states = asset.components(i).output.states(idx,:);
        end
        
        % put all actions for components of bridge in one cell  
        [n, m] = size(asset.output.states);
        asset.output.actions = cell(n, m);        
        
        for ii = 1:n
            for jj = 1:m
                for i = 1:asset.numberOfComponents
                    asset.output.actions{ii,jj} = [asset.output.actions{ii,jj} asset.components(i).output.actions(ii, jj)];
                end
                asset.output.actions{ii,jj} = mat2str(asset.output.actions{ii,jj}); % conver to string
                asset.output.actions{ii,jj} = asset.output.actions{ii,jj}(2:end-1); % remove brakets
            end
        end
        
    case 'pavimento'

        % read results
        indexes = {'iri' 'rut' 'crk' 'fri' 'fwd'};
        for i = 1:5            
            asset.output.(indexes{i}) = importdata(sprintf('temp/states.arc.%d', i));
        end   
        
        % sort
        for i = 1:5
            asset.output.(indexes{i}) = asset.output.(indexes{i})(idx,:);
        end
end

return


%% 
function xlsxSaveResults(outputFolder)

global asset

% creat folders
if exist(outputFolder,'dir') ~= 7; mkdir(outputFolder); end

% init headers
xlsx = xlsxAuxiliary();

% xlsx file containing output
xlsxFile = sprintf('%s/%s.xlsx', outputFolder, asset.name);

% delete file old file if it exists
if exist(xlsxFile, 'file') > 0; delete(xlsxFile); end

% get fields
namesOfFields = fieldnames(asset.output);

% save results for asset into xlsx
for j = 1:numel(namesOfFields)
    xlswrite(xlsxFile, xlsx.solIdx, j, 'A2'); % solution indexes
    xlswrite(xlsxFile, xlsx.headers{j}, j, 'B1'); % headers
    xlswrite(xlsxFile, asset.output.(namesOfFields{j}), j, 'B2'); % data
    xlsx_rename_worksheet(xlsxFile, j, namesOfFields(j)); % define worksheet name
end

% stop if there is only one component
if asset.numberOfComponents == 1 
    return; 
end

% save components into xlsx (bridge)
for i = 1:asset.numberOfComponents
    
    % xlsx file containing output
    xlsxFile = sprintf('%s/%s.xlsx', outputFolder, asset.components(i).name);
    
    % delete file old file if it exists
    if exist(xlsxFile,'file') ~= 7; delete(xlsxFile); end
    
    % save results into xlsx
    for j = 1:3
        xlswrite(xlsxFile, xlsx.solIdx, j, 'A2'); % solution indexes
        xlswrite(xlsxFile, xlsx.headers{2}, j, 'B1'); % header identical to states worksheet
        xlswrite(xlsxFile, asset.components(i).output.(namesOfFields{j}), j, 'B2');
        xlsx_rename_worksheet(xlsxFile, j, namesOfFields(j)); % define worksheet name
    end
end

return


%%
function [xlsx] = xlsxAuxiliary()

global asset

% headers for time intervals
switch asset.name
       
    case 'pavimento'
        xlsx.headers = cell(9, 1);
        h1 = cell(1, asset.timeHorizon);
        for j = 1:asset.timeHorizon
            h1{j} = sprintf('t_%d', j);
        end
        for j = 1:8
            xlsx.headers{j} = h1;
        end
        
    otherwise
        xlsx.headers = cell(4, 1);
        h1 = cell(1, asset.timeHorizon);
        for j = 1:asset.timeHorizon
            h1{j} = sprintf('t_%d', j);
        end
        for j = 1:3
            xlsx.headers{j} = h1;
        end
end

% for objectives
h1 = cell(1, asset.numberOfObjectives);
for j = 1:asset.numberOfObjectives
    h1{j} = sprintf('obj_%d', j);
end
xlsx.headers{end} = h1;

% solution indexes
numberOfSolutions = size(asset.output.actions,1);
xlsx.solIdx = cell(numberOfSolutions, 1);
for i = 1:numberOfSolutions
    xlsx.solIdx{i} = sprintf('sol_%d', i);
end

return


%%
function saveProblemFile(problemFile)

global asset

% save problem definition file
fid = fopen(problemFile, 'w');
fprintf(fid, '%s %s \n', 'name_of_asset', asset.name);
fprintf(fid, '%s %d \n', 'number_of_principal_elements', asset.numberOfPrincipal);
fprintf(fid, '%s', 'principal_elements');
for j = 1:asset.numberOfPrincipal
    fprintf(fid, ' %d', asset.indexesOfPrincipal(j));
end
fprintf(fid, '\n');
fprintf(fid, '%s %d \n', 'number_of_objectives', asset.numberOfObjectives);
fprintf(fid, '%s %.4f \n', 'discount_rate', asset.discountRate);
fprintf(fid, '%s %d \n', 'time_horizon', asset.timeHorizon);
fprintf(fid, '%s %d \n', 'number_of_samples', asset.numberOfSamples);
fprintf(fid, '%s %d \n', 'critical_state', asset.criticalState);
fprintf(fid, '%s %d \n', 'number_of_components', asset.numberOfComponents);

for i = 1:asset.numberOfComponents
    fprintf(fid, '\n\n');
    fprintf(fid, '#%d %s \n', i, asset.components(i).name); % name of component
    fprintf(fid, '%s %d \n', 'number_of_actions', asset.components(i).numberOfActions); % number of actions
    fprintf(fid, '%s %d \n', 'number_of_performance_indexes', asset.components(i).numberOfIndexes); % number of performace indexes
    % save critical states (constraints)
    fprintf(fid, '%s ', 'critical_state');    
    for ii = 1:asset.components(i).numberOfIndexes
        fprintf(fid, '%d ', asset.components(i).critical(ii));
    end
    fprintf(fid, '\n');
    % save init states
    fprintf(fid, '%s ', 'last_state');
    for ii = 1:asset.components(i).numberOfIndexes
        fprintf(fid, '%.2f ', asset.components(i).lastState(ii));
    end
    fprintf(fid, '\n');
    % save time elapsed from last inspection
    fprintf(fid, '%s ', 'elapsed_time');
    for ii = 1:asset.components(i).numberOfIndexes
        fprintf(fid, '%.2f ', asset.components(i).elapsedTime(ii));
    end
    fprintf(fid, '\n');
    % save files containing Q matrix
    fprintf(fid, '%s ', 'file_containing_Q');
    for ii = 1:asset.components(i).numberOfIndexes
        fprintf(fid, '%s ', asset.components(i).fileQ{ii});
    end
    fprintf(fid, '\n');
    % save files containing action effects
    fprintf(fid, '%s ', 'file_containing_action_effects');
    for ii = 1:asset.components(i).numberOfIndexes
        fprintf(fid, '%s ', asset.components(i).fileEffects{ii});
    end
    fprintf(fid, '\n');
    % save file with costs of actions
    fprintf(fid, '%s %s ', 'file_containing_action_costs', asset.components(i).fileCosts);
end
fclose(fid);

return


%%
function saveSettingsFile(settingsFile)

fid = fopen(settingsFile, 'w');
fprintf(fid, '%s %.2f \n', 'crossover_probability_CR', 1.0);
fprintf(fid, '%s %.4f \n', 'mutation_probability_pm', 0.01);
fprintf(fid, '%s %.2f \n', 'probability_for_mating_pool_delta', 0.8);
fprintf(fid, '%s %d \n', 'neighborhood_size_T', 5);
fprintf(fid, '%s %d \n', 'max_nr_of_replacements_nr', 2);
fprintf(fid, '%s %d \n', 'population_size_mu', 100);
fprintf(fid, '%s %d \n', 'stopping_criterion_maxGen', 300);
fprintf(fid, '%s %d \n', 'seed_for_moead', 10000);
fclose(fid);

return


%%
function saveOutputFile(outputFile)

fid = fopen(outputFile, 'w');
fprintf(fid, '%s %s \n', 'ficheiro_output_acoes', 'temp/actions');
fprintf(fid, '%s %s \n', 'ficheiro_output_estados', 'temp/states');
fprintf(fid, '%s %s \n', 'ficheiro_output_custos', 'temp/costs');
fprintf(fid, '%s %s \n', 'ficheiro_output_objetivos', 'temp/objectives');
fprintf(fid, '%s %s \n', 'ficheiro_output_violacao_restricoes', 'temp/cv');
fclose(fid);

return




