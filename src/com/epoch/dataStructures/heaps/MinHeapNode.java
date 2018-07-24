package com.epoch.dataStructures.heaps;

public class MinHeapNode {
	
	int priority;
	Object value;
	
	public MinHeapNode(int p, Object o) {
		priority = p;
		value = o;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
