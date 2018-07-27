package com.epoch.dataStructures.heaps;

import java.util.function.Consumer;
import java.util.function.Function;

public class FibNodeList<T> {
	public FibHeapNode<T> head, tail;
	
	public FibNodeList() {
		head = tail = null;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public void insert(FibHeapNode<T> x) {
		if (tail == null) {
			head = tail = x;
			head.left = tail;
			head.right = tail;
			tail.left = head;
			tail.right = head;
		} else {
			x.left = tail;
			x.right = head;
			tail.right = x;
			head.left = x;
			tail = x;
		}
	}
	
	public void remove(FibHeapNode<T> x) {
		if (x == head && x == tail) {
			head = tail = null;
		} else {
			if (x == head) {
				head = x.right;
			} else if (x == tail) {
				tail = x.left;
			}
			x.left.right = x.right;
			x.right.left = x.left;
		}
	}
	
	public void union(FibNodeList<T> other) {
		if (tail == null) {
			head = other.head;
			tail = other.tail;
		} else {
			tail.right = other.head;
			other.head.left = tail;
			other.tail.right = head;
			head.left = other.tail;
			tail = other.tail;
		}
	}
	
	public void iterate(Consumer<FibHeapNode<T>> action) {
		FibHeapNode<T> current = head;
		while (current != tail) {
			FibHeapNode<T> next = current.right;
			action.accept(current);
			current = next;
		}
		if (current != null)
			action.accept(current);
	}
}
