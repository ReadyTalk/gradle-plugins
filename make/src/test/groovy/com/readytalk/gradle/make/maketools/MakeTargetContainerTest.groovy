package com.readytalk.gradle.make.maketools

import com.readytalk.gradle.make.maketools.MakeTargetContainer
import spock.lang.*

class MakeTargetContainerTest extends Specification {

  def "get targets"() {
    given:
    def string =  '''
                  .PHONY: clean
                  clean:
                          @echo "removing build"
                          rm -rf build

                  $(build)/compile-x86-asm.o: $(src)/continuations-x86.S

                  .PHONY: build
                  build:
                          @echo "BUILDING!"
                  '''
    def container = new MakeTargetContainer()
    container.parse(string)

    expect:
    container[key].name == key

    where:
    key << ['clean', 'build']

  }
}
