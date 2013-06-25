import org.gradle.api.Plugin
import org.gradle.api.Project
import tasks.*
import extensions.*


class WrapperCreatorPlugin implements Plugin<Project> {

  static final String DOWNLOAD_GRADLE_TASK_NAME = 'downloadGradle'
  static final String PACKAGE_WRAPPER_TASK_NAME = 'packageWrapper'
  static final String WRAPPER_CREATOR_EXTENSION = 'wrapperCreator'

  Project project

  void addWrapperCreatorExtension() {
    project.extensions.create(WRAPPER_CREATOR_EXTENSION, WrapperCreatorExtension)
  }

  void addDownloadGradleTask() {
    project.tasks.create(DOWNLOAD_GRADLE_TASK_NAME, DownloadGradle) {
      // Any time you set up a task variable that references an external option (in this case buildDir)
      // you want to conventionMap it so that way if it changes, you'll get updated on the change
      // See: http://forums.gradle.org/gradle/topics/custom_task_with_fields_assign_directly_or_via_conventionmapping
      conventionMapping.destinationDir = { project.file("${project.buildDir}/gradle-downloads") }
      println project.extensions.getByName(WRAPPER_CREATOR_EXTENSION).gradleVersion
      conventionMapping.gradleVersion = { project.extensions.getByName(WRAPPER_CREATOR_EXTENSION).gradleVersion }
    }
  }

  void addPackageWrapperTask() {
    project.tasks.create(PACKAGE_WRAPPER_TASK_NAME, PackageWrapper) {
      dependsOn project.tasks.getByName(DOWNLOAD_GRADLE_TASK_NAME)

      initScriptsDirectory = project.file('src/main/gradle')
      conventionMapping.stockWrapper = { project.zipTree(project.tasks.getByName(DOWNLOAD_GRADLE_TASK_NAME).destinationFile) }
      conventionMapping.baseName = { project.name }
    }
  }

  void apply(Project project) {
    this.project = project

    project.apply(plugin: 'base')

    addWrapperCreatorExtension()
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