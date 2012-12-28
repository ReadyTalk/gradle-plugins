package com.sg.maketools

import java.util.regex.Matcher
import java.util.regex.Pattern
import groovy.util.logging.Commons
import org.gradle.api.tasks.Exec

@Commons
class Makefile {
  File file
  Map<String, List> variables

  Makefile() {

  }

  Makefile(File file) {
    this.file = file
    variables = new HashMap<String, List>()

    parse()
  }

  String getVariables() {
    Exec exec = new Exec()

    exec {
      def output = new ByteArrayOutputStream()

      exec {
        executable 'make'

        args "-f src/main/mk/test.mk", "-f ${file.path}", 'print'
        standardOutput = output
      }

      it = output.toString()
    }.execute()
  }


  Map<String, List> parseVariables(String variableList) {
    Map<String, List> map = new HashMap<String, List>()

    // grab [1 = variable name, 2 = variable value, 3 = variable definition]
    Pattern pattern = ~/.*:\s(\S+)\s*=\s*([^\(\)]+){0,1}[\s]{1}(?:\s*\((.*)\)){0,1}/
    Matcher matcher = variableList =~ pattern

    matcher.collect { match ->
      log.debug "${match[1]} -> [${match[2]}, ${match[3]}]"

      map.put(match[1], [match[2], match[3]])
    }

    return map
  }

  Map<String, String> parseTargets(File file) {
    return new HashMap<String, String>()
  }

  void parse() {
    variables = parseVariables(file.text)
  }

  String getDiff(Makefile file) {
    "I'm different!"
  }
}