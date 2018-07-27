package com.epoch.dataStructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.epoch.dataStructures.controls.ControlArray;
import com.epoch.dataStructures.controls.ControlSinglyLinkedList;
import com.epoch.dataStructures.hashtables.CHashNode;
import com.epoch.dataStructures.hashtables.ChainingHashtable;
import com.epoch.dataStructures.hashtables.LinearOpenAddressingHashtable;
import com.epoch.dataStructures.hashtables.OAHashNode;
import com.epoch.dataStructures.hashtables.PeriodicIncreasingAmplitudeOpenAddressingHashtable;
import com.epoch.dataStructures.hashtables.QuadraticOpenAddressingHashtable;
import com.epoch.dataStructures.heaps.MinHeap;
import com.epoch.dataStructures.heaps.MinHeapNode;
import com.epoch.dataStructures.lists.DoublyLinkedList;
import com.epoch.dataStructures.trees.AVLTree;
import com.epoch.dataStructures.trees.BinarySearchTree;
import com.epoch.dataStructures.trees.RedBlackTree;
import com.epoch.dataStructures.trees.vanEmdeBoas.proto_vanEmdeBoas;
import com.epoch.dataStructures.trees.vanEmdeBoas.vanEmdeBoas;
import com.epoch.dataStructures.tries.Trie;

public class DataStructuresTester {

	Benchmarker benchmark;
	ArrayList<String> testStrings;
	int[] testInts;
	RandomStringGenerator randS;
	Random rand;
	int passes;
	int stringLength;
	
	// add a data-holding array of some kind that can print out all structure names and testing results at the end of the run, during wrapup()

	public DataStructuresTester(int passes, int stringLength) {

		benchmark = new Benchmarker();
		// USING BENCHMARKER TO MEASURE TIME:
		// benchmark.start();
		// (run operations)
		// benchmark.stop();  << returns elapsed time in ns as Long, also prints to console
		//
		// USING BENCHMARKER TO MEASURE MEMORY USAGE:
		// benchmark.initSize();
		// (run operations)
		// benchmark.calculateSize(); << returns approximate size of object in bytes as int, also prints to console
		// 
		// KEEP TIME AND SIZE CHECKS SEPARATE, NEVER ON THE SAME RUN!
		// .initSize() and .calcSize() run garbage collection like 400 times and also .yield() their threads,
		// this will ruin any chance of accurate timing
		// .start() and .stop() create an additional process and some variables that may also distort the size estimation,
		// although this is probably much less impactful than the other way around

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

		System.out.println("DataStructuresTester initialized: " + passes + " passes of " + stringLength + "-character strings");
		System.out.println("Actual length of string list: "+testStrings.size());
		System.out.println("Duplicate strings generated and skipped: "+count);
		System.out.println();

	}

	public void wrapup() {
		Benchmarker.printSeparator("Testing complete.");
	}

	public void testControlArray() {
		/*
		 * Testing ControlArray
		 */

		Benchmarker.printSeparator("ControlArray");

		// testing time for .add()

		benchmark.start();

		ControlArray array = new ControlArray(passes);
		for(String s : testStrings) {
			array.add(s);
		}

		benchmark.stop("ControlArray: .add()");
		System.out.println("Size: "+ array.size());
		System.out.println("Last element: " + array.get(passes-1));
		try {
			System.out.println(array.get(passes));
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Hit expected exception:");
			System.out.println(e);
		}
		System.out.println();

		// testing size after .add()

		try {
			benchmark.initSize();

			ControlArray array2 = new ControlArray(passes);
			for(String s : testStrings) {
				array2.add(s);
			}

			benchmark.calculateSize();
			System.out.println();

		} catch (Exception e) {
			System.out.println("Failed during size test for ControlArray - stack trace follows");
			e.printStackTrace();
		}

		// testing time for .get()

		benchmark.start();

		for(int i : testInts) {
			array.get(i);
		}

		benchmark.stop("ControlArray: .get()");
		System.out.println("One random get: "+array.get(rand.nextInt(passes)));
		System.out.println();

		// testing time for .contains()

		benchmark.start();

		int countFound = 0;
		int countMissed = 0;
		for(int i = 0; i < passes; i++) {
			String s = randS.generate(stringLength);
			boolean tf = array.contains(s);
			if(tf == true) {
				countFound++;
			} else {
				countMissed++;
			}
		}

		benchmark.stop("ControlArray: .contains()");
		System.out.print("Found: ");
		System.out.println(countFound);
		System.out.print("Missed: ");
		System.out.println(countMissed);
		System.out.println();

		// testing time for .remove()

		benchmark.start();

		for (int i = 0; i < passes; i++) {
			int r = rand.nextInt(array.size());
			array.remove(r);
		}

		benchmark.stop("ControlArray: .remove()");
		System.out.println("Reported size: " + array.size());
		System.out.print("Double checking emptiness: ");
		int count = 0;
		for(Object o : array.getArray()) {
			if(o != null) {
				count++;
			}
		}
		System.out.println(count);
		System.out.println();
	}

