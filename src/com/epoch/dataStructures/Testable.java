package com.epoch.dataStructures;

public interface Testable {
	
	public <T> T access(T t);
	
	public <T> T search(T t);
	
	public <T> boolean contains(T t);
	
	public <T> void insert(T t);
	
	public <T> void remove(T t);

}
