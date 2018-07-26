package com.epoch.dataStructures.heaps;

public class PrintableMaxHeap extends MaxHeap {
	
	public PrintableMaxHeap(int maxSize) {
		super(maxSize);
	}
	
	public void print() {
		for (int i = 1; i < size + 1; i++) {
			if (data[i] != null) {
				System.out.println(data[i].value +", "+ data[i].object);
			}
		}
	}
	
	public HeapEntry get() {
		return data[ROOT_INDEX];
	}
	
}
