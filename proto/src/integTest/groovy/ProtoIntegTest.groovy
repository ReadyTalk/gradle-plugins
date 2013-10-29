import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.*

class ProtoPluginIntegTest extends Specification {

  @Shared Project project

  boolean fileExists(Object obj) {
    return project.file(obj).exists()
  }

  def setupSpec() {
    project = ProjectBuilder.builder().build()
    project.file('loader.gradle') << new File('../readytalk-gradlew/src/main/gradle/loader.gradle').text

    File sampleProto = project.file("${project.projectDir}/src/main/proto/sample.proto")
    sampleProto.parentFile.mkdirs()
    sampleProto.createNewFile()
    sampleProto << '''
message Msg {
  optional string foo = 1;
}
'''
    project.projectDir.deleteOnExit()
  }

  def "can compile proto files"() {
    setup:
    project.plugins.with {
      apply 'proto-java'
      apply 'proto-objc'
      apply 'proto-as3'
    }

    project.apply(from: 'loader.gradle')

    project.dependencies {
      proto (
          group: 'com.ecovate.protobuf',
          name: "protoc-linux-x86_64",
          version: '2.5.0.2-SNAPSHOT',
          configuration: 'compiler'
      )

      compile 'com.google.protobuf:protobuf-java:2.5.0'
    }

    when:
    project.tasks.setupProtoc.execute()
    project.tasks.generateMainProtocol.execute()

    then:
    fileExists("${project.buildDir}/tools/protoc/bin/protoc")

    File mainSourceSet = project.file("${project.buildDir}/src/main")
    fileExists("${mainSourceSet}/java/Sample.java")
    fileExists("${mainSourceSet}/as3/Msg.as")
    fileExists("${mainSourceSet}/m/Sample.pb.h")
    fileExists("${mainSourceSet}/m/Sample.pb.m")
  }

}
