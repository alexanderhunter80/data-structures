package com.epoch.dataStructures;

public class RandomStringGenerator {
	
	private static final String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public RandomStringGenerator() {};
	
	public String generate(int length) {
		StringBuilder output = new StringBuilder();
		int l = alphaNumeric.length();
		for(int i = 0; i < length; i++) {
			output.append(alphaNumeric.charAt((int)Math.floor(Math.random()*l)));
		}
		return output.toString();
	}

}
