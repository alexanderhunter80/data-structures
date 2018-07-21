package com.epoch.dataStructures.controls;

import com.epoch.dataStructures.Testable;

public class ControlArray implements Testable {
	
	// attributes
	
	private Object[] array;
	private int next;
	
	// constructors
	
	public ControlArray(int size) {
		array = new Object[size];
		next = 0;
	}
	
	// get & set
	
	public Object[] getArray() {
		return array;
	}
	
	public Object get(int idx) {
		return array[idx];
	}
	
	public int size() {
		return next;
	}
	
	// other methods
	
	public void add(Object o) throws ArrayIndexOutOfBoundsException {
		if(next > array.length || next < 0) {
			throw new ArrayIndexOutOfBoundsException();
		}
		array[next++] = o;
	}
	
	public boolean contains(Object o) {
		for(Object k : array) {
			if(o.equals(k)) {
				return true;
			}
		}
		return false;
	}
	
	public int indexOf(Object o) {
		for(int i = 0; i < array.length; i++) {
			if(o.equals(array[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public void remove(int idx) {
		int i;
		for(i = idx; i < next; i++) {
			array[i] = array[i+1];
		}
		array[i] = null;
		next--;
	}
	
	public boolean findAndRemove(Object o) {
		int at = indexOf(o);
		if(at>=0) {
			remove(at);
			return true;
		} else {
			return false;
		}
	}

}
