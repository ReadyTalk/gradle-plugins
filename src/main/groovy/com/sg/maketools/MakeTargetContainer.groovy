package com.sg.maketools

import groovy.util.logging.*

import java.util.regex.Matcher
import java.util.regex.Pattern

@Commons
class MakeTargetContainer extends HashMap<String, MakeTarget> {

  void parse(String targetList) {
    Pattern pattern = ~/\.PHONY:\s*([\S]+)\s*/
    Matcher matcher = targetList =~ pattern

    matcher.collect { match ->
      log.debug "PHONY target found: ${match}"

      MakeTarget target = new MakeTarget()
      target.name = match[1]

      put(target.name, target)
    }
  }

}