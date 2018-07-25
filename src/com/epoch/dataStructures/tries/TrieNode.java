package com.epoch.dataStructures.tries;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class TrieNode {

	TrieNode parent;
	Map<String, TrieNode> children;
	String value;

	public TrieNode(TrieNode parent) {
		value = null;
		this.parent = parent;
		children = new HashMap<String, TrieNode>();
	}

	public TrieNode(String value, TrieNode parent) {
		this.value = value;
		this.parent = parent;
		this.children = new HashMap<String, TrieNode>();
	}

	public Map<String, TrieNode> children() {
		return children;
	}

	public void addChild(String key) {
		children.put(key, new TrieNode(this));
	}

	public TrieNode getChild(String key) {
		return children.get(key);
	}

	public boolean has(String key) {
		return children.containsKey(key);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean hasValidChildren(){
		if(value != null) {
			return true;
		}
		for(String c : children.keySet()) {
			if(children.get(c).hasValidChildren()) {
				return true;
			}
		}

		return false;
	}

	public int countNodes() {
		if(children.isEmpty()) {
			//			if(value != null) {
			return 1;
			//			}
		}
		int all = 1;
		for(String c : children.keySet()) {
			all += children.get(c).countNodes();
		}
		return all;
	}

	public void poof() {
		if(!this.hasValidChildren()) {
			if(parent != null) {
				for (Entry<String, TrieNode> entry : parent.children().entrySet()) {
					if (Objects.equals(this, entry.getValue())) {
//						System.out.println("POOF");
						parent.children().remove(entry.getKey());
						parent.poof();
						break;
					}
				}
			}
		}
	}




}
