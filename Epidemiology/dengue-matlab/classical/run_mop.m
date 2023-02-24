clear
clc

% problem definition
% Problem.ObjFunction='dengue';
% Problem.Variables=1001;
% Problem.LB=zeros(1,Problem.Variables);
% Problem.UB=ones(1,Problem.Variables);
% % Problem.x0 = zeros(Problem.Variables,1);
% Problem.method='nc';

% Problem.Phi=[0.004192834133700 2.76892993074425;
%              84.000000000000000 0];

% load resNC
% Problem.x0 = XX(5,:);
% Problem.Phi=[0.0351 2.76892993074425;
%              9.3896 0];
% 
% [XX, YY, nf] =  MO_fmincon(Problem)
% 
% % YY=nondomination(YY);
% plot(YY(:,1),YY(:,2),'r.');


%% process
% load resNC
% y1=YY;
% load Im-u
% y2 = zeros(size(XX,1),2);
% for i=1:size(XX,1)
%     y2(i,:) = dengue(XX(i,:));
% end
% 
% plot(y1(:,1),y1(:,2),'r.', y2(:,1),y2(:,2),'b.');

% plot(linspace(0,84,1001),XX(15,:),'g.')

%% epsilon constraint method
fun = 'dengue'; eeps=linspace(0.0042, 2.77, 100); Problem.Variables=1001;

% fun = 'dengue'; eeps=linspace(0.0001, 1.485, 100); Problem.Variables=1001;

options=optimset('algorithm','sqp','MaxFunEvals',10000);

Problem.objFunction=@Eps_fun;
Problem.constraints=@Eps_constr;
Problem.LB=zeros(1,Problem.Variables);
Problem.UB=ones(1,Problem.Variables);
% Problem.x0 = ones(1, Problem.Variables);

% XX = ones(100, Problem.Variables); 
load('Ih-u')
for i = 82:99
    Problem.x0 = XX(i-1,:);
    x = fmincon(Problem.objFunction,Problem.x0,[],[],[],[],Problem.LB,Problem.UB,Problem.constraints,options, fun, eeps(i));
    XX(i,:)=x;
end
XX(end,:) = zeros(1,Problem.Variables);

YY = zeros(size(XX,1),2);
for i=1:size(XX,1)
    YY(i,:) = feval(fun, XX(i,:));
end
plot(YY(:,1),YY(:,2),'r.');

save('Ih-u','XX','YY');

%% test ZDT1
% Problem.ObjFunction=@ZDT1;
% Problem.Variables=30;
% Problem.LB=zeros(1,Problem.Variables);
% Problem.UB=ones(1,Problem.Variables);
% Problem.x0 = zeros(Problem.Variables,1);
% Problem.method='nc';
% Problem.Phi=[0 1;
%              1 0];
%       
% [XX, YY, nf] =  MO_fmincon(Problem);
% 
% plot(YY(:,1),YY(:,2),'r.');
