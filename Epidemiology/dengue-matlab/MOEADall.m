function [Approximation, Statistics] = MOEADall(Problem, varargin)
%% Reference
% H. Li and Q. Zhang. Multiobjective optimization problems with complicated
% Pareto sets, MOEA/D and NSGA-II. IEEE Transactions on Evolutionary
% Computation, 13(2):284-302, 2009.
%% Input
% Problem.m - number of objective functions
% Problem.n - number of variables
% Problem.lb - column vector of lower bounds of variables
% Problem.ub - column vector of upper bounds of variables
% Problem.objFunction - objective function
%% Output
% A.x - approximation in the decision space
% A.f - approximation in the objective space
% A.g - inequality constraints values
% A.h - equality constraints values
% Statistics - structure with running data
%% Implementation
% Roman Denysiuk
%%
tic;
% randomly generate initial population
[RunData, Population] = Initialization(Problem, varargin{:});
clear Problem

% vizualize current population
if RunData.Algorithm.verbose
    y=cell2mat({Population.f});
    grid on;
    title(strcat('Population at generation: ',num2str(0)));
    xlabel('f_1(x)'); ylabel('f_2(x)'); drawnow; hold on;
    p=plot(y(1,:),y(2,:),'.','EraseMode','background');
end

%% start evolutionary process
while  RunData.Statistics.generations < RunData.Algorithm.maxGen
    
    % increment number of generations
    RunData.Statistics.generations = RunData.Statistics.generations+1;
    
    
    for i = 1:RunData.Algorithm.mu
        
        % select mating pool
        [matingPool] = MatingSelection(RunData, i, varargin{:});
        
        % produce offspring
        [RunData, child] = Variation(RunData, Population, matingPool, i, varargin{:});
        
        % update population
        [Population] = EnvironmentalSelection(RunData, Population, child, matingPool, varargin{:});
        
    end
    
    % vizualize current population
    if RunData.Algorithm.verbose
        y=cell2mat({Population.f});
        pause(0.01)
        title(strcat('Population at generation: ',num2str(RunData.Statistics.generations)));
        set(p,'XData',y(1,:),'YData',y(2,:),'color','r');
        drawnow;
    end
    
end


% approximation to the Pareto front
Approximation.x=cell2mat({Population.x})';
Approximation.f=cell2mat({Population.f})';

% running statistics
Statistics=RunData.Statistics;
Statistics.time=toc;

return



%% Initialization Procedure
function [RunData, Population] = Initialization(Problem, varargin)

% structure of algorithm parameters
Algorithm = struct('CR', 1.0, ... DE crossover probability
                   'F', 0.5, ... scaling parameter
                   'pc', 1.0, ... GA crossover probability
                   'etac', 20, ... GA crossover distribution
                   'pm', 1/Problem.n, ... mutation probability
                   'etam', 20, ... mutation distribution index
                   'T', 20, ... number of weight vectors in the neighborhood of each weight vector
                   'delta', 0.9, ... probability that the parent selected from the neighborhood
                   'nr', 2, ... maximum number of solutions replaced by each child solution
                   'mu', 300, ... population size
                   'variation', 'GM', ... type of the variation operator (DE, GA, PM, GM)
                   'fitness', 'CHB', ... type of scalarizing fitness (WSM, CHB, PBI, VADS);   
                   'normalization', true, ... normalization of objectives
                   'maxGen', 300, ... stopping criterion
                   'verbose', true ... vizualization
                   );

% structure of statistics
Statistics = struct('evaluations', 0, ... number of function evaluations
                    'generations', 0, ... number of generations
                    'time', 0 ... running time
                    );
        
% general data structure
RunData = struct('Problem', Problem, ... problem data
                 'Algorithm', Algorithm, ... algorithm data
                 'Statistics', Statistics ... statistic data
                 );

% individual in the population
Individual = struct('x', zeros(Problem.n, 1), ... decision vector
                    'f', zeros(Problem.m, 1), ... objective vector
                    'nf', zeros(Problem.m, 1) ... normalized objective vector
                    );
              
% initialize population structure
Population = repmat(Individual, RunData.Algorithm.mu, 1);

% randomly generate initial population
% for i = 1:RunData.Algorithm.mu
%     Population(i).x = RunData.Problem.lb+rand(RunData.Problem.n,1).*(RunData.Problem.ub-RunData.Problem.lb);        
%     [RunData, Population(i)] = Evaluation(RunData, Population(i), varargin{:});
% end

% problem specific
cube = (RunData.Problem.ub-RunData.Problem.lb)/RunData.Algorithm.mu;
for i = 1:RunData.Algorithm.mu
    Population(i).x = RunData.Problem.lb+rand(RunData.Problem.n,1).*(cube*i);        
    [RunData, Population(i)] = Evaluation(RunData, Population(i), varargin{:});
