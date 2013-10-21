package com.readytalk.gradle.tasks

import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec

class ProcessJenkinsJobDsl extends JavaExec {

  ProcessJenkinsJobDsl() {
    super()
    main = 'javaposse.jobdsl.Run'
    workingDir project.buildDir
  }

  void exec() {
    workingDir.mkdirs()
    super.exec()
  }

}