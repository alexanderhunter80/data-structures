package com.epoch.dataStructures.trees.vanEmdeBoas;

public class proto_vanEmdeBoas {
	public static int NIL_VALUE = Integer.MIN_VALUE;
	private final int UNIVERSE_SIZE;
	private proto_vanEmdeBoas summary;
	private proto_vanEmdeBoas[] cluster;
	private int A[];
	
	public proto_vanEmdeBoas(int UNIVERSE_SIZE) {
		this.UNIVERSE_SIZE = UNIVERSE_SIZE;
		if (UNIVERSE_SIZE > 2) {
			int u = shrink(UNIVERSE_SIZE);
			summary = new proto_vanEmdeBoas(u);
			cluster = new proto_vanEmdeBoas[u];
			for (int i = 0; i < u; i++) {
				cluster[i] = new proto_vanEmdeBoas(u);
			}
		} else {
			A = new int[2];
		}
	}
	
	public boolean contains(int x) {
		if (this.UNIVERSE_SIZE == 2)
			return A[x] == 1;
		return cluster[high(x)].contains(low(x));
	}
	
	public int getMinimum() {
		if (UNIVERSE_SIZE == 2) {
			if (A[0] == 1) {
				return 0;
			} else if (A[1] == 1) {
				return 1;
			} else {
				return NIL_VALUE;
			}
		} else {
			int min_cluster = summary.getMinimum();
			if (min_cluster == NIL_VALUE)
				return NIL_VALUE;
			else {
				int offset = cluster[min_cluster].getMinimum();
				return index(min_cluster, offset);
			}
		}
	}
	
	public int getMaximum() {
		if (UNIVERSE_SIZE == 2) {
			if (A[1] == 1) {
				return 1;
			} else if (A[0] == 1) {
				return 0;
			} else {
				return NIL_VALUE;
			}
		} else {
			int max_cluster = summary.getMaximum();
			if (max_cluster == NIL_VALUE)
				return NIL_VALUE;
			else {
				int offset = cluster[max_cluster].getMaximum();
				return index(max_cluster, offset);
			}
		}
	}
	
	public int getSuccessor(int x) {
		if (UNIVERSE_SIZE == 2) {
			if (x == 0 && A[1] == 1)
				return 1;
			else
				return NIL_VALUE;
		} else {
			int offset = cluster[high(x)].getSuccessor(low(x));
			if (offset != NIL_VALUE)
				return index(high(x), offset);
			else {
				int succ_cluster = summary.getSuccessor(high(x));
				if (succ_cluster == NIL_VALUE)
					return NIL_VALUE;
				else {
					offset = cluster[succ_cluster].getMinimum();
					return index(succ_cluster, offset);
				}
			}
		}
	}
	
	public int getPredecessor(int x) {
		if (UNIVERSE_SIZE == 2) {
			if (x == 1 && A[0] == 1)
				return 0;
			else
				return NIL_VALUE;
		} else {
			int offset = cluster[high(x)].getPredecessor(low(x));
			if (offset != NIL_VALUE)
				return index(high(x), offset);
			else {
				int pred_cluster = summary.getPredecessor(high(x));
				if (pred_cluster == NIL_VALUE)
					return NIL_VALUE;
				else {
					offset = cluster[pred_cluster].getMaximum();
					return index(pred_cluster, offset);
				}
			}
		}
	}
	
	public void insert(int x) {
		if (UNIVERSE_SIZE == 2) 
			A[x] = 1;
		else {
			cluster[high(x)].insert(low(x));
			summary.insert(high(x));
		}
	}
	
	public void remove(int x) {
		if (UNIVERSE_SIZE == 2) {
			A[x] = 0;
		} else {
			proto_vanEmdeBoas selected = cluster[high(x)];
			selected.remove(low(x));
			if (!selected.isOccupied()) {
				summary.remove(high(x));
			}
		}
	}
	
	private boolean isOccupied() {
		if (UNIVERSE_SIZE == 2) {
			return A[0] == 1 || A[1] == 1;
		} else {
			for (int i = 0; i < cluster.length; i++) {
				if (cluster[i].isOccupied()) {
					return true;
				}
			}
			return false;
		}
	}
	
	private static int shrink(int size) {
		return (int)Math.sqrt((double)size);
	}
	
	private int high(int x) {
		return (int)((double)x / Math.sqrt((double)UNIVERSE_SIZE));
	}
	
	private int low(int x) {
		return (int)((double)x % Math.sqrt((double)UNIVERSE_SIZE));
	}
	
	private int index(int x, int y) {
		return (int)((double)x * Math.sqrt((double)UNIVERSE_SIZE) + (double)y);
	}
}
