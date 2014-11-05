nccreate('myncclassic.nc','x',...
'Dimensions',{'time' 30 'cols' 1},...
'Format','classic');
ncwrite('myncclassic.nc','x',randn(30,1));

ncwriteatt('myncclassic.nc','x','units','none');
nccreate('myncclassic.nc','time','Dimensions',{'time' 30});
ncwrite('myncclassic.nc','time',double(1:30));
ncwriteatt('myncclassic.nc','time','units','Days since 1901-01-01');
