clear
clc

x0 = importdata('x0.dat');
[~, ceq0] = constraints(x0);

x = importdata('x.dat');
[~, ceq1] = constraints(x);

disp([sum(abs(ceq0)) sum(abs(ceq1))]) 

% ceq2 = importdata('ceq.dat');
% disp([ceq1(:) ceq2(:)])


% data = rand(10);
% save('data.dat','data','-ascii');
% rdn = randn(10,1);
% save('randn.dat','rdn','-ascii');

% data = importdata('data.dat');
% poolSize = size(data,1);
% data = data - repmat(mean(data), poolSize, 1);
% C = data'*data/(poolSize-1);
% [B, D] = eig(C);
% rdn = importdata('randn.dat');
% v1 = importdata('v.dat');
% v2 = B*(D*rdn(:));
% disp([v1(:) v2(:)])