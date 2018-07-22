package com.epoch.dataStructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.epoch.dataStructures.controls.ControlArray;
import com.epoch.dataStructures.controls.ControlSinglyLinkedList;
import com.epoch.dataStructures.hashtables.LinearOpenAddressingHashtable;
import com.epoch.dataStructures.hashtables.OAHashNode;
import com.epoch.dataStructures.hashtables.PeriodicIncreasingAmplitudeOpenAddressingHashtable;
import com.epoch.dataStructures.hashtables.QuadraticOpenAddressingHashtable;
import com.epoch.dataStructures.lists.DoublyLinkedList;

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

		benchmark.stop("ControlArray: .add()");
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


}