end

% load(varargin{1})
% for i = 1:RunData.Algorithm.mu
%     Population(i).x = A.x(i,:)';        
%     Population(i).f = A.f(i,:)';
% end


% initialize weight vectors
if RunData.Problem.m == 2
    % generate
    w=zeros(RunData.Algorithm.mu, 2);
    for i = 1:RunData.Algorithm.mu
        w(i,1) = i/RunData.Algorithm.mu;
        w(i,2) = (RunData.Algorithm.mu-i)/RunData.Algorithm.mu;
    end
else
    % read from file
    load weights
    w = weights{RunData.Problem.m,4};
end
w(w==0)=1e-4;
RunData.Algorithm.w = w';

% initialize distance matrix
distanceMatrix = zeros(RunData.Algorithm.mu);
% initialize neighborhood matrix
RunData.Algorithm.neighborhood = zeros(RunData.Algorithm.mu, RunData.Algorithm.T);
for i = 1:RunData.Algorithm.mu
    for j=i+1:RunData.Algorithm.mu
        distanceMatrix(i,j)=norm(w(i,:)-w(j,:));
        distanceMatrix(j,i)=distanceMatrix(i,j);
    end
    [~,index]=sort(distanceMatrix(i,:));
    RunData.Algorithm.neighborhood(i,:)=index(1:RunData.Algorithm.T);
end
RunData.Algorithm.whole = 1:RunData.Algorithm.mu;

% reference point
RunData.Algorithm.ref = min(cell2mat({Population.f}), [],2);

return


%% Evaluation Procedure
function [RunData, individual] = Evaluation(RunData, individual, varargin)

try
    [individual.f] = feval(RunData.Problem.objFunction, individual.x, varargin{:});
    
    % update counter
    RunData.Statistics.evaluations = RunData.Statistics.evaluations+1;

catch
    error('MOEA/D:ObjectiveError', ...
        ['Cannot continue because user supplied objective function' ...
        ' failed with the following error:\n%s'], lasterr)
end

return


%% Mating Selection Procedure
function [matingPool] = MatingSelection(RunData, i, varargin)

% select individuals from neighborhood or population
if rand <= RunData.Algorithm.delta
    matingPool = RunData.Algorithm.neighborhood(i,:);
else
    matingPool = RunData.Algorithm.whole;
end

return


%% Variation procedure
function [RunData, child] = Variation(RunData, Population, matingPool, i, varargin)

poolSize = numel(matingPool);

switch RunData.Algorithm.variation
    
    case 'DE' % differential evolution operator
        
        % select individuals for reproduction
        r1 = matingPool(randi(poolSize));
        while r1 == i
            r1 = matingPool(randi(poolSize));
        end
        r2 = matingPool(randi(poolSize));
        while (r2 == r1 || r2 == i)
            r2 = matingPool(randi(poolSize));
        end
        
        % DE operator
        child.x = zeros(RunData.Problem.n, 1);
        jr = randi(RunData.Problem.n);
        for j = 1:RunData.Problem.n
            if rand <= RunData.Algorithm.CR || j==jr
                child.x(j) = Population(i).x(j)+RunData.Algorithm.F*(Population(r1).x(j)-Population(r2).x(j));
            else
                child.x(j) = Population(i).x(j);
            end
            % bounds
            if child.x(j) < RunData.Problem.lb(j)
                child.x(j) = RunData.Problem.lb(j) + rand*(Population(i).x(j)-RunData.Problem.lb(j));
            elseif child.x(j) > RunData.Problem.ub(j)
                child.x(j) = RunData.Problem.ub(j) - rand*(RunData.Problem.ub(j) - Population(i).x(j));
            end
        end
        
        
    case 'GA' % genetic operator (SBX crossover)
        
        % select individuals for reproduction
        r1 = matingPool(randi(poolSize));
        r2 = matingPool(randi(poolSize));
        while (r2 == r1)
            r2 = matingPool(randi(poolSize));
        end
        
        % initialize offspring
        child_1.x = Population(r1).x;
        child_2.x = Population(r2).x;
        
        % apply SBX (Simulated Binary Crossover) with probability pc
        if rand <= RunData.Algorithm.pc
            
            % perform crossover
            for j = 1:RunData.Problem.n
                
                u = rand;
                if u <= 0.5
                    beta = (2*u)^(1/(RunData.Algorithm.etac+1));
                else
                    beta = (1/(2*(1 - u)))^(1/(RunData.Algorithm.etac+1));
                end
                child_1.x(j) = 0.5*(((1 - beta)*Population(r1).x(j)) + (1 + beta)*Population(r2).x(j));
                child_2.x(j) = 0.5*(((1 + beta)*Population(r1).x(j)) + (1 - beta)*Population(r2).x(j));
                
                % swap gens with probability 0.5
                if rand <= 0.5
                    temp_1 = child_2.x(j);
                    temp_2 = child_1.x(j);
                else
                    temp_1 = child_1.x(j);
                    temp_2 = child_2.x(j);
                end
                child_1.x(j) = temp_1;
                child_2.x(j) = temp_2;
                
            end
            
        end
        
        if rand <= 0.5
            child.x = child_1.x;
        else
            child.x = child_2.x;
        end
        
    case 'PM' % probabilistic model based operator
        
        % build a model
        data = cell2mat({Population(matingPool).x})';
        data = data - repmat(mean(data), poolSize, 1);
        C = data'*data/(poolSize-1);
        [B, D] = eig(C);
        
        % generate offspring       
        child.x = Population(i).x + B*(D*randn(RunData.Problem.n, 1));
        
    case 'GM' % guided mutation
        
        % select a neighbor
        r = matingPool(randi(poolSize));
        while (r == i)
            r = matingPool(randi(poolSize));
        end
        
        t = Population(i).x; % current subproblem
        x = Population(r).x; % neighbor
        
        H = zeros(RunData.Problem.n, 1);
        for j = 1:RunData.Problem.n
            if rand <= RunData.Algorithm.pm
                H(j) = randn;
            end
        end
        
        mu = 0.005;
        R = max(0.1*norm(t-x), mu);
        
        % produce child
        child.x = x + 0.5*(t-x)*randn + R*H;
        
        
