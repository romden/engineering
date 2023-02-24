clear
clc


%% single run
% tic
% numberOfVariables = 337;
% lb = zeros(1, numberOfVariables);
% ub = ones(1, numberOfVariables);
% [objFunction, constFunction] = scalarization('eps');
% options=optimset('algorithm','sqp','MaxFunEvals',20000);
% 
% numberOfSubprob = 100;
% problem = ClassDengue(numberOfVariables); % problem object
% problem.idxFun = 1;
% problem.idxConst = 2;
% epsValues = linspace(0, problem.nadir(problem.idxConst), numberOfSubprob); % eps constraint values
% 
% XX = zeros(numberOfSubprob, numberOfVariables);
% for i = 2%:numberOfSubprob-1    
%     problem.epsConstraint = epsValues(i);
%     x0 = XX(i-1,:);    
%     x = fmincon(objFunction,x0,[],[],[],[],lb,ub,constFunction,options, problem);
%     XX(i,:) = x;
% end
% XX(end,:) = ones(1,Problem.Variables);
% 
% YY = zeros(size(XX,1),2);
% for i = 1:size(XX,1)
%     YY(i,:) = problem.evaluate(XX(i,:));
% end
% 
% plot(YY(:,1),YY(:,2),'ro');
% 
% t = toc





% tub = ClassTuberculosis(122);
% x = rand(100, 122);
% y1 = zeros(size(x,1),2);
% y2 = zeros(size(x,1),2);
% for i=1:size(x,1)
%     y1(i,:) = tuberculosis(x(i,:));
%     y2(i,:) = tub.evaluate(x(i,:));
% end
% plot(y1(:,1),y1(:,2),'ro', y2(:,1),y2(:,2),'g*');

% tub = ClassTuberculosis(122);


%
% Problem.objFunction=@eps_fun;
% Problem.constraints=@eps_constr;
% Problem.Variables=122;
% Problem.LB=zeros(1,Problem.Variables);
% Problem.UB=ones(1,Problem.Variables);
%
% Problem.x0 = zeros(1,Problem.Variables);
% options=optimset('algorithm','sqp','MaxFunEvals',20000);
% x = fmincon(Problem.objFunction,Problem.x0,[],[],[],[],Problem.LB,Problem.UB,Problem.constraints,options, tub);
%


%% plot fronts
% res = {'eps12_0_5', 'eps12_robust'};
% colors = {'bo', 'ro'};
%
% figure(1)
% for i=1:numel(res)
%     load(res{i})
%     hold on
%     plot(YY(:,1),YY(:,2),colors{i})
% end

% load(res{1})
% tub = ClassTuberculosis(122);
% % [f_1, S_1, L1_1, I_1, L2_1, R_1] = tub.evaluate(XX(50,:));
% f_1 = tub.evaluate(XX(50,:));
% [f_2, S_2, L1_2, I_2, L2_2, R_2] = tuberculosis(XX(50,:));
% [f_1'; f_2']
% plot(1:numel(tub.getS), tub.getS, 'b.', 1:20, S_2(1:20), 'r.')




%% controls and infected for different beta
% color = {'b.', 'go', 'rx', 'm*'};
% beta = [75, 100, 150, 175];
% eeps = [2.5, 5, 7.5];
%
% i=3; % point
% control = 2;
%
% figure(1)
% for k = 1:numel(beta)
%     load(strcat('beta_', num2str(beta(k))));
%     idx = find((YY(:,2) - eeps(i)<=0), 1, 'last');
%     hold on
%     if control == 1
%         plot(linspace(0, 5, size(XX,2)/2), XX(idx,1:size(XX,2)/2),color{k})
%     elseif control == 2
%         plot(linspace(0, 5, size(XX,2)/2), XX(idx,size(XX,2)/2+1:end),color{k})
%     end
% end
% legend('\beta=75', '\beta=100', '\beta=150', '\beta=175')
% xlabel('t', 'FontSize', 14)
% ylabel(['u_', num2str(control)], 'FontSize', 14)
% axis([0 5 0 1])
%
% figure(2)
% addpath('problems');
% for k = 1:numel(beta)
%
%     load(strcat('beta_', num2str(beta(k))));
%     idx = find((YY(:,2) - eeps(i)<=0), 1, 'last');
%     x = XX(idx,:); % x = zeros(122, 1); % x = ones(122, 1);
%
%     fun = strcat('tuberculosis_beta_', num2str(beta(k)));
%
%     [f, S, L1, I, L2, R] = feval(fun, x);
%
%     hold on
%     plot(linspace(0, 5, numel(x)/2), I+L2 ,color{k});
% %     plot(linspace(0, 5, numel(x)/2), I ,color{k});
% %         plot(linspace(0, 5, numel(x)/2), L2 ,color{k});
%
% end
% legend('\beta=75', '\beta=100', '\beta=150', '\beta=175')
% xlabel('t', 'FontSize', 14);
% ylabel('I+L_2', 'FontSize', 14);
% % ylabel('I', 'FontSize', 14);
% % ylabel('L_2', 'FontSize', 14);


