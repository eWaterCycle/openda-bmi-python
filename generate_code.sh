thrift -out generated/main/java/ -gen java src/main/thrift/bmi.thrift
thrift -out generated/main/python/ -gen py:new_style src/main/thrift/bmi.thrift
