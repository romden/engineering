function fobj = mop(name)

switch name
    case 'LGZ1'
        fobj = @LGZ1;
    case 'LGZ2'
        fobj = @LGZ2;
    case 'LGZ3'
        fobj = @LGZ3;
    case 'LGZ4'
        fobj = @LGZ4;
    case 'LGZ5'
        fobj = @LGZ5;
    case 'LGZ6'
        fobj = @LGZ6;
    case 'LGZ7'
        fobj = @LGZ7;
    case 'ZDT1'
        fobj = @ZDT1;
    case 'ZDT2'
        fobj = @ZDT2;
    case 'ZDT3'
        fobj = @ZDT3;
    case 'ZDT4'
        fobj = @ZDT4;
    case 'ZDT6'
        fobj = @ZDT6;
    case 'DTLZ1'
        fobj = @DTLZ1;
    case 'DTLZ2'
        fobj = @DTLZ2;
    case 'DTLZ3'
        fobj = @DTLZ3;
    case 'DTLZ4'
        fobj = @DTLZ4;
    case 'DTLZ5'
        fobj = @DTLZ5;
    case 'DTLZ6'
        fobj = @DTLZ6;
    case 'DTLZ7'
        fobj = @DTLZ7;
    case 'LZ09_F1'
        fobj = @LZ09_F1;
    case 'LZ09_F2'
        fobj = @LZ09_F2;
    case 'LZ09_F3'
        fobj = @LZ09_F3;
    case 'LZ09_F4'
        fobj = @LZ09_F4;
    case 'LZ09_F5'
        fobj = @LZ09_F5;
    case 'LZ09_F6'
        fobj = @LZ09_F6;
    case 'LZ09_F7'
        fobj = @LZ09_F7;
    case 'LZ09_F8'
        fobj = @LZ09_F8;
    case 'LZ09_F9'
        fobj = @LZ09_F9;
    case 'L1ZDT1'
        fobj = @L1ZDT1;
    case 'L1ZDT2'
        fobj = @L1ZDT2;
    case 'L1ZDT3'
        fobj = @L1ZDT3;
    case 'L1ZDT4'
        fobj = @L1ZDT4;
    case 'L1ZDT6'
        fobj = @L1ZDT6;
    case 'L2ZDT1'
        fobj = @L2ZDT1;
    case 'L2ZDT2'
        fobj = @L2ZDT2;
    case 'L2ZDT3'
        fobj = @L2ZDT3;
    case 'L2ZDT4'
        fobj = @L2ZDT4;
    case 'L2ZDT6'
        fobj = @L2ZDT6;
    case 'L3ZDT1'
        fobj = @L3ZDT1;
    case 'L3ZDT2'
        fobj = @L3ZDT2;
    case 'L3ZDT3'
        fobj = @L3ZDT3;
    case 'L3ZDT4'
        fobj = @L3ZDT4;
    case 'L3ZDT6'
        fobj = @L3ZDT6;
    case 'WFG1'
        fobj = @WFG1;
    case 'WFG2'
        fobj = @WFG2;
    case 'WFG3'
        fobj = @WFG3;
    case 'WFG4'
        fobj = @WFG4;
    case 'WFG5'
        fobj = @WFG5;
    case 'WFG6'
        fobj = @WFG6;
    case 'WFG7'
        fobj = @WFG7;
    case 'WFG8'
        fobj = @WFG8;
    case 'WFG9'
        fobj = @WFG9;
    case 'UF1'
        fobj = @UF1;
    case 'UF2'
        fobj = @UF2;
    case 'UF3'
        fobj = @UF3;
    case 'UF4'
        fobj = @UF4;
    case 'UF5'
        fobj = @UF5;
    case 'UF6'
        fobj = @UF6;
    case 'UF7'
        fobj = @UF7;
    case 'UF8'
        fobj = @UF8;
    case 'UF9'
        fobj = @UF9;
    case 'UF10'
        fobj = @UF10;
    case 'I1'
        fobj = @I1;
    case 'I2'
        fobj = @I2;
    case 'I3'
        fobj = @I3;
    case 'I4'
        fobj = @I4;
    case 'I5'
        fobj = @I5;
    case 'Kursawe'
        fobj=@Kursawe;
    case 'OKA1'
        fobj=@OKA1;
    case 'OKA2'
        fobj=@OKA2;
end

return

function f=LGZ1(x, varargin)
f=zeros(2,1);
t=x(2:end)-sin(0.5*pi*x(1));
g=2*sin(pi*x(1))*sum(-0.9*t.^2+power(abs(t),0.6));
f(1)=(1+g)*x(1);
f(2)=(1+g)*(1-sqrt(x(1)));
return

function f=LGZ2(x, varargin)
f=zeros(2,1);
t=x(2:end)-sin(0.5*pi*x(1));
g=10*sin(pi*x(1))*sum(abs(t)./(1+exp(5*abs(t))));
f(1)=(1+g)*x(1);
f(2)=(1+g)*(1-x(1)^2);
return

function f=LGZ3(x, varargin)
f=zeros(2,1);
t=x(2:end)-sin(0.5*pi*x(1));
g=10*sin(pi*x(1)/2)*sum(abs(t)./(1+exp(5*abs(t))));
f(1)=(1+g)*cos(pi*x(1)/2);
f(2)=(1+g)*sin(pi*x(1)/2);
return

function f=LGZ4(x, varargin)
f=zeros(2,1);
t=x(2:end)-sin(0.5*pi*x(1));
% g=1+10*sin(pi*x(1))*sum(abs(t)./(1+exp(5*abs(t))));
g=10*sin(pi*x(1))*sum(abs(t)./(1+exp(5*abs(t))));
f(1)=(1+g)*x(1);
f(2)=(1+g)*(1-power(x(1),0.5)*power(cos(2*pi*x(1)),2));
return

function f=LGZ5(x, varargin)
f=zeros(2,1);
t=x(2:end)-sin(0.5*pi*x(1));
g=2*abs(cos(pi*x(1)))*sum(-0.9*t.^2+power(abs(t),0.6));
f(1)=(1+g)*x(1);
f(2)=(1+g)*(1-sqrt(x(1)));
return

function f=LGZ6(x, varargin)
f=zeros(3,1);
t=x(3:end)-x(1)*x(2);
g=2*sin(pi*x(1))*sum(-0.9*t.^2+power(abs(t),0.6));
f(1)=(1+g)*x(1)*x(2);
f(2)=(1+g)*x(1)*(1-x(2));
f(3)=(1+g)*(1-x(1));
return

function f=LGZ7(x, varargin)
f=zeros(3,1);
t=x(3:end)-x(1)*x(2);
g=2*sin(pi*x(1))*sum(-0.9*t.^2+power(abs(t),0.6));
f(1)=(1+g)*cos(x(1)*pi/2)*cos(x(2)*pi/2);
f(2)=(1+g)*cos(x(1)*pi/2)*sin(x(2)*pi/2);
f(3)=(1+g)*sin(x(1)*pi/2);
return

function f=ZDT1(x, varargin)
f=zeros(2,1);
f(1)=x(1);
g=1+9*sum(x(2:end))/(length(x)-1);
f(2)=g*(1-sqrt(f(1)/g));
return

function f=ZDT2(x, varargin)
f=zeros(2,1);
f(1)=x(1);
g=1+9*sum(x(2:end))/(length(x)-1);
f(2)=g*(1-(f(1)/g)^2);
return

function f=ZDT3(x, varargin)
f=zeros(2,1);
f(1)=x(1);
g=1+9*sum(x(2:end))/(length(x)-1);
f(2)=g*(1-sqrt(f(1)/g)-f(1)*sin(10*pi*f(1))/g);
return

function f=ZDT4(x, varargin)
f=zeros(2,1);
f(1)=x(1);
g=1+10*(length(x)-1)+sum(x(2:end).^2-10*cos(4*pi*x(2:end)));
f(2)=g*(1-sqrt(f(1)/g));
return

function f=ZDT6(x, varargin)
f=zeros(2,1);
f(1)=1-exp(-4*x(1))*sin(6*pi*x(1))^6;
g=1+9*(sum(x(2:end))/(length(x)-1))^0.25;
f(2)=g*(1-(f(1)/g)^2);
return

function [y] = DTLZ1(x, M)

