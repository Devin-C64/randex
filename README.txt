==============
Devin Cummings
==============

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
Critique of Design v1
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

   As for the classes that I saw could easily be broken up into smaller components,
the first two that caught my eye were FindAnswers and FindProblems. The main thing
that stood out to me was that they both utilized the same match function but written
differently in each class. By making the match function it's own class and being shared between 
the two components, the code would become more efficient and cost effective from the lack of
redefining the same function multiple times (Gamma et. all 8).

   Another such instance is in the components RandomizeAnswers and RandomizeProblems,
which both share a similar randomize function. By taking this out and making it it's own
component we could make the incrementation of new functions more intuitive and reduce the
redundancy of having the function more than once (Parnas 4).

   In the same vein, it would be beneficial to break up some of the execute functions of the major
components, particularly in FindProblems.java and Randex.java since they are so massive and
a bit ungainly to read through and comprehend properly.

   One last major oversight that must be addressed is the fact that the program is catered
exclusively towards latex files. By doing this, the components in this project do
not have a good degree of applicability, since there are many other forms of text file
that people could be using to store their exams as (Gamma et. all 5). 

   To end, there are a couple smaller changes I made that didn't result in new modules. This 
isn't nearly as important but there were a few possible small human errors such as redefining the 
variable output in Randex.java's execute function which was unneeded. Additionally, I edited the 
seed variable in the main function of Randex.java such that it is based off of the current time 
in milliseconds so that the randomization is much more random and thus closer to true randomization.

----------
Design v2
----------

1: PatternMatcher.java
This module is used by FindAnswers.java and FindProblems.java and its purpose 
is to tell whether the sequence in array chars matches those of c. The reason I
made this function its own component was that it was already present in FindAnswers
and FindProblems but written sligtly differently. By doing this, I feel that I have
streamlined it and made it much easier to understand, as while as cutting back on the
clutter in the two parent components. Its only export is the match function and its
only secret is the array "chars".

2: FindAnswersInProblem.java
This module is used by FindAnswers.java and its purpose is that it contains the function
that finds the answers in the presented problem. The reason I gave this function its own
component was to increase the readability of FindAnswers. Its only export is the
findAnswersInProblem function and its secrets are char[] chars, int[] probStarts, int[] probStops,
int[][] answerStarts, and int[][] answerStops.

3: ToArray.java
This module is used by FindAnswersInProblem.java and it converts lists to arrays. The reason
I made this component was to reduce the clutter from the FindAnswersInProblem component since
it already has a lot of code within it. Also, by it being its own component, it can easily be used
by other components if necessary, thus making it very accessible. It has no secrets and its sole
export is the toArray function.

4: Randomizer.java
This module is used by RandomizeAnswers.java and RandomizeProbelms.java.
Its main purpose is to reduce the amount of times that randomize happens since
before I changed it it happened in both java files. Similar to with PatternMatcher.java,
this helps cut back on the clutter in the components it's used in as well as making
its own functinality much clearer. Its only export is the randomize function and its only
secret is the RandomGenerator "rand".

5: DebugRandex.java
This module is intended to be used by Randex.java and its main purpose is to
print out a 1 or 2 dimensial array of characters as a way to debug the output as
Randex.java goes through its execute function. The main reason I did this was to
clean up the Randex componet as I felt that it was quite dense and figured the
debugging functionality could be moved elsewhere. By doing this, I believe it is
easier to understand how the debugging works and makes Randex more digestible.
There are no secrets for this component and its exports are both of the print
functions, one for 1D arrays and one for 2D arrays.

-----------------------
Anticipation of Change
-----------------------

1: Making the program work with non-latex text files

For both versions of the code (v1 and v2), in order to make the program function with text 
files that aren't of the latex type, we would need to create new classes based on Input.java
that can handle different forms of documents. 

2: Combine RandomizeAnswers and RandomizeProblems / combine FindAnswers and combine FindProblems

There is a possbility that RandomizeAnswers and RandomizeProblems could be combined into one component 
because of how similar thier functionalities are, and the same thing can be said for FindAnswers and
FindProblems. For the original version of the code (v1), in order to combine these two components,
one would have to find all similar functionalities between both and make those parts into smaller functions
within the component or as another component. Additionally, all code in Randex.java that takes place between
the creation of each component would have to be included in the combined component. For my new version of the
code, the same thing can be said, although the process would be easier due to the fact that I have extracted
some of the functionality of these components and made them into their own classes.

3: Abstract constructor for all components used in Randex.java

For the original version of the code (v1),

For my new version of the code (v2)