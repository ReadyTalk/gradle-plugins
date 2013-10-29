package com.readytalk.plugins.proto.features

class JavaProtoFeature extends ProtoFeatureBase {

  void configure() {
    super.configure()
    args << "--java_out=${getOutputDir()}"
  }


}
