package com.epoch.dataStructures.heaps;

public class MaxHeap {
	protected static int EMPTY_INDEX = 0;
	protected static int ROOT_INDEX = 1;
	
	protected HeapEntry[] data;
	protected int size;
	
	public MaxHeap(int maxSize) {
		data = new HeapEntry[maxSize];
		data[EMPTY_INDEX] = HeapEntry.NIL;
		size = 0;
	}
	
	public int getSize() {
		return size;
	}
	
	public Object access() {
		return data[ROOT_INDEX].object;
	}
	
	public void insert(HeapEntry entry) {
		data[size + ROOT_INDEX] = entry;
		size ++;
		siftUp(size);
	}
	
	public Object remove() {
		Object output = access();
		swap(ROOT_INDEX, TAIL_INDEX());
		siftDown(ROOT_INDEX);
		size --;
		return output;
	}
	
	private void siftUp(int index) {
		int current = index;
		while (isImproper(current) && inBounds(parent(current))) {
			swap(current, parent(current));
			current = parent(current);
		}
	}
	
	private boolean inBounds(int idx) {
		return idx >= ROOT_INDEX && idx < size;
	}
	
	private void swap(int i, int j) {
		HeapEntry temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}
	
	private boolean isImproper(int idx) {
		return data[parent(idx)].value < data[idx].value;
	}
	
	private void siftDown(int index) {
		int current = index;
		while (inBounds(current)) {
			int max_index = current;
			if (inBounds(left(current))) {
				if (data[left(current)].value > data[current].value) {
					max_index = left(current);
				}
			}
			if (inBounds(right(current))) {
				if (data[right(current)].value > data[max_index].value) {
					max_index = right(current);
				}
			}
			swap(current, max_index);
			if (current == max_index) {
				break;
			}else {
				current = max_index;
			}
		}
	}
	
	private int parent(int index) {
		return index / 2;
	}
	
	private int left(int index) {
		return index * 2;
	}
	
	private int right(int index) {
		return index * 2 + 1;
	}
	
	private int TAIL_INDEX() {
		return size;
	}
}
