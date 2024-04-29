
		     Randex: The Exam Randomizer

Randex is used to randomly shuffle the problems and answers of a
multiple choice exam.  The given exam is in LaTeX format and
consists of the following sections, in order:

 1. possible text not including "\begin{problem}" or "\end{problem}"
 2. a sequence of problems
 3. possible text not including "\begin{problem}" or "\end{problem}"

Each problem has the following form:

 1. \begin{problem}
 2. possible text not including "\begin{enumerate}"
 3. \begin{enumerate}
 4. a sequence of answers
 5. \end{enumerate}
 6. possible text
 7. \end{problem}

Each answer has the following form:
 1. \item
 2. text not including "\item" or "\end{enumerate}"

See exam1.tex for a typical example.

Randex is a command line tool which takes 2 arguments: the name of the
file containing the exam in the format described above, and a long
integer which is the seed to Java's random number generator.  It reads
the file, randomly permutes the problems and answers to each problem,
and then writes the output to stdout in the same format as the
original file.

Design: Randex was developed using a modular design.  The modules are:

  1. Input
  2. FindProblems
  3. FindAnswers
  4. RandomizeProblems
  5. RandomizeAnswers
  6. Output
  7. Randex (main class)

Each module is a Java class.  See the comments in the source files to
see what each module does.

Potential changes/additions: here are some ways the app may have to
change in the future:

  0. Better/more robust error reporting.

  1. Ignore text in LaTeX comments (comments start with % and extend
     to end of line)

  2. An exam may be divided into sections.  Randomize within each
     section but do not move a problem from one section to another.

  3. Provide a way for the user to indicate which answer is correct.
     Make Randex keep track of the correct answers as it permutes.
     Produce an answer key at the end, in either plain text or
     Scantron format.

  4. Allow the user to produce n random exams instead of just one.

  5. Tell Randex to select a random subset of problems, rather than
     all problems.  The subset must meet some user-specified
     constraints.  For example, the user might associate points to
     each problem and the constraint may be to select a set of
     problems for which the total number of points is as specified.

  6. Allow some "free form" (short essay) problems in addition to
     multiple choice.
 
To build: change in to directory src/edu/udel/cisc675/randex and type
"make".  Type "make test1" to run a test.  See the Makefile for
further details.

Critique of Design v1:

To start, the thing that is most apparent to me is the execute functio
in Randex.java. It is doing way too much and thus should be separated into
its own component. 

Also, output never changes in randex after it is created so it should be
made immutable.

Furthermore, the classes FindAnswers AND FindProblems both use a match
function that while different in structure do the same thing.

Design v2:

1: PatternMatcher.Java
This module is used by FindAnswers.java and FindProblems.java and its
only secret is the list chars. Its purpose is to tells whether the sequence 
in array chars matches those of c. Its only export is the match function.

2: 