package com.readytalk.gradle.tasks

import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec

import java.io.ByteArrayOutputStream

class ProcessJenkinsJobDsl extends JavaExec {

  ProcessJenkinsJobDsl() {
    super()
    main = 'javaposse.jobdsl.Run'
    workingDir project.buildDir

    setErrorOutput(new ByteArrayOutputStream())
    setStandardOutput(new ByteArrayOutputStream())
  }

  void exec() {
    workingDir.mkdirs()
    super.exec()
  }

}