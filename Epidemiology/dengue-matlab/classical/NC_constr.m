function [g,h]=NC_constr(x)

load nc
f=feval(nc.fun,x);
F=(f-nc.ideal)./(nc.nadir-nc.ideal);
Z=nc.Phi*nc.w(:);
M=numel(f);

g=zeros(1,M-1);
for i=1:M-1
    N=nc.Phi(:,end)-nc.Phi(:,i);
    N=(N'-nc.ideal)./(nc.nadir-nc.ideal);
    g(i)=N*(F(:)-Z);
end

h=[];

return
