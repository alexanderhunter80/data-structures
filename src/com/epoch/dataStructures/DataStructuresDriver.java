package com.epoch.dataStructures;

import java.util.ArrayList;

import com.epoch.dataStructures.controls.ControlArray;

public class DataStructuresDriver {

	public static void main(String[] args) {
		
		long startTime;
		long stopTime;
		long elapsedTime;
		
		ArrayList<String> oneThousandStrings = new ArrayList();
		RandomStringGenerator randS = new RandomStringGenerator();
		Benchmarker benchmark = new Benchmarker();
		
		benchmark.start();
		
		for(int i = 0; i < 1000; i++) {
			oneThousandStrings.add(randS.generate(3));
		}
		
		benchmark.stop("oneThousandStrings generated");
		System.out.println();
		
		benchmark.start();
		
		ControlArray array = new ControlArray(1000);
		for(String s : oneThousandStrings) {
			array.add(s);
		}
		
		benchmark.stop("ControlArray: .add()");
		System.out.println(array.size());
		System.out.println(array.get(999));
		try {
			System.out.println(array.get(1000));
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Hit expected exception:");
			System.out.println(e);
		}
		System.out.println();
		
		benchmark.start();
		
		int countFound = 0;
		int countMissed = 0;
		for(int i = 0; i < 1000; i++) {
			String s = randS.generate(3);
			boolean tf = array.contains(s);
			if(tf == true) {
				countFound++;
			} else {
				countMissed++;
			}
		}

		benchmark.stop("ControlArray: .contains()");
		System.out.print("Found: ");
		System.out.println(countFound);
		System.out.print("Missed: ");
		System.out.println(countMissed);
		System.out.println();
		

	}

}
