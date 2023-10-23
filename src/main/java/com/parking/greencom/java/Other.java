package com.parking.greencom.java;

public class Other {
	public static long countChar(String str, char ch) {
        return str.chars()
                .filter(c -> c == ch)
                .count();
    }
}
