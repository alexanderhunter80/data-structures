package com.epoch.dataStructures.trees;

public class BSTNode {

	private int number;
	private Object value;
	private BSTNode left;
	private BSTNode right;

	public BSTNode(int i, Object o) {
		value = o;
		number = i;
		left = null;
		right = null;
	}



	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public BSTNode getLeft() {
		return left;
	}

	public void setLeft(BSTNode left) {
		this.left = left;
	}

	public BSTNode getRight() {
		return right;
	}

	public void setRight(BSTNode right) {
		this.right = right;
	}
	
	// remove() and minNumber() adapted from code found on algolist.com

    public boolean remove(int i, BSTNode parent) {
        if (i < number) {
              if (left != null)
                    return left.remove(i, this);
              else
                    return false;
        } else if (i > number) {
              if (right != null)
                    return right.remove(i, this);
              else
                    return false;
        } else {
              if (left != null && right != null) {
                    BSTNode nn = right.minNode();
                    this.setNumber(nn.getNumber());
                    this.setValue(nn.getValue());
                    this.setLeft(nn.getLeft());
                    this.setRight(nn.getRight());
                    right.remove(i, this);
              } else if (parent.getLeft() == this) {
                    parent.setLeft((left != null) ? left : right);
              } else if (parent.getRight() == this) {
                    parent.setRight((left != null) ? left : right);
              }
              return true;
        }
  }



  public BSTNode minNode() {
        if (left == null)
              return this;
        else
              return left.minNode();
  }

}
