package com.readytalk.plugins.proto.features

import com.readytalk.plugins.proto.compiler.DefaultProtoCompiler
import com.readytalk.plugins.proto.compiler.ProtoCompiler
import spock.lang.Specification


class ComboFeatureTest extends Specification {

  def "can chain compilers"() {
    setup:
    File idsIn = new File('src/main/proto')
    File idsOut = new File('build/proto')

    when:
    ProtoFeature java = new JavaProtoFeature (
      outputDir: new File('build/src/main/java')
    )
    ProtoFeature objc = new ObjcProtoFeature (
      outputDir: new File('build/src/main/objc')
    )
    ProtoFeature as3 = new As3ProtoFeature (
      outputDir: new File('build/src/main/as3')
    )

    File baseDir = new File('base')
    ProtoCompiler compiler = new DefaultProtoCompiler(baseDir)
    compiler.addFeature(java)
    compiler.addFeature(objc)
    compiler.addFeature(as3)
    compiler.configure()

    then:
    compiler.args.size() == 4 

  }
}
