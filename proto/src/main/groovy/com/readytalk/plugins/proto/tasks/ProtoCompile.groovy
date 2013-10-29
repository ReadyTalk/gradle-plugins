package com.readytalk.plugins.proto.tasks

import com.readytalk.plugins.proto.compiler.DefaultProtoCompiler
import com.readytalk.plugins.proto.compiler.ProtoCompiler
import com.readytalk.plugins.proto.extensions.ProtoExtension
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectories
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction
import org.gradle.util.CollectionUtils

class ProtoCompile extends SourceTask {

  @InputFiles
  Set<File> includeDirs

  ProtoCompiler compiler

  Set<File> outputDirs

  ProtoCompile() {
    compiler = new DefaultProtoCompiler(project.extensions.getByType(ProtoExtension).protocBaseDir)
    includeDirs = new HashSet<File>()
    includeDirs << "-I${project.projectDir}"
  }

  @OutputDirectories
  Set<File> getOutputDirs() {
    compiler.features.collect { it.getOutputDir() }
  }

  @TaskAction
  void compile() {
    compiler.configure()

    project.exec {
      workingDir = project.projectDir

      executable = compiler.executable
      args compiler.args
      args CollectionUtils.join("-I", includeDirs)

      args getSource().getFiles()

      environment (
        LD_LIBRARY_PATH: compiler.libDir,
        PATH: "${compiler.binDir}:${environment.PATH}"
      )
    }
  }

}
