package com.readytalk.plugins.proto.plugin

import com.readytalk.plugins.proto.extensions.ProtoExtension
import com.readytalk.plugins.proto.tasks.ProtoCompile
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.bundling.Zip

class BaseProtoPlugin implements Plugin<Project> {

  Project project
  ProtoExtension extension

  void apply(Project project) {
    this.project = project

    if(!project.extensions.findByType(ProtoExtension)) {
      addConfiguration()
      addExtension()
      addSetupProtocTask()
      addSourceSets()
      addGenerateMainProtocolTask()
      addPackageProtoTask()
    } else {
      extension = project.extensions.getByType(ProtoExtension)
    }
  }

  void addConfiguration() {
    project.configurations {
      create(ProtoExtension.CONFIG_NAME)
    }
  }

  void addExtension() {
    extension = project.extensions.create(ProtoExtension.NAME, ProtoExtension)
    extension.with {
      protoGenSrcDir = new File(project.buildDir, "src/main/")
      protoSrcDirs = new File(project.projectDir, 'src/main/proto')
      toolsDir = new File(project.buildDir, "tools")
      protocBaseDir = new File(toolsDir, "protoc")
    }
  }

  void addGenerateMainProtocolTask() {
    project.tasks.create(name: ProtoExtension.COMPILE_TASK_NAME, type: ProtoCompile) {
      dependsOn ProtoExtension.SETUP_PROTOC_TASK_NAME

      source { extension.protoSrcDirs }
      include "**/*.proto"
    }
  }

  void addSetupProtocTask() {
    project.tasks.create(name: ProtoExtension.SETUP_PROTOC_TASK_NAME, type: Copy) {
      from {
        project.tarTree(project.configurations.getByName(ProtoExtension.CONFIG_NAME).find {
          it.name.startsWith 'protoc'
        })
      }
      into extension.toolsDir
    }
  }

  void addPackageProtoTask() {
    project.tasks.create(name: ProtoExtension.PACKAGE_PROTO_TASK_NAME, type: Zip) {
      from('.')
      exclude '**/build/**'
      include '**/*.proto'
      includeEmptyDirs = false
      classifier = 'proto'
    }
  }

  void configureAssembleTask() {
    project.tasks.getByName('assemble') {
      dependsOn ProtoExtension.PACKAGE_PROTO_TASK_NAME
    }
  }

  void addSourceSets() {
//    project.sourceSets.create(ProtoExtension.SOURCE_SET_NAME) {
//
//    }
  }
}
