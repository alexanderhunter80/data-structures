package com.epoch.dataStructures.heaps;

// adapted in part from code by Roman Gonzalez, github: roman

public class MinHeap {	
	
	MinHeapNode[] array;
	int size;
	
	public MinHeap(int maxSize) {
		this.size = 0;
		array = new MinHeapNode[maxSize]; 
	}
	
	public int size() {
		return size;
	}
	
	public MinHeapNode[] getArray() {
		return array;
	}
	
	public int parent(int i) {
		return (i-1)/2;
	}
	
	public int left(int i) {
		return (2*i)+1;
	}
	
	public int right(int i) {
		return (2*i)+2;
	}
	
	private void swap(int a, int b) {
		MinHeapNode temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}
	
	private void bubbleUp(int i) {
		// if at head of heap, bail
		if(i==0) {
			return;
		}
		// else if parent's priority is higher than child, swap and recur; otherwise, bail
		if(array[i].getPriority() < array[parent(i)].getPriority()) {
			swap(i, parent(i));
			bubbleUp(parent(i));
		}
	}
	
	private boolean isLeaf(int i) {
		return (2*i)+2 >= size;
	}
	
	private void sinkDown(int i) {
		// if node is leaf, bail
		if(isLeaf(i)) {
			return;
		}
		// find index of lowest-priority child
		int minIndex;
		if(right(i)>size) {
			minIndex = left(i);
		} else {
			if(array[left(i)].getPriority() < array[right(i)].getPriority()) {
				minIndex = left(i);
			} else {
				minIndex = right(i);
			}
		}
		// if parent is lower priority than least child, bail
		if(array[i].getPriority() < array[minIndex].getPriority()) {
			return;
		}
		// otherwise, swap downwards and recur
		swap(i, minIndex);
		sinkDown(minIndex);
	}
	
	// add()
	public void add(int p, Object o) {
		array[size] = new MinHeapNode(p, o);
		bubbleUp(size);
		size++;
	}
	
	// get() - not a very useful function, but included for comparison - essentially the same as contains()
	public Object get(Object o) {
		return doGet(0, o);
	}
	
	private Object doGet(int i, Object o) {
		if(isLeaf(i)) {return null;}
		if(array[i].getValue().equals(o)) {
			return array[i].getValue();
		}
		Object l = doGet(left(i), o);
		if(l != null) {return l;}
		Object r = doGet(right(i), o);
		if(r != null) {return r;}
		return null;
	}
	
	// contains()
	public boolean contains(Object o) {
		return doContains(0, o);
	}
	
	private boolean doContains(int i, Object o) {
		if(isLeaf(i)) {return false;}
		if(array[i].getValue().equals(o)) {
			return true;
		}
		boolean l = doContains(left(i), o);
		boolean r = doContains(right(i), o);
		return (l || r);
	}
	
	// remove()
	public Object remove() {
		size--;
		Object top = array[0].getValue();
		array[0] = array[size];
		array[size] = null;
		sinkDown(0);
		return top;
	}


}
