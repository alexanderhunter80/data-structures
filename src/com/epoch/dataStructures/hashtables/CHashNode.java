package com.epoch.dataStructures.hashtables;

import java.util.LinkedList;

public class CHashNode {
	
	public String key;
	public Object value;
	public LinkedList<CHashNode> list;
	public boolean hasList;
	
	public CHashNode(String k, Object v) {
		key = k;
		value = v;
		list = null;
		hasList = false;
	}

}
