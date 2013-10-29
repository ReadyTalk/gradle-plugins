package com.readytalk.plugins.proto.features

interface ProtoFeature {
  void setOutputDir(File dir)
  File getOutputDir()
  Set<String> getArgs()
  void setArgs(Set<String> args)
  void configure()
}
