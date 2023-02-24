function [output] = xlsx_column_identifires(numberOfColumns, idx, varargin)

%% define columns identifies for xlsx
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

if nargin == 2
    output = columns{idx};
else
    output = columns;
end

return