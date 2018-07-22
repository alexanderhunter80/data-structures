package com.epoch.dataStructures.hashtables;

public class Hasher {
	
	// This is a reproduction of the Java String.hashCode(String s) function which also takes a modulo argument
	
	// any small prime number works here, ideally a Mersenne prime (can be expressed as 2^n - 1) as this can simplify the modulo operation
	private static int R = 31;
	
	public static int hash(String s, int l) {
		int output = 0;
			for(int i = 0; i < s.length(); i++) {
				output = (R*output + s.charAt(i)) % l;
			}
		return output;
	}

}