	public void testControlSinglyLinkedList() {

		/*
		 * Testing ControlSinglyLinkedList 
		 */

		Benchmarker.printSeparator("ControlSinglyLinkedList");

		// testing time for .add()

		benchmark.start();

		ControlSinglyLinkedList list = new ControlSinglyLinkedList();
		for(String s : testStrings) {
			list.add(s);
		}

		benchmark.stop("ControlSinglyLinkedList: .add()");
		//	System.out.println(list.size());
		//	System.out.println(list.get(passes-1));
		//	try {
		//		System.out.println(list.get(passes));
		//	} catch(IndexOutOfBoundsException e) {
		//		System.out.println("Hit expected exception:");
		//		System.out.println(e);
		//	}
		System.out.println();

		// testing size after .add()

		try {
			benchmark.initSize();

			ControlSinglyLinkedList list2 = new ControlSinglyLinkedList();
			for(String s : testStrings) {
				list2.add(s);
			}

			benchmark.calculateSize();
			System.out.println();

		} catch (Exception e) {
			System.out.println("Failed during size test for ControlSinglyLinkedList - stack trace follows");
			e.printStackTrace();
		}

		// testing time for .get()

		benchmark.start();

		for(int i : testInts) {
			list.get(i);
		}

		benchmark.stop("ControlSinglyLinkedList: .get()");
		System.out.println();

		// testing time for .contains()

		benchmark.start();

		int countFound = 0;
		int countMissed = 0;
		for(int i = 0; i < passes; i++) {
			String s = randS.generate(stringLength);
			boolean tf = list.contains(s);
			if(tf == true) {
				countFound++;
			} else {
				countMissed++;
			}
		}

		benchmark.stop("ControlSinglyLinkedList: .contains()");
		System.out.print("Found: ");
		System.out.println(countFound);
		System.out.print("Missed: ");
		System.out.println(countMissed);
		System.out.println();

		// testing time for .remove()

		benchmark.start();

		for (int i = 0; i < passes; i++) {
			int r = rand.nextInt(list.size());
			list.remove(r);
		}

		benchmark.stop("ControlSinglyLinkedList: .remove()");
		System.out.println("Reported size: " + list.size());
		System.out.print("Double checking emptiness: ");
		System.out.println(list.getHead());
		System.out.println();

	}

	public void testDoublyLinkedList() {
		/*
		 * Testing DoublyLinkedList
		 */

		Benchmarker.printSeparator("DoublyLinkedList");

		// testing time for .add()

		benchmark.start();

		DoublyLinkedList list = new DoublyLinkedList();
		for(String s : testStrings) {
			list.add(s);
		}

		benchmark.stop("DoublyLinkedList: .add()");
		//	System.out.println(list.size());
		//	System.out.println(list.get(passes-1));
		//	try {
		//		System.out.println(list.get(passes));
		//	} catch(IndexOutOfBoundsException e) {
		//		System.out.println("Hit expected exception:");
		//		System.out.println(e);
		//	}
		System.out.println();

		// testing size after .add()

		try {
			benchmark.initSize();

			DoublyLinkedList list2 = new DoublyLinkedList();
			for(String s : testStrings) {
				list2.add(s);
			}

			benchmark.calculateSize();
			System.out.println();

		} catch (Exception e) {
			System.out.println("Failed during size test for DoublyLinkedList - stack trace follows");
			e.printStackTrace();
		}

		// testing time for .get()

		benchmark.start();

		for(int i : testInts) {
			list.get(i);
		}

		benchmark.stop("DoublyLinkedList: .get()");
		System.out.println();

		// testing time for .contains()

		benchmark.start();

		int countFound = 0;
		int countMissed = 0;
		for(int i = 0; i < passes; i++) {
			String s = randS.generate(stringLength);
			boolean tf = list.contains(s);
			if(tf == true) {
				countFound++;
			} else {
				countMissed++;
			}
		}

		benchmark.stop("DoublyLinkedList: .contains()");
		System.out.print("Found: ");
		System.out.println(countFound);
		System.out.print("Missed: ");
		System.out.println(countMissed);
		System.out.println();

		// testing time for .remove()

		benchmark.start();

		for (int i = 0; i < passes; i++) {
			int r = rand.nextInt(list.size());
			list.remove(r);
		}

		benchmark.stop("DoublyLinkedList: .remove()");
		System.out.println("Reported size: " + list.size());
		System.out.print("Double checking emptiness: ");
		System.out.println(list.getHead());
		System.out.println();
	}
	
