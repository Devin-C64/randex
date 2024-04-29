package edu.udel.cisc675.randex;
import java.util.random.RandomGenerator;

/* Module RandomizeProblems.  Given the number of problems nprob, and
   a random number generator, constructs an array of length nprob
   which is a permutation of the integers 0..nprob-1.  Uses the
   Fisher-Yates algorithms.  See
   https://en.wikipedia.org/wiki/Fisher–Yates_shuffle. */
public class RandomizeProblems {

    /* The number of problems (in) */
    int nprob;

    /* The random number generator (in) */
    RandomGenerator rand;

    /* Permutation of problem IDs (out) */
    int[] probPerm;

    /* Constructs new instance from given fields.  Sets fields only,
       does nothing else. */
    public RandomizeProblems(int nprob, RandomGenerator rand) {
	this.nprob = nprob;
	this.rand = rand;
    }

    /* Constructs the probPerm. */
    public void execute() {
	this.probPerm = new int[nprob];
	for (int i=0; i<nprob; i++)
	    probPerm[i] = i;

	Randomizer randomizer = new Randomizer();
    randomizer.randomize(probPerm);
	//System.out.println();
    }
}
