clear
clc

%% second obj is minimized; first is constraint
fun = 'dengue';
numberOfVariables = 337; % time interval of 0.25
numberOfSubprob = 100;

f = feval(fun, ones(1, numberOfVariables));
epsValues = linspace(0, f(1), numberOfSubprob);

[Problem.ObjFunction, Problem.Constraints] = scalarization('eps');
Problem.Variables=numberOfVariables;
Problem.LB=zeros(1,Problem.Variables);
Problem.UB=ones(1,Problem.Variables);
options=optimset('algorithm','sqp','MaxFunEvals',20000);

method.fun = fun;

XX = zeros(numberOfSubprob, Problem.Variables);
for i = 2:numberOfSubprob-1
    method.eps = epsValues(i);
    Problem.x0 = XX(i-1,:);
    x = fmincon(Problem.ObjFunction,Problem.x0,[],[],[],[],Problem.LB,Problem.UB,Problem.Constraints,options, method);
    XX(i,:)=x;
end
XX(end,:) = ones(1,Problem.Variables);

YY = zeros(size(XX,1),2);
for i=1:size(XX,1)
    YY(i,:) = feval(fun, XX(i,:));
end

save dengue_eps XX YY


%% Normal nonstraint method
fun = 'dengue';
numberOfVariables = 337;
numberOfSubprob = 100;

H = numberOfSubprob-1;
w=[];
t1=0:H;
w=t1';
w=[w H-sum(w,2)]/H;

[Problem.ObjFunction, Problem.Constraints] = scalarization('nc');
Problem.Variables = numberOfVariables;
Problem.LB = zeros(1,Problem.Variables);
Problem.UB = ones(1,Problem.Variables);
options=optimset('algorithm','sqp','MaxFunEvals',20000);

nc.Phi = [feval(fun, zeros(1, numberOfVariables))', feval(fun, ones(1, numberOfVariables))']; %[f1min, f2min] (f1min, f2min - are column vectors)
nc.fun = fun;
nc.ideal = min(nc.Phi, [], 2)';
nc.nadir = max(nc.Phi, [], 2)';

XX = zeros(numberOfSubprob, numberOfVariables);
for i = 2:size(w,1)-1
    
    nc.w = w(i,:);
    Problem.x0 = XX(i-1,:);
    
    x = fmincon(Problem.ObjFunction,Problem.x0,[],[],[],[],Problem.LB, Problem.UB, Problem.Constraints, options, nc);
    
    XX(i,:)=x;
end
XX(end,:) = ones(1,Problem.Variables);

YY = zeros(size(XX,1), 2);
for i = 1:size(XX,1)
    YY(i,:) = feval(fun, XX(i,:));
end

save dengue_nc XX YY


%% Chebyshev method
fun = 'dengue';
numberOfVariables = 337;
numberOfSubprob = 100;

H = numberOfSubprob-1;
w=[];
t1=0:H;
w=t1';
w=[w H-sum(w,2)]/H;

[Problem.ObjFunction, Problem.Constraints] = scalarization('chb');
Problem.Variables = numberOfVariables;
Problem.LB = zeros(1,Problem.Variables);
Problem.UB = ones(1,Problem.Variables);
options=optimset('algorithm','sqp','MaxFunEvals',20000);

chb.fun = fun;
chb.ref = min([feval(fun, zeros(1, numberOfVariables)); feval(fun, ones(1, numberOfVariables))]);

XX = zeros(numberOfSubprob, Problem.Variables);
for i = 2:size(w,1)-1
    
    chb.w = w(i,:);
    Problem.x0 = XX(i-1,:);
    
    x = fmincon(Problem.ObjFunction,Problem.x0,[],[],[],[],Problem.LB, Problem.UB, Problem.Constraints, options, chb);
    
    XX(i,:)=x;
end
XX(end,:) = ones(1,Problem.Variables);

YY = zeros(size(XX,1), 2);
for i = 1:size(XX,1)
    YY(i,:) = feval(fun, XX(i,:));
end

save dengue_chb XX YY


%% Goal attainment method
fun = 'dengue';
numberOfVariables = 337;
numberOfSubprob = 100;

H = numberOfSubprob-1;
w=[];
t1=0:H;
w=t1';
w=[w H-sum(w,2)]/H;

[Problem.ObjFunction, Problem.Constraints] = scalarization('gam');
Problem.Variables = numberOfVariables+1; % plus alpha value as varibale
Problem.LB = zeros(1,Problem.Variables);
Problem.UB = ones(1,Problem.Variables);
options=optimset('algorithm','sqp','MaxFunEvals',20000);

gam.fun = fun;
data = [feval(fun, zeros(1, numberOfVariables)); feval(fun, ones(1, numberOfVariables))];
gam.ref = min(data);

alphaUB = linspace(data(2,1)-data(2,2), data(1,2)-data(1,1), numberOfSubprob);

XX = zeros(numberOfSubprob, Problem.Variables);
for i = 2:numberOfSubprob-1
    
    gam.w = w(i,:);
    %     Problem.LB(end) = alphaUB(i-1);
    Problem.UB(end) = alphaUB(i);
    
    Problem.x0 = zeros(1, Problem.Variables);
    
    x = fmincon(Problem.ObjFunction,Problem.x0,[],[],[],[],Problem.LB,Problem.UB,Problem.Constraints, options, gam);
    XX(i,:)=x;
    
end
XX(end,:) = ones(1, Problem.Variables);
XX(:,end) = [];

YY = zeros(size(XX,1),2);
for i=1:size(XX,1)
    YY(i,:) = feval(fun, XX(i,:));
end
plot(YY(:,1), YY(:,2), 'ro');

save dengue_gam XX YY

