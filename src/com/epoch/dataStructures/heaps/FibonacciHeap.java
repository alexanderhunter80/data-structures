package com.epoch.dataStructures.heaps;

public class FibonacciHeap<T> {
	public FibNodeList<T> root;
	private FibHeapNode<T> min;
	private int size;
	
	public FibonacciHeap() {
		root = new FibNodeList<T>();
		min = null;
		size = 0;
	}
	
	public void insert(FibHeapNode<T> x) {
		x.degree = 0;
		x.parent = null;
		x.children = new FibNodeList<T>();
		x.mark = false;
		if (min == null) {
			min = x;
			root = new FibNodeList<T>();
			root.insert(x);
		} else {
			root.insert(x);
			if (x.key < min.key)
				min = x;
		}
		size++;
	}
	
	public void insert(int key, T val) {
		insert(new FibHeapNode<T>(key, val));
	}
	
	public FibonacciHeap<T> union(FibonacciHeap<T> other) {
		FibonacciHeap<T> merged_heap = new FibonacciHeap<>();
		
		merged_heap.min = min;
		merged_heap.root = root;
		merged_heap.root.union(other.root);
		if (min == null || (other.min != null && other.min.key < min.key)) {
			merged_heap.min = other.min;
		}
		merged_heap.size = size + other.size;
		
		return merged_heap;
	}
	
	public FibHeapNode<T> getNode() {
		FibHeapNode<T> z = min;
		if (z != null) {
			z.children.iterate(current -> {
				root.insert(current);
				current.parent = null;
			});
			root.remove(z);
			if (z == z.right)
				min = null;
			else {
				min = z.right;
				consolidate();
			}
			size--;
		}
		return z;
	}
	
	public T get() {
		return getNode().val;
	}
	
	private void consolidate() {
		FibHeapNode<T>[] A = new FibHeapNode[D(size)];
		for (int i = 0; i < A.length; i++)
			A[i] = null;
		root.iterate(w -> {
			FibHeapNode<T> x = w;
			int d = x.degree;
			while (A[d] != null) {
				FibHeapNode<T> y = A[d];
				if (x.key > y.key) {
					FibHeapNode<T> temp = x;
					x = y;
					y = temp;
				}
				link(y, x);
				A[d] = null;
				d++;
			}
			A[d] = x;
		});
		min = null;
		for (int i = 0; i < D(size); i++) {
			if (A[i] != null) {
				if (min == null) {
					root = new FibNodeList<T>();
					root.insert(A[i]);
					min = A[i];
				} else {
					root.insert(A[i]);
					if (A[i].key < min.key)
						min = A[i];
				}
			}
		}
	}
	
	private void link(FibHeapNode<T> y, FibHeapNode<T> x) {
		root.remove(y);
		x.insert(y);
		y.mark = false;
	}
	
	public void decrease(FibHeapNode<T> x, int k) {
		if (k > x.key)
			return;
		x.key = k;
		FibHeapNode<T> y = x.parent;
		if (y != null && x.key < y.key) {
			cut(x, y);
			cascading_cut(y);
		}
		if (x.key < min.key)
			min = x;
	}
	
	private void cut(FibHeapNode<T> x, FibHeapNode<T> y) {
		y.remove(x);
		root.insert(x);
		x.parent = null;
		x.mark = false;
	}
	
	private void cascading_cut(FibHeapNode<T> y) {
		FibHeapNode<T> z = y.parent;
		if (z != null) {
			if (!y.mark)
				y.mark = true;
			else {
				cut(y, z);
				cascading_cut(z);
			}
		}
	}
	
	public void remove(FibHeapNode<T> x) {
		decrease(x, Integer.MIN_VALUE);
		getNode();
	}
	
	private int D(int x) {
		return size + 1;//(int)(Math.log10((double)x) / Math.log10(2));
	}
}
