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

proto
-----

More extensible Protobuf support with Gradle!

    buildscript {
      dependencies {
        classpath 'com.readytalk.gradle-plugins:proto:<version>'
      }
    }

    dependencies {
      proto (
        group: 'com.readytalk.protobuf',
        name: "protoc",
        version: '2.5.0.2-SNAPSHOT',
        configuration: 'compiler'
      )

      compile "com.google.protobuf:protobuf-java:2.5.0"
    }

    // apply plugin: 'proto-base'
    // apply plugin: 'proto-java'
    // apply plugin: 'proto-as3'

### `proto-base` Plugin

Adds the following items:

- `proto` configuration
- `proto` extension
- `setupProtoc` task
- `generateMainProtocol` `ProtoCompile` task which runs protoc: 
- `packageProtoc` `Zip` task which zips up your .proto files

### `proto-java` Plugin

This plugin plays well with the `java` plugin from Gradle and ties into the opinionated Java build lifecycle. For example, the standard compileMainJava is configured by the `proto-java` plugin to add the `build/src/main/java` sourceset and compiles it along with your sources in `src/main/java`.

### Assumptions
- a protobuf compiler is declared in the `proto` configuration
- the protobuf-java.jar has been added to the compile configuration so it available on your classpath during compilation
- `generateMainProtocol` task compiles `src/main/proto/**/*.proto` files into .class files in `build/src/main/java`.

### `proto-as3` Plugin

This plugin plays well with the `gradlefx` plugin from GradleFX and enables you to generate .swc files from .proto files


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
      gradleVersion = "2.0"
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
