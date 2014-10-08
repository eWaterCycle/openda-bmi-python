%% clean

clear all
close all
clc

%% model specifics

%Directories:
runDir = '.';
dataDir = [runDir filesep 'data'];
modelDir = [runDir filesep 'models'];
classDir = [runDir filesep '..'];

addpath(modelDir);
addpath(classDir);

sigma=10;
rho=28;
beta=8/3;

startTime=0;
endTime=500;
dt=1e-3;


%% create model

LorenzObj=LorenzBMI(startTime,endTime,dt,sigma,rho,beta);


%% run as ensemble

LorenzObj.initialize;

LorenzObj.update_until(10);

LorenzObj.set_value('x',LorenzObj.get_value('x')+randn(1));
LorenzObj.set_value('y',LorenzObj.get_value('y')+randn(1));
LorenzObj.set_value('z',LorenzObj.get_value('z')+randn(1));

LorenzObj.update_until(endTime);

vars=LorenzObj.get_output_var_names;

