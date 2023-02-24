function [Problem]=get_struct(name)

switch name
    case 'LGZ1'
        Problem = LGZ1(name);
    case 'LGZ2'
        Problem = LGZ2(name);
    case 'LGZ3'
        Problem = LGZ3(name);
    case 'LGZ4'
        Problem = LGZ4(name);
    case 'LGZ5'
        Problem = LGZ5(name);
    case 'LGZ6'
        Problem = LGZ6(name);
    case 'LGZ7'
        Problem = LGZ7(name);
    case 'ZDT1'
        Problem = ZDT1(name);
    case 'ZDT2'
        Problem = ZDT2(name);
    case 'ZDT3'
        Problem = ZDT3(name);
    case 'ZDT4'
        Problem = ZDT4(name);
    case 'ZDT6'
        Problem = ZDT6(name);
    case 'DTLZ1'
        Problem = DTLZ1(name);
    case 'DTLZ2'
        Problem = DTLZ2(name);
    case 'DTLZ3'
        Problem = DTLZ3(name);
    case 'DTLZ4'
        Problem = DTLZ4(name);
    case 'DTLZ5'
        Problem = DTLZ5(name);
    case 'DTLZ6'
        Problem = DTLZ6(name);
    case 'DTLZ7'
        Problem = DTLZ7(name);
    case 'LZ09_F1'
        Problem = LZ09_F1(name);
    case 'LZ09_F2'
        Problem = LZ09_F2(name);
    case 'LZ09_F3'
        Problem = LZ09_F3(name);
    case 'LZ09_F4'
        Problem = LZ09_F4(name);
    case 'LZ09_F5'
        Problem = LZ09_F5(name);
    case 'LZ09_F6'
        Problem = LZ09_F6(name);
    case 'LZ09_F7'
        Problem = LZ09_F7(name);
    case 'LZ09_F8'
        Problem = LZ09_F8(name);
    case 'LZ09_F9'
        Problem = LZ09_F9(name);
    case 'L1ZDT1'
        Problem = L1ZDT1(name);
    case 'L1ZDT2'
        Problem = L1ZDT2(name);
    case 'L1ZDT3'
        Problem = L1ZDT3(name);
    case 'L1ZDT4'
        Problem = L1ZDT4(name);
    case 'L1ZDT6'
        Problem = L1ZDT6(name);
    case 'L2ZDT1'
        Problem = L2ZDT1(name);
    case 'L2ZDT2'
        Problem = L2ZDT2(name);
    case 'L2ZDT3'
        Problem = L2ZDT3(name);
    case 'L2ZDT4'
        Problem = L2ZDT4(name);
    case 'L2ZDT6'
        Problem = L2ZDT6(name);
    case 'L3ZDT1'
        Problem = L3ZDT1(name);
    case 'L3ZDT2'
        Problem = L3ZDT2(name);
    case 'L3ZDT3'
        Problem = L3ZDT3(name);
    case 'L3ZDT4'
        Problem = L3ZDT4(name);
    case 'L3ZDT6'
        Problem = L3ZDT6(name);
    case 'WFG1'
        Problem = WFG1(name);
    case 'WFG2'
        Problem = WFG2(name);
    case 'WFG3'
        Problem = WFG3(name);
    case 'WFG4'
        Problem = WFG4(name);
    case 'WFG5'
        Problem = WFG5(name);
    case 'WFG6'
        Problem = WFG6(name);
    case 'WFG7'
        Problem = WFG7(name);
    case 'WFG8'
        Problem = WFG8(name);
    case 'WFG9'
        Problem = WFG9(name);
    case 'UF1'
        Problem = UF1(name);
    case 'UF2'
        Problem = UF2(name);
    case 'UF3'
        Problem = UF3(name);
    case 'UF4'
        Problem = UF4(name);
    case 'UF5'
        Problem = UF5(name);
    case 'UF6'
        Problem = UF6(name);
    case 'UF7'
        Problem = UF7(name);
    case 'UF8'
        Problem = UF8(name);
    case 'UF9'
        Problem = UF9(name);
    case 'UF10'
        Problem = UF10(name);
    case {'I1','I2','I3','I4','I5'}
        Problem = I(name);
    case 'Kursawe'
        Problem = Kursawe(name);
    case 'OKA1'
        Problem = OKA1(name);
    case 'OKA2'
        Problem = OKA2(name);
end

return

