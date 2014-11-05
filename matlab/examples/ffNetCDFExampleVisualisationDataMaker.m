clc
clear all
close all


%netCDF file on remote disk to get slices from:
fileNameRead ='/Volumes/MY_PASSPORT/eWaterCycleDATA/disChanWaterBody_monthAvg_1960_to_2010.nc';
%name of new netCDF file
fileNameWrite = 'eWaterExampleVisualisationData.nc';
%number of time-slices to extract
N=14;
startTime=50;

%globalUncertaintyIncreaseRate
globalUncertaintyIncreaseRate=0.1;

%create empty netcdf file
ncwriteschema(fileNameWrite,ncinfo(fileNameRead));

%read discharge, last 
dischargeData=ncread(fileNameRead,'discharge',[1 1 startTime],[inf inf N]);
timeData=ncread(fileNameRead,'time',startTime,N);

%write data into new file
ncwrite(fileNameWrite,'discharge',dischargeData); 
ncwrite(fileNameWrite,'time',timeData);
ncwrite(fileNameWrite,'longitude',ncread(fileNameRead,'longitude'));
ncwrite(fileNameWrite,'latitude',ncread(fileNameRead,'latitude'));

%add a discharge Uncertainty variable in the file based on the discharge
dischargeUncertaintySchema=ncinfo(fileNameRead,'discharge');
dischargeUncertaintySchema.Name='dischargeUncertainty';
ncwriteschema(fileNameWrite,dischargeUncertaintySchema);

%create FAKE!!!! discharge uncertainty data.
uncertaintyIncreaseRate=globalUncertaintyIncreaseRate*ones(size(dischargeData,1),size(dischargeData,2));
uncertaintyData=zeros(size(dischargeData));
maxUncertainty=ones(size(dischargeData,1),size(dischargeData,2));
for nCounter=1:N
    uncertaintyData(:,:,nCounter)=min(uncertaintyIncreaseRate*nCounter,maxUncertainty);
end
ncwrite(fileNameWrite,'dischargeUncertainty',uncertaintyData);
