package com.epoch.dataStructures.trees.vanEmdeBoas;

public class vanEmdeBoas {
	public static int NIL = Integer.MIN_VALUE;
	
	private int U, min, max;
	private vanEmdeBoas summary;
	private vanEmdeBoas[] clusters;
	
	public vanEmdeBoas(int U) {
		this.U = U;
		min = max = NIL;
		if (U > 2) {
			summary = new vanEmdeBoas(upperRoot(U));
			clusters = new vanEmdeBoas[upperRoot(U)];
			for (int i = 0; i < clusters.length; i++) {
				clusters[i] = new vanEmdeBoas(lowerRoot(U));
			}
		}
	}
	
	public int getMinimum() {
		return min;
	}
	
	public int getMaximum() {
		return max;
	}
	
	public boolean contains(int x) {
		if (x == min || x == max) {
			return true;
		} else if (U == 2) {
			return false;
		} else {
			return clusters[high(x)].contains(low(x));
		}
	}
	
	public int getSuccessor(int x) {
		if (U == 2) {
			if (x == 0 && max == 1)
				return 1;
			else
				return NIL;
		} else if (min != NIL && x < min) {
			return min;
		} else {
			int max_low = clusters[high(x)].getMaximum();
			if (max_low != NIL && low(x) < max_low) {
				int offset = clusters[high(x)].getSuccessor(low(x));
				return index(high(x), offset);
			} else {
				int succ_cluster = summary.getSuccessor(high(x));
				if (succ_cluster == NIL)
					return NIL;
				else {
					int offset = clusters[succ_cluster].getMinimum();
					return index(succ_cluster, offset);
				}
			}
		}
	}
	
	public int getPredecessor(int x) {
		if (U == 2)
			if (x == 1 && min == 0)
				return 0;
			else
				return NIL;
		else if (max != NIL && x > max)
			return max;
		else {
			int min_low = clusters[high(x)].getMinimum();
			if (min_low != NIL && low(x) > min_low) {
				int offset = clusters[high(x)].getPredecessor(low(x));
				return index(high(x), offset);
			} else {
				int pred_cluster = summary.getPredecessor(high(x));
				if (pred_cluster == NIL)
					if (min != NIL && x > min)
						return min;
					else
						return NIL;
				else {
					int offset = clusters[pred_cluster].getMaximum();
					return index(pred_cluster, offset);
				}
			}
		}
	}
	
	public void insert(int x) {
		if (min == NIL)
			empty_insert(x);
		else {
			int y = x;
			if (y < min) {
				y = min;
				min = x;
			}
			if (U > 2) {
				if (clusters[high(y)].getMinimum() == NIL) {
					summary.insert(high(y));
					clusters[high(y)].empty_insert(low(y));
				} else
					clusters[high(y)].insert(low(y));
			}
			if (y > max)
				max = y;
		}
	}
	
	public void remove(int x) {
		if (min == max) {
			min = NIL;
			max = NIL;
		} else if (U == 2) {
			if (x == 0)
				min = 1;
			else
				min = 0;
			max = min;
		} else {
			int y = x;
			if (y == min) {
				int first_cluster = summary.getMinimum();
				y = index(first_cluster, clusters[first_cluster].getMinimum());
				min = y;
			}
			clusters[high(y)].remove(low(y));
			if (clusters[high(y)].getMinimum() == NIL) {
				summary.remove(high(y));
				if (y == max) {
					int summary_max = summary.getMaximum();
					if (summary_max == NIL)
						max = min;
					else
						max = index(summary_max, clusters[summary_max].getMaximum());
				}
			} else if (y == max)
				max = index(high(y), clusters[high(y)].getMaximum());
		}
	}
	
	private void empty_insert(int x) {
		min = x;
		max = x;
	}
	
	public int high(int x) {
		return (int)((double)x / (double)lowerRoot(U));
	}
	
	public int low(int x) {
		return (int)((double)x % (double)lowerRoot(U));
	}
	
	private int index(int x, int y) {
		return (int)((double)x * (double)lowerRoot(U) + (double)y);
	}
	
	public static int upperRoot(int x) {
		return (int)Math.pow(2, Math.ceil(log(x)));
	}
	
	public static int lowerRoot(int x) {
		return (int)Math.pow(2, Math.floor(log(x)));
	}
	
	public static double log(int x) {
		return (Math.log10(x) / Math.log10(2)) / 2;
	}
	
}
