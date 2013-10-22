package com.readytalk.gradle.plugins

import spock.lang.*

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class JenkinsJobDslPluginTest extends Specification {

  @Shared Project project

  def setupSpec() {
    project = ProjectBuilder.builder().build()
    project.with {
      file('src/main/jenkins').mkdirs()
      ext.sampleJob = file('src/main/jenkins/unit.groovy')
      ext.sampleJob << 'job { name = "unit" }'
      apply(plugin: 'jenkins-job-dsl')
    }
  }

  def "has the base plugin applied"() { 
    expect:
    project.plugins.hasPlugin('base')
  }

  def "has a correct check task"() { 
    expect:
    project.tasks.getByName('check')
  }

  def "has a correct build task"() { 
    expect:
    project.tasks.getByName('build')
  }

  def "has a processDsl task"() {
    expect:
    project.tasks.getByName('processDsl')
  }

  def "has jenkins configurations"() {
    expect:
    project.configurations.jenkinsCompile
  }

  def "collects over job scripts"() {
    expect:
    project.tasks.getByName('processDsl').args.contains(project.ext.sampleJob.absolutePath)
  }
}
