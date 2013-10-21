package com.readytalk.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete

import java.io.ByteArrayOutputStream

import com.readytalk.gradle.tasks.ProcessJenkinsJobDsl

class JenkinsJobDslPlugin implements Plugin<Project> {

  File buildDir

  void apply(Project project) {
    buildDir = project.file("${project.projectDir}/build")

    project.plugins.apply('base')

    project.configurations {
      compile
    }

    project.tasks.create(name: 'processDsl', type: ProcessJenkinsJobDsl) {
      project.files("${project.projectDir}/src/main/jenkins").each {
        args it
      }

      classpath project.configurations.compile
      setErrorOutput(new ByteArrayOutputStream())
      setStandardOutput(new ByteArrayOutputStream())

      inputs.files classpath
      outputs.dir buildDir
    }

    project.tasks.assemble {
      dependsOn 'processDsl'
    }

    project.tasks.create('build') {
      dependsOn 'assemble'
    }
  }

}