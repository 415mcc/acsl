# ACSL Assembly Language Interpreter
This is a Java implementation of an interpreter for the "Assembly Language" used in the American Computer Science League competitions. This project adheres to the specifications outlined in the category's description document. Where the document did not specify an aspect of the language, I used my discretion. Test programs can be found in `examples/`.
### Notes:
- The label, opcode, LOC, and comments for each line can be delimited by any type or amount of Unicode whitespace, except for a newline, of course.
- The label, whether empty or not, is required for every line. Lines that do not need a label should begin with whitespace before the opcode.
- The accumulator (ACC) begins with a value of 0. This was a decision on my part.
```
TEMP	DC	5	I am a comment!
	LOAD	TEMP	I am another comment!
```
