package edu.udel.cisc675.randex;
import java.util.ArrayList;

/* Module FindProblems: finds all problems (text beginning with
   "\begin{problem}" and ending with "\end{problem}") in the given
   character array.  Constructs arrays probStarts and probStops.  Each
   has length n, the number of problems.  probStarts[i] is the index
   in chars of the first character of problem i.  probStops[i] is 1
   more than the index in chars of the last character of problem i.

   The client should instantiate this class with the chars produced
   by module Input, then call method execute, then read the fields
   probStarts and probStops. */
public class FindProblems {

    /* "\begin{problem}" as character array. */
    public final static char[] beginProblem =
	"\\begin{problem}".toCharArray();

    /* "\end{problem}" as character array. */
    public final static char[] endProblem =
	"\\end{problem}".toCharArray();

    /* The characters of the original file, from module Input (in). */
    char[] chars;

    /* The start index of each problem (out). */
    int[] probStarts;

    /* The stop index of each problem (out). */
    int[] probStops;

    /* Constructor: sets chars field and does nothing else. */
    public FindProblems(char[] chars) {
	this.chars = chars;
    }


    /* Constructs probStarts and probStops. */
    public void execute() {
	ArrayList<Integer> startList = new ArrayList<>(),
	    stopList = new ArrayList<>();
	int n = chars.length;
	boolean inProblem = false; // is i currently inside a problem?
	PatternMatcher patternMatcher = new PatternMatcher(chars);
	for (int i=0; i<n; i++) {
	    if (patternMatcher.match(i, beginProblem)) {
		if (inProblem)
		    throw new RuntimeException
			("Encountered \\begin{problem} when inside a problem");
		startList.add(i);
		inProblem = true;
	    } else if (patternMatcher.match(i, endProblem)) {
		if (!inProblem)
		    throw new RuntimeException
			("Encountered \\end{problem} when outside any problem");
		inProblem = false;
		stopList.add(i + endProblem.length);
	    }
	}
	if (inProblem)
	    throw new RuntimeException
		("Missing \\endProblem for last problem");
	int nprob = startList.size();
	assert nprob == stopList.size();
	probStarts = new int[nprob];
	probStops = new int[nprob];
	for (int i=0; i<nprob; i++)
	    probStarts[i] = startList.get(i);
	for (int i=0; i<nprob; i++)
	    probStops[i] = stopList.get(i);
    }
}
