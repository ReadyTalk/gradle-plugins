ReadyTalk's Gradle Plugins
==========================

[![Build Status](https://drone.io/github.com/ReadyTalk/gradle-plugins/status.png)](https://drone.io/github.com/ReadyTalk/gradle-plugins/latest)

In all of the examples below, you need to connect to our public binary repo managed by JFrog:

    buildscript {
      repositories {
        maven {
          url = http://oss.jfrog.org/repo
        }
      }
    }

jenkins-job-dsl
---------------
This project leverages the [Jenkins Job DSL](https://github.com/jenkinsci/job-dsl-plugin) project to enable the generation of Jenkins Jobs via a Groovy DSL. It expands upon the User Power move described as ["Generate a Job config.xml without having to fire up Jenkins"](https://github.com/jenkinsci/job-dsl-plugin/wiki/User-Power-Moves)

### Getting Started

    buildscript {
      // Include repositories definition above

      dependencies {
        classpath 'com.readytalk.gradle-plugins:jenkins-job-dsl:<version>'
      }
    }

    apply plugin: 'jenkins-job-dsl'

This gives you:
- A Gradle project with the base plugin applied (gives you "assemble" and "clean")
- A "processDsl" task
-- converts all Jenkins Job Dsl Groovy files
-- classpath: use configuration "jenkinsCompile"
-- sourceSet: src/main/jenkins/**/*.groovy
-- buildDir: build/*.xml
- Correct lifecycle functionality: processDsl -> assemble -> check -> build

### Future Improvements
- Configure/Connect to a Jenkins server to update jobs via a Gradle command
- Merge some of this functionality into the jenkins-job-dsl repository (expand support in Jenkins plugin as well)
- Be able to specify the groovy DSL versions of your Jenkins Jobs in a Gradle build.gradle file

make
----
This plugin provides a way to easily wrap a make-based project so that you can run any make target from Gradle and leverage the premium dependency management and publication support of Gradle.

### Getting Started

    buildscript {
      // Include repositories definition above

      dependencies {
        classpath 'com.readytalk.gradle-plugins:make:<version>'
      }
    }

    apply plugin: 'make'

    make.importBuild 'makefile'

### Future Improvements
- This project needs some love, as it may have been my first non-trivial Gradle plugin attempt
- Be able to namespace the make target imports (so if you had clean, build... in the Makefile), you could choose to have them imported into the Gradle build as "make.clean, make.build..."

tasks
-----

A collection of miscellaneous tasks that we use inside of a few projects at ReadyTalk.

    buildscript {
      // Include repositories definition above

      dependencies {
        classpath 'com.readytalk.gradle-plugins:tasks:<version>'
      }
    }

    import com.readytalk.gradle.tasks.SignJar // Gradle-ified version of Ant's SignJar task

wrapper-creator
---------------

At ReadyTalk we produce our own internal Gradle Wrapper which eases the ability for developers to connect to our internal artifact repository and adhere to project, build, test, and quality conventions. We're also able to control build changes and Gradle improvements across the board much easier with our wrapper.

This project isn't our internal wrapper, but is the project that enables us to produce our internal wrapper better, inspired by [this blog post](http://mrhaki.blogspot.com/2012/10/gradle-goodness-distribute-custom.html)

### Getting Started

#### Basic Setup

    buildscript {
      // Include repositories definition above

      dependencies {
        classpath 'com.readytalk.gradle-plugins:wrapper-creator:<version>'
      }
    }

    apply plugin: 'wrapper-creator'

    wrapperCreator {
      // extension to configure some global things, such as:
      gradleVersion = "1.8"
    }

    // packageWrapper task by default injects any .gradle file in src/main/gradle into the Gradle zip's init.d directory

#### More Advanced Usage

    // Optional
    downloadGradle {
      // downloads Gradle with version = wrapperCreator.gradleVersion
    }

    packageWrapper {
      // task that does takes a stock version of Gradle and injects your init scripts (located at src/main/gradle) and places them in the Gradle zip in the correct location

      // Additional Functionality
      setJvmOpts(String)
    }
