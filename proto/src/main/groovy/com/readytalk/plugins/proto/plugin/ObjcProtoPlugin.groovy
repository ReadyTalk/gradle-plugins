package com.readytalk.plugins.proto.plugin

import com.readytalk.plugins.proto.extensions.ProtoExtension
import com.readytalk.plugins.proto.features.ObjcProtoFeature
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Compression
import org.gradle.api.tasks.bundling.Tar

class ObjcProtoPlugin extends BaseProtoPlugin {
  static final String PACKAGE_OBJC_TASK_NAME = 'packageObjc'

  void apply(Project project) {
    super.apply(project)

    configureProtoTask()
    addPackageObjc()
    configureAssembleTask()
  }

  void configureProtoTask() {
    project.tasks.getByName(ProtoExtension.COMPILE_TASK_NAME) {
      compiler.addFeature (
        new ObjcProtoFeature (
          outputDir: new File(extension.protoGenSrcDir, 'm')
        )
      )
    }
  }

  void addPackageObjc() {
    project.tasks.create(name: PACKAGE_OBJC_TASK_NAME, type: Tar) {
      dependsOn ProtoExtension.COMPILE_TASK_NAME

      compression = Compression.GZIP
      from "${project.buildDir}/src/main/m"

      from(project.buildDir) {
        include "MessageTypeTranslation.*"
      }
    }
  }

  void configureAssembleTask() {
    super.configureAssembleTask()
    project.tasks.getByName('assemble') {
      dependsOn PACKAGE_OBJC_TASK_NAME
    }
  }
}
