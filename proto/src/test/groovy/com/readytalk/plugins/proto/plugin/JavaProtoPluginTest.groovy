package com.readytalk.plugins.proto.plugin

import com.readytalk.plugins.proto.compiler.ProtoCompiler
import com.readytalk.plugins.proto.features.JavaProtoFeature
import com.readytalk.plugins.proto.util.ProtoTestUtil
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.FileCollection
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.util.CollectionUtils
import spock.lang.*

class JavaProtoPluginTest extends Specification {

  @Shared
  Project project
  @Shared
  Task generateMainProtocol
  @Shared
  ProtoCompiler compiler

  def setupSpec() {
    project = ProjectBuilder.builder().build()
    project.apply(plugin: JavaProtoPlugin)
    generateMainProtocol = project.tasks.findByName('generateMainProtocol')
    compiler = generateMainProtocol.compiler

    ProtoTestUtil.createProtoFiles(project)
  }

  def "can retrieve proper proto sources"() {
    expect:
    generateMainProtocol.includes
    generateMainProtocol.getSource().getFiles().size() == 2
  }

  def "can apply java feature to protoc correctly"() {
    when:
    compiler.with {
      configure()
    }

    then:
    compiler.features.find { it.class == JavaProtoFeature }
    compiler.args.find { it =~ '--java_out' }
  }
}
