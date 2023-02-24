function output_solution(indexOfSolution, outputFolder)

% read vars from xlsx file storing results for network
vars = xlsread(sprintf('%s/network.xlsx', outputFolder), 1);

% get solution for network, consisting of indexes
solution = vars(indexOfSolution, :);

% load mat results
load('temp/assets');

% get sizes
numberOfAssets = numel(solution);
timeHorizon = size(assets(1).costs, 2);

% init
components = {'costs' 'states'};

% get results
for j = 1:numel(components)
    eval(sprintf('%s = zeros(numberOfAssets, timeHorizon);', components{j}));
    
    for i = 1:numberOfAssets
        eval( sprintf('%s(i,:) = assets(i).%s(solution(i)+1,:);', components{j}, components{j}) );
    end
end

xlsx.headers = cell(2, 1);
% init strings for names of assets
names = cell(numberOfAssets, 1);
for i = 1:numberOfAssets
    names{i} = sprintf('%s_%d_%s', 'asset', i, assets(i).name);
end
xlsx.headers{1} = names;

% init strings for time intervals 
names = cell(1, timeHorizon);
for j = 1:timeHorizon
    names{j} = sprintf('t_%d', j);
end
xlsx.headers{2} = names;

% file name
fileXLSX = sprintf('%s/solution_%d.xlsx', outputFolder, indexOfSolution);

% delete old file, if it exists
if exist(fileXLSX, 'file') > 0; delete(fileXLSX); end

% output into xlsx file
for j = 1:numel(components)
    xlswrite(fileXLSX, xlsx.headers{1}, j, 'A2'); % make headings in each sheet
    xlswrite(fileXLSX, xlsx.headers{2}, j, 'B1'); % write names for each asset
    xlsx_rename_worksheet(fileXLSX, j, components(j)); % define worksheet name
    xlswrite(fileXLSX, eval(components{j}), j, 'B2'); % insert data into final xlsx
end

return
