package com.readytalk.gradle.wrappercreator.tasks

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Shared
import spock.lang.Specification

class PackageWrapperTest extends Specification {

  @Shared Project project
  @Shared File gradleDir
  @Shared File gradleVersionDir
  @Shared File gradleLicense
  @Shared File gradleBinDir
  @Shared File gradlewBat
  @Shared File gradleInitDir
  @Shared File initScript

  def file(Object obj) {
    def file = project.file(obj)
    dir(file.parentFile)
    file.createNewFile()
    return file
  }

  def dir(Object obj) {
    def dir = project.file(obj)
    dir.mkdirs()
    return dir
  }

  def setupSpec() {
    project = ProjectBuilder.builder().build()
    gradleDir = dir("${project.buildDir}/gradle")
    gradleVersionDir = dir("${gradleDir}/gradle-version")
    gradleLicense = file("${gradleVersionDir}/LICENSE")
    gradleBinDir = dir("${gradleVersionDir}/bin")
    gradlewBat = dir("${gradleBinDir}/gradlew.bat")
    gradleInitDir = dir("${gradleVersionDir}/init.d")
    initScript = file("${project.projectDir}/src/main/gradle/init.gradle")
  }

  def "injects files into wrapper in correct locations"() {
    setup:
    project.tasks.create(name: 'packageWrapper', type: PackageWrapper) {
      baseName = 'temp-gradle-wrapper'
      destinationDir = project.buildDir
      stockWrapper = project.files(gradleDir)
      gradleVersion = 'version'
      initScriptsDirectory = initScript.parentFile
    }

    when:
    project.tasks.packageWrapper.execute()

    then:
    def gradleWrapper = project.zipTree(project.tasks.packageWrapper.outputs.files.singleFile)

    gradleWrapper.matching {
      include '**/*.gradle'
    }.files.size() == 1

  }

}
