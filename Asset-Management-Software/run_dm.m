clear
clc

% pesos
w = [0.2 0.8];

dataFile = 'data/output/network/network.xlsx';

% objetivos
y = xlsread(dataFile, 4, 'B2:C101');

types = {'CHB', 'WSUM', 'WM', 'WSFM'};

% indice da solucao
idx = valueFunction(y, w, types{1})