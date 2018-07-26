package com.epoch.dataStructures.trees;

public class AVLNode {
	
	public int number;
	public Object value;
	public int height;
	public AVLNode left;
	public AVLNode right;
	
	public AVLNode(int number, Object value) {
		this.number = number;
		this.value = value;
		height = 1;
		left = null;
		right = null;
	}
	

}
