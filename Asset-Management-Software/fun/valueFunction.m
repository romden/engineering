function [idx] = valueFunction(f, w, type, p)

switch(type)
    case 'CHB'
       idx = chebyshev(f, w);
    case 'WSUM'
        idx = weightedSum(f, w);
    case 'WM'
        if nargin < 4; p = 2; end
        idx = weightedMetrics(f, w, p);
    case 'WSFM'
        idx = weightedStressFunctionMethod(f, w);
end

return


function [idx] = chebyshev(f, w)

% ensure that vector of weights is row vector
w = w(:)';

% get size
[n, ~] = size(f);

% scale each objective to the interval [0,1]
f = (f - repmat(min(f),n,1))./repmat(max(f)-min(f),n,1);

% calc metric values
values = max(repmat(w, n, 1).*f, [], 2);

% find sol with min value
[~, idx] = min(values);

return


function [idx] = weightedSum(f, w)

% ensure that vector of weights is row vector
w = w(:)';

% get size
[n, ~] = size(f);

% scale each objective to the interval [0,1]
f = (f - repmat(min(f) ,n, 1))./repmat(max(f) - min(f), n, 1);

% calc metric values
values = sum(repmat(w, n, 1).*f, 2);

% find sol with min value
[~, idx] = min(values);

return



function [idx] = weightedMetrics(f, w, p)

% minimize Lp norm

% ensure that vector of weights is row vector
w = w(:)';

% get size
[n, ~] = size(f);

% scale each objective to the interval [0,1]
f = (f - repmat(min(f) ,n, 1))./repmat(max(f) - min(f), n, 1);

% calc metric values
values = power(sum(power(repmat(w, n, 1).*f, p), 2), 1/p);

% find sol with min value
[~, idx] = min(values);

return


function [idx] = weightedStressFunctionMethod(f, w)

% minimize Lp norm

% ensure that vector of weights is row vector
w = w(:)';

% first convert to maximization problem by flipping sign
% f = -f;

% get size
[n, m] = size(f);

% scale each objective to the interval [0,1]
f = (f - repmat(min(f) ,n, 1))./repmat(max(f) - min(f), n, 1);

metric = zeros(n, 1);
sigma = zeros(1, m);

% if m == 2 w = 1-w; end;

w = 1-w;

delta1 = 0.001;
delta2 = 0.001;
        
% calc metric values
for i = 1:n
    
    for j = 1:m
        
        xi = -1/tan(-pi/(2+2*delta2))*tan(pi*(w(j)-0.5)/(1+delta2)) + 1;
        phi = 3/4*power(1-w(j), 2) + 2*(1-w(j))+delta1;
        psi = phi + 4*w(j) - 2;
        omega = -xi*psi/(tan(pi/phi*(w(j)-1)+delta1)*phi);
        
        if(f(i,j) <= w(j))
            sigma(j) = omega*tan(-pi/psi*(f(i,j) - w(j))) + xi;
            
        else
            sigma(j) = -xi/tan(pi/phi*(w(j)-1))*tan(-pi/phi*(f(i,j)-w(j))) + xi;
        end
        
    end
    
    metric(i) =  max(sigma);
end

% find sol with min value
[~, idx] = min(metric);

return