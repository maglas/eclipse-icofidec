package de.vermat.icofidec.utils;

public class NumberUtils {
	
	public static int countDigits(int number) {
		return String.valueOf(Math.abs(number)).length();
	}
}