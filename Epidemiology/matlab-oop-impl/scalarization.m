function [fobj, fconst] = scalarization(name)

switch name
    case 'eps'
        fobj = @eps_fun;
        fconst = @eps_const;
end

return

function [f] = eps_fun(x, problem, varargin)

F = problem.evaluate(x);

f = F(problem.idxFun);

return

function [c, h] = eps_const(x, problem, varargin)

F = problem.evaluate(x);

c = zeros(1, numel(problem.idxConst));
for i = 1:numel(problem.idxConst)
    c(i) = F(problem.idxConst(i)) - problem.epsConstraint(i);
end

h =[];

return