package com.readytalk.plugins.proto.features

class ObjcProtoFeature extends ProtoFeatureBase {

  void configure() {
    args << "--objc_out=${getOutputDir()}"
  }
}
