clear
clc


x=rand(1,1001);
fid = fopen('xData.dat','w');
for i=1:length(x)
    fprintf(fid,'%.4f ',x(i));
end
fclose(fid);

% % test values
% x=importdata('xData.dat');
% y1=importdata('yData.dat');
% 
% % original
% y0=dengue(x);
% 
% % plot results
% plot(y0(1),y0(2),'g*',y1(1),y1(2),'ro')
% 
% %check integral
% t=linspace(0,84,1001);
% I=trapz(t,x)

    