% Objective function : DTLZ1 
y = zeros(M,1);
g=100*(numel(x(M:end))+sum((x(M:end)-0.5).^2-cos(20*pi*(x(M:end)-0.5))));
% y(M)
t = 1 + g;
y(M) = 0.5 * (1-x(1)) * t;
% y(M) - y(2)
for i = (M-1):-1:2
    t = x(M-i)* t ;
    y(i) = 0.5 *(1-x(M-i+1))* t ;
end
% y(1)
y(1) = 0.5 * x(M-1) * t;
return

function [y] = DTLZ2(x, M)

% Objective function : DTLZ2
y = zeros(M,1);
g = sum((x(M:end)-0.5).^2);
x = x*pi/2;
% y(M)
t = 1 + g;
y(M) = t * sin(x(1));
% y(M-1) - y(2)
for i = (M-1):-1:2
    t = t * cos( x(M-i) );
    y(i) = t * sin( x(M-i+1) );
end
% y(1)
y(1) = t * cos( x(M-1) );
return

function [y] = DTLZ3(x, M)

% Objective function : DTLZ3
y = zeros(M,1);
g=100*(numel(x(M:end))+sum((x(M:end)-0.5).^2-cos(20*pi*(x(M:end)-0.5))));
x = x*pi/2;
% y(M)
t = 1 + g;
y(M) = t * sin(x(1));
% y(M-1) - y(2)
for i = (M-1):-1:2
    t = t * cos( x(M-i) );
    y(i) = t * sin( x(M-i+1) );
end
% y(1)
y(1) = t * cos( x(M-1) );
return

function [y] = DTLZ4(x, M)

% Objective function : DTLZ4
y = zeros(M,1);
g = sum((x(M:end)-0.5).^2);
x = (x.^100)*pi/2;
% y(M)
t = 1 + g;
y(M) = t * sin(x(1));
% y(M-1) - y(2)
for i = (M-1):-1:2
    t = t * cos( x(M-i) );
    y(i) = t * sin( x(M-i+1) );
end
% y(1)
y(1) = t * cos( x(M-1) );
return

function [y] = DTLZ5(x, M)

% Objective function : DTLZ5
y = zeros(M,1);
theta=zeros(1,numel(x));
g = sum((x(M:end)-0.5).^2);
theta(1)=x(1)*pi/2;
theta(2:end)=0.25*pi*(1+2*x(2:end)*g)/(1+g);
% y(M)
t = 1 + g;
y(M) = t * sin(theta(1));
% y(M-1) - y(2)
for i = (M-1):-1:2
    t = t * cos( theta(M-i) );
    y(i) = t * sin( theta(M-i+1) );
end
% y(1)
y(1) = t * cos( theta(M-1) );
return

function [y] = DTLZ6(x, M)
% Objective function : DTLZ6
y = zeros(M,1);
theta=zeros(1,numel(x));
g = sum(x(M:end).^0.1);
theta(1)=x(1)*pi/2;
theta(2:end)=0.25*pi*(1+2*x(2:end)*g)/(1+g);
% y(M)
t = 1 + g;
y(M) = t * sin(theta(1));
% y(M-1) - y(2)
for i = (M-1):-1:2
    t = t * cos( theta(M-i) );
    y(i) = t * sin( theta(M-i+1) );
end
% y(1)
y(1) = t * cos( theta(M-1) );
return

function [y] = DTLZ7(x, M)
% Objective function : DTLZ7
y = zeros(M,1);
% y(1) - y(M-1)
y(1:M-1)=x(1:M-1);
g = 1+9*sum(x(M:end))/numel(x(M:end));
h=M-sum(y(1:M-1).*(1+sin(3*pi*y(1:M-1)))/(1+g));
% y(M)
y(M)=(1+g)*h;
return

function f=LZ09_F1(x, varargin)
f=zeros(2,1);
n=length(x);
sum1=0;
J1=0;
for j=3:2:n
    sum1=sum1+(x(j)-x(1)^(0.5*(1+3*(j-2)/(n-2))))^2;
    J1=J1+1;
end
f(1)=x(1)+2*sum1/J1;
sum2=0;
J2=0;
for j=2:2:n
    sum2=sum2+(x(j)-x(1)^(0.5*(1+3*(j-2)/(n-2))))^2;
    J2=J2+1;
end
f(2)=1-sqrt(x(1))+2*sum2/J2;
return

function f=LZ09_F2(x, varargin)
f=zeros(2,1);
n=length(x);
sum1=0;
J1=0;
for j=3:2:n
    sum1=sum1+(x(j)-sin(6*pi*x(1)+j*pi/n))^2;
    J1=J1+1;
end
f(1)=x(1)+2*sum1/J1;
sum2=0;
J2=0;
for j=2:2:n
    sum2=sum2+(x(j)-sin(6*pi*x(1)+j*pi/n))^2;
    J2=J2+1;
end
f(2)=1-sqrt(x(1))+2*sum2/J2;
return

function f=LZ09_F3(x, varargin)
f=zeros(2,1);
n=length(x);
sum1=0;
J1=0;
for j=3:2:n
    sum1=sum1+(x(j)-0.8*x(1)*cos(6*pi*x(1)+j*pi/n))^2;
    J1=J1+1;
end
f(1)=x(1)+2*sum1/J1;
sum2=0;
J2=0;
for j=2:2:n
    sum2=sum2+(x(j)-0.8*x(1)*sin(6*pi*x(1)+j*pi/n))^2;
    J2=J2+1;
end
f(2)=1-sqrt(x(1))+2*sum2/J2;
return

function f=LZ09_F4(x, varargin)
f=zeros(2,1);
n=length(x);
sum1=0;
J1=0;
for j=3:2:n
    sum1=sum1+(x(j)-0.8*x(1)*cos((6*pi*x(1)+j*pi/n)/3))^2;
    J1=J1+1;
end
f(1)=x(1)+2*sum1/J1;
sum2=0;
J2=0;
for j=2:2:n
    sum2=sum2+(x(j)-0.8*x(1)*sin(6*pi*x(1)+j*pi/n))^2;
    J2=J2+1;
end
f(2)=1-sqrt(x(1))+2*sum2/J2;
return

function f=LZ09_F5(x, varargin)
f=zeros(2,1);
n=length(x);
sum1=0;
J1=0;
for j=3:2:n
    sum1=sum1+(x(j)-(0.3*x(1)^2*cos(24*pi*x(1)+4*j*pi/n)+0.6*x(1))*cos(6*pi*x(1)+j*pi/n))^2;
    J1=J1+1;
end
f(1)=x(1)+2*sum1/J1;
sum2=0;
J2=0;
for j=2:2:n
    sum2=sum2+(x(j)-(0.3*x(1)^2*cos(24*pi*x(1)+4*j*pi/n)+0.6*x(1))*sin(6*pi*x(1)+j*pi/n))^2;
    J2=J2+1;
end
f(2)=1-sqrt(x(1))+2*sum2/J2;
return

function f=LZ09_F6(x, varargin)
f=zeros(3,1);
n=length(x);
sum1=0;
J1=0;
for j=4:3:n
    sum1=sum1+(x(j)-2*x(2)*sin(2*pi*x(1)+j*pi/n))^2;
    J1=J1+1;
end
f(1)=cos(0.5*x(1)*pi)*cos(0.5*x(2)*pi)+2*sum1/J1;
sum2=0;
J2=0;
for j=5:3:n
    sum2=sum2+(x(j)-2*x(2)*sin(2*pi*x(1)+j*pi/n))^2;
    J2=J2+1;
end
f(2)=cos(0.5*x(1)*pi)*sin(0.5*x(2)*pi)+2*sum2/J2;
sum3=0;
J3=0;
for j=3:3:n
    sum3=sum3+(x(j)-2*x(2)*sin(2*pi*x(1)+j*pi/n))^2;
    J3=J3+1;
end
f(3)=sin(0.5*x(1)*pi)+2*sum3/J3;
return

function f=LZ09_F7(x, varargin)
f=zeros(2,1);
n=length(x);
sum1=0;
J1=0;
for j=3:2:n
    y=x(j)-x(1)^(0.5*(1+3*(j-2)/(n-2)));
    sum1=sum1+(4*y^2-cos(8*y*pi)+1);
    J1=J1+1;
