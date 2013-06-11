import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

class DownloadGradle extends DefaultTask {
  @Input 
  String gradleVersion

  @Input 
  File destinationDir

  @Input 
  String gradleDownloadBase = "http://services.gradle.org/distributions"

  @TaskAction 
  doDownloadGradle() {
    destinationFile.bytes = new URL(downloadUrl).bytes
  }

  String getDownloadUrl() {
    "${gradleDownloadBase}/${getDownloadFileName()}"
  }

  String getDownloadFileName() {
    "gradle-${gradleVersion}-bin.zip"
  }

  // Set up this way to be able to handle updates to the underlying properties
  @OutputFile
  File getDestinationFile() {
    new File(getDestinationDir(), getDownloadFileName())
  }
}