package com.readytalk.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete

import java.io.ByteArrayOutputStream

import com.readytalk.gradle.tasks.ProcessJenkinsJobDsl

class JenkinsJobDslPlugin implements Plugin<Project> {

  Project project
  File jenkinsBuildDir

  void apply(Project project) {

    this.project = project

    jenkinsBuildDir = project.file("${project.projectDir}/build/jenkins")

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
      FileTree tree = project.fileTree("${project.projectDir}/src/main/jenkins") {
        include '**/*.groovy'
      }

      tree.getFiles().each {
        args it.getAbsolutePath()
      }

      classpath project.configurations.jenkinsCompile

      inputs.files classpath
      outputs.dir jenkinsBuildDir
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