function [g,h]=NBI_constr(x)

g=[];

load nbi
Phi=nbi.Phi;
w=nbi.w;
fun=nbi.fun;

F=feval(fun,x(1:end-1));
t=x(end);
e=ones(numel(F),1);
n=-Phi*e;

h=Phi*w(:)+t*n-F(:);
h=h';



% Phi=importdata('Phi.txt');
% w=importdata('w.txt');
% 
% fid = fopen('fun.txt');
% fun=fgetl(fid);
% fclose(fid);