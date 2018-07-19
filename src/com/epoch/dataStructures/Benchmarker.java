package com.epoch.dataStructures;

public class Benchmarker {
	
	private long startTime;
	private long stopTime;
	private long elapsedTime;
	
	public Benchmarker() {}
	
	public void start(){
		startTime = System.nanoTime();
	}
	
	public long stop(String s) {
		stopTime = System.nanoTime();
		elapsedTime = stopTime - startTime;
		System.out.println(s);
		System.out.print("Operation completed in ");
		System.out.print(elapsedTime);
		System.out.println("ns");
		return elapsedTime;
	}

}
