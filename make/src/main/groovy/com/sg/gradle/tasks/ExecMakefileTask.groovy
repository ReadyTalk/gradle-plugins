package com.sg.gradle.tasks

import org.gradle.api.Project
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.Exec

import com.sg.maketools.Makefile

class ExecMakefileTask extends Exec {

  File makefile

  ExecMakefileTask() {
    super()
    executable('make')
  }

  @TaskAction
  void run() {
    assert makefile
    args "-f ${makefile.path}"
  }

}