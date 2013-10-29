package com.readytalk.plugins.proto.extensions

class ProtoExtension {
  static final String NAME = 'proto'
  static final String CONFIG_NAME = 'proto'
  static final String SOURCE_SET_NAME = 'proto'
  static final String COMPILE_TASK_NAME = "generateMainProtocol"
  static final String SETUP_PROTOC_TASK_NAME = 'setupProtoc'
  static final String PACKAGE_PROTO_TASK_NAME = 'packageProto'

  File protoGenSrcDir
  File protoSrcDirs
  File toolsDir
  File protocBaseDir
}
