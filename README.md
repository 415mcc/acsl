# ACSL Assembly Language Interpreter
This is a Java implementation of an interpreter for the "Assembly Language" used in the American Computer Science League competitions. This project adheres to the specifications outlined in the category's description document. Where the document did not specify an aspect of the language, I used my discretion. Test programs can be found in `examples/`.
## Notes:
- The label, opcode, LOC, and comments for each line can be delimited by any type or amount of Unicode whitespace, except for a newline, of course.
- I would recommend using hard tabs as a delimiter because it will make the alignment of text straightforward.
- The label, whether empty or not, is required for every line. Lines that do not need a label should begin with whitespace before the opcode.
- The accumulator (ACC) begins with a value of 0. This was a decision on my part.
```
TEMP    DC      5       I am a comment!
        LOAD    TEMP    These are actually spaces b/c GitHub Markdown rendering.
        MULT    =5      Just pretend they're tabs.
```
## Build & Run:
### With JAR File:
```bash
# Build
git clone https://github.com/lachm/acsl.git && cd acsl
javac AALInterpreter.java
jar cvfm AALInterpreter.jar MANIFEST.MF *.class

# Run
java -jar AALInterpreter.jar [file] # will read from stdin in absence of filename
```
### Without JAR File:
```bash
# Build
git clone https://github.com/lachm/acsl.git && cd acsl
javac AALInterpreter.java

# Run
java AALInterpreter [file] # will read from stdin in absence of filename
```
