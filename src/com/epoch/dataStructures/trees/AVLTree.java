package com.epoch.dataStructures.trees;

// adapted from code by Mayank Jaiswal found on geeksforgeeks.com, and from code by StackOverflow user "Trying"

public class AVLTree {

	private AVLNode head;
	private int size;

	public AVLTree() {
		head = null;
		size = 0;
	}

	public int size() {
		return size;
	}

	public AVLNode getHead() {
		return head;
	}

	public int height(AVLNode node) {
		if(node == null) {
			return 0;
		}
		return node.height;
	}

	public int max(int a, int b) {
		if(a>b) {return a;}
		else {return b;}
	}

	public int balance(AVLNode node) {
		if(node == null) {
			return 0;
		}
		return height(node.left) - height(node.right);
	}
	
	public AVLNode minMember(AVLNode node) {
		AVLNode current = node;
		while(current.left != null) {
			current = current.left;
		}
		return current;
	}

	public AVLNode rotateRight(AVLNode node) {
		AVLNode piv = node.left;
		AVLNode subtree = piv.right;
		piv.right = node;
		node.left = subtree;

		// update height information
		node.height = max(height(node.left), height(node.right)) + 1;
		piv.height = max(height(piv.left), height(piv.right)) + 1;

		return piv;	
	}

	public AVLNode rotateLeft(AVLNode node) {
		AVLNode piv = node.right;
		AVLNode subtree = piv.left;
		piv.left = node;
		node.right = subtree;

		// update height info
		node.height = max(height(node.left), height(node.right)) + 1;
		piv.height = max(height(piv.left), height(piv.right)) + 1;
		return piv;
	}



	// add()
	public void add(int n, Object o) {
		size++;
		head = doAdd(head, n, o); 
	}

	public AVLNode doAdd(AVLNode node, int n, Object o) {
		// add new node
		if(node == null) {
			node = new AVLNode(n, o);
			return node;
		} else {
			if(n < node.number) {
				node.left = doAdd(node.left, n, o);
			} else {
				node.right = doAdd(node.right, n, o);
			}
		}

		// height update
		node.height = max(height(node.left), height(node.right))+1;

		// check balance and rotate
		int bal = balance(node);
		if(bal < -1) {
			if(balance(node.right) > 0) {
				node.right = rotateRight(node.right);
				return rotateLeft(node);
			} else {
				return rotateLeft(node);
			}
		} else if (bal > 1) {
			if(balance(node.left) < 0) {
				node.left = rotateLeft(node.left);
				return rotateRight(node);
			} else {
				return rotateRight(node);
			}
		}
		return node;
	}

	// get()
	public Object get(int n) {
		AVLNode got = doGet(head, n);
		return got.value;
	}

	private AVLNode doGet(AVLNode node, int n) {
		if(node.number == n) {
			return node;
		}
		if(node.left != null) {
			AVLNode l = doGet(node.left, n);
			if(l != null) {return l;}	
		}
		if(node.right != null) {
			AVLNode r = doGet(node.right, n);
			if(r != null) {return r;}
		}
		return null;
	}

	// contains()
	public boolean contains(Object o) {
		return doContains(head, o);
	}
	
	private boolean doContains(AVLNode node, Object o) {
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
	public void remove(int n) {
		head = doRemove(head, n);
		size--;
	}
	
	public AVLNode doRemove(AVLNode node, int n) {
		// if this node is null, bail
		if(node == null) {
			return node;
		}
		// if this is not the node to remove, move towards that node
		if(n < node.number) {
			node.left = doRemove(node.left, n);
		} else if(n > node.number) {
			node.right = doRemove(node.right, n);
		} else {
			// this is the node to remove!
			// if both children exist, swap with closest-value number and remove that node 
			if(node.left != null && node.right != null) {
				AVLNode temp = minMember(node.right);  // vvvvv this doesn't look right vvvvv
				node.number = temp.number;
				node.value = temp.value;
				node.right = doRemove(node.right, temp.number);
			} else {
				// only one child exists, move that child into this node's place
				if(node.left != null) {
					node = node.left;
				} else {
					node = node.right;
				}
			}
		}
		return node;
	}



	public void preOrder(AVLNode node) {
		if (node != null) {
			System.out.print(node.number + ", ");
			preOrder(node.left);
			preOrder(node.right);
		}
	}

	public int countNodes(AVLNode node) {
		if (node == null) {
			return 0;
		}
		int l = countNodes(node.left);
		int r = countNodes(node.right);
		return 1 + l + r;
	}

}
