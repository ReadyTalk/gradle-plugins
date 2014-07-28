package com.readytalk.plugins.proto.plugin

import com.readytalk.plugins.proto.tasks.ProtoCompile
import com.readytalk.plugins.proto.extensions.ProtoExtension
import com.readytalk.plugins.proto.features.MessageIdsProtoFeature
import org.gradle.api.Plugin
import org.gradle.api.Project


class MessageIdsProtoPlugin extends BaseProtoPlugin {

  void apply(Project project) {
    super.apply(project)

    configureExtension()
    configureProtoTask()
  }

  void configureExtension() {
    File compilerBinDir = project.tasks.getByName(ProtoExtension.COMPILE_TASK_NAME).compiler.getBinDir()
    extension.metaClass.messageIdPlugin = new File(compilerBinDir, "messageIdPlugin")
    extension.metaClass.messageInputFile = new File(project.projectDir, 'message-ids.txt')
    extension.metaClass.messageOutputDir = project.buildDir
  }

  void configureProtoTask() {
    project.tasks.getByName(ProtoExtension.COMPILE_TASK_NAME) {
      compiler.addFeature(
        new MessageIdsProtoFeature (
          plugin: extension.messageIdPlugin,
          idsIn: extension.messageInputFile,
          idsOut: extension.messageOutputDir,
          outputDir: extension.messageOutputDir
        )
      )

      if(project.plugins.hasPlugin(As3ProtoPlugin)) {
        ProtoCompile.metaClass.generatedAs3MessageIds = null
        doLast {
          if(generatedAs3MessageIds) {
            File messageIdsOut = project.file(generatedAs3MessageIds)
            project.copy {
              from extension.messageOutputDir
              into messageIdsOut.parentFile
              include 'message-ids.as'
              rename('message-ids.as', messageIdsOut.name)
            }
          }
        }
      }

      if(project.plugins.hasPlugin(JavaProtoPlugin)) {
        ProtoCompile.metaClass.generatedJavaMessageIds = null
        doLast {
          if(generatedJavaMessageIds) {
            File messageIdsOut = project.file(generatedJavaMessageIds)
            project.copy {
              from extension.messageOutputDir
              into messageIdsOut.parentFile
              include 'message-ids.properties'
              rename('message-ids.properties', messageIdsOut.name)
            }
          }
        }
      }
    }
  }

}
