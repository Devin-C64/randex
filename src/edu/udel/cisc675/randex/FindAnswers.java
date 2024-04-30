package edu.udel.cisc675.randex;
import java.util.ArrayList;

/* Module FindAnswers: searches through the character array from the file,
   and for each problem, finds the starting and ending points of each answer.
   The answer includes "\item" and ends just before the next "\item" or
   "\end{enumerate}". */
public class FindAnswers {

    // strings we will search for...
    
    public final static char[] beginEnumerate =
	"\\begin{enumerate}".toCharArray();
    
    public final static char[] item = "\\item".toCharArray();
    
    public final static char[] endEnumerate =
	"\\end{enumerate}".toCharArray();

    /* The chars array generated by Input module (in) */
    char[] chars;

    /* Start index of each problem, from module FindProblems (in) */
    int[] probStarts;

    /* Stop index of each problem, from module FindProblems (in) */
    int[] probStops;

    /* For each problem i, start index of each answer to the problem
       (out).  This is an array of length number of problems.
       answerStarts[i] is an array whose length is the number of
       answers to the i-th problem.  answerStarts[i][j] is the index
       in chars of the first character of the j-th answer to the i-th
       problem. */
    int[][] answerStarts;

    /* Like answerStarts, except it gives the stop index of each
       answer (out).  That is 1 greater than the index of the last
       character of the answer. */
    int[][] answerStops;
    
    /* Constructs new FindAnswers instance from the given data.  Sets
       the fields and does nothing else. */
    public FindAnswers(char[] chars, int[] probStarts, int[] probStops) {
	this.chars = chars;
	this.probStarts = probStarts;
	this.probStops = probStops;
    }

    /* Converts an array list of Integer to an array of int. */
    private static int[] toArray(ArrayList<Integer> list) {
	int n = list.size();
	int[] result = new int[n];
	for (int i=0; i<n; i++)
	    result[i] = list.get(i);
	return result;
    }

    /* Finds the answers to the problem with ID pid, setting
       answerStarts[pid] and answerStops[pid].  */
    private void findAnswersInProblem(int pid) {
	ArrayList<Integer> startList = new ArrayList<>(),
	    stopList = new ArrayList<>();
	int i = probStarts[pid]; // starting character index for problem pid
	int stop = probStops[pid];
	PatternMatcher patternMatcher = new PatternMatcher(chars);
	for (; i < stop && !patternMatcher.match(i, beginEnumerate); i++) ;
	if (i == stop)
	    throw new RuntimeException
		("No \\begin{enumerate} found for problem "+pid);
	for (; i < stop; i++) {
	    if (patternMatcher.match(i, endEnumerate)) {
		if (!startList.isEmpty()) stopList.add(i);
		break;
	    }
	    if (patternMatcher.match(i, item)) {
		if (!startList.isEmpty()) stopList.add(i);
		startList.add(i);
	    }
	}
	if (i == stop)
	    throw new RuntimeException
		("No \\end{enumerate} found for problem "+pid);
	int nanswer = startList.size();
	assert nanswer == stopList.size();
	answerStarts[pid] = toArray(startList);
	answerStops[pid] = toArray(stopList);
    }

    /* Constructs answerStarts and answerStops. */
    public void execute() {
	int nprob = probStarts.length;
	answerStarts = new int[nprob][];
	answerStops = new int[nprob][];
	for (int i=0; i<nprob; i++)
	    findAnswersInProblem(i);
    }
}
