package com.epoch.dataStructures;

import com.epoch.dataStructures.heaps.*;

public class Playground {
	
	public static void main(String[] args) {
		
		Benchmarker benchmarker = new Benchmarker();
		
		FibonacciHeap<String> heap = new FibonacciHeap<>();
		
		FibHeapNode<String> y = new FibHeapNode<String>(10, "f");
		heap.insert(y);
		heap.insert(15, "e");
		heap.insert(1, "g");
		heap.insert(3, "gf");
		heap.insert(5, "gd");
		heap.insert(15, "f");
		
		print(heap);
		
		heap.decrease(y, -1);
		
		print(heap);
		
		
		
//		FibHeapNode<String> x;
//		while ((x = heap.getNode()) != null) {
//			System.out.println(x.key);
////			print(heap);
//		}
//		
//		print(heap);
//		
//		System.out.println("Min: " + heap.extract_min().key);
//		System.out.println();
//		
//		heap.root.iterate(x -> {
//			System.out.println(x.key);
//		});
////		
//		System.out.println("Min: " + heap.extract_min().key);
//		System.out.println();
//		
//		heap.root.iterate(x -> {
//			System.out.println(x.key);
//		});
//		
//		System.out.println("Min: " + heap.extract_min().key);
//		System.out.println();
//		
//		heap.root.iterate(x -> {
//			System.out.println(x.key);
//		});
		
		//print(heap);
		
	}
	
	public static <T> void print(FibonacciHeap<T> heap) {
		System.out.println("Heap: {");
		heap.root.iterate(x -> {
			System.out.println(" " + x.key);
			if (!x.children.isEmpty()) {
				printList(x.children, 2);
				System.out.println();
			}
		});
		System.out.println("}");
		System.out.println();
	}
	
	public static <T> void printList(FibNodeList<T> list, int depth) {
		String d = "";
		for (int i = 0; i < depth; i++)
			d += " ";
		final String m = d;
		list.iterate(y -> {
			System.out.println(m + y.key);
			if (!y.children.isEmpty())
				printList(y.children, depth + 1);
		});
	}
	
}
