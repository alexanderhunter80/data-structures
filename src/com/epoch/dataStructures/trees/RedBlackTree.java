package com.epoch.dataStructures.trees;

// adapted from code by Jatin Thakur on codebytes.in

public class RedBlackTree {

	private final int RED = 0;
	private final int BLACK = 1;

	private final RBNode nil = new RBNode(-1, null);

	private RBNode head = nil;
	private int size = 0;

	private class RBNode {

		public int number;
		public Object value;
		public int color = BLACK;
		public RBNode left = nil;
		public RBNode right = nil;
		public RBNode parent = nil;

		public RBNode (int number, Object value) {
			this.number = number;
			this.value = value;
		}

	}

	public RedBlackTree() {}

	public RBNode getHead() {
		return head;
	}

	public int size() {
		return size;
	}

	private void rotateRight(RBNode node) {
		if (node.parent != nil) {
			if (node == node.parent.left) {
				node.parent.left = node.left;
			} else {
				node.parent.right = node.left;
			}

			node.left.parent = node.parent;
			node.parent = node.left;
			if (node.left.right != nil) {
				node.left.right.parent = node;
			}
			node.left = node.left.right;
			node.parent.right = node;
		} else {//Need to rotate head
			RBNode left = head.left;
			head.left = head.left.right;
			left.right.parent = head;
			head.parent = left;
			left.right = head;
			left.parent = nil;
			head = left;
		}
	}

	private void rotateLeft(RBNode node) {
		if (node.parent != nil) {
			if (node == node.parent.left) {
				node.parent.left = node.right;
			} else {
				node.parent.right = node.right;
			}
			node.right.parent = node.parent;
			node.parent = node.right;
			if (node.right.left != nil) {
				node.right.left.parent = node;
			}
			node.right = node.right.left;
			node.parent.left = node;
		} else {//Need to rotate head
			RBNode right = head.right;
			head.right = right.left;
			right.left.parent = head;
			head.parent = right;
			right.left = head;
			right.parent = nil;
			head = right;
		}

	}

	private void fixTree(RBNode node) {
		// we know node is red, so while its parent is also red, we need to recolour and/or rotate
		while(node.parent.color == RED) {
			RBNode uncle = nil;
			// if node's parent is a left child
			if(node.parent == node.parent.parent.left) {
				uncle = node.parent.parent.right;
				// if node's parent & uncle are both red, recolor them black and their parent red, then skip to next loop iteration
				if(uncle != nil && uncle.color == RED) {
					node.parent.color = BLACK;
					uncle.color = BLACK;
					node.parent.parent.color = RED;
					node = node.parent.parent;
					continue;
				}
				// if our new node is towards the middle of the tree (right child of left parent), double-rotate
				if(node == node.parent.right) {
					node = node.parent;
					rotateLeft(node);
				}
				// once here, recolor parent&grandparent and then rotate grandparent
				node.parent.color = BLACK;
				node.parent.parent.color = RED;
				rotateRight(node.parent.parent);
			} else {
				// node's parent is a right child
				uncle = node.parent.parent.left;
				// if node's parent & uncle are both red, recolor them black and their parent red, then skip to next loop iteration
				if(uncle != nil && uncle.color == RED) {
					node.parent.color = BLACK;
					uncle.color = BLACK;
					node.parent.parent.color = RED;
					node = node.parent.parent;
					continue;
				}
				// if our new node is towards the middle of the tree (left child of right parent), double-rotate
				if(node == node.parent.left) {
					node = node.parent;
					rotateRight(node);
				}
				// once here, recolor parent&grandparent and then rotate grandparent
				node.parent.color = BLACK;
				node.parent.parent.color = RED;
				rotateLeft(node.parent.parent);
			}
		}
		head.color = BLACK;
	}



	// add()
	public void add(int n, Object o) {
		RBNode newNode = new RBNode(n, o);
		RBNode temp = head;
		// if tree is empty
		if(head == nil) {
			head = newNode;
			head.color = BLACK;
			head.parent = nil;
		} else {
			// color this node red and traverse until we find place for it
			newNode.color = RED;
			while(true) {
				if(n < temp.number) {
					// look left then add or move
					if(temp.left == nil) {
						temp.left = newNode;
						newNode.parent = temp;
						break;
					} else {
						temp = temp.left;
					}
				} else if(n > temp.number) {
					// look right then add or move
					if(temp.right == nil) {
						temp.right = newNode;
						newNode.parent = temp;
						break;
					} else {
						temp = temp.right;
					}
				}
			}
			fixTree(newNode);
		}
		size++;
	}

