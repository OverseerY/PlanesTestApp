package com.yaroslav.customtestapp;

public class Point {
    private final int x;
    private final int y;

    public Point(final double x, final double y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y + "}";
    }
}