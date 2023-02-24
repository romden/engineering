function [XX, YY, nf] =  MO_fmincon(Problem, varargin)

Problem.LB=Problem.LB(:)';
Problem.UB=Problem.UB(:)';
if ~isfield(Problem,'x0') || isempty(Problem.x0)
    Problem.x0 = Problem.LB+rand(1,Problem.Variables).*(Problem.UB-Problem.LB);
end
f=feval(Problem.ObjFunction, Problem.x0, varargin{:});
m=numel(f);

fun=Problem.ObjFunction;

nf=0;
if ~isfield(Problem,'Phi') || isempty(Problem.Phi)
    Phi=zeros(m);
    for i=1:m
        Problem.ObjFunction='singleFun';
        options=optimset('algorithm','sqp','MaxFunEvals',10000);
        Problem.x0 = Problem.LB+rand(1,Problem.Variables).*(Problem.UB-Problem.LB);
        x = fmincon(Problem.ObjFunction,Problem.x0,[],[],[],[],Problem.LB,Problem.UB,[],options,fun,i);
        Phi(:,i)=feval(fun, x, varargin{:});
        %     nf=nf+nf0;
    end
else
    Phi=Problem.Phi;
end


load weights
w=weights{m,2};

XX=zeros(size(w,1),Problem.Variables);
YY=zeros(size(w));

switch Problem.method
    
    case 'nbi'
        
        nbi.Phi=Phi;
        nbi.fun=fun;
        
        Problem.Variables=Problem.Variables+1;
        Problem.LB=[Problem.LB 0];
        Problem.UB=[Problem.UB 1];
        Problem.ObjFunction='NBI_fun';
        Problem.Constraints='NBI_constr';
%         Problem.x0 = Problem.LB+rand(1,Problem.Variables).*(Problem.UB-Problem.LB);
        options=optimset('algorithm','sqp','MaxFunEvals',1000);
        for i=1:size(w,1)
            
            nc.w=w(i,:);
            save nc nc
            
            x = fmincon(Problem.ObjFunction,Problem.x0,[],[],[],[],Problem.LB,Problem.UB,Problem.Constraints,options, varargin{:});
%             nf=nf+stats.ObjFunCounter;
            Problem.x0=x;
            
            XX(i,:)=x(1:end-1);
            YY(i,:)=feval(nbi.fun,x(1:end-1));
        end
        
           
    case 'nc'
        
        nc.Phi=Phi;%[1 0; 0 1]
        nc.fun=fun;
        nc.ideal=min(nc.Phi);
        nc.nadir=max(nc.Phi);
        
        Problem.ObjFunction='NC_fun';
        Problem.Constraints='NC_constr';
%         Problem.x0 = Problem.LB+rand(1,Problem.Variables).*(Problem.UB-Problem.LB);
        options=optimset('algorithm','sqp','MaxFunEvals',10000);
        for i=1:size(w,1)
            
            nc.w=w(i,:);
            save nc nc
            
            x = fmincon(Problem.ObjFunction,Problem.x0,[],[],[],[],Problem.LB,Problem.UB,Problem.Constraints,options, varargin{:});
            
%             nf=nf+stats.ObjFunCounter;
            Problem.x0=x;
            
            XX(i,:)=x;
            YY(i,:)=feval(nc.fun,x);
        end
end
        
        

return