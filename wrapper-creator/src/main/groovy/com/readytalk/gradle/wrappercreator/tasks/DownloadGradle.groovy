package com.readytalk.gradle.wrappercreator.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class DownloadGradle extends DefaultTask {
  @Input 
  String gradleVersion

  @Input 
  File destinationDir

  @Input 
  String gradleDownloadBase = "http://services.gradle.org/distributions"

  @TaskAction 
  doDownloadGradle() {
    destinationFile.bytes = new URL(getDownloadUrl()).bytes
  }

  String getDownloadUrl() {
    "${getGradleDownloadBase()}/${getDownloadFileName()}"
  }

  String getDownloadFileName() {
    "gradle-${getGradleVersion()}-bin.zip"
  }

  // Set up this way to be able to handle updates to the underlying properties
  @OutputFile
  File getDestinationFile() {
    new File(getDestinationDir(), getDownloadFileName())
  }
}
