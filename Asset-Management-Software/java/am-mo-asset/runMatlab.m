clear
clc

% add path
addpath(genpath('Output'));


%% execute java package
% tic
% system('java -jar dist/asset-mo.jar param_problem_ponte.dat param_solver.dat param_output.dat');
% toc

%% plot pareto front
% y = importdata('objectives');
% plot(y(:,1),y(:,2),'ro')
% xlabel('performance');
% ylabel('cost');


% hold on
% y = importdata('objectives.arc');
% if size(y,2)==2
%     plot(y(:,1),y(:,2),'g.');
% elseif size(y,2)==3
%     plot3(y(:,1),y(:,2),y(:,3),'r*');
% end

%% plot states
figure(2)
fileName=strcat('states.arc');
y = importdata(fileName);
idx = 78;
plot(1:size(y,2),y(idx,:),'b.')
xlabel('time');
ylabel('state');
axis([0 numel(1:size(y,2)) 0 5])

% hold on
% plot(1:size(y,2),y(1,:),'r.')















%% actions
% fileName=strcat(folder, 'actions.dat');
% y = importdata(fileName);



%% calc states for components
% Q = {'../Assets/IntensityMatrices/Pav_iri.dat';
%     '../Assets/IntensityMatrices/Pav_rut.dat';
%     '../Assets/IntensityMatrices/Pav_ckr.dat';
%     '../Assets/IntensityMatrices/Pav_fri.dat'; 
%     '../Assets/IntensityMatrices/Pav_fwd.dat';};
% 
% effects = {'../Assets/MaintenanceActions/Effects/Pav_iri.dat'; 
%            '../Assets/MaintenanceActions/Effects/Pav_rut.dat'; 
%            '../Assets/MaintenanceActions/Effects/Pav_ckr.dat'; 
%            '../Assets/MaintenanceActions/Effects/Pav_fri.dat';
%            '../Assets/MaintenanceActions/Effects/Pav_fwd.dat';};
%  
% idx = 2;
% 
% system(['java -jar C:/SustIMS/performance/sustims-performance.jar', ' ', Q{idx}, ' ', 'params.dat', ' ', 'actions.dat', ' ', effects{idx}, ' ', 'output.dat 100'])
% 
% 
% %% plot states
% fileName=strcat('output.dat');
% y = importdata(fileName);
% idx = 1;
% plot(1:size(y,2),y(idx,:),'b.')
% xlabel('time');
% ylabel('state');
% axis([0 30 0 5])































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



% Q=[-0.25289420837327026 0.25289420837327026 0.0 0.0 0.0 ;
% 0.0 -0.06052186396042611 0.06052186396042611 0.0 0.0 ;
% 0.0 0.0 -0.05219325007888024 0.05219325007888024 0.0 ;
% 0.0 0.0 0.0 -0.05360549452068326 0.05360549452068326 ;
% 0.0 0.0 0.0 0.0 0.0 ];
% c=[0 1 0 0 0];
% v=[1:5]';
% y = zeros(1,30);
% for i=1:30
%     P=expm(Q*(2.25+(i-1)));
%     y(i)= (c*P)*v;
% end
% hold on
% plot(1:numel(y),y,'g.')
% axis([0 30 0 5])
% trapz(y)
