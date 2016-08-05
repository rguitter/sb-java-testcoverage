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
    public void callingConstructorWithRadiusEqualTo0IsValid() {
        Assert.assertNotNull(new Circle(0));
    }

    @Test
    public void areaForRadius1ShouldBe28_27() {
        Circle circle = new Circle(3);
        double result = circle.area();
        Assert.assertEquals("For radius 3, area should be 28.27", 28.27, result, 0.01);
    }

    @Test
    public void perimeterForRadius1ShouldBe18_84() {
        Circle circle = new Circle(3);
        double result = circle.perimeter();
        Assert.assertEquals("For radius 3, perimeter should be 18.84", 18.84, result, 0.01);
    }

}
