package com.technoirarts;

public class Stopwatch {

    private long startTime = System.nanoTime();

    public void start() {
        startTime = System.nanoTime();
    }

    public long elapsed() {
        return (System.nanoTime() - startTime) / 1000000;
    }
}
