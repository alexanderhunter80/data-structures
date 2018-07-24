package com.epoch.dataStructures;

import com.epoch.dataStructures.DataStructuresTester;

public class DataStructuresDriver {

	public static void main(String[] args) {
		
		System.out.println("Starting run in DataStructuresDriver");
		
		// so, uh, this doesn't work if the string length is less than 3...
		// which OF COURSE is because there are only 36*36 = 1296 unique strings of length 2 :/ good job, brain!
		DataStructuresTester tester = new DataStructuresTester(10000,4);
		
		
////		// controls - Array and SLL
////		
//		tester.testControlArray();
//		tester.testControlSinglyLinkedList();
////		
////		// lists
////		
//		tester.testDoublyLinkedList();
////		
////		// hashtables
////
//		tester.testLinearOpenAddressingHashtable();
//		tester.testQuadraticOpenAddressingHashtable();
//		tester.testPeriodicHashtable();
//		
//		// heaps
//		
//		tester.testMinHeap();
//		
//		// trees
		
		tester.testBinarySearchTree();
		
		// wrapup
		
		tester.wrapup();
		


	}

}
