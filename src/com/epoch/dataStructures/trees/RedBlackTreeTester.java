package com.epoch.dataStructures.trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.epoch.dataStructures.Benchmarker;
import com.epoch.dataStructures.RandomStringGenerator;

public class RedBlackTreeTester {
	
	Benchmarker benchmark;
	ArrayList<String> testStrings;
	int[] testInts;
	RandomStringGenerator randS;
	Random rand;
	int passes;
	int stringLength;
	
	public RedBlackTreeTester(int passes, int stringLength) {
		
		benchmark = new Benchmarker();
		
		testStrings = new ArrayList<String>();
		randS = new RandomStringGenerator();
		rand = new Random();

		this.passes = passes;
		this.stringLength = stringLength;
		
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

		System.out.println("RedBlackTreeTester initialized: " + passes + " passes of " + stringLength + "-character strings");
		System.out.println("Actual length of string list: "+testStrings.size());
		System.out.println("Duplicate strings generated and skipped: "+count);
		System.out.println();
		
	}
	
	public void testRedBlackTree() {

		Benchmarker.printSeparator("RedBlackTree");
		System.out.println();
		
		// setting up vanilla array from testStrings, for minimum overhead
		String[] strings = new String[passes];
		for(int i = 0; i < passes; i++) {
			strings[i] = testStrings.get(i);
		}
		// set up to add all strings with randomized priority
		ArrayList<Integer> accessList = new ArrayList<Integer>();
		for(int k : testInts) {
			accessList.add(k);
		}
		Collections.shuffle(accessList);
		int[] randomOrder = new int[passes];
		for(int i=0; i < passes; i++) {
			randomOrder[i] = accessList.get(i);
		}

		// testing time for .add()

		benchmark.start();

		RedBlackTree rbt = new RedBlackTree();
		for(int i=0; i < passes; i++) {
			rbt.add(randomOrder[i], strings[i]);
		}

		benchmark.stop("RedBlackTree: .add()");
		System.out.print("Size: ");
		System.out.println(rbt.size());
		System.out.println("Double checking size:");
		System.out.println(rbt.countNodes(rbt.getHead()));
		System.out.println();
		
		// testing size after .add()

		try {
			benchmark.initSize();

			RedBlackTree rb2 = new RedBlackTree();
			for(int i=0; i < passes; i++) {
				rb2.add(randomOrder[i], strings[i]);
			}

			benchmark.calculateSize();
			System.out.println();

		} catch (Exception e) {
			System.out.println("Failed during size test for RedBlackTree - stack trace follows");
			e.printStackTrace();
		}
		
		
		
		// testing time for .get()

		benchmark.start();

		for(int i=0; i < passes; i++) {
			rbt.get(i);
		}

		benchmark.stop("RedBlackTree: .get()");
		System.out.println();
		
		// check that table behaves properly after being populated
		System.out.println("Confirming proper operation of RedBlackTree");
		System.out.println("~~ THE NEXT TWO LINES SHOULD MATCH ~~");
		String ts = null;
		boolean flag = false;
		while(flag != true) {
			ts = randS.generate(stringLength);
			if(!testStrings.contains(ts)) {
				flag = true;
			}
		}
		System.out.println(ts);
		rbt.add(passes+1, ts);
		System.out.println(rbt.get(passes+1));
		rbt.remove(rbt.findNode(passes+1, rbt.getHead()));
		System.out.println();
		
		// testing time for .contains()

		benchmark.start();

		int countFound = 0;
		int countMissed = 0;
		for(int i = 0; i < passes; i++) {
			String s = randS.generate(stringLength);
			boolean tf = rbt.contains(s); // changed
			if(tf == true) {
				countFound++;
			} else {
				countMissed++;
			}
		}

		benchmark.stop("RedBlackTree: .contains()");
		System.out.print("Found: ");
		System.out.println(countFound);
		System.out.print("Missed: ");
		System.out.println(countMissed);
		System.out.println();
		
		// testing time for .remove()

		// set up to remove all previously used keys in random order
		accessList = new ArrayList<Integer>();
		for(int k : testInts) {
			accessList.add(k);
		}
		Collections.shuffle(accessList);
		randomOrder = new int[passes];
		for(int i=0; i < passes; i++) {
			randomOrder[i] = accessList.get(i);
		}

		benchmark.start();

		for(int i : randomOrder) {
			rbt.remove(rbt.findNode(i, rbt.getHead()));
		}

		benchmark.stop("RedBlackTree: .remove()");
		System.out.println("Reported size: " + rbt.size());
		System.out.println("Double checking emptiness:");
		System.out.println(rbt.countNodes(rbt.getHead()));
		System.out.println();
		
		
	}
	


}
