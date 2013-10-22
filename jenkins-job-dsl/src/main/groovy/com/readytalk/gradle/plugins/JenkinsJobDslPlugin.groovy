package com.readytalk.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete

import java.io.ByteArrayOutputStream

import com.readytalk.gradle.tasks.ProcessJenkinsJobDsl

class JenkinsJobDslPlugin implements Plugin<Project> {

  Project project

  void apply(Project project) {

    this.project = project

    project.plugins.apply('base')

    addJenkinsConfiguration()

    addProcessDslTask()

    configureAssembleTask()
    configureCheckTask()
    configureBuildTask()
  }

  void addJenkinsConfiguration() {
    project.configurations {
      jenkinsCompile
    }
  }

  void addProcessDslTask() {
    project.tasks.create(name: 'processDsl', type: ProcessJenkinsJobDsl) {
      project.files("${project.projectDir}/src/main/jenkins").each {
        args it
      }

      classpath project.configurations.jenkinsCompile
      setErrorOutput(new ByteArrayOutputStream())
      setStandardOutput(new ByteArrayOutputStream())

      inputs.files classpath
      outputs.dir project.buildDir
    }
  }

  void configureAssembleTask() {
    project.tasks.assemble {
      dependsOn 'processDsl'
    }
  }

  void configureCheckTask() {
    if(!project.tasks.findByName('check')) {
      project.tasks.create('check')
    }

    project.tasks.check {
      dependsOn 'assemble'
    }
  }

  void configureBuildTask() {
    if(!project.tasks.findByName('build')) {
      project.tasks.create('build')
    }

    project.tasks.build {
      dependsOn 'check'
    }
  }

}