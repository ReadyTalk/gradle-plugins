package com.sg.gradle

import com.sg.gradle.plugins.MakefilePlugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

import spock.lang.*

class MakefilePluginTest extends Specification {
  
  def 'apply to project'() {
    given:
    Project project = ProjectBuilder.builder().build()
    project.apply(plugin: 'makefile')
    def plugins = project.getPlugins() 

    expect:
    plugins.hasPlugin(MakefilePlugin)      
  }
}