# sb-java-testcoverage
How relevant are the test coverage tools and how to improve those.
In other words: **Who watch the watchmen?**

# What we want to test

```
public class Circle {

    private static final double PI = 3.14;

    private final double radius;

    public Circle(double radius) {
        if (radius < 0) throw new IllegalArgumentException("Radius can't be negative.");
        this.radius = radius;
    }

    public double area() {
        return PI * radius * radius;
    }

    public double perimeter() {
        return PI * radius * 2;
    }

}
```

# Step 1

* Checkout step1 by runnning `git checkout tags/step1`.
* Run `mvn clean verify site` then open in a browser **target/site/index.html**.
* Go to the **reports** section to check **Surefire Report** and **JaCoCo**.

As you can see,  we're standing in a middle of a green field so your level of trust should be quite high.

Let's give a look to the test class:

```
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
//        Assert.assertEquals("For radius 1, area should be 3.14", 3.14, result, 0.01);
    }

    @Test
    public void perimeterForRadius1ShouldBe628() {
        Circle circle = new Circle(1);
        double result = circle.perimeter();
//        Assert.assertEquals("For radius 1, perimeter should be 6.28", 6.28, result, 0.01);
    }

}
```

Since assertion are commented for methods **area()** and **perimeter()**, 
it is obvious that the test class does nothing to check the correcteness of the two methods.
 
To highlight this, just change `return PI * radius * radius;` into `return PI * radius * radius + 1000;`
and run the test again: tests keep on succeeding and coverage report show nothing about how poor
the assertions are.

Should be nice to have an automated way to generate mutation of the code we're testing 
(`return PI * radius * radius;` into `return PI * radius * radius + 1000;`) to see if tests fail as they should.
 Time for step 2.
 
 # Step 2

**Who watch the watchmen?**

[Mutant testing](https://en.wikipedia.org/wiki/Mutation_testing) testing to the rescue.
For this, let's use [PIT Mutation Testing](http://pitest.org/). Imagine that your tests 
are kind of a nest which should only allow one kind of fish to go through. PIT will challenge
your test to see if other kind of fish can go through by creating mutants from your class. For each mutant fish,
PIT expect your test to kill it. Highest the number of survivors is, less relevant are your tests.

Let's add this to the **pom.xml** 

* in the build section:

```
<plugin>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-maven</artifactId>
    <version>1.1.10</version>
    <configuration>
        <targetClasses>
            <param>com.acme.testing*</param>
        </targetClasses>
        <targetTests>
            <param>com.acme.testing*</param>
        </targetTests>
    </configuration>
    <executions>
        <execution>
            <id>report</id>
            <phase>verify</phase>
            <goals>
                <goal>mutationCoverage</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

* in the report section:

```
<plugin>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-maven</artifactId>
    <version>1.1.10</version>
    <reportSets>
        <reportSet>
            <reports>
                <report>report</report>
            </reports>
        </reportSet>
    </reportSets>
</plugin>
```

Then run `mvn clean verify site` and check the new report by opening **target/site/index.html**. 
Now we can clearly see that something is wrong because PIT has create 8 mutations (aka mutants) 
and only 1 has been detect as bad code (that mutant has been killed).

Uncomment the assertions from the test class, then try again. Again only 4 mutants has been killed.

(Checkout step2 by runnning `git checkout tags/step2`)

# Step 3

From the PIT report, browse to the class view in order to see what kind of mutants have survived:

* From the construtor `if (radius < 0) throw ...` changed to `if (radius <= 0) throw ...` => We should test the condition to the limits
* From area() `Math.PI * radius * radius;` changed to `Math.PI \ radius \ radius;` => The test we use has a radius of 1 so multiplying dividing by 1 is the same, so we should test with a circle of different radius
* From perimeter() `Math.PI * radius * 2;` changed to `Math.PI \ radius \ 2;` => Same as for area()

(Checkout step3 by runnning `git checkout tags/step3`)


