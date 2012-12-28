import spock.lang.*

class MakefileTest extends Specification {

  def "makefile print variable output to be parsed correctly from a string"() {
    given:
    def makefile = new Makefile()
    def string = '../include/test.mk:3: converter-tool-sources=src/main/cpp/main.cpp ($(src)/main.cpp)'
    makefile.parse(string)

    expect:
    makefile.variables[key][0] == value
    makefile.variables[key][1] == vardef

    where:
    key                       | value                       | vardef
    'converter-tool-sources'  |  'src/main/cpp/main.cpp'    | '$(src)/main.cpp'
  }

  def "variable output to be parsed correctly from a file"() {
    given:
    def makefile = new Makefile()
    String file = '''../include/test.mk:3: converter-tool-sources=src/main/cpp/main.cpp ($(src)/main.cpp)
../include/test.mk:3: cpp-objects= ($(foreach x,$(1),$(patsubst $(2)/%.cpp,$(3)/%.o,$(x))))
../include/test.mk:3: cxx=g++ -m64 ($(build-cxx) $(mflag))
../include/test.mk:3: db=gdb --args (gdb --args)
../include/test.mk:3: dlltool=dlltool (dlltool)
../include/test.mk:3: jar="/bin/jar" ("$(JAVA_HOME)/bin/jar")
../include/test.mk:3: java-classes= ($(foreach x,$(1),$(patsubst $(2)/%.java,$(3)/%.class,$(x))))
../include/test.mk:3: javac="/bin/javac" ("$(JAVA_HOME)/bin/javac")
../include/test.mk:3: javah="/bin/javah" ("$(JAVA_HOME)/bin/javah")
../include/test.mk:3: mflag=-m64 (-m64)
../include/test.mk:3: mode=fast (fast)
../include/test.mk:3: name=avian (avian)
../include/test.mk:3: optimization-cflags=-O3 -g3 -DNDEBUG (-O3 -g3 -DNDEBUG)
../include/test.mk:3: platform=linux ($(bootimage-platform))
../include/test.mk:3: pointer-size=8 (8)
../include/test.mk:3: process=compile (compile)'''
    makefile.parse(file)

    expect:
    makefile.variables[key][0] == value
    makefile.variables[key][1] == vardef

    where:
    key                       | value                       | vardef
    'converter-tool-sources'  |  'src/main/cpp/main.cpp'    | '$(src)/main.cpp'
    'cpp-objects'             |  null                       | '$(foreach x,$(1),$(patsubst $(2)/%.cpp,$(3)/%.o,$(x)))'
    'cxx'                     |  'g++ -m64'                 | '$(build-cxx) $(mflag)'
    'db'                      |  'gdb --args'               | 'gdb --args'
    'dlltool'                 |  'dlltool'                  | 'dlltool'
    'jar'                     |  '"/bin/jar"'               | '"$(JAVA_HOME)/bin/jar"'
    'java-classes'            |  null                       | '$(foreach x,$(1),$(patsubst $(2)/%.java,$(3)/%.class,$(x)))'
    'javac'                   |  '"/bin/javac"'             | '"$(JAVA_HOME)/bin/javac"'
    'javah'                   |  '"/bin/javah"'             | '"$(JAVA_HOME)/bin/javah"'
    'mflag'                   |  '-m64'                     | '-m64'
    'mode'                    |  'fast'                     | 'fast'
    'name'                    |  'avian'                    | 'avian'
    'optimization-cflags'     |  '-O3 -g3 -DNDEBUG'         | '-O3 -g3 -DNDEBUG'
    'platform'                |  'linux'                    | '$(bootimage-platform)'
    'pointer-size'            |  '8'                        | '8'
    'process'                 |  'compile'                  | 'compile'
  }
}