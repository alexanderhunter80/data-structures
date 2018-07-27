package com.epoch.dataStructures;

public class DataStructuresDriver {

	public static void main(String[] args) {
		
		System.out.println("Starting run in DataStructuresDriver");
		
		DataStructuresTester tester = new DataStructuresTester(10000,9);
		
		FibonacciHeapTester heapTester = new FibonacciHeapTester(10000, 9);
		vanEmdeBoasTester vEBTester = new vanEmdeBoasTester(10000, 9);
		
		heapTester.run();
		vEBTester.run();
		
		// controls - Array and SLL
		
//		tester.testControlArray();
//		tester.testControlSinglyLinkedList();
//		
//		// lists
//	
//		tester.testDoublyLinkedList();
//		
//		// hashtables
//
//		tester.testChainingHashtable();
//		tester.testLinearOpenAddressingHashtable();
//		tester.testQuadraticOpenAddressingHashtable();
//		tester.testPeriodicHashtable();
//		
//		// heaps
//		
//		tester.testMinHeap();
//		// tester.testFibHeap();
//		
//		// trees
//		
//		tester.testBinarySearchTree();
//		tester.testTrie();
//		tester.testAVLTree();
//		tester.testRedBlackTree();
		tester.test_proto_vanEmdeBoas();
//		
		// wrapup
		
		tester.wrapup();
		


	}

}
