function [fobj, fconst] = scalarization(name)

switch name
    case 'wsum'
        fobj = @wsum_fun;
        fconst = [];
    case 'chb'
        fobj = @chb_fun;
        fconst = [];
    case 'wmetric'
        fobj = @wmetric_fun;
        fconst = [];
    case 'eps'
        fobj = @eps_fun;
        fconst = @eps_const;
    case 'nc'
        fobj = @nc_fun;
        fconst = @nc_const;
    case 'gam'
        fobj = @gam_fun;
        fconst = @gam_const;
end

return

%% weighted sum method
function [f] = wsum_fun(x, method, varargin)

F = feval(method.fun, x);

f = method.w(:)'*(F(:)- method.ref(:));

return


%% Chebyshev method
function [f] = chb_fun(x, method, varargin)

F = feval(method.fun, x);

f = max(method.w(:).*(F(:)- method.ref(:)));

return


%% weighted sum method
function [f] = wmetric_fun(x, method, varargin)

F = feval(method.fun, x);

f = method.w(:)'*power(F(:)-method.ref(:), method.p);

f = power(f, 1/method.p);

return



%% epsilon constraint method
function [f] = eps_fun(x, method, varargin)

[F] = feval(method.fun, x, varargin{:});
f = F(2);

return

function [c, h] = eps_const(x, method, varargin)

[F] = feval(method.fun, x, varargin{:});
c = F(1) - method.eps;

h =[];

return


%% normal constraint method
function [f] = nc_fun(x, nc, varargin)

F = feval(nc.fun,x);
F =(F(:)'-nc.ideal)./(nc.nadir-nc.ideal);
f = F(end);

return

function [g, h] = nc_const(x, nc, varargin)

f=feval(nc.fun,x);
F=(f(:)'-nc.ideal)./(nc.nadir-nc.ideal);

Z=nc.w(:);
M=numel(f);

g=zeros(1,M-1);
for i=1:M-1
    N=nc.Phi(:,end) - nc.Phi(:,i);
    N=(N'-nc.ideal)./(nc.nadir-nc.ideal);
    g(i)=N*(F(:)-Z);
end

h=[];

return


%% goal attainment method
function [f] = gam_fun(x, gam, varargin)

f = x(end); % get alpha

return

function [c, h] = gam_const(x, gam, varargin)

[F] = feval(gam.fun, x(1:end-1), varargin{:});

c = gam.w(:).*(F(:) - gam.ref(:)) - x(end);
h =[];

return