function [Q] = build_model(dataFile)
% A função build_model calcula calcular a matriz de intensidade, 
% que descreve o modelo de deterioração de Markov em tempo contínuo, 
% a partir dos dados históricos guardados num ficheiro de texto.
% Dados de Input:
% dataFile - o caminho e o nome do ficheiro que contem o registo de inspecções
% Dados de Output:
% Q - matriz de intensidade

%% 
% create temp directory
if exist('temp','dir') ~= 7; mkdir('temp'); end

if strcmp(dataFile(end-3:end), 'xlsx')
    inputFile = database_xlsx_to_dat(dataFile); % if input is xlsx file, convert to txt file
else
    inputFile = dataFile; % if input is text file
end

% define files
javaExeFile = 'java -jar bin/am-model.jar';
minState = 1;
maxState = 5;
timeSpan = 365;
outputFile = 'temp/model_output';

% execute java package
system([javaExeFile, ' ', inputFile, ' ', num2str(minState), ' ', num2str(maxState), ' ', num2str(timeSpan), ' ', outputFile]);

% get output
Q = importdata(outputFile);
    
% remove temp directory
% rmdir('temp', 's');

return


function [inputFile] = database_xlsx_to_dat(xlsxFile)

data = readtable(xlsxFile);

[n, m] = size(data);

inputFile = 'temp/model_input';

fid = fopen(inputFile, 'w');

for i = 1:n
    for j = 2:m
        str = 'NA';
        x = data{i,j};
        if iscell(x)
            x = x{1};
        end
        if ischar(x) && numel(x) > 0
            str = x;
        end
        if isnumeric(x) && isfinite(x)
            str = num2str(x);
        end
       fprintf(fid, '%s;', str);
    end
    fprintf(fid, 'NA;NA;\n');
end

fclose(fid);

return

