package com.epoch.dataStructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.epoch.dataStructures.controls.ControlArray;
import com.epoch.dataStructures.controls.ControlSinglyLinkedList;
import com.epoch.dataStructures.hashtables.LinearOpenAddressingHashtable;
import com.epoch.dataStructures.hashtables.OAHashNode;

public class DataStructuresDriver {

	public static void main(String[] args) {
		
		Benchmarker benchmark = new Benchmarker();
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
		
		ArrayList<String> testStrings = new ArrayList<String>();
		RandomStringGenerator randS = new RandomStringGenerator();
		Random rand = new Random();
		
		int passes = 10000;
		int stringLength = 5;
		
		// setup, creates an ArrayList of one thousand random strings and an Array of one thousand ints 0-999
		
		int[] testInts = new int[passes];
		for(int i = 0; i < passes; i++) {
			testInts[i] = i;
			testStrings.add(randS.generate(stringLength));
		}
		
		System.out.println("tenThousandStrings generated");
		System.out.println();
		
		
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
		
		benchmark.stop("ControlArray: .add()");
//		System.out.println(list.size());
//		System.out.println(list.get(passes-1));
//		try {
//			System.out.println(list.get(passes));
//		} catch(IndexOutOfBoundsException e) {
//			System.out.println("Hit expected exception:");
//			System.out.println(e);
//		}
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
		
		countFound = 0;
		countMissed = 0;
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
		
		LinearOpenAddressingHashtable linearTable = new LinearOpenAddressingHashtable(1000000, 0.25);
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
			
			LinearOpenAddressingHashtable linearTable2 = new LinearOpenAddressingHashtable(1000, 0.25);
			for(int i=0; i < passes; i++) {
				linearTable2.add(strings[i], i);
			}

			benchmark.calculateSize();
			System.out.println();
			
		} catch (Exception e) {
			System.out.println("Failed during size test for LinearOpenAddressingTable - stack trace follows");
			e.printStackTrace();
		}
		
		// testing time for .get()
		
		benchmark.start();
		
		for(int i=0; i < passes; i++) {
			linearTable.get(strings[i]);
		}
		
		benchmark.stop("LinearOpenAddressingHashtable: .get()");
		System.out.println();
		
		// testing time for .contains()
		
		benchmark.start();
		
		countFound = 0;
		countMissed = 0;
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
		count = 0;
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

}
