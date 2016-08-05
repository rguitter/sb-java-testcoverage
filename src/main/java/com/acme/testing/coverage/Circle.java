package com.acme.testing.coverage;

/**
 * Created by rguitter on 05/08/16.
 */
public class Circle {

    private final double radius;

    public Circle(double radius) {
        if (radius < 0) throw new IllegalArgumentException("Radius can't be negative.");
        this.radius = radius;
    }

    public double area() {
        return Math.PI * radius * radius;
    }

    public double perimeter() {
        return Math.PI * radius * 2;
    }

}
