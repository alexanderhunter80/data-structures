package com.epoch.dataStructures;

import java.util.ArrayList;
import java.util.Random;

public class Tester {
	
	ArrayList<String> testStrings;
	int[] testInts;
	RandomStringGenerator randS;
	Random rand;
	int passes;
	int stringLength;
	
	public Tester(int passes, int stringLength) {
		testStrings = new ArrayList<String>();
		randS = new RandomStringGenerator();
		rand = new Random();
		
		this.passes = passes;
		this.stringLength = stringLength;

		// setup, creates an ArrayList of one thousand random strings and an Array of one thousand ints 0-999

		testInts = new int[passes];
		int count = 0;
		for(int i = 0; i < passes; i++) {
			testInts[i] = i;
			String s = randS.generate(stringLength);
			if(!testStrings.contains(s)) {
				testStrings.add(s);
			} else {
				count++;
				i--;
			}
		}
	}
	

}
