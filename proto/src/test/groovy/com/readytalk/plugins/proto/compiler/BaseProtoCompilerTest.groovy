package com.readytalk.plugins.proto.compiler

import com.readytalk.plugins.proto.compiler.DefaultProtoCompiler
import spock.lang.*

class BaseProtoCompilerTest extends Specification {

  @Shared
  File baseDir

  def setupSpec() {
    baseDir = new File('base')
  }

  def "can create base object"() {
    when:
    def compiler = new DefaultProtoCompiler(baseDir)
    def extraArg = "--extra_option='test'"
    compiler.args(extraArg)
    compiler.configure()

    then:
    compiler.executable == new File(compiler.binDir, 'protoc')
    compiler.libDir == new File(baseDir, 'lib')
    compiler.binDir == new File(baseDir, 'bin')
    compiler.baseDir == baseDir
    compiler.args.contains extraArg
  }

}
