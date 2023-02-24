function [data,index]=nondomination_sorting(data, varargin)
% P is a matrix N-by-M
% N - number of individual
% M - number of objectives
P=data;
index=zeros(size(P,1),1);
k=0;
if size(P,1)>1
    for i=1:size(P,1)
        t=0;
        for j=1:size(P,1)
            if i==j
                continue
            end
            if all(P(i,:)>=P(j,:)) && any(P(i,:)>P(j,:))
                t=1; % i is dominated by j
                break
            end
        end
        if t==1
            continue
        else
            k=k+1;
            index(k)=i;
        end
    end
else
    return
end
index=index(index~=0);
data=data(index,:);

return

