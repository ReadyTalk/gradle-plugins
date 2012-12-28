package com.sg.gradle.plugins

import org.gradle.api.Project
import org.gradle.api.Plugin

import com.sg.gradle.tasks.CompareMakefileTask

class MakefilePlugin implements Plugin<Project> {

  Project project

  void apply(Project project) {
    this.project = project
  }

  void addMakefileComparisonTask() {
    project.tasks.add(name: 'compareMakes', type: CompareMakefileTask) {
      oldMakefile = project.file('makefile')
      newMakefile = project.file('newmakefile.mk')
    }
  }

}