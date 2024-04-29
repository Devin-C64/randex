package edu.udel.cisc675.randex;
import java.util.random.RandomGenerator;

/* Module Randomizer: a generic class for randomizing arrays using the
   Fisher-Yates algorithm. */
public class Randomizer {

    /* Random number generator (in) */
    RandomGenerator rand;

    /* Constructs new instance from given random number generator. */
    public Randomizer(RandomGenerator rand) {
        this.rand = rand;
    }

    /* Randomizes the given array using the Fisher-Yates algorithm. */
    public void randomize(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            if (i != j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
    }
}
