#
# Autogenerated by Thrift Compiler (0.9.0)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py:new_style
#

from thrift.Thrift import TType, TMessageType, TException, TApplicationException
import bmi.thrift.BMIService
from ttypes import *
from thrift.Thrift import TProcessor
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TProtocol
try:
  from thrift.protocol import fastbinary
except:
  fastbinary = None


class Iface(bmi.thrift.BMIService.Iface):
  def get_grid_shape(self, long_var_name):
    """
    Parameters:
     - long_var_name
    """
    pass

  def get_grid_x(self, long_var_name):
    """
    Parameters:
     - long_var_name
    """
    pass

  def get_grid_y(self, long_var_name):
    """
    Parameters:
     - long_var_name
    """
    pass

  def get_grid_z(self, long_var_name):
    """
    Parameters:
     - long_var_name
    """
    pass


class Client(bmi.thrift.BMIService.Client, Iface):
  def __init__(self, iprot, oprot=None):
    bmi.thrift.BMIService.Client.__init__(self, iprot, oprot)

  def get_grid_shape(self, long_var_name):
    """
    Parameters:
     - long_var_name
    """
    self.send_get_grid_shape(long_var_name)
    return self.recv_get_grid_shape()

  def send_get_grid_shape(self, long_var_name):
    self._oprot.writeMessageBegin('get_grid_shape', TMessageType.CALL, self._seqid)
    args = get_grid_shape_args()
    args.long_var_name = long_var_name
    args.write(self._oprot)
    self._oprot.writeMessageEnd()
    self._oprot.trans.flush()

  def recv_get_grid_shape(self, ):
    (fname, mtype, rseqid) = self._iprot.readMessageBegin()
    if mtype == TMessageType.EXCEPTION:
      x = TApplicationException()
      x.read(self._iprot)
      self._iprot.readMessageEnd()
      raise x
    result = get_grid_shape_result()
    result.read(self._iprot)
    self._iprot.readMessageEnd()
    if result.success is not None:
      return result.success
    raise TApplicationException(TApplicationException.MISSING_RESULT, "get_grid_shape failed: unknown result");

  def get_grid_x(self, long_var_name):
    """
    Parameters:
     - long_var_name
    """
    self.send_get_grid_x(long_var_name)
    return self.recv_get_grid_x()

  def send_get_grid_x(self, long_var_name):
    self._oprot.writeMessageBegin('get_grid_x', TMessageType.CALL, self._seqid)
    args = get_grid_x_args()
    args.long_var_name = long_var_name
    args.write(self._oprot)
    self._oprot.writeMessageEnd()
    self._oprot.trans.flush()

  def recv_get_grid_x(self, ):
    (fname, mtype, rseqid) = self._iprot.readMessageBegin()
    if mtype == TMessageType.EXCEPTION:
      x = TApplicationException()
      x.read(self._iprot)
      self._iprot.readMessageEnd()
      raise x
    result = get_grid_x_result()
    result.read(self._iprot)
    self._iprot.readMessageEnd()
    if result.success is not None:
      return result.success
    raise TApplicationException(TApplicationException.MISSING_RESULT, "get_grid_x failed: unknown result");

  def get_grid_y(self, long_var_name):
    """
    Parameters:
     - long_var_name
    """
    self.send_get_grid_y(long_var_name)
    return self.recv_get_grid_y()

  def send_get_grid_y(self, long_var_name):
    self._oprot.writeMessageBegin('get_grid_y', TMessageType.CALL, self._seqid)
    args = get_grid_y_args()
    args.long_var_name = long_var_name
    args.write(self._oprot)
    self._oprot.writeMessageEnd()
    self._oprot.trans.flush()

  def recv_get_grid_y(self, ):
    (fname, mtype, rseqid) = self._iprot.readMessageBegin()
    if mtype == TMessageType.EXCEPTION:
      x = TApplicationException()
      x.read(self._iprot)
      self._iprot.readMessageEnd()
      raise x
    result = get_grid_y_result()
    result.read(self._iprot)
    self._iprot.readMessageEnd()
    if result.success is not None:
      return result.success
    raise TApplicationException(TApplicationException.MISSING_RESULT, "get_grid_y failed: unknown result");

  def get_grid_z(self, long_var_name):
    """
    Parameters:
     - long_var_name
    """
    self.send_get_grid_z(long_var_name)
    return self.recv_get_grid_z()

  def send_get_grid_z(self, long_var_name):
    self._oprot.writeMessageBegin('get_grid_z', TMessageType.CALL, self._seqid)
    args = get_grid_z_args()
    args.long_var_name = long_var_name
    args.write(self._oprot)
    self._oprot.writeMessageEnd()
    self._oprot.trans.flush()

  def recv_get_grid_z(self, ):
    (fname, mtype, rseqid) = self._iprot.readMessageBegin()
    if mtype == TMessageType.EXCEPTION:
      x = TApplicationException()
      x.read(self._iprot)
      self._iprot.readMessageEnd()
      raise x
    result = get_grid_z_result()
    result.read(self._iprot)
    self._iprot.readMessageEnd()
    if result.success is not None:
      return result.success
    raise TApplicationException(TApplicationException.MISSING_RESULT, "get_grid_z failed: unknown result");


