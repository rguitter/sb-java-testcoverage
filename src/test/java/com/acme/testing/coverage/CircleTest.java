package com.acme.testing.coverage;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by rguitter on 05/08/16.
 */
public class CircleTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void callingConstructorWithNegativeRadiusShouldFail() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Radius can't be negative.");
        new Circle(-1);
    }

    @Test
    public void areaForRadius1ShouldBe314() {
        Circle circle = new Circle(1);
        double result = circle.area();
        Assert.assertEquals("For radius 1, area should be 3.14", 3.14, result, 0.01);
    }

    @Test
    public void perimeterForRadius1ShouldBe628() {
        Circle circle = new Circle(1);
        double result = circle.perimeter();
        Assert.assertEquals("For radius 1, perimeter should be 6.28", 6.28, result, 0.01);
    }

}