	public void testChainingHashtable() {

		/*
		 * Testing ChainingHashtable
		 */

		Benchmarker.printSeparator("ChainingHashtable");


		// setting up vanilla array from oneThousandStrings, for minimum overhead
		String[] strings = new String[passes];
		for(int i = 0; i < passes; i++) {
			strings[i] = testStrings.get(i);
		}

		// testing time for .add()

		benchmark.start();

		ChainingHashtable chainTable = new ChainingHashtable(passes, 3);
		for(int i=0; i < passes; i++) {
			chainTable.add(strings[i], i);
		}

		benchmark.stop("ChainingHashtable: .add()");
		System.out.print("Size: ");
		System.out.println(chainTable.getSize());
		System.out.print("Load: ");
		System.out.println(chainTable.getLoad());
		System.out.print("Load factor: ");
		double lf = (double) chainTable.getLoad()/chainTable.getSize();
		System.out.println(lf);
		System.out.println("Chains: "+chainTable.chains);
		System.out.println();

		// testing size after .add()

		try {
			benchmark.initSize();

			ChainingHashtable chainTable2 = new ChainingHashtable(passes, 3);
			for(int i=0; i < passes; i++) {
				chainTable2.add(strings[i], i);
			}

			benchmark.calculateSize();
			System.out.println();

		} catch (Exception e) {
			System.out.println("Failed during size test for ChainingHashtable - stack trace follows");
			e.printStackTrace();
		}
		
		// check that table behaves properly after being populated
		System.out.println("Confirming proper operation of ChainingHashtable");
		String t = "~~ THESE TWO LINES SHOULD MATCH ~~";
		String ts = null;
		boolean flag = false;
		while(flag != true) {
			ts = randS.generate(stringLength);
			if(!testStrings.contains(ts)) {
				flag = true;
			}
		}
		System.out.println(t);
		chainTable.add(ts, t);
		System.out.println(chainTable.get(ts));
		chainTable.remove(ts);
		System.out.println();

		// testing time for .get()

		benchmark.start();

		for(int i=0; i < passes; i++) {
			chainTable.get(strings[i]);
		}

		benchmark.stop("ChainingHashtable: .get()");
		System.out.println();

		// testing time for .contains()

		benchmark.start();

		int countFound = 0;
		int countMissed = 0;
		for(int i = 0; i < passes; i++) {
			String s = randS.generate(stringLength);
			boolean tf = chainTable.contains(s);
			if(tf == true) {
				countFound++;
			} else {
				countMissed++;
			}
		}

		benchmark.stop("ChainingHashtable: .contains()");
		System.out.print("Found: ");
		System.out.println(countFound);
		System.out.print("Missed: ");
		System.out.println(countMissed);
		System.out.println();

		// testing time for .remove()

		// set up to remove all previously used keys in random order
		ArrayList<String> accessList = new ArrayList<String>();
		for(String s : strings) {
			accessList.add(s);
		}
		Collections.shuffle(accessList);
		String[] randomOrder = new String[passes];
		for(int i=0; i < passes; i++) {
			randomOrder[i] = accessList.get(i);
		}

		benchmark.start();

		for(String s : randomOrder) {
			chainTable.remove(s);
		}

		benchmark.stop("ChainingHashtable: .remove()");
		System.out.println("Reported size: " + chainTable.getLoad());
		System.out.print("Double checking emptiness: ");
		int count = 0;
		for(CHashNode o : chainTable.getEntireTable()) {
			if(o != null) {
				if(o.key != null) {
					count++;
				}
			}
		}
		System.out.println(count);
		System.out.println();

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public void testLinearOpenAddressingHashtable() {

		/*
		 * Testing LinearOpenAddressingHashtable
		 */

		Benchmarker.printSeparator("LinearOpenAddressingHashtable");


		// setting up vanilla array from oneThousandStrings, for minimum overhead
		String[] strings = new String[passes];
		for(int i = 0; i < passes; i++) {
			strings[i] = testStrings.get(i);
		}

		// testing time for .add()

		benchmark.start();

		LinearOpenAddressingHashtable linearTable = new LinearOpenAddressingHashtable(passes, 0.25);
		for(int i=0; i < passes; i++) {
			linearTable.add(strings[i], i);
		}

		benchmark.stop("LinearOpenAddressingHashtable: .add()");
		System.out.print("Size: ");
		System.out.println(linearTable.getSize());
		System.out.print("Load: ");
		System.out.println(linearTable.getLoad());
		System.out.print("Load factor: ");
		double lf = (double) linearTable.getLoad()/linearTable.getSize();
		System.out.println(lf);
		System.out.println();

		// testing size after .add()

		try {
			benchmark.initSize();

			LinearOpenAddressingHashtable linearTable2 = new LinearOpenAddressingHashtable(passes, 0.25);
			for(int i=0; i < passes; i++) {
				linearTable2.add(strings[i], i);
			}

			benchmark.calculateSize();
			System.out.println();

		} catch (Exception e) {
			System.out.println("Failed during size test for LinearOpenAddressingTable - stack trace follows");
			e.printStackTrace();
		}
		
		// check that table behaves properly after being populated
		System.out.println("Confirming proper operation of LinearOpenAddressingHashtable");
		String t = "~~ THESE TWO LINES SHOULD MATCH ~~";
		String ts = null;
		boolean flag = false;
		while(flag != true) {
			ts = randS.generate(stringLength);
			if(!testStrings.contains(ts)) {
				flag = true;
			}
		}
		System.out.println(t);
		linearTable.add(ts, t);
		System.out.println(linearTable.get(ts));
		linearTable.remove(ts);
		System.out.println();

		// testing time for .get()

		benchmark.start();

		for(int i=0; i < passes; i++) {
			linearTable.get(strings[i]);
		}

		benchmark.stop("LinearOpenAddressingHashtable: .get()");
		System.out.println();

		// testing time for .contains()

		benchmark.start();

		int countFound = 0;
		int countMissed = 0;
		for(int i = 0; i < passes; i++) {
			String s = randS.generate(stringLength);
			boolean tf = linearTable.contains(s);
			if(tf == true) {
				countFound++;
			} else {
				countMissed++;
			}
		}

		benchmark.stop("LinearOpenAddressingHashtable: .contains()");
		System.out.print("Found: ");
		System.out.println(countFound);
		System.out.print("Missed: ");
		System.out.println(countMissed);
		System.out.println();

		// testing time for .remove()

		// set up to remove all previously used keys in random order
		ArrayList<String> accessList = new ArrayList<String>();
		for(String s : strings) {
			accessList.add(s);
		}
		Collections.shuffle(accessList);
		String[] randomOrder = new String[passes];
		for(int i=0; i < passes; i++) {
			randomOrder[i] = accessList.get(i);
		}

		benchmark.start();

		for(String s : randomOrder) {
			linearTable.remove(s);
		}

		benchmark.stop("LinearOpenAddressingHashtable: .remove()");
		System.out.println("Reported size: " + linearTable.getLoad());
		System.out.print("Double checking emptiness: ");
		int count = 0;
		for(OAHashNode o : linearTable.getEntireTable()) {
			if(o != null) {
				if(o.key != null) {
					count++;
				}
			}
		}
		System.out.println(count);
		System.out.println();

	}

	public void testQuadraticOpenAddressingHashtable() {

		/*
		 * Testing QuadraticOpenAddressingHashtable
		 */

		Benchmarker.printSeparator("QuadraticOpenAddressingHashtable");


		// setting up vanilla array from oneThousandStrings, for minimum overhead
		String[] strings = new String[passes];
		for(int i = 0; i < passes; i++) {
			strings[i] = testStrings.get(i);
		}

		// testing time for .add()

		benchmark.start();

		QuadraticOpenAddressingHashtable quadraticTable = new QuadraticOpenAddressingHashtable(passes, 0.25);
		for(int i=0; i < passes; i++) {
			quadraticTable.add(strings[i], i);
		}

		benchmark.stop("QuadraticOpenAddressingHashtable: .add()");
		System.out.print("Size: ");
		System.out.println(quadraticTable.getSize());
		System.out.print("Load: ");
		System.out.println(quadraticTable.getLoad());
		System.out.print("Load factor: ");
		double lf = (double) quadraticTable.getLoad()/quadraticTable.getSize();
		System.out.println(lf);
		System.out.println();

		// testing size after .add()

		try {
			benchmark.initSize();

			QuadraticOpenAddressingHashtable quadraticTable2 = new QuadraticOpenAddressingHashtable(passes, 0.25);
			for(int i=0; i < passes; i++) {
				quadraticTable2.add(strings[i], i);
			}

			benchmark.calculateSize();
			System.out.println();

		} catch (Exception e) {
			System.out.println("Failed during size test for QuadraticOpenAddressingTable - stack trace follows");
			e.printStackTrace();
		}
		
		// check that table behaves properly after being populated
		System.out.println("Confirming proper operation of QuadraticOpenAddressingHashtable");
		String t = "~~ THESE TWO LINES SHOULD MATCH ~~";
		String ts = null;
		boolean flag = false;
		while(flag != true) {
			ts = randS.generate(stringLength);
			if(!testStrings.contains(ts)) {
				flag = true;
			}
		}
		System.out.println(t);
		quadraticTable.add(ts, t);
		System.out.println(quadraticTable.get(ts));
		quadraticTable.remove(ts);
		System.out.println();

		// testing time for .get()

		benchmark.start();

		for(int i=0; i < passes; i++) {
			quadraticTable.get(strings[i]);
		}

		benchmark.stop("QuadraticOpenAddressingHashtable: .get()");
		System.out.println();

		// testing time for .contains()

		benchmark.start();

		int countFound = 0;
		int countMissed = 0;
		for(int i = 0; i < passes; i++) {
			String s = randS.generate(stringLength);
			boolean tf = quadraticTable.contains(s);
			if(tf == true) {
				countFound++;
			} else {
				countMissed++;
			}
		}

		benchmark.stop("QuadraticOpenAddressingHashtable: .contains()");
		System.out.print("Found: ");
		System.out.println(countFound);
		System.out.print("Missed: ");
		System.out.println(countMissed);
		System.out.println();

		// testing time for .remove()

		// set up to remove all previously used keys in random order
		ArrayList<String> accessList = new ArrayList<String>();
		for(String s : strings) {
			accessList.add(s);
		}
		Collections.shuffle(accessList);
		String[] randomOrder = new String[passes];
		for(int i=0; i < passes; i++) {
			randomOrder[i] = accessList.get(i);
		}

		benchmark.start();

		for(String s : randomOrder) {
			quadraticTable.remove(s);
		}

		benchmark.stop("QuadraticOpenAddressingHashtable: .remove()");
		System.out.println("Reported size: " + quadraticTable.getLoad());
		System.out.print("Double checking emptiness: ");
		int count = 0;
		for(OAHashNode o : quadraticTable.getEntireTable()) {
			if(o != null) {
				if(o.key != null) {
					count++;
				}
			}
		}
		System.out.println(count);
		System.out.println();

	}

	public void testPeriodicHashtable() {
		/*
		 * Testing PeriodicIncreasingAmplitudeOpenAddressingHashtable
		 */

		Benchmarker.printSeparator("PeriodicIncreasingAmplitudeOpenAddressingHashtable");


		// setting up vanilla array from testStrings, for minimum overhead
		String[] strings = new String[passes];
		for(int i = 0; i < passes; i++) {
			strings[i] = testStrings.get(i);
		}

		// testing time for .add()

		benchmark.start();

		PeriodicIncreasingAmplitudeOpenAddressingHashtable periodicTable = new PeriodicIncreasingAmplitudeOpenAddressingHashtable(passes, 0.25);
		for(int i=0; i < passes; i++) {
			periodicTable.add(strings[i], i);
		}

		benchmark.stop("PeriodicIncreasingAmplitudeOpenAddressingHashtable: .add()");
		System.out.print("Size: ");
		System.out.println(periodicTable.getSize());
		System.out.print("Load: ");
		System.out.println(periodicTable.getLoad());
		System.out.print("Load factor: ");
		double lf = (double) periodicTable.getLoad()/periodicTable.getSize();
		System.out.println(lf);
		System.out.println();

		// testing size after .add()

		try {
			benchmark.initSize();

			PeriodicIncreasingAmplitudeOpenAddressingHashtable periodicTable2 = new PeriodicIncreasingAmplitudeOpenAddressingHashtable(passes, 0.25);
			for(int i=0; i < passes; i++) {
				periodicTable2.add(strings[i], i);
			}

			benchmark.calculateSize();
			System.out.println();

		} catch (Exception e) {
			System.out.println("Failed during size test for PeriodicIncreasingAmplitudeOpenAddressingHashtable - stack trace follows");
			e.printStackTrace();
		}

		// check that table behaves properly after being populated
		System.out.println("Confirming proper operation of PeriodicIncreasingAmplitudeOpenAddressingHashtable");
		String t = "~~ THESE TWO LINES SHOULD MATCH ~~";
		String ts = null;
		boolean flag = false;
		while(flag != true) {
			ts = randS.generate(stringLength);
			if(!testStrings.contains(ts)) {
				flag = true;
			}
		}
		System.out.println(t);
		periodicTable.add(ts, t);
		System.out.println(periodicTable.get(ts));
		periodicTable.remove(ts);
		System.out.println();

		// testing time for .get()

		benchmark.start();

		for(int i=0; i < passes; i++) {
			periodicTable.get(strings[i]);
		}

		benchmark.stop("PeriodicIncreasingAmplitudeOpenAddressingHashtable: .get()");
		System.out.println();

		// testing time for .contains()

		benchmark.start();

		int countFound = 0;
		int countMissed = 0;
		for(int i = 0; i < passes; i++) {
			String s = randS.generate(stringLength);
			boolean tf = periodicTable.contains(s);
			if(tf == true) {
				countFound++;
			} else {
				countMissed++;
			}
		}

		benchmark.stop("PeriodicIncreasingAmplitudeOpenAddressingHashtable: .contains()");
		System.out.print("Found: ");
		System.out.println(countFound);
		System.out.print("Missed: ");
		System.out.println(countMissed);
		System.out.println();

		// testing time for .remove()

		// set up to remove all previously used keys in random order
		ArrayList<String> accessList = new ArrayList<String>();
		for(String s : strings) {
			accessList.add(s);
		}
		Collections.shuffle(accessList);
		String[] randomOrder = new String[passes];
		for(int i=0; i < passes; i++) {
			randomOrder[i] = accessList.get(i);
		}

		benchmark.start();

		for(String s : randomOrder) {
			periodicTable.remove(s);
		}

		benchmark.stop("PeriodicIncreasingAmplitudeOpenAddressingHashtable: .remove()");
		System.out.println("Reported size: " + periodicTable.getLoad());
		System.out.print("Double checking emptiness: ");
		int count = 0;
		for(OAHashNode o : periodicTable.getEntireTable()) {
			if(o != null) {
				if(o.key != null) {
					count++;
				}
			}
		}
		System.out.println(count);
		System.out.println();


	}
	
	public void testBinarySearchTree() {

		/*
		 * Testing ControlSinglyLinkedList 
		 */

		Benchmarker.printSeparator("BinarySearchTree");

		// testing time for .add()

		// setting up vanilla array from testStrings, for minimum overhead
		String[] strings = new String[passes];
		for(int i = 0; i < passes; i++) {
			strings[i] = testStrings.get(i);
		}

		// testing time for .add()

		benchmark.start();

		BinarySearchTree bst = new BinarySearchTree();
		for(int i=0; i < passes; i++) {
			bst.add(i, strings[i]);
		}

		benchmark.stop("BinarySearchTree: .add()");
		System.out.print("Size: ");
		System.out.println(bst.size());
		System.out.println();
		
		
		// testing size after .add()

		try {
			benchmark.initSize();

			BinarySearchTree bst2 = new BinarySearchTree();
			for(int i=0; i < passes; i++) {
				bst2.add(i, strings[i]);
			}

			benchmark.calculateSize();
			System.out.println();

		} catch (Exception e) {
			System.out.println("Failed during size test for BinarySearchTree - stack trace follows");
			e.printStackTrace();
		}
		
		// testing time for .get()

		benchmark.start();

		for(int i=0; i < passes; i++) {
			bst.get(i);
		}

		benchmark.stop("BinarySearchTree: .get()");
		System.out.println();
		
		// check that table behaves properly after being populated
		System.out.println("Confirming proper operation of BinarySearchTree");
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
		bst.add(passes+1, ts);
		System.out.println(bst.get(passes+1));
		bst.remove(passes+1);
		System.out.println();
		
		// testing time for .contains()

		benchmark.start();

		int countFound = 0;
		int countMissed = 0;
		for(int i = 0; i < passes; i++) {
			String s = randS.generate(stringLength);
			boolean tf = bst.contains(s); // changed
			if(tf == true) {
				countFound++;
			} else {
				countMissed++;
			}
		}

		benchmark.stop("BinarySearchTree: .contains()");
		System.out.print("Found: ");
		System.out.println(countFound);
		System.out.print("Missed: ");
		System.out.println(countMissed);
		System.out.println();
		
		// testing time for .remove()

		// set up to remove all previously used keys in random order
		ArrayList<Integer> accessList = new ArrayList<Integer>();
		for(int k : testInts) {
			accessList.add(k);
		}
		Collections.shuffle(accessList);
		int[] randomOrder = new int[passes];
		for(int i=0; i < passes; i++) {
			randomOrder[i] = accessList.get(i);
		}

		benchmark.start();

		for(int i : randomOrder) {
			bst.remove(i);
		}

		benchmark.stop("BinarySearchTree: .remove()");
		System.out.println("Reported size: " + bst.size());
		System.out.print("Double checking emptiness: ");
		System.out.println(bst.getHead());
		System.out.println();
		
	}
	
	
	public void testAVLTree() {

		/*
		 * Testing AVLTree
		 */

		Benchmarker.printSeparator("AVLTree");
		System.out.println();

		// testing time for .add()

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

		AVLTree avl = new AVLTree();
		for(int i=0; i < passes; i++) {
			avl.add(randomOrder[i], strings[i]);
		}

		benchmark.stop("AVLTree: .add()");
		System.out.print("Size: ");
		System.out.println(avl.size());
		System.out.println("Double checking size:");
		System.out.println(avl.countNodes(avl.getHead()));
		System.out.println();
		
		
		// testing size after .add()

		try {
			benchmark.initSize();

			AVLTree avl2 = new AVLTree();
			for(int i=0; i < passes; i++) {
				avl2.add(randomOrder[i], strings[i]);
			}

			benchmark.calculateSize();
			System.out.println();

		} catch (Exception e) {
			System.out.println("Failed during size test for AVLTree - stack trace follows");
			e.printStackTrace();
		}
		
		// testing time for .get()

		benchmark.start();

		for(int i=0; i < passes; i++) {
			avl.get(i);
		}

		benchmark.stop("AVLTree: .get()");
		System.out.println();
		
		// check that table behaves properly after being populated
		System.out.println("Confirming proper operation of AVLTree");
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
		avl.add(passes+1, ts);
		System.out.println(avl.get(passes+1));
		avl.remove(passes+1);
		System.out.println();
		
		// testing time for .contains()

		benchmark.start();

		int countFound = 0;
		int countMissed = 0;
		for(int i = 0; i < passes; i++) {
			String s = randS.generate(stringLength);
			boolean tf = avl.contains(s); // changed
			if(tf == true) {
				countFound++;
			} else {
				countMissed++;
			}
		}

		benchmark.stop("AVLTree: .contains()");
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
			avl.remove(i);
		}

		benchmark.stop("AVLTree: .remove()");
		System.out.println("Reported size: " + avl.size());
		System.out.println("Double checking emptiness:");
		System.out.println(avl.countNodes(avl.getHead()));
		System.out.println();
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	public void testMinHeap() {

		/*
		 * Testing MinHeap 
		 */

		Benchmarker.printSeparator("MinHeap");
		
		// testing time for .add()

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

		benchmark.start();

		MinHeap minHeap = new MinHeap(passes);
		for(int i=0; i < passes; i++) {
			minHeap.add(randomOrder[i], strings[i]);
		}

		benchmark.stop("MinHeap: .add()");
		System.out.print("Size: ");
		System.out.println(minHeap.size());
		System.out.println();
		
		// testing size after .add()
		
		try {
			benchmark.initSize();

			MinHeap mh2 = new MinHeap(passes);
			for(int i=0; i < passes; i++) {
				mh2.add(i, strings[i]);
			}

			benchmark.calculateSize();
			System.out.println();

		} catch (Exception e) {
			System.out.println("Failed during size test for MinHeap - stack trace follows");
			e.printStackTrace();
		}
		
		// testing time for .get()

		benchmark.start();

		for(int i=0; i < passes; i++) {
			minHeap.get(strings[i]);
		}

		benchmark.stop("MinHeap: .get()");
		System.out.println();
		
		// testing time for .contains()

		benchmark.start();

		int countFound = 0;
		int countMissed = 0;
		for(int i = 0; i < passes; i++) {
			String s = randS.generate(stringLength);
			boolean tf = minHeap.contains(s); // changed
			if(tf == true) {
				countFound++;
			} else {
				countMissed++;
			}
		}

		benchmark.stop("MinHeap: .contains()");
		System.out.print("Found: ");
		System.out.println(countFound);
		System.out.print("Missed: ");
		System.out.println(countMissed);
		System.out.println();
		
		// testing time for .remove()

		benchmark.start();

		for(int i : testInts) {
			minHeap.remove();
		}

		benchmark.stop("MinHeap: .remove()");
		System.out.println("Reported size: " + minHeap.size());
		System.out.print("Double checking emptiness: ");
		int count = 0;
		for(MinHeapNode node : minHeap.getArray()) {
			if(node != null) {
				count++;
			}
		}
		System.out.println(count);
		System.out.println();
		
	}
	
	public void testTrie() {
		
		/*
		 * Testing Trie
		 */

		Benchmarker.printSeparator("Trie");
		
		// testing time for .add()

		// setting up vanilla array from testStrings, for minimum overhead
		String[] strings = new String[passes];
		for(int i = 0; i < passes; i++) {
			strings[i] = testStrings.get(i);
		}
		
		benchmark.start();

		Trie trie = new Trie();
		for(int i=0; i < passes; i++) {
			trie.add(strings[i]);
		}

		benchmark.stop("Trie: .add()");
		System.out.print("Size: ");
		System.out.println(trie.size());
		System.out.println();
		
		// testing size after .add()
		
		try {
			benchmark.initSize();

			Trie trie2 = new Trie();
			for(int i=0; i < passes; i++) {
				trie2.add(strings[i]);
			}

			benchmark.calculateSize();
			System.out.println();

		} catch (Exception e) {
			System.out.println("Failed during size test for Trie - stack trace follows");
			e.printStackTrace();
		}
		
		// testing time for .get()
		
		benchmark.start();

		for(int i=0; i < passes; i++) {
			trie.get(strings[i]);
		}

		benchmark.stop("Trie: .get()");
		System.out.println();
		
		System.out.println("Confirming proper operation of Trie");
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
		trie.add(ts);
		System.out.println(trie.get(ts));
		trie.remove(ts);
		System.out.println();
		
		// testing time for .contains()

		benchmark.start();

		int countFound = 0;
		int countMissed = 0;
		for(int i = 0; i < passes; i++) {
			String s = randS.generate(stringLength);
			boolean tf = trie.contains(s); // changed
			if(tf == true) {
				countFound++;
			} else {
				countMissed++;
			}
		}
		
		benchmark.stop("Trie: .contains()");
		System.out.print("Found: ");
		System.out.println(countFound);
		System.out.print("Missed: ");
		System.out.println(countMissed);
		System.out.println();
		
		// testing time for .remove()

		benchmark.start();

		for(int i=0; i < passes; i++) {
			trie.remove(strings[i]);
		}

		benchmark.stop("Trie: .remove()");
		System.out.println("Reported size: " + trie.size());
		System.out.println("Double checking emptiness (should be 1, false): ");
		System.out.println(trie.getHead().countNodes());
		System.out.println(trie.getHead().hasValidChildren());
		System.out.println();
		
	}

	
	public void test_proto_vanEmdeBoas() {
		
		/*
		 * Testing ControlSinglyLinkedList 
		 */

		Benchmarker.printSeparator("proto van Emde Boas Structure");
		
		// testing time for .add()

		benchmark.start();
		
		proto_vanEmdeBoas vEB = new proto_vanEmdeBoas((int)Math.pow(2, 16));
		
		benchmark.stop("proto van Emde Boas Structure: .init()");
		
		String[] strings = new String[passes];
		for(int i = 0; i < passes; i++) {
			strings[i] = testStrings.get(i);
		}
		
		benchmark.start();
		
		for (int i = 0; i < passes; i++)
			vEB.insert(i);
		
		benchmark.stop("proto van Emde Boas Structure: .insert()");
		
		benchmark.start();
		
		for (int i = 0; i < passes; i++)
			vEB.insert(i);
		
		benchmark.stop("proto van Emde Boas Structure: .contains()");
		
		benchmark.start();
		
		for (int i = 0; i < passes; i++)
			vEB.remove(i);
		
		benchmark.stop("proto van Emde Boas Structure: .remove()");
		
		
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
