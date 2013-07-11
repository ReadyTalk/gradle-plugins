package com.readytalk.gradle.make.plugins

import org.gradle.api.Project
import org.gradle.api.Plugin

import com.readytalk.gradle.make.extensions.MakefileExtension

class MakefilePlugin implements Plugin<Project> {

  Project project

  void apply(Project project) {
    this.project = project

    addMakefileExtension()
  }

  void addMakefileExtension() {
    project.extensions.create("makefile", MakefileExtension, project)
  }

}
