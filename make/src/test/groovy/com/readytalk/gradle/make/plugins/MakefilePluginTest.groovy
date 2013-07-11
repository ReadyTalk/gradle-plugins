package com.readytalk.gradle.make.plugins

import spock.lang.*

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class MakefilePluginTest extends Specification {
  
  Project setupProject() {
    Project project = ProjectBuilder.builder().build()
    project.apply(plugin: 'makefile')
    return project
  }

  File setupMakefile(File projectDir) {
    File file = new File("$projectDir/makefile")

    file << '''
.PHONY: clean
clean:
	@echo \'CLEANED!\'
'''

    return file
  }

  def 'apply to project'() {
    given:
    Project project = setupProject()

    expect:
    project.plugins.hasPlugin(MakefilePlugin)      
  }

  def 'check import of makefile'() {
    given:
    Project project = setupProject()
    File generatedMakefile = setupMakefile(project.projectDir)
    project.makefile.importBuild(generatedMakefile.path)

    expect:
    project.tasks.'clean'
  }

  def 'run clean'() {
    given:
    Project project = setupProject()
    File generatedMakefile = setupMakefile(project.projectDir)

    project.makefile.importBuild(generatedMakefile.path)
    project.tasks.'clean'.execute()
  }
}
