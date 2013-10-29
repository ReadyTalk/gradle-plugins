package com.readytalk.gradle.tasks

import spock.lang.*

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Task

class JarSignTest extends Specification {

  @Shared Project project
  @Shared File defaultKeyStoreRepo
  @Shared File defaultKeyStore
  @Shared File defaultUnsignedJar
  @Shared Task sign
  String defaultBaseName = 'base name'
  String defaultStorePassword = 'store password'
  String defaultKeyPass = 'key password'
  String defaultAlias = 'alias'
  String defaultKeyStoreName = 'keyStoreFile'

  def setupSpec() {
    project = ProjectBuilder.builder().build()
    project.apply(plugin: 'base')
    project.libsDir.mkdirs()

    defaultKeyStore = new File(project.buildDir, 'keyStoreFile')
    defaultKeyStore.createNewFile()

    defaultUnsignedJar = new File(project.libsDir, 'unsignedJar.jar')
    defaultUnsignedJar.createNewFile()

    defaultKeyStoreRepo = new File(project.buildDir, 'keyStoreRepo.tar')
    project.ant.tar(destfile: defaultKeyStoreRepo) {
      fileset(dir: project.buildDir) {
        include(name: "**/${defaultKeyStore.getName()}")
      }
    }
  }

  def setup() {
    sign = project.tasks.create(name: 'sign', type: SignJar)
  }

  def cleanup() {
    project.tasks.remove(sign)
  }

  def "applies at least java-base plugin"() {
    expect:
    project.plugins.hasPlugin('java-base')
  }

  def "respond to full set of parameters"() {
    when:
    sign.configure {
      baseName = defaultBaseName
      storePass = defaultStorePassword
      keyPass = defaultKeyPass
      alias = defaultAlias
      keyStoreName = defaultKeyStoreName
      keyStoreRepo = defaultKeyStoreRepo
      keyStore = defaultKeyStore
      unsignedJar = defaultUnsignedJar
    }

    then:
    sign.baseName == defaultBaseName
    sign.storePass == defaultStorePassword
    sign.keyPass == defaultKeyPass
    sign.alias == defaultAlias
    sign.keyStoreName == defaultKeyStoreName
    sign.keyStoreRepo == defaultKeyStoreRepo
    sign.keyStore == defaultKeyStore
    sign.unsignedJar == defaultUnsignedJar
  }

  def "keyPass is storePass if not set"() {
    when:
    sign.configure {
      storePass = defaultStorePassword
    }
    sign.setupKeyPass()

    then:
    sign.storePass == defaultStorePassword
    sign.keyPass == defaultStorePassword
  }

  def "can find a keystore in a tarball"() {
    when:
    sign.configure {
      baseName = defaultBaseName
      storePass = defaultStorePassword
      keyPass = defaultKeyPass
      alias = defaultAlias
      keyStoreName = defaultKeyStoreName
      keyStoreRepo = defaultKeyStoreRepo
      keyStore = defaultKeyStore
      unsignedJar = defaultUnsignedJar
    }
    sign.setup()

    then:
    defaultKeyStoreName == sign.keyStore.getName()
  }
}
