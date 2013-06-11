import org.gradle.api.Plugin
import org.gradle.api.Project
import tasks.*
import extensions.*


class WrapperCreatorPlugin implements Plugin<Project> {

  String DOWNLOAD_GRADLE_TASK_NAME = 'downloadGradle'
  String PACKAGE_WRAPPER_TASK_NAME = 'packageWrapper'

  Project project

  void addDownloadGradleTask() {
    project.tasks.create(DOWNLOAD_GRADLE_TASK_NAME, DownloadGradle) {
      // Any time you set up a task variable that references an external option (in this case buildDir)
      // you want to conventionMap it so that way if it changes, you'll get updated on the change
      // See: http://forums.gradle.org/gradle/topics/custom_task_with_fields_assign_directly_or_via_conventionmapping
      conventionMapping.destinationDir = { project.file("${project.buildDir}/gradle-downloads") }
    }
  }

  void addPackageWrapperTask() {
    project.tasks.create(PACKAGE_WRAPPER_TASK_NAME, PackageWrapper) {
      dependsOn project.downloadGradle

      initScriptsDirectory = project.file('src/main/gradle')
      conventionMapping.stockWrapper = { project.zipTree(project.downloadGradle.destinationFile) }
    }
  }

  void apply(Project project) {
    this.project = project

    project.apply(plugin: 'base')

    addDownloadGradleTask()
    addPackageWrapperTask()

    project.assemble {
      dependsOn project.packageWrapper
    }

    project.artifacts {
      archives project.packageWrapper
    }
  }

}