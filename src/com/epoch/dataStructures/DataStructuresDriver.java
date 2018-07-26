package com.epoch.dataStructures;

import com.epoch.dataStructures.DataStructuresTester;
import com.epoch.dataStructures.heaps.*;

public class DataStructuresDriver {

	public static void main(String[] args) {
		
		System.out.println("Starting run in DataStructuresDriver");
		
		DataStructuresTester tester = new DataStructuresTester(10000,4);
		
		
		// controls - Array and SLL
		
		tester.testControlArray();
		tester.testControlSinglyLinkedList();
		
		// lists
	
		tester.testDoublyLinkedList();
		
		// hashtables

		tester.testLinearOpenAddressingHashtable();
		tester.testQuadraticOpenAddressingHashtable();
		tester.testPeriodicHashtable();
		
		// heaps
		
		tester.testMinHeap();
		
		// trees
		
		//tester.testBinarySearchTree();
		tester.test_proto_vanEmdeBoas();
		
		// wrapup
		
		tester.wrapup();


	}

}
