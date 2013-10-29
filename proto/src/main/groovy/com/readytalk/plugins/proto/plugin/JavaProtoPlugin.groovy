package com.readytalk.plugins.proto.plugin

import com.readytalk.plugins.proto.extensions.ProtoExtension
import com.readytalk.plugins.proto.features.JavaProtoFeature
import org.gradle.api.Project


class JavaProtoPlugin extends BaseProtoPlugin {

  void apply(Project project) {
    super.apply(project)

    project.plugins.with {
      apply('java')
    }

    configureProtoTask()
    configureSourceSets()
    configureTaskDependencies()
  }

  void configureProtoTask() {
    project.tasks.getByName(ProtoExtension.COMPILE_TASK_NAME) {
      compiler.addFeature (
        new JavaProtoFeature (
          outputDir: new File(extension.protoGenSrcDir, 'java')
        )
      )
    }
  }

  void configureSourceSets() {
    project.sourceSets {
      main {
        java {
          srcDir "${project.buildDir}/src/main/java"
        }
      }
    }
  }

  void configureTaskDependencies() {
    project.tasks.getByName('compileJava') {
      dependsOn ProtoExtension.COMPILE_TASK_NAME
    }

    project.tasks.getByName('processResources') {
      dependsOn ProtoExtension.COMPILE_TASK_NAME
      from("${project.buildDir}/src/main/java") {
        include '**/*.properties'
        includeEmptyDirs = false
      }
    }
  }

}
