clear
clc

problemName = 'WFG1'; % ZDT1 DTLZ1 WFG1 LZ09_F1 LGZ1 UF1 R2_DTLZ2_M5 R2_DTLZ3_M5 CEC2007_S_ZDT1
objectives = 3; 
variables = 22;

fid = fopen('param_problem.dat','w');
fprintf(fid,'problem %s \n', problemName);
fprintf(fid,'objectives %d \n', objectives);
fprintf(fid,'variables %d \n', variables);
fclose(fid);

pause(1)
system('Debug\testProblems.exe');
pause(1)

problem = mop(problemName);

x0=importdata('VAR.dat');
y0=importdata('FUN.dat');


y1=zeros(size(y0));
for i=1:size(x0,1)
    y1(i,:)=feval(problem,x0(i,:)',size(y0,2));
end

if size(y1,2)==2
    plot(y0(:,1),y0(:,2),'g*',y1(:,1),y1(:,2),'ro')
elseif size(y1,2)==3
    plot3(y0(:,1),y0(:,2),y0(:,3),'g*',y1(:,1),y1(:,2),y1(:,3),'ro')
end


% load PF
% for i = 1:size(PF,1)
%     if strcmp(PF{i,1},problemName)
%         pf = PF{i,size(y0,2)};
%         
%         hold on
%         if size(pf,2)==2
%             plot(pf(:,1),pf(:,2),'b.')
%         elseif size(pf,2)==3
%             plot3(pf(:,1),pf(:,2),pf(:,3),'b.')
%         end
%         break
%     end
% end
        


% if size(y0,2)==2
%     plot(y0(:,1),y0(:,2),'g*')
% elseif size(y0,2)==3
%     plot3(y0(:,1),y0(:,2),y0(:,3),'g*')
% else
%     boxplot(y0);
% end
