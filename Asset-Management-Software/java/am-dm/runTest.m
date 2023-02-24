clear
clc

pathName = sprintf('%s%s', pwd, '\dist\sustims-dm.jar');
% strrep(pathName,'\', '/');
javaaddpath(pathName);

DM = dm.DecisionMaking();

load PF
p = 1;
m = 2;

y = PF{p,m};

if size(y,2)==2
    plot(y(:,1), y(:,2), 'b.');
elseif size(y,2)==3
    plot3(y(:,1), y(:,2), y(:,3), 'b.');
end

w = [0.9 0.1];
% w = [1 0];
% w = [0.2 0.8];
% w = [0.6 0.2 0.2];


if size(y,2)==2
    
    idx = DM.execute(y, w, 'WCM');
    hold on
    plot(y(idx,1), y(idx,2), 'rx', 'MarkerSize', 12, 'LineWidth', 2.5)
    
%     idx = DM.execute(y, w, 'WSFM');
%     hold on
%     plot(y(idx,1), y(idx,2), 'gx', 'MarkerSize', 12, 'LineWidth', 2.5)
    
elseif size(y,2)==3
    
    idx = DM.execute(y, w, 'WCM');
    hold on
    plot3(y(idx,1), y(idx,2),  y(idx,3), 'rx', 'MarkerSize', 15, 'LineWidth', 3)
    
%     idx = DM.execute(y, w, 'WSFM');
%     hold on
%     plot3(y(idx,1), y(idx,2),  y(idx,3), 'gx', 'MarkerSize', 15, 'LineWidth', 3)
%     xlabel('f_1');ylabel('f_2');zlabel('f_3');
end








% plot(y(:,1), y(:,2), 'b.')
% javarmpath('C:\Users\Roman\Desktop\SustIMS-Optimization-Libraries\sustims-dm\dist\sustims-dm.jar');
% javaclasspath('-dynamic')
