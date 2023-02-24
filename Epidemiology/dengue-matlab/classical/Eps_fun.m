function [f] = Eps_fun(x, fun, ee, varargin)

% [F] = dengue(x);
% [F] = ZDT1(x);
[F] = feval(fun, x, varargin{:});
f = F(2);

return