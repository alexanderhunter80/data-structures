package com.epoch.dataStructures.heaps;

public class FibHeapNode<T> {
	public T val;
	public int key, degree;
	public FibHeapNode<T> left, right, parent;
	public FibNodeList<T> children;
	public boolean mark;
	
	public FibHeapNode(int key, T val) {
		this.key = key;
		this.val = val;
		mark = false;
		children = new FibNodeList<T>();
	}
	
	public void insert(FibHeapNode<T> x) {
		children.insert(x);
		x.parent = this;
		degree++;
	}
	
	public void remove(FibHeapNode<T> x) {
		children.remove(x);
		x.parent = null;
		degree--;
		mark = true;
	}
}
