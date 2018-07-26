package com.epoch.dataStructures;

import com.epoch.dataStructures.trees.vanEmdeBoas.proto_vanEmdeBoas;

public class Playground {
	
	public static void main(String[] args) {
		
		int size = 2;
		int actual_size = (int)Math.pow(2, Math.pow(2, size));
		proto_vanEmdeBoas p_vEB = new proto_vanEmdeBoas(actual_size);
		
		p_vEB.insert(4);
		p_vEB.insert(9);
		p_vEB.insert(13);
		p_vEB.insert(2);
		p_vEB.insert(5);
		p_vEB.insert(3);
		
		print(p_vEB);
		
	}
	
	public static void print(proto_vanEmdeBoas vEB) {
		int x = vEB.getMinimum();
		while (x != proto_vanEmdeBoas.NIL_VALUE) {
			System.out.println(x);
			x = vEB.getSuccessor(x);
		}
		System.out.println();
	}
	
}
