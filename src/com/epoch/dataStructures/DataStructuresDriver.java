package com.epoch.dataStructures;

import java.util.ArrayList;

import com.epoch.dataStructures.controls.ControlArray;
import com.epoch.dataStructures.controls.ControlSinglyLinkedList;

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
		
		ArrayList<String> oneThousandStrings = new ArrayList();
		RandomStringGenerator randS = new RandomStringGenerator();
		
		// setup, creates an ArrayList of one thousand random strings
		
		benchmark.start();
		
		for(int i = 0; i < 1000; i++) {
			oneThousandStrings.add(randS.generate(3));
		}
		
		benchmark.stop("oneThousandStrings generated");
		System.out.println();
		
		/*
		 * Testing ControlArray
		 */
		
		// testing time for .add()
		
		benchmark.start();
		
		ControlArray array = new ControlArray(1000);
		for(String s : oneThousandStrings) {
			array.add(s);
		}
		
		benchmark.stop("ControlArray: .add()");
		System.out.println(array.size());
		System.out.println(array.get(999));
		try {
			System.out.println(array.get(1000));
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Hit expected exception:");
			System.out.println(e);
		}
		System.out.println();
		
		// testing size after .add()
		
		try {
			benchmark.initSize();
			
			ControlArray array2 = new ControlArray(1000);
			for(String s : oneThousandStrings) {
				array2.add(s);
			}
			
			benchmark.calculateSize();
			
		} catch (Exception e) {
			System.out.println("Failed during size test for ControlArray - stack trace follows");
			e.printStackTrace();
		}
		
		
		// testing time for .contains()
		
		benchmark.start();
		
		int countFound = 0;
		int countMissed = 0;
		for(int i = 0; i < 1000; i++) {
			String s = randS.generate(3);
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
		
		/*
		 * Testing ControlSinglyLinkedList 
		 */
		
		// testing time for .add()
		
		benchmark.start();
		
		ControlSinglyLinkedList list = new ControlSinglyLinkedList();
		for(String s : oneThousandStrings) {
			list.add(s);
		}
		
		benchmark.stop("ControlArray: .add()");
		System.out.println(list.size());
		System.out.println(list.get(999));
		try {
			System.out.println(list.get(1000));
		} catch(IndexOutOfBoundsException e) {
			System.out.println("Hit expected exception:");
			System.out.println(e);
		}
		System.out.println();
		
		// testing size after .add()
		
		try {
			benchmark.initSize();
			
			ControlSinglyLinkedList list2 = new ControlSinglyLinkedList();
			for(String s : oneThousandStrings) {
				list2.add(s);
			}
			
			benchmark.calculateSize();
			
		} catch (Exception e) {
			System.out.println("Failed during size test for ControlSinglyLinkedList - stack trace follows");
			e.printStackTrace();
		}
		
		// testing time for .contains()
		
		benchmark.start();
		
		countFound = 0;
		countMissed = 0;
		for(int i = 0; i < 1000; i++) {
			String s = randS.generate(3);
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
		

	}

}
