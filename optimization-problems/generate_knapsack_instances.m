clear
clc


%% generate knapsack instances

rs = RandStream.create('mt19937ar', 'Seed', 5000);
RandStream.setGlobalStream(rs);

for knapsacks = 2:10; % number of knapsacks
    
    for items = [1000 1500 2000 5000 10000]; % number of items
        
        w = 10+randi(90, [items, knapsacks]); % weights
        c = round(sum(w)/2); % capacity
        p = 10+randi(90, [items, knapsacks]); % profits
        
        folder = sprintf('knapsacks/knapsack_%d_%d', knapsacks, items);
        
        if exist(folder,'dir') ~= 7; mkdir(folder); end
        
        % save weights and profits to files
        fileWeight = fopen(sprintf('%s/%s', folder, 'weight'), 'w');
        fileProfit = fopen(sprintf('%s/%s', folder, 'profit'), 'w');
        for i = 1:items
            for j = 1:knapsacks
                fprintf(fileWeight,' %d', w(i,j));
                fprintf(fileProfit,' %d', p(i,j));
            end
            fprintf(fileWeight,'\n');
            fprintf(fileProfit,'\n');
        end
        fclose(fileWeight);
        fclose(fileProfit);
        
        % save capacity
        fileCapacity = fopen(sprintf('%s/%s', folder, 'capacity'), 'w');
        for j = 1:knapsacks
            fprintf(fileCapacity,' %d', c(j));
        end
        fclose(fileCapacity);
        
    end
    
end