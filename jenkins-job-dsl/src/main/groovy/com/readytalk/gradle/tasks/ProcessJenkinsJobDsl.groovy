package com.readytalk.gradle.tasks

import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec

class ProcessJenkinsJobDsl extends JavaExec {

  OutputStream output
  OutputStream errorOutput

  ProcessJenkinsJobDsl() {
    super()
    main = 'javaposse.jobdsl.Run'
    workingDir project.buildDir

    output = new ByteArrayOutputStream()
    errorOutput = new ByteArrayOutputStream()

    setErrorOutput(output)
    setStandardOutput(errorOutput)
  }

  void exec() {
    workingDir.mkdirs()
    super.exec()
  }

}