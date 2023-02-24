clear
clc

%% perform selection
global problem

for timeHorizon = 20% [10 20 30] % 10; %
    
    problem = [];    
    problem.namesOfAssets = {'Pavimentos'}; %{'Muros', 'Taludes', 'Pavimentos'};
    problem.numbersOfAssets = 50; %[1 5 5]; % [1 13 99];
    problem.numberOfStrategies = 100;
    problem.timeHorizon = timeHorizon;
    problem.type = 8; % 1 - mean and std cost; 
    
    opt_network();
end



% y = importdata('Temp/objs.dat');
% if size(y,2) == 2
%     y = importdata('Temp/objs.dat');
% elseif size(y,2) == 3
%     plot3(y(:,1),y(:,2),y(:,3), 'r*')
% end




%% ver graficocs
% y = importdata('objectives.dat');
% plot(y(:,1),y(:,2),'ro')
% xlabel('performance');
% ylabel('cost');


% axis([0 max(y(:,1)) 0 max(y(:,2))])

% fileName=strcat('states.dat');
% y = importdata(fileName);
% idx = 2;
% plot(1:size(y,2),y(idx,:),'b.')
% hold on
% plot(1:size(y,2),y(1,:),'r.')
% xlabel('time');
% ylabel('state');
% axis([0 30 0 5])


% fileName=strcat(folder, 'actions.dat');
% y = importdata(fileName);




%% multiple runs
% asset = {'pavimentos', 'aparelhos_apoio'};
% for a = 1:numel(asset)
% fid = fopen('problem.dat', 'w');
% fprintf(fid, '%s \n 1', asset{a});
% fclose(fid);
% 
% data.actions=cell(1,10);
% data.objectives=cell(1,10);
% data.states=cell(1,10);
% 
% for i=1:10
%     system('java -jar dist\sustims-moead.jar');
%     pause(1);
%     data.actions{i} = importdata('actions.dat');
%     data.objectives{i} = importdata('objectives.dat');
%     data.states{i} = importdata('states.dat');
%     fprintf('run : %d\n', i);
% end
% 
% save(asset{a},'data')
% 
% end
