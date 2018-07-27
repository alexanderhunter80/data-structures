package com.epoch.dataStructures;

import java.util.ArrayList;
import java.util.Random;

import com.epoch.dataStructures.heaps.FibHeapNode;
import com.epoch.dataStructures.heaps.FibonacciHeap;

public class FibonacciHeapTester extends Tester {
	
	public FibonacciHeapTester(int passes, int stringLength) {
		super(passes, stringLength);
	}
	
	public void run() {
		Benchmarker benchmarker = new Benchmarker();
		
		Benchmarker.printSeparator("Fibonacci Heap");
		System.out.println();
		
		benchmarker.start();
		
		FibonacciHeap<String> heap = new FibonacciHeap<String>();
		
		benchmarker.stop("Fibonacci Heap: .init()");
		
		String[] strings = new String[passes];
		for(int i = 0; i < passes; i++) {
			strings[i] = testStrings.get(i);
		}
		
		benchmarker.start();
		
		for (int i = 0; i < passes; i++)
			heap.insert(i, strings[i]);
		
		benchmarker.stop("Fibonacci Heap: .insert()");
		
		benchmarker.start();
		
		for (int i = 0; i < passes; i++)
			heap.contains(i);
		
		benchmarker.stop("Fibonacci Heap: .contains()");
		
		benchmarker.start();
		
		for (int i = 0; i < passes; i++) {
			heap.get();
		}
		
		benchmarker.stop("Fibonacci Heap: .get()");
		
		ArrayList<FibHeapNode<String>> nodes = new ArrayList<>();
		for (int i = 0; i < passes; i++) {
			FibHeapNode<String> x = new FibHeapNode<>(i, strings[i]);
			nodes.add(x);
			heap.insert(x);
		}
		
		benchmarker.start();
		
		for (int i = 0; i < passes; i++)
			heap.remove(nodes.get(i));
		
		benchmarker.stop("Fibonacci Heap: .remove()");
	}
	
}
