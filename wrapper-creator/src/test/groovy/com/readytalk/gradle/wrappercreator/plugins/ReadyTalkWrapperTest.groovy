package com.readytalk.gradle.wrappercreator.plugins

import com.readytalk.gradle.wrappercreator.extensions.WrapperCreatorExtension
import spock.lang.*

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Task

class WrapperCreatorPluginTest extends Specification {

  @Shared Project project

  def setupSpec() {
    project = ProjectBuilder.builder().build()
  }

  def "can apply plugin"() {
    when:
    project.apply(plugin: 'wrapper-creator')

    then:
    project.extensions.findByType(WrapperCreatorExtension)
    project.tasks.getByName('packageWrapper')
    project.tasks.getByName('downloadGradle')
  }

}
