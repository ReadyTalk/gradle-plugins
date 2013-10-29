package com.readytalk.plugins.proto.plugin

import com.readytalk.plugins.proto.extensions.ProtoExtension
import com.readytalk.plugins.proto.features.As3ProtoFeature
import org.gradle.api.Plugin
import org.gradle.api.Project

class As3ProtoPlugin extends BaseProtoPlugin {

  void apply(Project project) {
    super.apply(project)

    configureProtoTask()
  }

  void configureProtoTask() {
    project.tasks.getByName(ProtoExtension.COMPILE_TASK_NAME) {
      compiler.addFeature (
        new As3ProtoFeature (
          outputDir: new File(extension.protoGenSrcDir, 'as3')
        )
      )
    }
  }
}