class Processor(bmi.thrift.BMIService.Processor, Iface, TProcessor):
  def __init__(self, handler):
    bmi.thrift.BMIService.Processor.__init__(self, handler)
    self._processMap["get_grid_shape"] = Processor.process_get_grid_shape
    self._processMap["get_grid_x"] = Processor.process_get_grid_x
    self._processMap["get_grid_y"] = Processor.process_get_grid_y
    self._processMap["get_grid_z"] = Processor.process_get_grid_z

  def process(self, iprot, oprot):
    (name, type, seqid) = iprot.readMessageBegin()
    if name not in self._processMap:
      iprot.skip(TType.STRUCT)
      iprot.readMessageEnd()
      x = TApplicationException(TApplicationException.UNKNOWN_METHOD, 'Unknown function %s' % (name))
      oprot.writeMessageBegin(name, TMessageType.EXCEPTION, seqid)
      x.write(oprot)
      oprot.writeMessageEnd()
      oprot.trans.flush()
      return
    else:
      self._processMap[name](self, seqid, iprot, oprot)
    return True

  def process_get_grid_shape(self, seqid, iprot, oprot):
    args = get_grid_shape_args()
    args.read(iprot)
    iprot.readMessageEnd()
    result = get_grid_shape_result()
    result.success = self._handler.get_grid_shape(args.long_var_name)
    oprot.writeMessageBegin("get_grid_shape", TMessageType.REPLY, seqid)
    result.write(oprot)
    oprot.writeMessageEnd()
    oprot.trans.flush()

  def process_get_grid_x(self, seqid, iprot, oprot):
    args = get_grid_x_args()
    args.read(iprot)
    iprot.readMessageEnd()
    result = get_grid_x_result()
    result.success = self._handler.get_grid_x(args.long_var_name)
    oprot.writeMessageBegin("get_grid_x", TMessageType.REPLY, seqid)
    result.write(oprot)
    oprot.writeMessageEnd()
    oprot.trans.flush()

  def process_get_grid_y(self, seqid, iprot, oprot):
    args = get_grid_y_args()
    args.read(iprot)
    iprot.readMessageEnd()
    result = get_grid_y_result()
    result.success = self._handler.get_grid_y(args.long_var_name)
    oprot.writeMessageBegin("get_grid_y", TMessageType.REPLY, seqid)
    result.write(oprot)
    oprot.writeMessageEnd()
    oprot.trans.flush()

  def process_get_grid_z(self, seqid, iprot, oprot):
    args = get_grid_z_args()
    args.read(iprot)
    iprot.readMessageEnd()
    result = get_grid_z_result()
    result.success = self._handler.get_grid_z(args.long_var_name)
    oprot.writeMessageBegin("get_grid_z", TMessageType.REPLY, seqid)
    result.write(oprot)
    oprot.writeMessageEnd()
    oprot.trans.flush()


# HELPER FUNCTIONS AND STRUCTURES