function [Problem]=LGZ1(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=LGZ2(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=LGZ3(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=LGZ4(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=LGZ5(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=LGZ6(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=LGZ7(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=ZDT1(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=ZDT2(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=ZDT3(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=ZDT4(name)
Problem.n=30;
Problem.lb=-5*ones(Problem.n,1); Problem.lb(1)=0;
Problem.ub=5*ones(Problem.n,1); Problem.ub(1)=1;
Problem.objFunction=mop(name);
return

function [Problem]=ZDT6(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=DTLZ1(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=DTLZ2(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=DTLZ3(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=DTLZ4(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=DTLZ5(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=DTLZ6(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=DTLZ7(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=LZ09_F1(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=LZ09_F2(name)
Problem.n=30;
Problem.lb=-1*ones(Problem.n,1); Problem.lb(1)=0; 
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=LZ09_F3(name)
Problem.n=30;
Problem.lb=-1*ones(Problem.n,1); Problem.lb(1)=0; 
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=LZ09_F4(name)
Problem.n=30;
Problem.lb=-1*ones(Problem.n,1); Problem.lb(1)=0; 
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=LZ09_F5(name)
Problem.n=30;
Problem.lb=-1*ones(Problem.n,1); Problem.lb(1)=0; 
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=LZ09_F6(name)
Problem.n=30;
Problem.lb=-2*ones(Problem.n,1); Problem.lb(1:2)=0; 
Problem.ub=2*ones(Problem.n,1); Problem.ub(1:2)=1;
Problem.objFunction=mop(name);
return

function [Problem]=LZ09_F7(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=LZ09_F8(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=LZ09_F9(name)
Problem.n=30;
Problem.lb=-1*ones(Problem.n,1); Problem.lb(1)=0; 
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=L1ZDT1(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=L1ZDT2(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=L1ZDT3(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=L1ZDT4(name)
Problem.n=10;
Problem.lb=-5*ones(Problem.n,1); Problem.lb(1)=0;
Problem.ub=5*ones(Problem.n,1); Problem.ub(1)=1;
Problem.objFunction=mop(name);
return

function [Problem]=L1ZDT6(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=L2ZDT1(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=L2ZDT2(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=L2ZDT3(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=L2ZDT4(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=L2ZDT6(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=L3ZDT1(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=L3ZDT2(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=L3ZDT3(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=L3ZDT4(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=L3ZDT6(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=WFG1(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=2*[1:Problem.n]';
Problem.objFunction=mop(name);
return

function [Problem]=WFG2(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=2*[1:Problem.n]';
Problem.objFunction=mop(name);
return

function [Problem]=WFG3(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=2*[1:Problem.n]';
Problem.objFunction=mop(name);
return

function [Problem]=WFG4(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=2*[1:Problem.n]';
Problem.objFunction=mop(name);
return

function [Problem]=WFG5(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=2*[1:Problem.n]';
Problem.objFunction=mop(name);
return

function [Problem]=WFG6(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=2*[1:Problem.n]';
Problem.objFunction=mop(name);
return

function [Problem]=WFG7(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=2*[1:Problem.n]';
Problem.objFunction=mop(name);
return

function [Problem]=WFG8(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=2*[1:Problem.n]';
Problem.objFunction=mop(name);
return

function [Problem]=WFG9(name)
Problem.n=10;
Problem.lb=zeros(Problem.n,1);
Problem.ub=2*[1:Problem.n]';
Problem.objFunction=mop(name);
return

function [Problem]=UF1(name)
Problem.n=30;
Problem.lb=-1*ones(Problem.n,1); Problem.lb(1)=0; 
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=UF2(name)
Problem.n=30;
Problem.lb=-1*ones(Problem.n,1); Problem.lb(1)=0; 
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=UF3(name)
Problem.n=30;
Problem.lb=zeros(Problem.n,1);
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=UF4(name)
Problem.n=30;
Problem.lb=-2*ones(Problem.n,1); Problem.lb(1)=0; 
Problem.ub=2*ones(Problem.n,1); Problem.ub(1)=1;
Problem.objFunction=mop(name);
return

function [Problem]=UF5(name)
Problem.n=30;
Problem.lb=-1*ones(Problem.n,1); Problem.lb(1)=0; 
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=UF6(name)
Problem.n=30;
Problem.lb=-1*ones(Problem.n,1); Problem.lb(1)=0; 
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=UF7(name)
Problem.n=30;
Problem.lb=-1*ones(Problem.n,1); Problem.lb(1)=0; 
Problem.ub=ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=UF8(name)
Problem.n=30;
Problem.lb=-2*ones(Problem.n,1); Problem.lb(1:2)=0; 
Problem.ub=2*ones(Problem.n,1); Problem.ub(1:2)=1;
Problem.objFunction=mop(name);
return

function [Problem]=UF9(name)
Problem.n=30;
Problem.lb=-2*ones(Problem.n,1); Problem.lb(1:2)=0; 
Problem.ub=2*ones(Problem.n,1); Problem.ub(1:2)=1;
Problem.objFunction=mop(name);
return

function [Problem]=UF10(name)
Problem.n=30;
Problem.lb=-2*ones(Problem.n,1); Problem.lb(1:2)=0; 
Problem.ub=2*ones(Problem.n,1); Problem.ub(1:2)=1;
Problem.objFunction=mop(name);
return

function [Problem]=I(name)
Problem.n=24;
Problem.lb=zeros(Problem.n,1);
Problem.ub=2*[1:Problem.n]';
Problem.objFunction=mop(name);
return

function [Problem]=Kursawe(name)
Problem.n=3;
Problem.lb=-5*ones(Problem.n,1);
Problem.ub=5*ones(Problem.n,1);
Problem.objFunction=mop(name);
return

function [Problem]=OKA1(name)
Problem.n=2;
s1=sin(pi/12);
s2=cos(pi/12);
Problem.lb=[6*s1; -2*pi*s1];
Problem.ub=[6*s1+2*pi*s2; 6*s2];
Problem.objFunction=mop(name);
return

function [Problem]=OKA2(name)
Problem.n=3;
Problem.lb=[-pi; -5; -5];
Problem.ub=[pi; 5; 5];
Problem.objFunction=mop(name);
return
