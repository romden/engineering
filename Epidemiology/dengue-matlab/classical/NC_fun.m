function f=NC_fun(x)
load nc
f=feval(nc.fun,x);
f=(f-nc.ideal)./(nc.nadir-nc.ideal);
f=f(end);
