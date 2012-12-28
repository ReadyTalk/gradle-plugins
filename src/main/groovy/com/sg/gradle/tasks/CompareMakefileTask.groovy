package com.sg.gradle.tasks

import org.gradle.api.Project
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory

import com.sg.maketools.Makefile

class CompareMakefileTask extends DefaultTask {

  Makefile oldMakefile
  Makefile newMakefile

  @TaskAction
  void run() {
    assert oldMakefile
    assert newMakefile

    log.info oldMakefile.getDiff(newMakefile)
  }

  void oldMakefile(File file) {
    oldMakefile = new Makefile(file)
  }

  void newMakefile(File file) {
    newMakefile = new Makefile(file)
  }

}