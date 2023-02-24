function [c, h] = Eps_constr(x, fun, ee, varargin)
% global ee
% [F] = dengue(x);
% [F] = ZDT1(x);
[F] = feval(fun, x, varargin{:});
c = F(1) - ee;

h =[];

return