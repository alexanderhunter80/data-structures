package com.epoch.dataStructures.controls;

import com.epoch.dataStructures.Testable;

public class ControlSinglyLinkedList implements Testable {

	// attributes

	private ControlSLNode head;
	private int size;

	// constructors

	public ControlSinglyLinkedList() {
		head = null;
		size = 0;
	}

	// get & set

	public ControlSLNode getHead() {
		return head;
	}

	public int size() {
		return size;
	}

	// other methods

	public void add(Object o) {
		if(head == null) {
			head = new ControlSLNode(o);
			size++;
		} else {
			ControlSLNode current = head;
			while(current.getNext() != null) {
				current = current.getNext();
			}
			current.setNext(new ControlSLNode(o));
			size++;
		}
	}

	public Object get(int idx) throws IndexOutOfBoundsException {
		if(idx >= size || idx < 0) {
			throw new IndexOutOfBoundsException();
		}
		if(idx == 0) {
			return head.getValue();
		}
		ControlSLNode current = head;
		for(int i = 0; i < idx; i++) {
			current = current.getNext();
		}
		return current.getValue();
	}

	public ControlSLNode getNode(int idx) throws IndexOutOfBoundsException {
		if(idx >= size || idx < 0) {
			throw new IndexOutOfBoundsException();
		}
		if(idx == 0) {
			return head;
		}
		ControlSLNode current = head;
		for(int i = 0; i < idx; i++) {
			current = current.getNext();
		}
		return current;
	}

	public boolean contains(Object o) {
		if(head == null) {
			return false;
		}
		if(head.getValue().equals(o)) {
			return true;
		}
		ControlSLNode current = head;
		while(current.getNext() != null) {
			current = current.getNext();
			if(current.getValue().equals(o)) {
				return true;
			}
		}
		return false;
	}

	public int indexOf(Object o) {
		if(head == null) {
			return -1;
		}
		ControlSLNode current = head;
		for(int i=0; i < size; i++) {
			if(current.getValue().equals(o)) {
				return i;
			}
			current = current.getNext();
		}
		return -1;
	}
	
	public void remove(int idx) throws IndexOutOfBoundsException {
		if(idx >= size || idx < 0) {
			throw new IndexOutOfBoundsException();
		}
		if(idx == 0) {
			head = head.getNext();
		} else {
			ControlSLNode current = head;
			for(int i = 0; i < idx-1; i++) {
				current = current.getNext();
			}
			current.setNext(current.getNext().getNext());
		}
	}
	
	public boolean findAndRemove(Object o) {
		int at = indexOf(o);
		if(at >= 0) {
			remove(at);
			return true;
		} else {
			return false;
		}
	}

}