end


% apply polynomial mutation
for j = 1:RunData.Problem.n
    
    % mutate offspring with probability pm
    if rand <= RunData.Algorithm.pm
        u = rand;
        if u <= 0.5
            delta = (2*u)^(1/(RunData.Algorithm.etam+1)) - 1;
        else
            delta = 1 - (2*(1 - u))^(1/(RunData.Algorithm.etam+1));
        end
        child.x(j) = child.x(j) + (RunData.Problem.ub(j)-RunData.Problem.lb(j))*delta;
    end
    
    % bounds
    if child.x(j) < RunData.Problem.lb(j)
        child.x(j) = RunData.Problem.lb(j);
    end
    if child.x(j) > RunData.Problem.ub(j)
        child.x(j) = RunData.Problem.ub(j);
    end
end


% evaluate a new solution
[RunData, child] = Evaluation(RunData, child, varargin{:});

% update ideal point
RunData.Algorithm.ref = min(horzcat(RunData.Algorithm.ref, child.f), [],2);


return


%% environmental selection procedure
function [Population] = EnvironmentalSelection(RunData, Population, child, matingPool, varargin)

poolSize = numel(matingPool);

% normalize objectives
if RunData.Algorithm.normalization    
    nf = horzcat(cell2mat({Population.f}), child.f);
    nf = (nf - repmat(RunData.Algorithm.ref, 1, RunData.Algorithm.mu+1))./repmat(max(nf, [], 2) - RunData.Algorithm.ref, 1, RunData.Algorithm.mu+1);
    for j = matingPool
        Population(j).nf = nf(:,j);
    end
    child.nf = nf(:,end);
end

% update solutions
matingPool = matingPool(randperm(poolSize));
c=0;
for j = matingPool
    
    % calculate fitness
    if RunData.Algorithm.normalization
        newValue = Fitness(zeros(RunData.Problem.m, 1), RunData.Algorithm.w(:,j), child.nf, RunData.Algorithm.fitness);
        oldValue = Fitness(zeros(RunData.Problem.m, 1), RunData.Algorithm.w(:,j), Population(j).nf, RunData.Algorithm.fitness);
    else
        newValue = Fitness(RunData.Algorithm.ref, RunData.Algorithm.w(:,j), child.f, RunData.Algorithm.fitness);
        oldValue = Fitness(RunData.Algorithm.ref, RunData.Algorithm.w(:,j), Population(j).f, RunData.Algorithm.fitness);
    end
    
    % replace
    if newValue < oldValue
        Population(j).x = child.x;
        Population(j).f = child.f;
        c=c+1;
    end
    if c == RunData.Algorithm.nr
        break
    end
    
end

return

%% fitness function
function value = Fitness(z, w, f, type)

switch type
    
    case 'WSM' % weighted sum scalariazing methd
        value = w'*(f-z);
        
    case 'CHB' % Chebyshev method
        value = max(w.*abs(f-z));
        
    case 'PBI' % penalty boundary intersection method
        d1 = (w'*(f-z))/norm(w);
        d2 = norm(f - (z+d1*w));
        theta = 5;
        value = d1 + theta*d2;
        
    case 'VADS' % vector-angle distance scaling aggregation method (MSOPS-II)
        b = 100;
        value = exp((b+1)*log(norm(f)) - b*log(w'*(f-z)));        
end

return
