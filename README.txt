
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

----------------------
Critique of Design v1:
----------------------

To start, one thing that is very apparent to me is the methods are simply
too big and incorporate too many methods. According to "Design Software for
Ease of Extension and Contraction" by David L. Parnas on page 3, one of the most common
design errors that programers run into is creating components that perform more
than one function. He goes onto say that separation of these large components into
smaller ones would allow for components that are easier to use and more widely
applicable to different circumstances. Another reason it would help to break up 
some of these larger components is so that it would become quicker for a new programmer 
to figure out what they do, which is stressed greatly in "Design Patterns: Abstraction 
and Reuse of Object-Oriented Design" by Erich Gamma et. all on page 11. 

One of the things that is most apparent to me is the execute function
in Randex.java. It is doing way too much and thus should be separated into
its own component. 

Also, output never changes in randex after it is created so it should be
made immutable.

As for the classes that I saw could easily be broken up into smaller components,
the first two that caught my eye were FindAnswers and FindProblems. The main thing
that stood out to me was that they both utilized the same match function but written
differently in each class. By making the match function it's own class and being shared between 
the two components, the code would become more efficient and consistent (Gamma et. all).

Another such instance is in the components RandomizeAnswers and RandomizeProblems,
which both share a similar randomize function. By taking this out and making it it's own
component we could make the incrementation of new functions more intuitive and reduce the
redundancy of having the function more than once (Parnas 4).

One last oversight that must be addressed is the fact that the program is catered
exclusively towards latex files. By doing this, the components in this project do
not have a good degree of applicability, since there are many other forms of text file
that people could be using to store their exams as (Gamma et. all, 5). 

Generate seed using time.

----------
Design v2:
----------

1: PatternMatcher.java
This module is used by FindAnswers.java and FindProblems.java and its purpose 
is to tell whether the sequence in array chars matches those of c. The reason I
made this function its own component was that it was already present in FindAnswers
and FindProblems but written sligtly differently. By doing this, I feel that I have
streamlined it and made it much easier to understand, as while as cutting back on the
clutter in the two parent components. Its only export is the match function and its
only secret is the chars array.

2: Randomizer.java
This module is used by RandomizeAnswers.java and RandomizeProbelms.java.
Its main purpose is to reduce the amount of times that randomize happens since
before I changed it it happened in both java files. Also it cuts back on the amount
of calls to the rand object which saves on space and time. Its main export is the
randomize function.

3: DebugRandex.java
This moduel is intended to be used by Randex.java and its main purpose is to
print out a 1 or 2 dimensial array of characters as a way to debug the output as
Randex.java goes through its execute function. The main reason I did this was to
clean up the Randex componet as I felt that it was quite dense and figured the
debugging functionality could be moved elsewhere. By doing this, I believe it is
easier to understand how the debugging works and makes Randex more digestible.
There are no secrets for this component and its exports are both of the print
functions, one for 1D arrays and one for 2D arrays.
-----------------------
Anticipation of Change:
-----------------------

1: Making the program work with non-latex text files

In order to make the program function with text files that aren't of the latex
type, we would need to create 

2: 

3: