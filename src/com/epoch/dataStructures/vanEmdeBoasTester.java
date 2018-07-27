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
		vanEmdeBoas vEB = null;
		
		Benchmarker.printSeparator("van Emde Boas tree");
		System.out.println();
		try {
		benchmarker.initSize();
		
		benchmarker.start();
		
		vEB = new vanEmdeBoas((int)Math.pow(2, 16));
		
		benchmarker.stop("van Emde Boas tree: .init()");
		
		String[] strings = new String[passes];
		for(int i = 0; i < passes; i++) {
			strings[i] = testStrings.get(i);
		}
		

		benchmarker.start();
		
		for (int i = 0; i < passes; i++)
			vEB.insert(i);
		
		benchmarker.stop("van Emde Boas tree: .insert()");
		benchmarker.calculateSize();
		} catch (Exception e) {}
		
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
