package edu.udel.cisc675.randex;
import java.io.PrintStream;

public class RandexDebug {
        /* Prints a 1d-array of ints (for debugging) */
    private static void print(PrintStream out, int[] a) {
	out.print("{ ");
	for (int x:a) out.print(x+" ");
	out.print("}");
    }

    /* Prints a 2d-array of ints (for debugging) */
    private static void print(PrintStream out, int[][] a2d) {
	out.print("{ ");
	for (int[] a:a2d) {
	    print(out, a);
	    out.print(" ");
	}
	out.print("}");	
    }
}
