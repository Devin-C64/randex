package edu.udel.cisc675.randex;
import java.util.random.RandomGenerator;

/* Module RandomizeAnswers: for each problem i, constructs a random
   permutation of 0..m-1, where m is the number of answers to problem
   i.  Uses Fisher-Yates algorithm; see See
   https://en.wikipedia.org/wiki/Fisher–Yates_shuffle. */
public class RandomizeAnswers{

    /* For each i in 0..numProblems - 1, the number of answers to
     * problem i (in). */
    int[] numAnswers;

    /* Random number generator (in) */
    RandomGenerator rand;

    /* A permutation for each problem (out) */
    int[][] answerPerms;

    /* Constructs new instance from given fields; sets the fields and
       does nothing else. */
    public RandomizeAnswers(int[] numAnswers,  RandomGenerator rand) {
	this.numAnswers = numAnswers;
	this.rand = rand;
    }

    /* Constructs random permutation for problem pid, writing to
       answerPerms[pid][*]. */
    private void randomizeProblem(int pid) {
	int nanswer = numAnswers[pid];
	Randomizer randomizer = new Randomizer(rand);
	for (int i=0; i<nanswer; i++)
	    answerPerms[pid][i] = i;
		randomizer.randomize(answerPerms[pid]);

    }

    /* Constructs random permutations for each problem */
    public void execute() {
	int nprob = numAnswers.length;
	answerPerms = new int[nprob][];
	for (int i=0; i<nprob; i++) {
	    answerPerms[i] = new int[numAnswers[i]];
	    randomizeProblem(i);
	}
    }
}
