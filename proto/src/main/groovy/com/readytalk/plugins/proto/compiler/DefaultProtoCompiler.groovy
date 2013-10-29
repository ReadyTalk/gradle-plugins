package com.readytalk.plugins.proto.compiler

import com.readytalk.plugins.proto.features.ProtoFeature

class DefaultProtoCompiler implements ProtoCompiler {
  File baseDir
  File libDir
  File binDir
  File incDir
  File executable
  Set<String> args
  Set<ProtoFeature> features

  DefaultProtoCompiler(File baseDir) {
    setBaseDir(baseDir)
    args = new HashSet<String>()
    features = new HashSet<ProtoFeature>()
  }

  void setBaseDir(File baseDir) {
    this.baseDir = baseDir

    libDir = new File(baseDir, 'lib')
    binDir = new File(baseDir, 'bin')
    incDir = new File(baseDir, 'include')
    executable = new File(binDir, 'protoc')
  }

  void args(String... newArgs) {
    args.addAll(newArgs)
  }

  void configure() {
    args << "-I${getIncDir()}"
    features.each { feature ->
      feature.configure()
      args.addAll(feature.args)
    }
  }

  void addFeature(ProtoFeature lang) {
    features.add(lang)
  }
}
