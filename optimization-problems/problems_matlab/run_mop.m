clear
clc

addpath(genpath('moeas'));
% addpath(genpath('polar_variation'));

% prob='ZDT2';
% prob='DTLZ2'; 
% prob='LGZ1';
% prob='WFG8';
prob='LZ09_F2';
% prob='UF6';
% prob='L2ZDT4';
% prob='I5';
% prob='Kursawe';
% prob='OKA2';


Problem=get_struct(prob);

% number of objectives
Problem.m = 2;

% [A, Stat] = NSGA2(Problem)
% [A, Stat] = MOEADall(Problem, Problem.m)
% [A, Stat] = IBEA(Problem, Problem.m)
% [A, Stat] = MOEApc(Problem, Problem.m)

% [A, Stat, Arc] = MOEAD_archive(Problem, Problem.m)
% [A, Stat] = SMSEMOA(Problem, Problem.m)

% [A, Stat] = NSGA2rank(Problem)
% [A, Stat] = NSGA2pc(Problem)
% [A, Stat] = EMO_new_1(Problem, Problem.m)
% [A, Stat] = EMyOC_2015(Problem, Problem.m)
[A, Stat, Arc] = MOEAD_kernel(Problem, Problem.m)

% [A, Stat] = MOEADall_ga_rotation(Problem, Problem.m)

 
plot_front(A.f, prob)

% figure(2)
% y = A.f;
% if size(y,2) == 2
%     plot(y(:,1),y(:,2),'ro');
% elseif size(y,2) == 3
%     plot3(y(:,1),y(:,2),y(:,3),'r.');
%     y = A.f;
% else
%     for i=1:size(y,1)
%         hold on
%         plot(1:size(y,2),y(i,:), 'b');
%     end
% end


% figure(3)
% y = Arc.f;
% if size(y,2) == 2
%     plot(y(:,1),y(:,2),'ro');
% elseif size(y,2) == 3
%     plot3(y(:,1),y(:,2),y(:,3),'r.');
% end