	// get()
	public Object get(int n) {
		RBNode got = doGet(head, n);
		return got.value;
	}

	private RBNode doGet(RBNode node, int n) {
		if(node.number == n) {
			return node;
		}
		if(node.left != null) {
			RBNode l = doGet(node.left, n);
			if(l != null) {return l;}	
		}
		if(node.right != null) {
			RBNode r = doGet(node.right, n);
			if(r != null) {return r;}
		}
		return null;
	}

	// contains()
	public boolean contains(Object o) {
		return doContains(head, o);
	}
	
	private boolean doContains(RBNode node, Object o) {
		if(node == nil) {
			return false;
		}
		if(node.value.equals(o)) {
			return true;
		}
		boolean l = false;
		boolean r = false;
		if(node.left != null) {
			l = doContains(node.left, o);
		}
		if(node.right != null) {
			r = doContains(node.right, o);
		}
		return (l || r);
	}

	// remove()
	public boolean remove(RBNode z){
		if((z = findNode(z.number, head))==nil) {return false;}
		RBNode x;
		RBNode y = z;
		int y_original_color = y.color;

		if(z.left == nil){
			x = z.right;  
			transplant(z, z.right);  
		}else if(z.right == nil){
			x = z.left;
			transplant(z, z.left); 
		}else{
			y = treeMinimum(z.right);
			y_original_color = y.color;
			x = y.right;
			if(y.parent == z)
				x.parent = y;
			else{
				transplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			transplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color; 
		}
		if(y_original_color==BLACK)
			repair(x);
		size--;
		return true;
	}

	// transplant code does not automatically reassign child pointers
	private void transplant(RBNode target, RBNode with){ 
		if(target.parent == nil){
			head = with;
		}else if(target == target.parent.left){
			target.parent.left = with;
		}else
			target.parent.right = with;
		with.parent = target.parent;
	}

	public RBNode findNode(int findNode, RBNode node) {   // changed from node as arg, to number as arg
		if (head == nil) {
			return null;
		}

		if (findNode < node.number) {
			if (node.left != nil) {
				return findNode(findNode, node.left);
			}
		} else if (findNode > node.number) {
			if (node.right != nil) {
				return findNode(findNode, node.right);
			}
		} else if (findNode == node.number) {
			return node;
		}
		return null;
	}

	RBNode treeMinimum(RBNode subTreeRoot){
		while(subTreeRoot.left!=nil){
			subTreeRoot = subTreeRoot.left;
		}
		return subTreeRoot;
	}

	private void repair(RBNode x){
		while(x!=head && x.color == BLACK){ 
			if(x == x.parent.left){
				RBNode w = x.parent.right;
				if(w.color == RED){
					w.color = BLACK;
					x.parent.color = RED;
					rotateLeft(x.parent);
					w = x.parent.right;
				}
				if(w.left.color == BLACK && w.right.color == BLACK){
					w.color = RED;
					x = x.parent;
					continue;
				}
				else if(w.right.color == BLACK){
					w.left.color = BLACK;
					w.color = RED;
					rotateRight(w);
					w = x.parent.right;
				}
				if(w.right.color == RED){
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.right.color = BLACK;
					rotateLeft(x.parent);
					x = head;
				}
			}else{
				RBNode w = x.parent.left;
				if(w.color == RED){
					w.color = BLACK;
					x.parent.color = RED;
					rotateRight(x.parent);
					w = x.parent.left;
				}
				if(w.right.color == BLACK && w.left.color == BLACK){
					w.color = RED;
					x = x.parent;
					continue;
				}
				else if(w.left.color == BLACK){
					w.right.color = BLACK;
					w.color = RED;
					rotateLeft(w);
					w = x.parent.left;
				}
				if(w.left.color == RED){
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.left.color = BLACK;
					rotateRight(x.parent);
					x = head;
				}
			}
		}
		x.color = BLACK; 
	}
	
	public int countNodes(RBNode node) {
		if (node == nil) {
			return 0;
		}
		int l = countNodes(node.left);
		int r = countNodes(node.right);
		return 1 + l + r;
	}

}
