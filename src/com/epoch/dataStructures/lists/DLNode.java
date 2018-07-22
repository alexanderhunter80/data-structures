package com.epoch.dataStructures.lists;

public class DLNode {
	
	private Object value;
	private DLNode next;
	private DLNode prev;
	
	public DLNode(Object val) {
		value = val;
		next = null;
		prev = null;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public DLNode getNext() {
		return next;
	}

	public void setNext(DLNode next) {
		this.next = next;
	}

	public DLNode getPrev() {
		return prev;
	}

	public void setPrev(DLNode prev) {
		this.prev = prev;
	}
	
	

}
