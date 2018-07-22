package com.epoch.dataStructures;

/*
 * @author Alexander Hunter
 * @version 1.0
 * 
 * Partly adapted from Vladimir Roubtsov's sizeOf() published in JavaWorld, Aug 2002
 */

public class Benchmarker {
	
	private static final Runtime _runtime = Runtime.getRuntime();
	
	private long startTime;
	private long stopTime;
	private long elapsedTime;
	
	private long heap1;
	private long heap2;
	
	public Benchmarker() {}
	
	public void start(){
		startTime = System.nanoTime();
	}
	
	public long stop(String s) {
		stopTime = System.nanoTime();
		elapsedTime = stopTime - startTime;
		String millis = String.format("%.3f", (double) elapsedTime / 1000000);
		System.out.println(s);
		System.out.println("Operation completed in " + Long.toString(elapsedTime) + "ns (" + millis + " ms)");
		return elapsedTime;
	}
	
	public void initSize() throws Exception {
		runGC();
		heap1 = usedMemory();	
	}
	
	public int calculateSize() throws Exception {
		runGC();
		heap2 = usedMemory();
		int size = (int) heap2 - (int) heap1;
		System.out.println("heap before: " + Long.toString(heap1) + " , heap after: " + Long.toString(heap2));
		System.out.println("heap delta: " + Integer.toString(size) + "bytes");
		return size;
	}
	
	
	
	private static void runGC() throws Exception {
		for(int i = 0; i < 4; i++) {
			_runGC();
		}
	}
	
	private static void _runGC() throws Exception{
		long uM1 = usedMemory();
		long uM2 = Long.MAX_VALUE;
		for(int i = 0; ((uM1 < uM2)&&(i < 100)); i++) {
			_runtime.runFinalization();
			_runtime.gc();
			Thread.currentThread().yield();
			
			uM2 = uM1;
			uM1 = usedMemory();
		}
	}
	
	private static long usedMemory() {
		return _runtime.totalMemory() - _runtime.freeMemory();
	}
	
	public static void printSeparator(String s) {
		String separator = new String(new char[80]).replace("\0", "*");
		System.out.println(separator);
		System.out.println(s);
		System.out.println(separator);
		System.out.println();
	}

}
