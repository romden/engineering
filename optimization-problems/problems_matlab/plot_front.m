function plot_front(YY, name)

figure(2)
if size(YY,2)==2
    plot(YY(:,1),YY(:,2),'ro');
elseif size(YY,2)==3
    plot3(YY(:,1),YY(:,2),YY(:,3),'r*');
end

load PF
hold on
for i = 1:size(PF,1)
    if strcmp(PF{i,1},name)
        if size(YY,2)==2
            pf=PF{i,2};
        plot(pf(:,1),pf(:,2),'b.');
    elseif size(YY,2)==3
        pf=PF{i,3};
        plot3(pf(:,1),pf(:,2),pf(:,3),'b.');
        end
    end
end

return

