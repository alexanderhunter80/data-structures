package com.epoch.dataStructures.hashtables;

public class LinearOpenAddressingHashtable {

	private int size;
	private OAHashNode[] table;
	private OAHashNode deleted;
	private int load;
	private double threshold;

	public LinearOpenAddressingHashtable(int size, double threshold) {
		this.size = size;
		table = new OAHashNode[size];
		load = 0;
		this.threshold = threshold;
		deleted = new OAHashNode(null, null); // replaces deleted items so as not to break the probing process
	}
	
	// for testing purposes only
	public OAHashNode[] getEntireTable() {
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
		// step through table starting at hash, inserting if either a null node or a dummy node is found
		while(at<size) {
			if(table[at]==null) {
				table[at] = new OAHashNode(k,v);
				return;
			} else if(table[at].key==null) {
				table[at] = new OAHashNode(k,v);
				return;
			} else {
				// linear probing: always step by 1
				at++;
			}
		}
		// if we have gotten here, then we are past the end of the table and have failed to add the value, necessitating a resize() call
		System.out.println("Hit end of table!");
		resize();
		doAdd(k, v);
	}

	public void remove(String k) {
		// hash key for initial address
		int at = Hasher.hash(k, size);
		// begin probing at hash
		while(at<size) {
			// fail if any probed node is null
			if(table[at] == null) {
				return;
			}
			// test for "deleted" node's null key to avoid NullPointerException
			if(table[at].key == null) {
				// no-op
				assert true;
			}
			// if node is not null, compare keys for equality
			else if(table[at].key.equals(k)) {
				table[at] = deleted;
				load--;
				return;
			}
			// otherwise, step ahead
			// linear probing: always step by 1
			at++;
		}
		// if we have gotten here, then we are past the end of the table and the key is not present
		System.out.println("Hit end of table!");
		return;
	}

	public Object get(String k) {
		// hash key for initial address
		int at = Hasher.hash(k, size);
		// begin probing at hash
		while(at<size) {
			// fail if any probed node is null
			if(table[at] == null) {
				return null;
			}
			// if node is not null, compare keys for equality
			if(table[at].key.equals(k)) {
				return table[at].value;
			}
			// otherwise, step ahead
			// linear probing: always step by 1
			at++;
		}
		// if we have gotten here, then we are past the end of the table and the key is not present
		System.out.println("Hit end of table!");
		return null;
	}

	public boolean contains(String k) {
		// hash key for initial address
		int at = Hasher.hash(k, size);
		// begin probing at hash
		while(at<size) {
			// fail if any probed node is null
			if(table[at] == null) {
				return false;
			}
			// if node is not null, compare keys for equality
			if(table[at].key.equals(k)) {
				return true;
			}
			// otherwise, step ahead
			// linear probing: always step by 1
			at++;
		}
		// if we have gotten here, then we are past the end of the table and the key is not present
//		System.out.println("Hit end of table!");
		return false;
	}

	private void resize(){
		// resize should callout when it is called, just to see how often it happens
		System.out.println("LinearOpenAddressingHashtable is resizing from "+size+" to "+(size*2));

		size *= 2;
		OAHashNode[] oldTable = table;
		table = new OAHashNode[size];
		for(OAHashNode node : oldTable) {
			if(node != null) {
				doAdd(node.key, node.value);
			}
		}

	}

}
