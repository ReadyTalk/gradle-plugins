package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import org.gradle.api.GradleException

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile

class SignJar extends DefaultTask {

  @Input
  String baseName
  @Input
  String storePass
  @Input
  String keyPass
  @Input
  String alias
  @Input
  String keyStoreName
  @InputFile
  File keyStoreRepo
  @InputFile
  File unsignedJar
  @OutputFile
  File signedJar

  File keyStore

  SignJar() {
    project.apply(plugin: 'java-base')
  }

  protected void setupKeyStore() {
    keyStore = project.tarTree(keyStoreRepo).filter {
      it.name == keyStoreName
    }.singleFile
  }

  protected void setupOutputJar() {
    signedJar = project.file("${project.libsDir}/${baseName}.jar")
  }

  protected void setupKeyPass() {
    if(!keyPass) {
      keyPass = storePass
    }
  }

  protected void setup() {
    setupOutputJar()
    setupKeyPass()
    setupKeyStore()
    setupOutputJar()
  }

  @TaskAction
  def sign() {
    setup()
    project.ant.signjar(jar: unsignedJar,
                        alias: alias,
                        storepass: storePass,
                        keystore: keyStore,
                        keypass: keyPass,
                        signedjar: signedJar)
  }
}
