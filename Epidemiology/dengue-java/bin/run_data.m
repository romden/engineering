clear
clc


%% check 
for i=1:400
    XX0(i,:)=rand(1,1001);
    YY0(i,:)=dengue(XX0(i,:));
    fid = fopen('xData.dat','w');
    for j=1:size(XX0,2)
        fprintf(fid,'%.4f ',XX0(i,j));
    end
    fclose(fid);
    pause(1)
    system('java runDengue');
    pause(1)
    YY1(i,:)=importdata('yData.dat');
end
plot(YY0(1:400),YY0(1:400,2),'g*',YY1(1:400,1),YY1(1:400,2),'ro')

    