package com.readytalk.plugins.proto.util

import org.gradle.api.Project


class ProtoTestUtil {
  static void createProtoFiles(Project project) {
    def files = [
        project.file('src/main/proto/test.proto'),
        project.file('src/main/proto/test2.pb'),
        project.file('src/main/proto/test3.proto')
    ]

    files[0].parentFile.mkdirs()
    files.each { it.createNewFile() }
  }
}
