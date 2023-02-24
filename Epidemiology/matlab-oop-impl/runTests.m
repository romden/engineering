function runTests()

global problem;

tic
objFunction = @eps_fun;
constFunction = @eps_const;
numberOfVariables = 337;
lb = zeros(1, numberOfVariables);
ub = ones(1, numberOfVariables);
options=optimset('algorithm','sqp','MaxFunEvals',20000);

numberOfSubprob = 100;
problem = ClassDengue(numberOfVariables); % problem object
problem.idxFun = 1;
problem.idxConst = 2;
epsValues = linspace(0, problem.nadir(problem.idxConst), numberOfSubprob); % eps constraint values

XX = zeros(numberOfSubprob, numberOfVariables);
for i = 2%:numberOfSubprob-1    
    problem.epsConstraint = epsValues(i);
    x0 = XX(i-1,:);    
    x = fmincon(objFunction,x0,[],[],[],[],lb,ub,constFunction,options);
    XX(i,:) = x;
end
XX(end,:) = ones(1,Problem.Variables);

YY = zeros(size(XX,1),2);
for i = 1:size(XX,1)
    YY(i,:) = problem.evaluate(XX(i,:));
end
disp(toc)
return


function [f] = eps_fun(x, varargin)
global problem;
F = problem.evaluate(x);

f = F(problem.idxFun);

return

function [c, h] = eps_const(x, varargin)
global problem;
F = problem.evaluate(x);

c = zeros(1, numel(problem.idxConst));
for i = 1:numel(problem.idxConst)
    c(i) = F(problem.idxConst(i)) - problem.epsConstraint(i);
end

h =[];

return