class get_grid_shape_args(object):
  """
  Attributes:
   - long_var_name
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRING, 'long_var_name', None, None, ), # 1
  )

  def __init__(self, long_var_name=None,):
    self.long_var_name = long_var_name

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.STRING:
          self.long_var_name = iprot.readString();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('get_grid_shape_args')
    if self.long_var_name is not None:
      oprot.writeFieldBegin('long_var_name', TType.STRING, 1)
      oprot.writeString(self.long_var_name)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class get_grid_shape_result(object):
  """
  Attributes:
   - success
  """

  thrift_spec = (
    (0, TType.LIST, 'success', (TType.I32,None), None, ), # 0
  )

  def __init__(self, success=None,):
    self.success = success

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 0:
        if ftype == TType.LIST:
          self.success = []
          (_etype80, _size77) = iprot.readListBegin()
          for _i81 in xrange(_size77):
            _elem82 = iprot.readI32();
            self.success.append(_elem82)
          iprot.readListEnd()
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('get_grid_shape_result')
    if self.success is not None:
      oprot.writeFieldBegin('success', TType.LIST, 0)
      oprot.writeListBegin(TType.I32, len(self.success))
      for iter83 in self.success:
        oprot.writeI32(iter83)
      oprot.writeListEnd()
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class get_grid_x_args(object):
  """
  Attributes:
   - long_var_name
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRING, 'long_var_name', None, None, ), # 1
  )

  def __init__(self, long_var_name=None,):
    self.long_var_name = long_var_name

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.STRING:
          self.long_var_name = iprot.readString();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('get_grid_x_args')
    if self.long_var_name is not None:
      oprot.writeFieldBegin('long_var_name', TType.STRING, 1)
      oprot.writeString(self.long_var_name)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class get_grid_x_result(object):
  """
  Attributes:
   - success
  """

  thrift_spec = (
    (0, TType.LIST, 'success', (TType.DOUBLE,None), None, ), # 0
  )

  def __init__(self, success=None,):
    self.success = success

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 0:
        if ftype == TType.LIST:
          self.success = []
          (_etype87, _size84) = iprot.readListBegin()
          for _i88 in xrange(_size84):
            _elem89 = iprot.readDouble();
            self.success.append(_elem89)
          iprot.readListEnd()
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('get_grid_x_result')
    if self.success is not None:
      oprot.writeFieldBegin('success', TType.LIST, 0)
      oprot.writeListBegin(TType.DOUBLE, len(self.success))
      for iter90 in self.success:
        oprot.writeDouble(iter90)
      oprot.writeListEnd()
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class get_grid_y_args(object):
  """
  Attributes:
   - long_var_name
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRING, 'long_var_name', None, None, ), # 1
  )

  def __init__(self, long_var_name=None,):
    self.long_var_name = long_var_name

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.STRING:
          self.long_var_name = iprot.readString();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('get_grid_y_args')
    if self.long_var_name is not None:
      oprot.writeFieldBegin('long_var_name', TType.STRING, 1)
      oprot.writeString(self.long_var_name)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class get_grid_y_result(object):
  """
  Attributes:
   - success
  """

  thrift_spec = (
    (0, TType.LIST, 'success', (TType.DOUBLE,None), None, ), # 0
  )

  def __init__(self, success=None,):
    self.success = success

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 0:
        if ftype == TType.LIST:
          self.success = []
          (_etype94, _size91) = iprot.readListBegin()
          for _i95 in xrange(_size91):
            _elem96 = iprot.readDouble();
            self.success.append(_elem96)
          iprot.readListEnd()
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('get_grid_y_result')
    if self.success is not None:
      oprot.writeFieldBegin('success', TType.LIST, 0)
      oprot.writeListBegin(TType.DOUBLE, len(self.success))
      for iter97 in self.success:
        oprot.writeDouble(iter97)
      oprot.writeListEnd()
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class get_grid_z_args(object):
  """
  Attributes:
   - long_var_name
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRING, 'long_var_name', None, None, ), # 1
  )

  def __init__(self, long_var_name=None,):
    self.long_var_name = long_var_name

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.STRING:
          self.long_var_name = iprot.readString();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('get_grid_z_args')
    if self.long_var_name is not None:
      oprot.writeFieldBegin('long_var_name', TType.STRING, 1)
      oprot.writeString(self.long_var_name)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class get_grid_z_result(object):
  """
  Attributes:
   - success
  """

  thrift_spec = (
    (0, TType.LIST, 'success', (TType.DOUBLE,None), None, ), # 0
  )

  def __init__(self, success=None,):
    self.success = success

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 0:
        if ftype == TType.LIST:
          self.success = []
          (_etype101, _size98) = iprot.readListBegin()
          for _i102 in xrange(_size98):
            _elem103 = iprot.readDouble();
            self.success.append(_elem103)
          iprot.readListEnd()
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('get_grid_z_result')
    if self.success is not None:
      oprot.writeFieldBegin('success', TType.LIST, 0)
      oprot.writeListBegin(TType.DOUBLE, len(self.success))
      for iter104 in self.success:
        oprot.writeDouble(iter104)
      oprot.writeListEnd()
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)
