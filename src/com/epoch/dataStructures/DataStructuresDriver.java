package com.epoch.dataStructures;

public class DataStructuresDriver {

	public static void main(String[] args) {
		
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
		
		// wrapup
		
		tester.wrapup();
		


	}

}