end
f(1)=x(1)+2*sum1/J1;
sum2=0;
J2=0;
for j=2:2:n
    y=x(j)-x(1)^(0.5*(1+3*(j-2)/(n-2)));
    sum2=sum2+(4*y^2-cos(8*y*pi)+1);
    J2=J2+1;
end
f(2)=1-sqrt(x(1))+2*sum2/J2;
return

function f=LZ09_F8(x, varargin)
f=zeros(2,1);
n=length(x);
sum1=0;
prod1=1;
J1=0;
for j=3:2:n
    y=x(j)-x(1)^(0.5*(1+3*(j-2)/(n-2)));
    sum1=sum1+y^2;
    prod1=prod1*cos(20*y*pi/sqrt(j));
    J1=J1+1;
end
f(1)=x(1)+2*(4*sum1-2*prod1+2)/J1;
sum2=0;
prod2=1;
J2=0;
for j=2:2:n
    y=x(j)-x(1)^(0.5*(1+3*(j-2)/(n-2)));
    sum2=sum2+y^2;
    prod2=prod2*cos(20*y*pi/sqrt(j));
    J2=J2+1;
end
f(2)=1-sqrt(x(1))+2*(4*sum2-2*prod2+2)/J2;
return


function f=LZ09_F9(x, varargin)
f=zeros(2,1);
n=length(x);
sum1=0;
J1=0;
for j=3:2:n
    sum1=sum1+(x(j)-sin(6*pi*x(1)+j*pi/n))^2;
    J1=J1+1;
end
f(1)=x(1)+2*sum1/J1;
sum2=0;
J2=0;
for j=2:2:n
    sum2=sum2+(x(j)-sin(6*pi*x(1)+j*pi/n))^2;
    J2=J2+1;
end
f(2)=1-x(1)^2+2*sum2/J2;
return

function f=L1ZDT1(x, varargin)
if size(x,2)>size(x,1)
    x=x';
end
f=zeros(2,1);
load M
M(1,:)=0; M(:,1)=0; M(1,1)=1;
n=length(x);
M=M(1:n,1:n);
y=M*x;
f(1)=y(1)^2;
g=1+9*sum(y(2:end).^2)/(length(y)-1);
h=1-sqrt(f(1)/g);
f(2)=g*h;
return

function f=L1ZDT2(x, varargin)
if size(x,2)>size(x,1)
    x=x';
end
f=zeros(2,1);
load M
M(1,:)=0; M(:,1)=0; M(1,1)=1;
n=length(x);
M=M(1:n,1:n);
y=M*x;
f(1)=y(1)^2;
g=1+9*sum(y(2:end).^2)/(length(y)-1);
h=1-(f(1)/g)^2;
f(2)=g*h;
return

function f=L1ZDT3(x, varargin)
if size(x,2)>size(x,1)
    x=x';
end
f=zeros(2,1);
load M
M(1,:)=0; M(:,1)=0; M(1,1)=1;
n=length(x);
M=M(1:n,1:n);
y=M*x;
f(1)=y(1)^2;
g=1+9*sum(y(2:end).^2)/(length(y)-1);
h=1-sqrt(f(1)/g)-f(1)*sin(10*pi*f(1))/g;
f(2)=g*h;
return

function f=L1ZDT4(x, varargin)
if size(x,2)>size(x,1)
    x=x';
end
f=zeros(2,1);
load M
M(1,:)=0; M(:,1)=0; M(1,1)=1;
n=length(x);
M=M(1:n,1:n);
y=M*x;
f(1)=y(1)^2;
g=1+10*(length(y)-1)+sum(y(2:end).^2-10*cos(4*pi*y(2:end)));
h=1-sqrt(f(1)/g);
f(2)=g*h;
return

function f=L1ZDT6(x, varargin)
if size(x,2)>size(x,1)
    x=x';
end
f=zeros(2,1);
load M
M(1,:)=0; M(:,1)=0; M(1,1)=1;
n=length(x);
M=M(1:n,1:n);
y=M*x;
f(1)=y(1)^2;
g=1+9*(sum(y(2:end).^2)/(length(y)-1))^0.25;
h=1-(f(1)/g)^2;
f(2)=g*h;
return

function f=L2ZDT1(x, varargin)
if size(x,2)>size(x,1)
    x=x';
end
f=zeros(2,1);
load M
n=length(x);
M=M(1:n,1:n);
y=M*x;
f(1)=y(1)^2;
g=1+9*sum(y(2:end).^2)/(length(y)-1);
h=1-sqrt(f(1)/g);
f(2)=g*h;
return

function f=L2ZDT2(x, varargin)
if size(x,2)>size(x,1)
    x=x';
end
f=zeros(2,1);
load M
n=length(x);
M=M(1:n,1:n);
y=M*x;
f(1)=y(1)^2;
g=1+9*sum(y(2:end).^2)/(length(y)-1);
h=1-(f(1)/g)^2;
f(2)=g*h;
return

function f=L2ZDT3(x, varargin)
if size(x,2)>size(x,1)
    x=x';
end
f=zeros(2,1);
load M
n=length(x);
M=M(1:n,1:n);
y=M*x;
f(1)=y(1)^2;
g=1+9*sum(y(2:end).^2)/(length(y)-1);
h=1-sqrt(f(1)/g)-f(1)*sin(10*pi*f(1))/g;
f(2)=g*h;
return

function f=L2ZDT4(x, varargin)
if size(x,2)>size(x,1)
    x=x';
end
f=zeros(2,1);
load M
n=length(x);
M=M(1:n,1:n);
y=M*x;
f(1)=y(1)^2;
g=1+10*(length(y)-1)+sum(y(2:end).^2-10*cos(4*pi*y(2:end)));
h=1-sqrt(f(1)/g);
f(2)=g*h;
return

function f=L2ZDT6(x, varargin)
if size(x,2)>size(x,1)
    x=x';
end
f=zeros(2,1);
load M
n=length(x);
M=M(1:n,1:n);
y=M*x;
f(1)=y(1)^2;
g=1+9*(sum(y(2:end).^2)/(length(y)-1))^0.25;
h=1-(f(1)/g)^2;
f(2)=g*h;
return

function f=L3ZDT1(x, varargin)
if size(x,2)>size(x,1)
    x=x';
end
f=zeros(2,1);
load M
n=length(x);
M=M(1:n,1:n);
y=M*(x.^2);
f(1)=y(1)^2;
g=1+9*sum(y(2:end).^2)/(length(y)-1);
h=1-sqrt(f(1)/g);
f(2)=g*h;
return

function f=L3ZDT2(x, varargin)
if size(x,2)>size(x,1)
    x=x';
end
f=zeros(2,1);
load M
n=length(x);
M=M(1:n,1:n);
y=M*(x.^2);
f(1)=y(1)^2;
g=1+9*sum(y(2:end).^2)/(length(y)-1);
h=1-(f(1)/g)^2;
f(2)=g*h;
return

function f=L3ZDT3(x, varargin)
if size(x,2)>size(x,1)
    x=x';
end
f=zeros(2,1);
load M
n=length(x);
M=M(1:n,1:n);
y=M*(x.^2);
f(1)=y(1)^2;
g=1+9*sum(y(2:end).^2)/(length(y)-1);
h=1-sqrt(f(1)/g)-f(1)*sin(10*pi*f(1))/g;
f(2)=g*h;
return

function f=L3ZDT4(x, varargin)
if size(x,2)>size(x,1)
    x=x';
end
f=zeros(2,1);
load M
n=length(x);
M=M(1:n,1:n);
y=M*(x.^2);
f(1)=y(1)^2;
g=1+10*(length(y)-1)+sum(y(2:end).^2-10*cos(4*pi*y(2:end)));
h=1-sqrt(f(1)/g);
f(2)=g*h;
return

function f=L3ZDT6(x, varargin)
if size(x,2)>size(x,1)
    x=x';
end
f=zeros(2,1);
load M
n=length(x);
M=M(1:n,1:n);
y=M*(x.^2);
f(1)=y(1)^2;
g=1+9*(sum(y(2:end).^2)/(length(y)-1))^0.25;
h=1-(f(1)/g)^2;
f(2)=g*h;
return

function f = UF1(x, varargin)
f=zeros(2,1);
if size(x,2)>size(x,1)
    x=x';
