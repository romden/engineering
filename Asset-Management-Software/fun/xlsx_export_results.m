function xlsx_export_results(inputData)


%% export costs from xlsx to dat
if exist('temp','dir') ~= 7; mkdir('temp'); end

components = {'costs' 'states'};

numberOfAssets = size(inputData, 1);

% define data structures
assets = struct('costs', [], 'states', [], 'numberOfStategies', 0, 'type', 0, 'name', '');
assets = repmat(assets, numberOfAssets, 1);

for i = 1:numberOfAssets
    
    for j = 1:numel(components)
        % read xlsx data
        assets(i).(components{j}) = xlsread(inputData{i, 1}, j+1); % read 2 and 3 sheets, for costs and states respectively
    end
    [numberOfStrategies, timeHorizon] = size(assets(i).costs);
    assets(i).numberOfStrategies = numberOfStrategies; % number of strategies
    assets(i).type = inputData{i, 2}; % type of asset
    idx1 = strfind(inputData{i, 1}, '/'); idx2 = strfind(inputData{i, 1}, '.xlsx');
    assets(i).name = inputData{i, 1}(idx1(end)+1:idx2-1); % assign name of asset
    
    
    % open files
    fid = zeros(numel(components), 1);
    for j = 1:numel(components)
        fid(j) = fopen(sprintf('%s/asset_%d_%s', 'temp', i, components{j}), 'w');
    end    
    
    % save into files
    for ii = 1:numberOfStrategies
        for jj = 1:timeHorizon
            for j = 1:numel(components)
                fprintf(fid(j), '%.4f ', assets(i).(components{j})(ii, jj));
            end
        end
        for j = 1:numel(components)
            fprintf(fid(j), '\n');
        end
    end
    
    % close files
    for j = 1:numel(components)
        fclose(fid(j));
    end
    
end

save('temp/assets', 'assets');

return
