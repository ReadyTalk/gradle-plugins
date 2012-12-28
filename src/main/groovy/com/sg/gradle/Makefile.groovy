import java.util.regex.Matcher
import java.util.regex.Pattern

class Makefile {
  Map<String, List> variables = new HashMap<String, List>()

  Matcher parseVariables(String output) {
    // grab [0 = variable name, 1 = variable value, 2 = variable definition]
    Pattern pattern = ~/.*:\s(\S+)\s*=\s*([^\(\)]+){0,1}[\s]{1}(?:\s*\((.*)\)){0,1}/

    Matcher matcher = output =~ pattern

    return matcher
  }

  void parse(String string) {
    Matcher m = parseVariables(string)

    m.collect { match ->
      //println "${match[1]} -> [${match[2]}, ${match[3]}]"

      variables.put(match[1], [match[2], match[3]])
    }
  }
}