package tasks

import org.gradle.api.tasks.bundling.Zip
import org.gradle.api.tasks.*
import org.gradle.api.file.FileCollection
import org.gradle.api.InvalidUserDataException

class PackageWrapper extends Zip {

  @Input @Optional
  String gradleOpts

  @InputDirectory
  File initScriptsDirectory

  @InputFiles
  FileCollection stockWrapper

  @Input
  String gradleVersion

  PackageWrapper() {
    description = 'Add extra files to ReadyTalk Gradle distribution'
    classifier = 'bin'

    into('') {
      from {
        stockWrapper
      }
    }

    into("gradle-${getGradleVersion()}/init.d") {
      from {
        initScriptsDirectory
      }
    }

  }

  void setGradleOpts(String options) {
    gradleOpts = options
    if(options) {

      into('') {

        from(stockWrapper) {
          include  '**/bin/gradle'
          filter { line ->
            if (line =~ "DEFAULT_JVM_OPTS=") {
              return "GRADLE_OPTS=\"${options} \$GRADLE_OPTS\"\nDEFAULT_JVM_OPTS="
            } else {
              return line
            }
          }
        }

        from(stockWrapper) {
          include  '**/bin/gradle.bat'
          filter { line ->
            if (line =~ "set DEFAULT_JVM_OPTS=") {
              return "set GRADLE_OPTS=${options} %GRADLE_OPTS%\nset DEFAULT_JVM_OPTS="
            } else {
              return line
            }
          }
        }
      }
    }

  }

}