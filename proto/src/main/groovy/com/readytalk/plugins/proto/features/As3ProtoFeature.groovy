package com.readytalk.plugins.proto.features

class As3ProtoFeature extends ProtoFeatureBase {

  void configure() {
    args << "--as3_out=${getOutputDir()}"
  }

}
