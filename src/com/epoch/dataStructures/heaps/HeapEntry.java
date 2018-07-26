package com.epoch.dataStructures.heaps;

public class HeapEntry {
	public int value;
	public Object object;
	
	public HeapEntry(int value, Object object) {
		this.value = value;
		this.object = object;
	}
	
	public static HeapEntry NIL = new HeapEntry(Integer.MAX_VALUE, null);
}
