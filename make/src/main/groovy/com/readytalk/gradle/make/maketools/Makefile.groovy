package com.readytalk.gradle.make.maketools

import java.util.regex.Matcher
import java.util.regex.Pattern

import groovy.util.logging.*

@Commons
class Makefile {
  File file
  String lines
  MakeVarContainer variables = new MakeVarContainer()
  MakeTargetContainer targets = new MakeTargetContainer()

  private Makefile() {

  }

  Makefile(String contents) {
    lines = contents
    parse()
  }

  Makefile(File file) {
    this.file = file
    lines = file.text
    parse()
  }

  void parse() {
    variables.parse(lines)
    targets.parse(lines)
  }
}
