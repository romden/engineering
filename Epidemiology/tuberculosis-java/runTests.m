clear
clc

% problem{1}.type = 'IL2-u1u2'; problem{2}.type = 'I-L2-u1u2'; problem{3}.type = 'IL2-u1-u2'; problem{4}.type = 'I-L2-u1-u2';
% problem{1}.m = 2; problem{2}.m = 3; problem{3}.m = 3; problem{4}.m = 4;
% problem{1}.n = 60; problem{2}.n = 60; problem{3}.n = 60; problem{4}.n = 60;
% 
% p = 2; % problem
% 
% fid = fopen('param_problem.dat','w');
% fprintf(fid, 'problem %s\n', problem{p}.type);
% fprintf(fid, 'objectives %s\n', num2str(problem{p}.m));
% fprintf(fid, 'variables %s', num2str(problem{p}.n));
% fclose(fid);

% system('java -jar dist\tuberculosis-java.jar');

x = importdata('VAR.dat');
y = importdata('FUN.dat');

% figure(1)
% if size(y,2)==2
%     plot(y(:,1),y(:,2),'ro');
% elseif size(y,2)==3
%     plot3(y(:,1),y(:,2),y(:,3),'r*');
% end
figure(2)
idx = 50;
plot(1:size(x,2)/2,x(idx,1:size(x,2)/2),'b.');
% plot(1:size(x,2)/2,x(idx,1:2:size(x,2)),'b.');
%  axis([0 size(x,2)/2 0 1])

%% check
% fid = fopen('param_problem.dat','r');
% fscanf(fid, '%s', 1);
% str = fscanf(fid, '%s', 1);
% fclose(fid);
% y2=zeros(size(y));
% for i=1:size(x,1)
%     y2(i,:) = tuberculosis(x(i,:), str);
% end
% if size(y,2)==2
%     plot(y(:,1),y(:,2),'ro', y2(:,1),y2(:,2),'g*');
% elseif size(y,2)==3
%     plot3(y(:,1),y(:,2),y(:,3),'ro', y2(:,1),y2(:,2),y2(:,3),'g*');
% end

% load(str)
% hold on
% if size(y,2)==2
%     plot(A.f(:,1),A.f(:,2),'b.');
% elseif size(y,2)==3
%     plot3(A.f(:,1),A.f(:,2),A.f(:,3),'b.');
% end



%% process results
% for i=1:10:size(x,1)
%     figure(i+1)
%     plot(1:size(x,2)/2,x(i,1:size(x,2)/2),'b.', 1:size(x,2)/2, x(i,size(x,2)/2+1:size(x,2)),'r.');
% end


%% multiple runs
    
% problem{1}.type = 'IL2-u1u2'; problem{2}.type = 'I-L2-u1u2'; problem{3}.type = 'IL2-u1-u2'; problem{4}.type = 'I-L2-u1-u2';
% problem{1}.m = 2; problem{2}.m = 3; problem{3}.m = 3; problem{4}.m = 4;
% problem{1}.n = 60; problem{2}.n = 60; problem{3}.n = 60; problem{4}.n = 60;
% 
% variation{1} = 'DE'; variation{2} = 'PM'; variation{3} = 'GM'; variation{4} = 'GA';
% v=3; % variation type
% 
% for p = 1:length(problem)
%     
%     fid = fopen('param_problem.dat','w');
%     fprintf(fid, 'problem %s\n', problem{p}.type);
%     fprintf(fid, 'objectives %s\n', num2str(problem{p}.m));
%     fprintf(fid, 'variables %s', num2str(problem{p}.n));
%     fclose(fid);
%     
%     data.x = cell(30, 1);
%     data.f = cell(30, 1);
%     data.t = cell(30, 1);
%     data.gen = cell(30, 10);
%     
%     for i = 1:30
%         pause(1)
%         tic
%         system('java -jar dist\tuberculosis-java.jar');
%         t=toc;
%         pause(1)
%         data.x{i} = importdata('VAR.dat');
%         data.f{i} = importdata('FUN.dat');
%         data.t{i} = t;
%         for j = 1:10
%             data.gen{i,j} = importdata(strcat('gen_',num2str(50*j),'.dat'));
%         end
%         
%         fprintf('problem: %s \t run: %d \t time: %.2f \n', problem{p}.type, i, t);
%     end
%     
%     save(strcat(variation{v},'-', problem{p}.type), 'data')
% end






%% check problem
% prob='LZ09_F2';
% Problem=get_struct(prob);
% y2=zeros(size(y));
% for i=1:size(x,1)
%     y2(i,:)=feval(Problem.objFunction,x(i,:));
% %     y2(i,:)=feval(Problem.objFunction, x(i,:), size(y,2));
% end
% if size(y,2)==2
%     plot(y(:,1),y(:,2),'ro', y2(:,1),y2(:,2),'b*');
% else
%     plot3(y(:,1),y(:,2),y(:,3),'ro', y2(:,1),y2(:,2), y2(:,3),'b*');
% end
