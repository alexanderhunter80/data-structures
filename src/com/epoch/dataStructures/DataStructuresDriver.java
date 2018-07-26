package com.epoch.dataStructures;

import com.epoch.dataStructures.trees.RedBlackTreeTester;

public class DataStructuresDriver {

	public static void main(String[] args) {
		
		System.out.println("Starting run in DataStructuresDriver");
		
//		DataStructuresTester tester = new DataStructuresTester(10000,3);
		
		
		// controls - Array and SLL
		
//		tester.testControlArray();
//		tester.testControlSinglyLinkedList();
		
		// lists
	
//		tester.testDoublyLinkedList();
		
		// hashtables

//		tester.testChainingHashtable();
//		tester.testLinearOpenAddressingHashtable();
//		tester.testQuadraticOpenAddressingHashtable();
//		tester.testPeriodicHashtable();
		
		// heaps
		
//		tester.testMinHeap();
		
		// trees
		
		//tester.testBinarySearchTree();
//		tester.test_proto_vanEmdeBoas();
//		tester.testTrie();
//		tester.testAVLTree();
		
		// wrapup
		
//		tester.wrapup();
		
		RedBlackTreeTester rbtest = new RedBlackTreeTester(10000,3);
		
		rbtest.testRedBlackTree();


	}

}