end
[dim, num]  = size(x);
tmp         = zeros(dim,num);
tmp(2:dim,:)= (x(2:dim,:) - sin(6.0*pi*repmat(x(1,:),[dim-1,1]) + pi/dim*repmat((2:dim)',[1,num]))).^2;
tmp1        = sum(tmp(3:2:dim,:));  % odd index
tmp2        = sum(tmp(2:2:dim,:));  % even index
f(1)      = x(1,:)             + 2.0*tmp1/size(3:2:dim,2);
f(2)      = 1.0 - sqrt(x(1,:)) + 2.0*tmp2/size(2:2:dim,2);
clear tmp;
return

function f = UF2(x, varargin)
f=zeros(2,1);
if size(x,2)>size(x,1)
    x=x';
end
[dim, num]  = size(x);
X1          = repmat(x(1,:),[dim-1,1]);
A           = 6*pi*X1 + pi/dim*repmat((2:dim)',[1,num]);
tmp         = zeros(dim,num);
tmp(2:dim,:)= (x(2:dim,:) - 0.3*X1.*(X1.*cos(4.0*A)+2.0).*cos(A)).^2;
tmp1        = sum(tmp(3:2:dim,:));  % odd index
tmp(2:dim,:)= (x(2:dim,:) - 0.3*X1.*(X1.*cos(4.0*A)+2.0).*sin(A)).^2;
tmp2        = sum(tmp(2:2:dim,:));  % even index
f(1)      = x(1,:)             + 2.0*tmp1/size(3:2:dim,2);
f(2)      = 1.0 - sqrt(x(1,:)) + 2.0*tmp2/size(2:2:dim,2);
clear X1 A tmp;
return

function f = UF3(x, varargin)
f=zeros(2,1);
if size(x,2)>size(x,1)
    x=x';
end
[dim, num]   = size(x);
Y            = zeros(dim,num);
Y(2:dim,:)   = x(2:dim,:) - repmat(x(1,:),[dim-1,1]).^(0.5+1.5*(repmat((2:dim)',[1,num])-2.0)/(dim-2.0));
tmp1         = zeros(dim,num);
tmp1(2:dim,:)= Y(2:dim,:).^2;
tmp2         = zeros(dim,num);
tmp2(2:dim,:)= cos(20.0*pi*Y(2:dim,:)./sqrt(repmat((2:dim)',[1,num])));
tmp11        = 4.0*sum(tmp1(3:2:dim,:)) - 2.0*prod(tmp2(3:2:dim,:)) + 2.0;  % odd index
tmp21        = 4.0*sum(tmp1(2:2:dim,:)) - 2.0*prod(tmp2(2:2:dim,:)) + 2.0;  % even index
f(1)       = x(1,:)             + 2.0*tmp11/size(3:2:dim,2);
f(2)       = 1.0 - sqrt(x(1,:)) + 2.0*tmp21/size(2:2:dim,2);
clear Y tmp1 tmp2;
return

function f = UF4(x, varargin)
f=zeros(2,1);
if size(x,2)>size(x,1)
    x=x';
end
[dim, num]  = size(x);
Y           = zeros(dim,num);
Y(2:dim,:)  = x(2:dim,:) - sin(6.0*pi*repmat(x(1,:),[dim-1,1]) + pi/dim*repmat((2:dim)',[1,num]));
H           = zeros(dim,num);
H(2:dim,:)  = abs(Y(2:dim,:))./(1.0+exp(2.0*abs(Y(2:dim,:))));
tmp1        = sum(H(3:2:dim,:));  % odd index
tmp2        = sum(H(2:2:dim,:));  % even index
f(1)      = x(1,:)          + 2.0*tmp1/size(3:2:dim,2);
f(2)      = 1.0 - x(1,:).^2 + 2.0*tmp2/size(2:2:dim,2);
clear Y H;
return

function f = UF5(x, varargin)
f=zeros(2,1);
if size(x,2)>size(x,1)
    x=x';
end
N           = 10.0;
E           = 0.1;
[dim, num]  = size(x);
Y           = zeros(dim,num);
Y(2:dim,:)  = x(2:dim,:) - sin(6.0*pi*repmat(x(1,:),[dim-1,1]) + pi/dim*repmat((2:dim)',[1,num]));
H           = zeros(dim,num);
H(2:dim,:)  = 2.0*Y(2:dim,:).^2 - cos(4.0*pi*Y(2:dim,:)) + 1.0;
tmp1        = sum(H(3:2:dim,:));  % odd index
tmp2        = sum(H(2:2:dim,:));  % even index
tmp         = (0.5/N+E)*abs(sin(2.0*N*pi*x(1,:)));
f(1)      = x(1,:)      + tmp + 2.0*tmp1/size(3:2:dim,2);
f(2)      = 1.0 - x(1,:)+ tmp + 2.0*tmp2/size(2:2:dim,2);
clear Y H;
return

function f = UF6(x, varargin)
f=zeros(2,1);
if size(x,2)>size(x,1)
    x=x';
end
N            = 2.0;
E            = 0.1;
[dim, num]   = size(x);
Y            = zeros(dim,num);
Y(2:dim,:)  = x(2:dim,:) - sin(6.0*pi*repmat(x(1,:),[dim-1,1]) + pi/dim*repmat((2:dim)',[1,num]));
tmp1         = zeros(dim,num);
tmp1(2:dim,:)= Y(2:dim,:).^2;
tmp2         = zeros(dim,num);
tmp2(2:dim,:)= cos(20.0*pi*Y(2:dim,:)./sqrt(repmat((2:dim)',[1,num])));
tmp11        = 4.0*sum(tmp1(3:2:dim,:)) - 2.0*prod(tmp2(3:2:dim,:)) + 2.0;  % odd index
tmp21        = 4.0*sum(tmp1(2:2:dim,:)) - 2.0*prod(tmp2(2:2:dim,:)) + 2.0;  % even index
tmp          = max(0,(1.0/N+2.0*E)*sin(2.0*N*pi*x(1,:)));
f(1)       = x(1,:)       + tmp + 2.0*tmp11/size(3:2:dim,2);
f(2)       = 1.0 - x(1,:) + tmp + 2.0*tmp21/size(2:2:dim,2);
clear Y tmp1 tmp2;
return

function f = UF7(x, varargin)
f=zeros(2,1);
if size(x,2)>size(x,1)
    x=x';
end
[dim, num]  = size(x);
Y           = zeros(dim,num);
Y(2:dim,:)  = (x(2:dim,:) - sin(6.0*pi*repmat(x(1,:),[dim-1,1]) + pi/dim*repmat((2:dim)',[1,num]))).^2;
tmp1        = sum(Y(3:2:dim,:));  % odd index
tmp2        = sum(Y(2:2:dim,:));  % even index
tmp         = (x(1,:)).^0.2;
f(1)      = tmp       + 2.0*tmp1/size(3:2:dim,2);
f(2)      = 1.0 - tmp + 2.0*tmp2/size(2:2:dim,2);
clear Y;
return

function f = UF8(x, varargin)
f=zeros(3,1);
if size(x,2)>size(x,1)
    x=x';
end
[dim, num]  = size(x);
Y           = zeros(dim,num);
Y(3:dim,:)  = (x(3:dim,:) - 2.0*repmat(x(2,:),[dim-2,1]).*sin(2.0*pi*repmat(x(1,:),[dim-2,1]) + pi/dim*repmat((3:dim)',[1,num]))).^2;
tmp1        = sum(Y(4:3:dim,:));  % j-1 = 3*k
tmp2        = sum(Y(5:3:dim,:));  % j-2 = 3*k
tmp3        = sum(Y(3:3:dim,:));  % j-0 = 3*k
f(1)      = cos(0.5*pi*x(1,:)).*cos(0.5*pi*x(2,:)) + 2.0*tmp1/size(4:3:dim,2);
f(2)      = cos(0.5*pi*x(1,:)).*sin(0.5*pi*x(2,:)) + 2.0*tmp2/size(5:3:dim,2);
f(3)      = sin(0.5*pi*x(1,:))                     + 2.0*tmp3/size(3:3:dim,2);
clear Y;
return

function f = UF9(x, varargin)
f=zeros(3,1);
if size(x,2)>size(x,1)
    x=x';
end
E           = 0.1;
[dim, num]  = size(x);
Y           = zeros(dim,num);
Y(3:dim,:)  = (x(3:dim,:) - 2.0*repmat(x(2,:),[dim-2,1]).*sin(2.0*pi*repmat(x(1,:),[dim-2,1]) + pi/dim*repmat((3:dim)',[1,num]))).^2;
tmp1        = sum(Y(4:3:dim,:));  % j-1 = 3*k
tmp2        = sum(Y(5:3:dim,:));  % j-2 = 3*k
tmp3        = sum(Y(3:3:dim,:));  % j-0 = 3*k
tmp         = max(0,(1.0+E)*(1-4.0*(2.0*x(1,:)-1).^2));
f(1)      = 0.5*(tmp+2*x(1,:)).*x(2,:)     + 2.0*tmp1/size(4:3:dim,2);
f(2)      = 0.5*(tmp-2*x(1,:)+2.0).*x(2,:) + 2.0*tmp2/size(5:3:dim,2);
f(3)      = 1-x(2,:)                       + 2.0*tmp3/size(3:3:dim,2);
clear Y;
return

function f = UF10(x, varargin)
f=zeros(3,1);
if size(x,2)>size(x,1)
    x=x';
end
[dim, num]  = size(x);
Y           = zeros(dim,num);
Y(3:dim,:)  = x(3:dim,:) - 2.0*repmat(x(2,:),[dim-2,1]).*sin(2.0*pi*repmat(x(1,:),[dim-2,1]) + pi/dim*repmat((3:dim)',[1,num]));
H           = zeros(dim,num);
H(3:dim,:)  = 4.0*Y(3:dim,:).^2 - cos(8.0*pi*Y(3:dim,:)) + 1.0;
tmp1        = sum(H(4:3:dim,:));  % j-1 = 3*k
tmp2        = sum(H(5:3:dim,:));  % j-2 = 3*k
tmp3        = sum(H(3:3:dim,:));  % j-0 = 3*k
f(1)      = cos(0.5*pi*x(1,:)).*cos(0.5*pi*x(2,:)) + 2.0*tmp1/size(4:3:dim,2);
f(2)      = cos(0.5*pi*x(1,:)).*sin(0.5*pi*x(2,:)) + 2.0*tmp2/size(5:3:dim,2);
f(3)      = sin(0.5*pi*x(1,:))                     + 2.0*tmp3/size(3:3:dim,2);
clear Y H;
return

function f=WFG1(z, M)
if size(z,1)>size(z,2)
    z=z';
end
% parameters
n=length(z);
k=M-1;
S=2*(1:M);
A=ones(1,M-1);
% problem variables
zmax=2*(1:n);
% transform z onto [0,1] set
y=z./zmax;
% first level mapping
t_func=Transformation('s_linear');
t1=zeros(1,n);
for i=1:n
    if i<=k
        t1(i)=y(i);
    else
        t1(i)=feval(t_func,y(i),0.35);
    end
end
% second level mapping
t_func=Transformation('b_flat');
t2=zeros(1,n);
for i=1:n
    if i<=k
        t2(i)=t1(i);
    else
        t2(i)=feval(t_func,t1(i),0.8,0.75,0.85);
    end
end
% third level mapping
t_func=Transformation('b_poly');
t3=feval(t_func,t2,0.02); % original = 0.02; my setting = 1.0 
% forth level mapping
t_func=Transformation('r_sum');
w=2*(1:n);
t4=zeros(1,M);
for i=1:M
    if i<=M-1
        t4(i)=feval(t_func,t3((i-1)*k/(M-1)+1:i*k/(M-1)),w((i-1)*k/(M-1)+1:i*k/(M-1)));
    else
        t4(i)=feval(t_func,t3(k+1:n),w(k+1:n));
    end
end
% Define objective function variables
x=zeros(1,M);
for i=1:M
    if i<=M-1
        x(i)=max(t4(M),A(i))*(t4(i)-0.5)+0.5;
    else
        x(i)=t4(M);
    end
end
% Define function h
h=zeros(1,M);
for m=1:M
    if m==1
        s_func=Shape('convex_1');
        h(m)=feval(s_func,x,M);
    elseif m>1 && m<=M-1
        s_func=Shape('convex_m');
        h(m)=feval(s_func,x,M,m);
    else
        s_func=Shape('mixed_M');
        h(m)=feval(s_func,x,1,5);
    end
end
% The objective functions
f=zeros(M,1);
for m=1:M
    f(m)=x(M)+S(m)*h(m);
end
return

function f=WFG2(z, M)
if size(z,1)>size(z,2)
    z=z';
end
% param
n=length(z);
k=M-1;
l=n-k;
S=2*(1:M);
A=ones(1,M-1);
% transform z into [0,1] set
zmax=2*(1:n);
y=z./zmax;
% first level mapping
t_func=Transformation('s_linear');
t1=zeros(1,n);
for i=1:n
    if i<=k
        t1(i)=y(i);
    else
        t1(i)=feval(t_func,y(i),0.35);
    end
end
% second level mapping
t_func=Transformation('r_nonsep');
t2=zeros(1,k+l/2);
for i=1:k+l/2
    if i<=k
        t2(i)=t1(i);
    else
        t2(i)=feval(t_func,t1(k+2*(i-k)-1:k+2*(i-k)),2);
    end
end
% third level mapping
t_func=Transformation('r_sum');
w=ones(1,n);
t3=zeros(1,M);
for i=1:M
    if i<=M-1
        t3(i)=feval(t_func,t2((i-1)*k/(M-1)+1:i*k/(M-1)),w((i-1)*k/(M-1)+1:i*k/(M-1)));
    else
        t3(i)=feval(t_func,t2(k+1:k+l/2),w(k+1:k+l/2));
    end
end
% Define objective function variables
x=zeros(1,M);
for i=1:M
    if i<=M-1
        x(i)=max(t3(M),A(i))*(t3(i)-0.5)+0.5;
    else
        x(i)=t3(i);
    end
end
% Define objective function function h
h=zeros(1,M);
for m=1:M
    if m==1
        s_func=Shape('convex_1');
        h(m)=feval(s_func,x,M);
    elseif m>1 && m<=M-1
        s_func=Shape('convex_m');
        h(m)=feval(s_func,x,M,m);
    else
        s_func=Shape('disc_M');
        h(m)=feval(s_func,x,1,1,5);
    end
end
% The objective functions
f=zeros(M,1);
for m=1:M
    f(m)=x(M)+S(m)*h(m);
end
return

function f=WFG3(z, M)
if size(z,1)>size(z,2)
    z=z';
end
% parameters
n=length(z);
k=M-1;
l=n-k;
S=2*(1:M);
A=zeros(1,M-1);
A(1)=1;
% transform z onto [0,1] set
zmax=2*(1:n);
y=z./zmax;
% first level mapping
t_func=Transformation('s_linear');
t1=zeros(1,n);
for i=1:n
    if i<=k
        t1(i)=y(i);
    else
        t1(i)=feval(t_func,y(i),0.35);
    end
end
% second level mapping
t_func=Transformation('r_nonsep');
t2=zeros(1,k+l/2);
for i=1:k+l/2
    if i<=k
        t2(i)=t1(i);
    else
        t2(i)=feval(t_func,t1(k+2*(i-k)-1:k+2*(i-k)),2);
    end
end
% third level mapping
t_func=Transformation('r_sum');
w=ones(1,n);
t3=zeros(1,M);
for i=1:M
    if i<=M-1
        t3(i)=feval(t_func,t2((i-1)*k/(M-1)+1:i*k/(M-1)),w((i-1)*k/(M-1)+1:i*k/(M-1)));
    else
        t3(i)=feval(t_func,t2(k+1:k+l/2),w(k+1:k+l/2));
    end
end
% Define objective function variables
x=zeros(1,M);
for i=1:M
    if i<=M-1
        x(i)=max(t3(M),A(i))*(t3(i)-0.5)+0.5;
    else
        x(i)=t3(i);
    end
end
% Define objective function function h
h=zeros(1,M);
for m=1:M
    if m==1
        s_func=Shape('linear_1');
        h(m)=feval(s_func,x,M);
    elseif m>1 && m<=M-1
        s_func=Shape('linear_m');
        h(m)=feval(s_func,x,M,m);
    else
        s_func=Shape('linear_M');
        h(m)=feval(s_func,x);
    end
end
% The objective functions
f=zeros(M,1);
for m=1:M
    f(m)=x(M)+S(m)*h(m);
end
return

function f=WFG4(z, M)
if size(z,1)>size(z,2)
    z=z';
end
% parameters
n=length(z);
k=M-1;
S=2*(1:M);
A=ones(1,M-1);
% transform z onto [0,1] set
zmax=2*(1:n);
y=z./zmax;
% first level mapping
t_func=Transformation('s_multi');
t1=zeros(1,n);
for i=1:n
    t1(i)=feval(t_func,y(i),30,10,0.35);
end
% second level mapping
t_func=Transformation('r_sum');
w=ones(1,n);
t2=zeros(1,M);
for i=1:M
    if i<=M-1
        t2(i)=feval(t_func,t1((i-1)*k/(M-1)+1:i*k/(M-1)),w((i-1)*k/(M-1)+1:i*k/(M-1)));
    else
        t2(i)=feval(t_func,t1(k+1:n),w(k+1:n));
    end
end
% Define objective function variables
x=zeros(1,M);
for i=1:M
    if i<=M-1
        x(i)=max(t2(M),A(i))*(t2(i)-0.5)+0.5;
    else
        x(i)=t2(i);
    end
end
% Define function h
h=zeros(1,M);
for m=1:M
    if m==1
        s_func=Shape('concave_1');
        h(m)=feval(s_func,x,M);
    elseif m>1 && m<=M-1
        s_func=Shape('concave_m');
        h(m)=feval(s_func,x,M,m);
    else
        s_func=Shape('concave_M');
        h(m)=feval(s_func,x);
    end
end
% The objective functions
f=zeros(M,1);
for m=1:M
    f(m)=x(M)+S(m)*h(m);
end
return   

function f=WFG5(z, M)
if size(z,1)>size(z,2)
    z=z';
end
% parameters
n=length(z);
k=M-1;
S=2*(1:M);
A=ones(1,M-1);
% transform z onto [0,1] set
zmax=2*(1:n);
y=z./zmax;
% first level mapping
t_func=Transformation('s_decept');
t1=zeros(1,n);
for i=1:n
    t1(i)=feval(t_func,y(i),0.35,0.001,0.05);
end
% second level mapping
t_func=Transformation('r_sum');
w=ones(1,n);
t2=zeros(1,M);
for i=1:M
    if i<=M-1
        t2(i)=feval(t_func,t1((i-1)*k/(M-1)+1:i*k/(M-1)),w((i-1)*k/(M-1)+1:i*k/(M-1)));
    else
        t2(i)=feval(t_func,t1(k+1:n),w(k+1:n));
    end
end
% Define objective function variables
x=zeros(1,M);
for i=1:M
    if i<=M-1
        x(i)=max(t2(M),A(i))*(t2(i)-0.5)+0.5;
    else
        x(i)=t2(M);
    end
end
% Define function h
h=zeros(1,M);
for m=1:M
    if m==1
        s_func=Shape('concave_1');
        h(m)=feval(s_func,x,M);
    elseif m>1 && m<=M-1
        s_func=Shape('concave_m');
        h(m)=feval(s_func,x,M,m);
    else
        s_func=Shape('concave_M');
        h(m)=feval(s_func,x);
    end
end
% The objective functions
f=zeros(M,1);
for m=1:M
    f(m)=x(M)+S(m)*h(m);
end
return  

function f=WFG6(z, M)
if size(z,1)>size(z,2)
    z=z';
end
% parameters
n=length(z);
k=M-1;
l=n-k;
S=2*(1:M);
A=ones(1,M-1);
% transform z onto [0,1] set
zmax=2*(1:n);
y=z./zmax;
% first level mapping
t_func=Transformation('s_linear');
t1=zeros(1,n);
for i=1:n
    if i<=k
        t1(i)=y(i);
    else
        t1(i)=feval(t_func,y(i),0.35);
    end
end
% second level mapping
t_func=Transformation('r_nonsep');
t2=zeros(1,M);
for i=1:M
    if i<=M-1
        t2(i)=feval(t_func,t1((i-1)*k/(M-1)+1:i*k/(M-1)),k/(M-1));
    else
        t2(i)=feval(t_func,t1(k+1:n),l);
    end
end
% Define objective function variables
x=zeros(1,M);
for i=1:M
    if i<=M-1
        x(i)=max(t2(M),A(i))*(t2(i)-0.5)+0.5;
    else
        x(i)=t2(M);
    end
end
% Define function h
h=zeros(1,M);
for m=1:M
    if m==1
        s_func=Shape('concave_1');
        h(m)=feval(s_func,x,M);
    elseif m>1 && m<=M-1
        s_func=Shape('concave_m');
        h(m)=feval(s_func,x,M,m);
    else
        s_func=Shape('concave_M');
        h(m)=feval(s_func,x);
    end
end
% The objective functions
f=zeros(M,1);
for m=1:M
    f(m)=x(M)+S(m)*h(m);
end
return

function f=WFG7(z, M)
if size(z,1)>size(z,2)
    z=z';
end
% parameters
n=length(z);
k=M-1;
S=2*(1:M);
A=ones(1,M-1);
% transform z onto [0,1] set
zmax=2*(1:n);
y=z./zmax;
% first level mapping
t_func=Transformation('b_param');
w=ones(1,n);
t1=zeros(1,n);
for i=1:n
    if i<=k
        t_func_temp=Transformation('r_sum');
        u=feval(t_func_temp,y(i+1:n),w(i+1:n));
        t1(i)=feval(t_func,y(i),u,0.98/49.98,0.02,50);
    else
        t1(i)=y(i);
    end
end
% second level mapping
t_func=Transformation('s_linear');
t2=zeros(1,n);
for i=1:n
    if i<=k
        t2(i)=t1(i);
    else
        t2(i)=feval(t_func,t1(i),0.35);
    end
end
% third level mapping
t_func=Transformation('r_sum');
w=ones(1,n);
t3=zeros(1,M);
for i=1:M
    if i<=M-1
        t3(i)=feval(t_func,t2((i-1)*k/(M-1)+1:i*k/(M-1)),w((i-1)*k/(M-1)+1:i*k/(M-1)));
    else
        t3(i)=feval(t_func,t2(k+1:n),w(k+1:n));
    end
end
% Define objective function variables
x=zeros(1,M);
for i=1:M
    if i<=M-1
        x(i)=max(t3(M),A(i))*(t3(i)-0.5)+0.5;
    else
        x(i)=t3(M);
    end
end
% Define function h
h=zeros(1,M);
for m=1:M
    if m==1
        s_func=Shape('concave_1');
        h(m)=feval(s_func,x,M);
    elseif m>1 && m<=M-1
        s_func=Shape('concave_m');
        h(m)=feval(s_func,x,M,m);
    else
        s_func=Shape('concave_M');
        h(m)=feval(s_func,x);
    end
end
% The objective functions
f=zeros(M,1);
for m=1:M
    f(m)=x(M)+S(m)*h(m);
end
return

function f=WFG8(z, M)
if size(z,1)>size(z,2)
    z=z';
end
% parameters
n=length(z);
k=M-1;
S=2*(1:M);
A=ones(1,M-1);
% transform z onto [0,1] set
zmax=2*(1:n);
y=z./zmax;
% first level mapping
t_func=Transformation('b_param');
w=ones(1,n);
t1=zeros(1,n);
for i=1:n
    if i<=k
        t1(i)=y(i);
    else
        t_func_temp=Transformation('r_sum');
        u=feval(t_func_temp,y(1:i-1),w(1:i-1));
        t1(i)=feval(t_func,y(i),u,0.98/49.98,0.02,50);
    end
end
% second level mapping
t_func=Transformation('s_linear');
t2=zeros(1,n);
for i=1:n
    if i<=k
        t2(i)=t1(i);
    else
        t2(i)=feval(t_func,t1(i),0.35);
    end
end
% third level mapping
t_func=Transformation('r_sum');
w=ones(1,n);
t3=zeros(1,M);
for i=1:M
    if i<=M-1
        t3(i)=feval(t_func,t2((i-1)*k/(M-1)+1:i*k/(M-1)),w((i-1)*k/(M-1)+1:i*k/(M-1)));
    else
        t3(i)=feval(t_func,t2(k+1:n),w(k+1:n));
    end
end
% Define objective function variables
x=zeros(1,M);
for i=1:M
    if i<=M-1
        x(i)=max(t3(M),A(i))*(t3(i)-0.5)+0.5;
    else
        x(i)=t3(M);
    end
end
% Define function h
h=zeros(1,M);
for m=1:M
    if m==1
        s_func=Shape('concave_1');
        h(m)=feval(s_func,x,M);
    elseif m>1 && m<=M-1
        s_func=Shape('concave_m');
        h(m)=feval(s_func,x,M,m);
    else
        s_func=Shape('concave_M');
        h(m)=feval(s_func,x);
    end
end
% The objective functions
f=zeros(M,1);
for m=1:M
    f(m)=x(M)+S(m)*h(m);
end
return

function f=WFG9(z, M)
if size(z,1)>size(z,2)
    z=z';
end
% parameters
n=length(z);
k=M-1;
l=n-k;
S=2*(1:M);
A=ones(1,M-1);
% transform z onto [0,1] set
zmax=2*(1:n);
y=z./zmax;
% first level mapping
t_func=Transformation('b_param');
w=ones(1,n);
t1=zeros(1,n);
for i=1:n
    if i<=n-1
        t_func_temp=Transformation('r_sum');
        u=feval(t_func_temp,y(i+1:n),w(i+1:n));
        t1(i)=feval(t_func,y(i),u,0.98/49.98,0.02,50);
    else
        t1(i)=y(i);
    end
end
% second level mapping
t2=zeros(1,n);
for i=1:n
    if i<=k
        t_func=Transformation('s_decept');
        t2(i)=feval(t_func,t1(i),0.35,0.001,0.05);
    else
        t_func=Transformation('s_multi');
        t2(i)=feval(t_func,t1(i),30,95,0.35);
    end
end
% third level mapping
t_func=Transformation('r_nonsep');
t3=zeros(1,M);
for i=1:M
    if i<=M-1
        t3(i)=feval(t_func,t2((i-1)*k/(M-1)+1:i*k/(M-1)),k/(M-1));
    else
        t3(i)=feval(t_func,t2(k+1:n),l);
    end
end
% Define objective function variables
x=zeros(1,M);
for i=1:M
    if i<=M-1
        x(i)=max(t3(M),A(i))*(t3(i)-0.5)+0.5;
    else
        x(i)=t3(M);
    end
end
% Define function h
h=zeros(1,M);
for m=1:M
    if m==1
        s_func=Shape('concave_1');
        h(m)=feval(s_func,x,M);
    elseif m>1 && m<=M-1
        s_func=Shape('concave_m');
        h(m)=feval(s_func,x,M,m);
    else
        s_func=Shape('concave_M');
        h(m)=feval(s_func,x);
    end
end
% The objective functions
f=zeros(M,1);
for m=1:M
    f(m)=x(M)+S(m)*h(m);
end
return

function t_func = Transformation(name)
switch name
    case 'b_poly'
        t_func = @b_poly;
    case 'b_flat'
        t_func = @b_flat;
    case 'b_param'
        t_func = @b_param;
    case 's_linear'
        t_func = @s_linear;
    case 's_decept'
        t_func = @s_decept;
    case 's_multi'
        t_func = @s_multi;
    case 'r_sum'
        t_func = @r_sum;
    case 'r_nonsep'
        t_func = @r_nonsep;
end
return

function t=b_poly(y,alfa)
t=y.^alfa;
return

function t=b_flat(y,A,B,C)
t=A+min(0,floor(y-B))*(A*(B-y))/B-min(0,floor(C-y))*(1-A)*(y-C)/(1-C);
return

function t=b_param(y,u,A,B,C)
v=A-(1-2*u)*abs(floor(0.5-u)+A);
t=y^(B+(C-B)*v);
return

function t=s_linear(y,A)
t=abs(y-A)/abs(floor(A-y)+A);
return

function t=s_decept(y,A,B,C)
t=1+(abs(y-A)-B)*((floor(y-A+B)*(1-C+(A-B)/B))/(A-B)+(floor(A+B-y)*(1-C+(1-A-B)/B))/(1-A-B)+1/B);
return

function t=s_multi(y,A,B,C)
t=(1+cos((4*A+2)*pi*(0.5-abs(y-C)/(2*(floor(C-y)+C))))+4*B*(abs(y-C)/(2*(floor(C-y)+C)))^2)/(B+2);
return

function t=r_sum(y,w)
t=sum(w.*y)/sum(w);
return

function t=r_nonsep(y,A)
nominator=0;
for j=1:length(y)
    nominator=nominator+y(j);
    for k=0:A-2
        nominator=nominator+abs(y(j)-y(1+mod(j+k,length(y))));
    end
end
denominator=length(y)/A*ceil(A/2)*(1+2*A-2*ceil(A/2));
t=nominator/denominator;
return

function s_func = Shape(name)
switch name
    case 'linear_1'
        s_func = @linear_1;
    case 'linear_m'
        s_func = @linear_m;
    case 'linear_M'
        s_func = @linear_M;
    case 's_linear'
        s_func = @s_linear;
    case 'convex_1'
        s_func = @convex_1;
    case 'convex_m'
        s_func = @convex_m;
    case 'convex_M'
        s_func = @convex_M;
    case 'concave_1'
        s_func = @concave_1;
    case 'concave_m'
        s_func = @concave_m;
    case 'concave_M'
        s_func = @concave_M;
    case 'mixed_M'
        s_func = @mixed_M;
    case 'disc_M'
        s_func = @disc_M;
end
return

function h=linear_1(x,M)
h=prod(x(1:M-1));
return

function h=linear_m(x,M,m)
h=prod(x(1:M-m))*(1-x(M-m+1));
return

function h=linear_M(x)
h=1-x(1);
return

function h=convex_1(x,M)
h=prod(1-cos(x(1:M-1)*pi/2));
return

function h=convex_m(x,M,m)
h=prod(1-cos(x(1:M-m)*pi/2))*(1-sin(x(M-m+1)*pi/2));
return

function h=convex_M(x)
h=1-sin(x(1)*pi/2);
return

function h=concave_1(x,M)
h=prod(sin(x(1:M-1)*pi/2));
return

function h=concave_m(x,M,m)
h=prod(sin(x(1:M-m)*pi/2))*cos(x(M-m+1)*pi/2);
return

function h=concave_M(x)
h=cos(x(1)*pi/2);
return

function h=mixed_M(x,alfa,A)
h=(1-x(1)-cos(2*A*pi*x(1)+pi/2)/(2*A*pi))^alfa;
return

function h=disc_M(x,alfa,beta,A)
h=1-x(1)^alfa*cos(A*x(1)^beta*pi)^2;
return

function f=I1(z, M)
if size(z,1)>size(z,2)
    z=z';
end
n=length(z);
k=n/2;
S=ones(1,M);
A=ones(1,M-1);
y=z./(2*(1:n));
% first level mapping
t1=y;
% second level mapping
t_func=Transformation('s_linear');
t2=zeros(1,n);
for i=1:n
    if i<=k
        t2(i)=t1(i);
    else
        t2(i)=feval(t_func,t1(i),0.35);
    end
end
% third level mapping
t_func=Transformation('r_sum');
w=ones(1,n);
t3=zeros(1,M);
for i=1:M
    if i<=M-1
        t3(i)=feval(t_func,t2((i-1)*k/(M-1)+1:i*k/(M-1)),w((i-1)*k/(M-1)+1:i*k/(M-1)));
    else
        t3(i)=feval(t_func,t2(k+1:n),w(k+1:n));
    end
end
% Define objective function variables
x=zeros(1,M);
for i=1:M
    if i<=M-1
        x(i)=max(t3(M),A(i))*(t3(i)-0.5)+0.5;
    else
        x(i)=t3(i);
    end
end
% Define function h
h=zeros(1,M);
for m=1:M
    if m==1
        s_func=Shape('concave_1');
        h(m)=feval(s_func,x,M);
    elseif m>1 && m<=M-1
        s_func=Shape('concave_m');
        h(m)=feval(s_func,x,M,m);
    else
        s_func=Shape('concave_M');
        h(m)=feval(s_func,x);
    end
end
% The objective functions
f=zeros(M,1);
for m=1:M
    f(m)=x(M)+S(m)*h(m);
end
return

function f=I2(z, M)
if size(z,1)>size(z,2)
    z=z';
end
n=length(z);
k=n/2;
S=ones(1,M);
A=ones(1,M-1);
y=z./(2*(1:n));
% first level mapping
t_func=Transformation('b_param');
w=ones(1,n);
t1=zeros(1,n);
for i=1:n
    if i<=n-1
        t_func_temp=Transformation('r_sum');
        u=feval(t_func_temp,y(i+1:n),w(i+1:n));
        t1(i)=feval(t_func,y(i),u,0.98/49.98,0.02,50);
    else
        t1(i)=y(i);
    end
end
% second level mapping
t_func=Transformation('s_linear');
t2=zeros(1,n);
for i=1:n
    if i<=k
        t2(i)=t1(i);
    else
        t2(i)=feval(t_func,t1(i),0.35);
    end
end
% third level mapping
t_func=Transformation('r_sum');
w=ones(1,n);
t3=zeros(1,M);
for i=1:M
    if i<=M-1
        t3(i)=feval(t_func,t2((i-1)*k/(M-1)+1:i*k/(M-1)),w((i-1)*k/(M-1)+1:i*k/(M-1)));
    else
        t3(i)=feval(t_func,t2(k+1:n),w(k+1:n));
    end
end
% Define objective function variables
x=zeros(1,M);
for i=1:M
    if i<=M-1
        x(i)=max(t3(M),A(i))*(t3(i)-0.5)+0.5;
    else
        x(i)=t3(i);
    end
end
% Define function h
h=zeros(1,M);
for m=1:M
    if m==1
        s_func=Shape('concave_1');
        h(m)=feval(s_func,x,M);
    elseif m>1 && m<=M-1
        s_func=Shape('concave_m');
        h(m)=feval(s_func,x,M,m);
    else
        s_func=Shape('concave_M');
        h(m)=feval(s_func,x);
    end
end
% The objective functions
f=zeros(M,1);
for m=1:M
    f(m)=x(M)+S(m)*h(m);
end
return

function f=I3(z, M)
if size(z,1)>size(z,2)
    z=z';
end
n=length(z);
k=n/2;
S=ones(1,M);
A=ones(1,M-1);
y=z./(2*(1:n));
% first level mapping
t_func=Transformation('b_param');
w=ones(1,n);
t1=zeros(1,n);
for i=1:n
    if i==1
        t1(i)=y(i);
    else
        t_func_temp=Transformation('r_sum');
        u=feval(t_func_temp,y(1:i-1),w(1:i-1));
        t1(i)=feval(t_func,y(i),u,0.98/49.98,0.02,50);
    end
end
% second level mapping
t_func=Transformation('s_linear');
t2=zeros(1,n);
for i=1:n
    if i<=k
        t2(i)=t1(i);
    else
        t2(i)=feval(t_func,t1(i),0.35);
    end
end
% third level mapping
t_func=Transformation('r_sum');
w=ones(1,n);
t3=zeros(1,M);
for i=1:M
    if i<=M-1
        t3(i)=feval(t_func,t2((i-1)*k/(M-1)+1:i*k/(M-1)),w((i-1)*k/(M-1)+1:i*k/(M-1)));
    else
        t3(i)=feval(t_func,t2(k+1:n),w(k+1:n));
    end
end
% Define objective function variables
x=zeros(1,M);
for i=1:M
    if i<=M-1
        x(i)=max(t3(M),A(i))*(t3(i)-0.5)+0.5;
    else
        x(i)=t3(i);
    end
end
% Define function h
h=zeros(1,M);
for m=1:M
    if m==1
        s_func=Shape('concave_1');
        h(m)=feval(s_func,x,M);
    elseif m>1 && m<=M-1
        s_func=Shape('concave_m');
        h(m)=feval(s_func,x,M,m);
    else
        s_func=Shape('concave_M');
        h(m)=feval(s_func,x);
    end
end
% The objective functions
f=zeros(M,1);
for m=1:M
    f(m)=x(M)+S(m)*h(m);
end
return

function f=I4(z, M)
if size(z,1)>size(z,2)
    z=z';
end
n=length(z);
k=n/2;
l=n-k;
S=ones(1,M);
A=ones(1,M-1);
y=z./(2*(1:n));
% first level mapping
t1=y;
% second level mapping
t_func=Transformation('s_linear');
t2=zeros(1,n);
for i=1:n
    if i<=k
        t2(i)=t1(i);
    else
        t2(i)=feval(t_func,t1(i),0.35);
    end
end
% third level mapping
t_func=Transformation('r_nonsep');
t3=zeros(1,M);
for i=1:M
    if i<=M-1
        t3(i)=feval(t_func,t2((i-1)*k/(M-1)+1:i*k/(M-1)),k/(M-1));
    else
        t3(i)=feval(t_func,t2(k+1:n),l);
    end
end
% Define objective function variables
x=zeros(1,M);
for i=1:M
    if i<=M-1
        x(i)=max(t3(M),A(i))*(t3(i)-0.5)+0.5;
    else
        x(i)=t3(i);
    end
end
% Define function h
h=zeros(1,M);
for m=1:M
    if m==1
        s_func=Shape('concave_1');
        h(m)=feval(s_func,x,M);
    elseif m>1 && m<=M-1
        s_func=Shape('concave_m');
        h(m)=feval(s_func,x,M,m);
    else
        s_func=Shape('concave_M');
        h(m)=feval(s_func,x);
    end
end
% The objective functions
f=zeros(M,1);
for m=1:M
    f(m)=x(M)+S(m)*h(m);
end
return

function f=I5(z, M)
if size(z,1)>size(z,2)
    z=z';
end
n=length(z);
k=n/2;
l=n-k;
S=ones(1,M);
A=ones(1,M-1);
y=z./(2*(1:n));
% first level mapping
t_func=Transformation('b_param');
w=ones(1,n);
t1=zeros(1,n);
for i=1:n
    if i==1
        t1(i)=y(i);
    else
        t_func_temp=Transformation('r_sum');
        u=feval(t_func_temp,y(1:i-1),w(1:i-1));
        t1(i)=feval(t_func,y(i),u,0.98/49.98,0.02,50);
    end
end
% second level mapping
t_func=Transformation('s_linear');
t2=zeros(1,n);
for i=1:n
    if i<=k
        t2(i)=t1(i);
    else
        t2(i)=feval(t_func,t1(i),0.35);
    end
end
% third level mapping
t_func=Transformation('r_nonsep');
t3=zeros(1,M);
for i=1:M
    if i<=M-1
        t3(i)=feval(t_func,t2((i-1)*k/(M-1)+1:i*k/(M-1)),k/(M-1));
    else
        t3(i)=feval(t_func,t2(k+1:n),l);
    end
end
% Define objective function variables
x=zeros(1,M);
for i=1:M
    if i<=M-1
        x(i)=max(t3(M),A(i))*(t3(i)-0.5)+0.5;
    else
        x(i)=t3(i);
    end
end
% Define function h
h=zeros(1,M);
for m=1:M
    if m==1
        s_func=Shape('concave_1');
        h(m)=feval(s_func,x,M);
    elseif m>1 && m<=M-1
        s_func=Shape('concave_m');
        h(m)=feval(s_func,x,M,m);
    else
        s_func=Shape('concave_M');
        h(m)=feval(s_func,x);
    end
end
% The objective functions
f=zeros(M,1);
for m=1:M
    f(m)=x(M)+S(m)*h(m);
end
return

function f=Kursawe(x, varargin)
f=zeros(2,1);
f(1)=0;
for i=1:length(x)-1
    f(1)=f(1)-10*exp(-0.2*sqrt(x(i)^2+x(i+1)^2));
end
f(2)=0;
for i=1:length(x)
    f(2)=f(2)+abs(x(i))^0.8+5*sin(x(i)^3);
end
return

function f=OKA1(x, varargin)
f=zeros(2,1);
y=[0;0];
y(1)=cos(pi/12)*x(1)-sin(pi/12)*x(2);
y(2)=sin(pi/12)*x(1)+cos(pi/12)*x(2);
f(1)=y(1);
f(2)=sqrt(2*pi)-sqrt(abs(y(1)))+2*abs(y(2)-3*cos(y(1))-3)^0.5;
return

function f=OKA2(x, varargin)
f=zeros(2,1);
f(1)=x(1);
f(2)=1-(x(1)+pi)^2/(4*pi^2)+abs(x(2)-5*cos(x(1)))^(1/3)+abs(x(3)-5*sin(x(1)))^(1/3);
return
