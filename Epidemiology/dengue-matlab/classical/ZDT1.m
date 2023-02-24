function f=ZDT1(x)
f=zeros(1,2);
f(1)=x(1);
g=1+9*sum(x(2:end))/(length(x)-1);
f(2)=g*(1-sqrt(f(1)/g));
return