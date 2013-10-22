package com.readytalk.gradle.make.extensions

import org.gradle.api.Project
import org.gradle.api.tasks.Exec

import com.readytalk.gradle.make.maketools.Makefile

class MakefileExtension {
  
  Project project
  Makefile makefile

  MakefileExtension(Project project) {
    this.project = project
  }

  void importBuild(String makefileLocation) {
    makefile = new Makefile(new File(makefileLocation))

    makefile.targets.collect { target ->
      String name = target.value.name

      project.tasks.create(name: name, type: Exec) {
        executable 'make'
        args "-f", makefile.file, name
        group 'make'
      }
    }
  }

}
