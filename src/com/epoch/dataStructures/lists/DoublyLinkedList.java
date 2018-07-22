package com.epoch.dataStructures.lists;

public class DoublyLinkedList {

	private DLNode head;
	private DLNode tail;
	private int size;

	public DoublyLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}

	public DLNode getHead() {
		return head;
	}

	public void setHead(DLNode head) {
		this.head = head;
	}

	public DLNode getTail() {
		return tail;
	}

	public void setTail(DLNode tail) {
		this.tail = tail;
	}

	public int size() {
		return size;
	}

	//add()
	public void add(Object o) {
		if(head==null) {
			head = new DLNode(o);
			tail = head;
		} else {
			tail.setNext(new DLNode(o));
			tail = tail.getNext();
		}
		size++;
		return;
	}

	//get() by index
	public Object get(int idx) throws IndexOutOfBoundsException {
		if(idx >= size || idx < 0) {
			throw new IndexOutOfBoundsException();
		}
		if(idx == 0) {
			return head.getValue();
		}
		if(idx==size) {
			return tail.getValue();
		}
		DLNode current;
		if(idx < size/2) {
			// target in first half of list, traverse from head
			current = head;
			for(int i=0; i<idx; i++) {
				current = current.getNext();
			}
		} else {
			// target in second half of list, traverse from tail
			current = tail;
			for(int i=size-1; i>idx; i--) {
				current = current.getPrev();
			}
		}
		return current.getValue();
	}

	//contains()
	public boolean contains(Object o) {
		// fast fails
		if(head == null) {
			return false;
		}
		if(head.getValue().equals(o) || tail.getValue().equals(o)) {
			return true;
		}
		// traverse
		DLNode current = head;
		while(current.getNext() != null) {
			current = current.getNext();
			if(current.getValue().equals(o)) {
				return true;
			}
		}
		return false;
	}

	//indexOf()

	//remove() by index
	public void remove(int idx) {
		if(idx >= size || idx < 0) {
			throw new IndexOutOfBoundsException();
		}
		DLNode current;
		if(idx == 0) {
			head = head.getNext();
			return;
		} else if(idx == size) {
			tail = tail.getPrev();
			return;
		} else if(idx < size/2) {
			// target in first half of list, traverse from head
			current = head;
			for(int i=0; i<idx; i++) {
				current = current.getNext();
			}
		} else {
			// target in second half of list, traverse from tail
			current = tail;
			for(int i=size-1; i>idx; i--) {
				current = current.getPrev();
			}
		}
		current.getPrev().setNext(current.getNext());
		current.getNext().setPrev(current.getPrev());
		return;
	}

	//findAndRemove() calls indexof() then remove()



}
