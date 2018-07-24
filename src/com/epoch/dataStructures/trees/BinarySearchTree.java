package com.epoch.dataStructures.trees;

public class BinarySearchTree {

	private BSTNode head;
	private int size;

	public BinarySearchTree () {
		head = null;
	}
	
	public BSTNode getHead() {
		return head;
	}

	public int size() {
		return size;
	}

	// add()
	public void add(int i, Object o) {
		size++;
		if(head == null) {
			head = new BSTNode(i, o);
			return;
		}
		doAdd(head, i, o);	
	}

	private void doAdd(BSTNode node, int i, Object o) {
		if(i < node.getNumber()) {
			if(node.getLeft() == null) {
				node.setLeft(new BSTNode(i, o));
			} else {
				doAdd(node.getLeft(), i, o);
			}
		} else {  // if compare == 0 i.e. strings are exact matches, default to right
			if(node.getRight() == null) {
				node.setRight(new BSTNode(i, o));
			} else {
				doAdd(node.getRight(), i, o);
			}
		}
	}

	// get()
	public Object get(int i) {
		BSTNode got = doGet(head, i);
		return got.getValue();
	}

	private BSTNode doGet(BSTNode node, int i) {
		if(node.getNumber() == i) {
			return node;
		}
		if(node.getLeft() != null) {
			BSTNode l = doGet(node.getLeft(), i);
			if(l != null) {return l;}	
		}
		if(node.getRight() != null) {
			BSTNode r = doGet(node.getRight(), i);
			if(r != null) {return r;}
		}
		return null;
	}

	// contains()
	public boolean contains(Object o) {
		return doContains(head, o);
	}
	
	private boolean doContains(BSTNode node, Object o) {
		if(node.getValue().equals(o)) {
			return true;
		}
		boolean l = false;
		boolean r = false;
		if(node.getLeft() != null) {
			l = doContains(node.getLeft(), o);
		}
		if(node.getRight() != null) {
			r = doContains(node.getRight(), o);
		}
		return (l || r);
	}
	
	// contains()
	public int keyOf(Object o) {
		return doKeyOf(head, o);
	}
	
	private int doKeyOf(BSTNode node, Object o) {
		if(node.getValue().equals(o)) {
			return node.getNumber();
		}
		int l = 0;
		int r = 0;
		if(node.getLeft() != null) {
			l = doKeyOf(node.getLeft(), o);
			if(l != -1) {
				return l;
			}
		}
		if(node.getRight() != null) {
			r = doKeyOf(node.getRight(), o);
			if(r != -1) {
				return r;
			}
		}
		return -1;
	}
	
	

	// remove() adapted from code found on algolist.com
    public boolean remove(int i) {
        if (head == null)
              return false;
        else {
              if (head.getNumber() == i) {
                    BSTNode auxRoot = new BSTNode(i, null);
                    auxRoot.setLeft(head);
                    boolean result = head.remove(i, auxRoot);
                    head = auxRoot.getLeft();
                    if(result == true) {size--;}
                    return result;
              } else {
                    boolean removed = head.remove(i, null);
                    if(removed == true) {size--;}
                    return removed;
              }
        }
        
  }

}
