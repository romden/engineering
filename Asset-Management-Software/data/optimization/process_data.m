clear
clc

for i = 1:13
    srcFolder = sprintf('C:/Users/Roman/Desktop/SustIMS-Case-Studies/Experiments_3_sublanco_real_initial_states/Input/Taludes/Talude_%d', i);
    destFolder = sprintf('talude_%d', i);
    xlsxFile = sprintf('%s/talude.xlsx', destFolder);
    
    if exist(destFolder,'dir') ~= 7; mkdir(destFolder); end

    copyfile(sprintf('%s/taludes.xlsx', srcFolder),  xlsxFile, 'f');


c = importdata(sprintf('%s/taludes_costs.dat', srcFolder));
Q = importdata(sprintf('%s/taludes_Q.dat', srcFolder));
lastState = importdata(sprintf('%s/estado_inicial.dat', srcFolder));
lastState = str2num(lastState{1}(1));

xlswrite(xlsxFile, c(:), 2, 'A1');
xlswrite(xlsxFile, Q, 3, 'A1');
xlswrite(xlsxFile, [numel(c); 3; lastState] , 4, 'A1');
xlswrite(xlsxFile, {'Nr_actions'; 'state_bound'; 'last_state'}, 4, 'B1');

xlsx_rename_worksheet(xlsxFile, 1:4, {'effects'; 'costs'; 'Q'; 'params'})

end