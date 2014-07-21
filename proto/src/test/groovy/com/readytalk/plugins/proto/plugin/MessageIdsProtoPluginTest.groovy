package com.readytalk.plugins.proto.plugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.*

class MessageIdsProtoPluginTest extends Specification {

  Project project

  def setup() {
    project = ProjectBuilder.builder().build()
    project.apply(plugin: JavaProtoPlugin)
    project.apply(plugin: As3ProtoPlugin)
    project.apply(plugin: MessageIdsProtoPlugin)
  }

  def "should have messageIds in and out specified"() {
    when:
    def task = project.generateMainProtocol
    def compiler = task.compiler
    task.compiler.configure()

    then:
    compiler.args.find { it =~ '--ids_out' }
  }

  def "should be able to specify proto message file outputs"() {
    setup:
    File newPropertiesFile = new File('seth-ids.properties')
    File newAsFile = new File('seth-ids.as')

    when:
    project.generateMainProtocol {
      generatedJavaMessageIds = newPropertiesFile
      generatedAs3MessageIds = newAsFile
    }

    then:
    project.generateMainProtocol {
      assert generatedJavaMessageIds == newPropertiesFile
      assert generatedAs3MessageIds == newAsFile
    }
  }
}
