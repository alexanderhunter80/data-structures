package com.epoch.dataStructures.controls;

public class ControlSLNode {
	
	// attributes
	
	private Object value;
	private ControlSLNode next;
	
	// constructors
	
	public ControlSLNode(Object value) {
		this.value = value;
		this.next = null;
	}
	
	// get & set

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public ControlSLNode getNext() {
		return next;
	}

	public void setNext(ControlSLNode next) {
		this.next = next;
	}

}
