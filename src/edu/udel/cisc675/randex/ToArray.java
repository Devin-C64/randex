package edu.udel.cisc675.randex;

import java.util.ArrayList;

public class ToArray {
    //Takes a list and makes it an array
    public ToArray() {}

    public int[] toArray(ArrayList<Integer> list) {
	int n = list.size();
	int[] result = new int[n];
	for (int i=0; i<n; i++)
	    result[i] = list.get(i);
	return result;
    }
}
