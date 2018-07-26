package com.epoch.dataStructures.tries;

public class Trie {

	TrieNode head;
	int size;

	public Trie() {
		head = new TrieNode(null);
		size = 0;
	}

	public TrieNode getHead() {
		return head;
	}

	public void setHead(TrieNode head) {
		this.head = head;
	}

	public int size() {
		return size;
	}



	// add()
	public void add(String s) {
		doAdd(head, s, 0);
		size++;
	}

	private void doAdd(TrieNode node, String s, int atIndex) {
		String c = String.valueOf(s.charAt(atIndex));
		if(atIndex == s.length()-1) {
			node.children().putIfAbsent(c, new TrieNode(s, node));
		} else if(node.has(c)) {
			doAdd(node.getChild(c), s, atIndex+1);
		} else {
			node.addChild(c);
			doAdd(node.getChild(c), s, atIndex+1);
		}
	}

	// get()
	public String get(String s) {
		TrieNode current = head;
		for(int i=0; i<s.length(); i++) {
			String c = String.valueOf(s.charAt(i));
			if(!current.has(c)) {
				return null;
			}
			current = current.getChild(c);
		}
		return current.getValue();
	}

	// contains()
	public boolean contains(String s) {
		TrieNode current = head;
		for(int i=0; i<s.length(); i++) {
			String c = String.valueOf(s.charAt(i));
			if(!current.has(c)) {
				return false;
			}
			current = current.getChild(c);
		}
		if(current.getValue().equals(s)) {
			return true;
		} else {
			return false;
		}
	}

	// remove()
	public void remove(String s) {
		TrieNode current = head;
		TrieNode parent = head;
		String c = null;
		for(int i=0; i<s.length(); i++) {
			c = String.valueOf(s.charAt(i));
			if(!current.has(c)) {
				return;
			}
			parent = current;
			current = current.getChild(c);
		}
		current.setValue(null);
		if(!current.hasValidChildren()) {
			parent.children().remove(c);
			parent.poof();
		}
		size--;
	}
}
