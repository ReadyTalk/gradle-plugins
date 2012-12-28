package com.sg.maketools

import groovy.util.logging.Commons

import java.util.regex.Matcher
import java.util.regex.Pattern

@Commons
class MakeVarContainer extends HashMap<String, MakeVar> {

  void parse(String variableList) {
    // grab [1 = variable name, 2 = variable value, 3 = variable definition]
    Pattern pattern = ~/.*:\s(\S+)\s*=\s*([^\(\)]+){0,1}[\s]{1}(?:\s*\((.*)\)){0,1}/
    Matcher matcher = variableList =~ pattern

    matcher.collect { match ->
      log.debug "${match[1]} -> [${match[2]}, ${match[3]}]"

      def variable = new MakeVar()
      variable.name = match[1]
      variable.value = match[2]
      variable.definition = match[3]

      put(variable.name, variable)
    }
  }

  // String getVariables() {
  //   Exec exec = new Exec()

  //   exec {
  //     def output = new ByteArrayOutputStream()

  //     exec {
  //       executable 'make'

  //       args "-f src/main/mk/test.mk", "-f ${file.path}", 'print'
  //       standardOutput = output
  //     }

  //     it = output.toString()
  //   }.execute()
  // }

}