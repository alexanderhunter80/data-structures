package com.epoch.dataStructures.hashtables;

import java.util.LinkedList;

public class ChainingHashtable {
	
	private int size;
	private CHashNode[] table;
	private int load;
	private double threshold;
	public int chains;
	
	public ChainingHashtable(int size, double threshold) {
		this.size = size;
		table = new CHashNode[size];
		load = 0;
		chains = 0;
		this.threshold = threshold;
	}
	
	// for testing purposes only
	public CHashNode[] getEntireTable() {
		return table;
	}
	
	// for testing purposes only
	public int getSize() {
		return size;
	}
	
	// for testing purposes only
	public int getLoad() {
		return load;
	}
	
	private void resize() {
		// resize should callout when it is called, just to see how often it happens
		System.out.println("ChainingHashtable is resizing from "+size+" to "+(size*2));

		size *= 2;
		CHashNode[] oldTable = table;
		table = new CHashNode[size];
		for(CHashNode o : oldTable) {
			if(o != null) {
				if(!o.hasList) {
					doAdd(o.key, o.value);
				} else if (o.hasList) {
					for(CHashNode node : o.list) {
						doAdd(node.key, node.value);
					}
				}
			}
		}
	}
	
	public void add(String k, Object v) {
		doAdd(k, v);
		load++;
	}
	
	public void doAdd(String k, Object v) {
		// test load threshold, possibly resize
		double lf = (double) load / size;
		if(lf > threshold) {
			resize();
		}
		// hash key for initial address
		int at = Hasher.hash(k, size);
		// if that position is null, add k,v pair
		if(table[at] == null) {
			table[at] = new CHashNode(k,v);
			return;
		} else if(table[at].hasList) {
			table[at].list.add(new CHashNode(k,v));
			return;
		} else if(!table[at].hasList){
			chains++;
			CHashNode temp = table[at];
			table[at] = new CHashNode(null, null);
			table[at].list = new LinkedList<CHashNode>();
			table[at].hasList = true;
			table[at].list.add(temp);
			table[at].list.add(new CHashNode(k,v));
			return;
		}
		
		// if we have gotten here, then we are past the end of the table and have failed to add the value, necessitating a resize() call
		System.out.println("Hit end of table!");
		resize();
		doAdd(k, v);
	}

	public Object get(String k) {
		// hash key for initial address
		int at = Hasher.hash(k, size);
		if(table[at] == null) {
			return null;
		}
		else if(!table[at].hasList) {
			return table[at].value;
		}
		else if(table[at].hasList) {
			for(CHashNode o : table[at].list) {
				if(o.key.equals(k)) {
					return o.value;
				}
			}
		}
		// if we have gotten here, then we are past the end of the table and the key is not present
		System.out.println("Hit end of table!");
		return null;
	}
	
	public boolean contains(String k) {
		// hash key for initial address
		int at = Hasher.hash(k, size);
		if(table[at] == null) {
			return false;
		}
		else if(!table[at].hasList) {
			return table[at].value.equals(k);
		}
		else if(table[at].hasList) {
			boolean flag = false;
			for(CHashNode o : table[at].list) {
				if(o.key.equals(k)) {
					flag = true;
				}
			}
			return flag;
		}
		// if we have gotten here, then we are past the end of the table and the key is not present
		System.out.println("Hit end of table!");
		return false;
	}
	
	public void remove(String k) {
		// hash key for initial address
		int at = Hasher.hash(k, size);
		if(table[at] == null) {
			return;
		}
		else if(!table[at].hasList) {
			if(table[at].key.equals(k)){
				table[at] = null;
				load--;
				return;
			}
		}
		else if(table[at].hasList) {
			int idx = -1;
			for(int i=0; i < table[at].list.size(); i++) {
				if(table[at].list.get(i).key.equals(k)) {
					idx = i;
					break;
				}
			}
			if(idx >= 0) {
				table[at].list.remove(idx);
				load--;
				return;
			}
			return;
		}

		// if we have gotten here, then we are past the end of the table and the key is not present
		System.out.println("Hit end of table!");
		return;
	}
	
	
}
