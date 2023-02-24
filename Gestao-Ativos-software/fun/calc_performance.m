function [states] = calc_performance(Q, lastState, actions, effectsFile, elapsedTime)
% A função AssetPerformance calcular o desempenho futuro baseado no processo de
% Markov defenido pela matriz de intensidade e ações de manutenção.
% Dados de Input:
% Q - matriz de intensidade
% lastState - estado de condicão inicial
% actions - estrategias de manutenção (ou tempo horizonte se é escalar)
% Dados de Output:
% states - estados de condição no futuro

%% 
% create temp directory
if ~isdir('temp')
    mkdir('temp');
end

if nargin < 5
    elapsedTime = 0;
end

% intensity matrix files
if isa(Q, 'char')
    matrixFile = Q;
else
    matrixFile = 'temp/Q.dat';
    % save data into files
    fid = fopen(matrixFile, 'w');
    [n, m] = size(Q);
    for i = 1:n
        for j = 1:m
            fprintf(fid, '%f ', Q(i,j));
        end
        fprintf(fid, '\n');
    end
    fclose(fid);
end

if nargin > 3
    % action effects file
    if strcmp(effectsFile(end-4:end),'.xlsx')
        % conver xlsx to dat
        system(sprintf('java -jar bin/am-xlsx2txt.jar %s %s', effectsFile, 'temp/effects.dat'));
        effectsFile = 'temp/effects.dat';
    end
end

outputFile = 'temp/output.dat';

% params file
paramsFile = 'temp/params.dat';
actionsFile = 'temp/actions.dat';
if isscalar(actions)
    th = actions;
    actions = zeros(1, th);
else
    th = size(actions, 2);
end

fid1 = fopen(paramsFile, 'w');
fid2 = fopen(actionsFile, 'w');
for i = 1:size(actions, 1)
    % params
    fprintf(fid1, '%.2f %.2f %d \n', lastState, elapsedTime, th);
    %actions
    for j = 1:th
        fprintf(fid2, '%d ', actions(i, j));
    end
    fprintf(fid2, '\n');
end
fclose(fid1);
fclose(fid2);

% execute java package
if nargin > 3
    % with maintenance
    system(sprintf('java -jar bin/am-performance.jar %s %s %s %s %s %d', matrixFile, paramsFile, actionsFile, effectsFile,  outputFile, 100));
else
    % without maintenance
    system(sprintf('java -jar bin/am-performance.jar %s %s %s', matrixFile, paramsFile, outputFile));
end

% get output
states = importdata(outputFile);
    
% remove temp directory
% rmdir('temp', 's');

return