package com.readytalk.plugins.proto.features

abstract class ProtoFeatureBase implements ProtoFeature {
  Set<String> args
  File outputDir

  ProtoFeatureBase() {
    args = new HashSet<String>()
  }

  void createDir(File dir) {
    if(dir) {
      dir.mkdirs()
    }
  }

  void configure() {
    createDir(outputDir)
  }

}
