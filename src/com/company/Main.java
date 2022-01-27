package com.company;

import java.lang.Math;

public class Main {

    private static boolean isPrime(int number) {
        int sqrtnum = (int)Math.floor(Math.sqrt(number));
        for (int i = 2; i <= sqrtnum; i++){
            if (number % i == 0) {
                System.out.println(i);
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
	// write your code here
    //    System.out.println(isPrime(1605281107));
    }
}
