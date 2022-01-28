package com.company;

import java.io.*;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

class PrimeCheck extends Thread {

    public void run() {
        //The threads search through 100 numbers at a time for primes and adjust the total primes and number of primes as they are found
        int curInt = Counter.nextRange();
        while (curInt < (1e8)) {
            for (int i = curInt + 1; i < curInt + 100; i += 2) {
                if (isPrime(i)) {
                    Main.numPrimes.incrementAndGet();
                    Main.primeSum.addAndGet(i);
                }
            }
            curInt = Counter.nextRange();
        }
        return;
    }

    public static boolean isPrime(int number) {
        // This primality algorithm comes from the following wikipedia page: https://en.wikipedia.org/wiki/Primality_test
        if (number == 2 || number == 3)
            return true;
        if (number <= 1 || number % 2 == 0 || number % 3 == 0)
            return false;
        int sqrtnum = (int)Math.floor(Math.sqrt(number));
        for (int i = 5; i <= sqrtnum; i += 6){
            if (number % i == 0 || number % (i+2) == 0)
                return false;
        }
        return true;
    }
}

class Counter {
    //A counter class for the threads to fetch numbers from
    static AtomicInteger intCounter;

    Counter(int initnum) {
        intCounter = new AtomicInteger(initnum);
    }

    public static int nextRange() {
        return intCounter.getAndAdd(100);
    }
}

public class Main {

    public static AtomicInteger numPrimes = new AtomicInteger(1); // Starting at 1 to account for 2 being a prime
    public static AtomicLong primeSum = new AtomicLong(2); // Starting at 2 because 2 is a prime
    static int threads = 8;

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter(2);

        //Create and start threads
        ArrayList<PrimeCheck> arrThreads = new ArrayList<>();
        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < threads; i++) {
            PrimeCheck object = new PrimeCheck();
            object.start();
            arrThreads.add(object);
        }
        //wait for the threads to complete
        for (PrimeCheck arrThread : arrThreads) {
            arrThread.join();
        }
        //find how long the threads took to run
        long elapsedTimeMilli = System.currentTimeMillis() - startTime;

        // it is easier (and probably faster) to find the largest 10 primes separately than to try and keep track in the threads
        int tenBiggestPrimeCounter = (int)1e8 - 1; //top of range
        ArrayList<Integer> primes = new ArrayList<>(10);
        while (primes.size() < 10) {
            if (PrimeCheck.isPrime(tenBiggestPrimeCounter)){
                primes.add(tenBiggestPrimeCounter);
            }
            tenBiggestPrimeCounter -= 2;
        }
        // sort the found numbers to be ascending
        Collections.sort(primes);
        // Printing the results to a file
        try {
            PrintWriter out = new PrintWriter("primes.txt");
            out.println("Execution time(ms): " + elapsedTimeMilli);
            out.println("Total primes: " + numPrimes.toString());
            out.println("Sum of all primes: " + primeSum.toString());
            out.println("Ten Largest Primes:");
            for (Integer p : primes)
                out.println(p);
            out.close();
            } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
