package com.epoch.dataStructures;

import com.epoch.dataStructures.trees.vanEmdeBoas.vanEmdeBoas;

public class Playground {
	
	public static void main(String[] args) {
		
		Benchmarker benchmarker = new Benchmarker();
		
		int size = 10;
		int actual_size = (int)Math.pow(2, size);
		
		benchmarker.start();
		
		vanEmdeBoas vEB = new vanEmdeBoas(actual_size);
		
		benchmarker.stop("Init");
		
		benchmarker.start();
		
		vEB.insert(4);
		
		benchmarker.stop("Add");
		
		print(vEB);
		
		System.out.println(vEB.contains(9));
		
		System.out.println(vEB.getMinimum());
		System.out.println(vEB.getMaximum());
		
		vEB.insert(9);
		vEB.insert(40);
		vEB.insert(999);
		
		print(vEB);
		
		System.out.println(vEB.contains(9));
		
		
	}
	
	public static void print(vanEmdeBoas vEB) {
		int x = vEB.getMinimum();
		while (x != vanEmdeBoas.NIL) {
			System.out.println(x);
			x = vEB.getSuccessor(x);
		}
		System.out.println();
	}
	
}
