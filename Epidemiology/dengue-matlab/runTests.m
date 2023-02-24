clear
clc

% types = {'Ih-u', 'Im-u'};
% type = types{2};
% 
% Problem.objFunction = @dengue;
% Problem.n = 501;
% Problem.m = 2;
% Problem.lb = zeros(Problem.n,1);
% Problem.ub = ones(Problem.n,1);
% 
% 
% [A, Stat] = MOEADall(Problem, type)
% 
% 
% figure(2)
% if size(A.f,2)==2
%     plot(A.f(:,1),A.f(:,2),'ro');
%     xlabel('f_1');ylabel('f_2');
% elseif size(A.f,2)==3
%     plot3(A.f(:,1),A.f(:,2),A.f(:,3),'r.');
%     xlabel('f_1');ylabel('f_2');zlabel('f_3');
% end







%% check implementations
% x = rand(100, 501);
% % y1 = zeros(size(x,1),2);
% y2 = zeros(size(x,1),2);
% for i=1:size(x,1)
% %     y1(i,:) = dengue0(x(i,:));
%     y2(i,:) = dengue(x(i,:));
% end
% % plot(y1(:,1),y1(:,2),'ro', y2(:,1),y2(:,2),'g*');
% plot(y2(:,1),y2(:,2),'g*');


load('eps-Ih-u')
% YY = zeros(size(XX,1),2);
% for i=1:size(XX,1)
%     YY(i,:) = dengue(XX(i,:));
% end
plot(YY(:,1),YY(:,2),'g*');
