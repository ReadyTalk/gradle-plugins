package com.readytalk.plugins.proto.plugin

import com.readytalk.plugins.proto.compiler.ProtoCompiler
import com.readytalk.plugins.proto.features.JavaProtoFeature
import com.readytalk.plugins.proto.util.ProtoTestUtil
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder

import spock.lang.*

class AllPluginTest extends Specification {

  def "can apply java feature to protoc correctly"() {
    when:
    Project project = ProjectBuilder.builder().build()
    project.plugins.with {
      apply(JavaProtoPlugin)
      apply(As3ProtoPlugin)
      apply(MessageIdsProtoPlugin)
      apply(ObjcProtoPlugin)
    }

    Task generateMainProtocol = project.tasks.findByName('generateMainProtocol')
    ProtoCompiler compiler = generateMainProtocol.compiler

    compiler.configure()

    then:
    compiler.args.find { it =~ '--java_out' }
    compiler.args.find { it =~ '--objc_out' }
    compiler.args.find { it =~ '--as3_out' }
    compiler.args.find { it =~ '--ids_out' }
  }
}
