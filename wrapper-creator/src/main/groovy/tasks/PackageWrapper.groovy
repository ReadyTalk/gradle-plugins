package tasks

import org.gradle.api.tasks.bundling.Zip
import org.gradle.api.tasks.*
import org.gradle.api.file.FileCollection

class PackageWrapper extends Zip {

  @Input @Optional
  String jvmOpts

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

    into {
      "gradle-${getGradleVersion()}/init.d"
    } {
      from {
        initScriptsDirectory
      }
    }

  }

  void setJvmOpts(String options) {
    jvmOpts = options
    if(options) {

      into('') {

        from(stockWrapper) {
          include  '**/bin/gradle'
          filter { line ->
            if (line =~ "DEFAULT_JVM_OPTS=") {
              return "DEFAULT_JVM_OPTS=\"${options}\""
            } else {
              return line
            }
          }
        }

        from(stockWrapper) {
          include  '**/bin/gradle.bat'
          filter { line ->
            if (line =~ "set DEFAULT_JVM_OPTS=") {
              return "set DEFAULT_JVM_OPTS=${options}"
            } else {
              return line
            }
          }
        }
      }
    }
  }
}