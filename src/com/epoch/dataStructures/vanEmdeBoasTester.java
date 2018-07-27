package com.epoch.dataStructures;

import java.util.ArrayList;

import com.epoch.dataStructures.heaps.FibHeapNode;
import com.epoch.dataStructures.heaps.FibonacciHeap;
import com.epoch.dataStructures.trees.vanEmdeBoas.vanEmdeBoas;

public class vanEmdeBoasTester extends Tester {

	public vanEmdeBoasTester(int passes, int stringLength) {
		super(passes, stringLength);
	}

	public void run() {
		Benchmarker benchmarker = new Benchmarker();
		
		Benchmarker.printSeparator("van Emde Boas tree");
		System.out.println();
		
		benchmarker.start();
		
		vanEmdeBoas vEB = new vanEmdeBoas((int)Math.pow(2, 14));
		
		benchmarker.stop("van Emde Boas tree: .init()");
		
		String[] strings = new String[passes];
		for(int i = 0; i < passes; i++) {
			strings[i] = testStrings.get(i);
		}
		
		benchmarker.start();
		
		for (int i = 0; i < passes; i++)
			vEB.insert(i);
		
		benchmarker.stop("van Emde Boas tree: .insert()");
		
		benchmarker.start();
		
		for (int i = 0; i < passes; i++)
			vEB.insert(i);
		
		benchmarker.stop("van Emde Boas tree: .contains()");
		
		benchmarker.start();
		
		for (int i = 0; i < passes; i++)
			vEB.remove(i);
		
		benchmarker.stop("Fibonacci Heap: .remove()");
	}
	
}
