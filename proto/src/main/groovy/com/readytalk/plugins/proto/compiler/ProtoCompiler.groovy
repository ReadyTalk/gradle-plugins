package com.readytalk.plugins.proto.compiler

import com.readytalk.plugins.proto.features.ProtoFeature

interface ProtoCompiler {
  File getBaseDir()
  void setBaseDir(File baseDir)
  File getLibDir()
  void setLibDir(File libDir)
  File getBinDir()
  void setBinDir(File binDir)
  File getIncDir()
  void setIncDir(File incDir)
  Set<String> getArgs()

  void args(String... args)

  void configure()
  void addFeature(ProtoFeature feature)
  Set<ProtoFeature> getFeatures()
}
