function xlsx_rename_worksheet(fileXLSX, sheetNumbers, sheetNames)

%% rename sheet names in xlsx file

% rename work sheets, using ActiveX
currentFolder = pwd; % get current folder path
currentFolder(currentFolder=='\') = '/';
for j = 1:numel(sheetNumbers)
    idx = sheetNumbers(j);
    e = actxserver('Excel.Application'); % # open Activex server
    ewb = e.Workbooks.Open([currentFolder '/' fileXLSX]); % # open file (enter full path!)
    ewb.Worksheets.Item(idx).Name = sheetNames{j}; % # rename j-th sheet
    ewb.Save; % # save to the same file
    ewb.Close(false);
    e.Quit;
end

return