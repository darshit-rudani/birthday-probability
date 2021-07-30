package mooc.vandy.java4android.birthday.logic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestTimedOutException;

import io.magnum.autograder.junit.Rubric;
import mooc.vandy.java4android.birthday.ui.OutputInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UnitTests {
    private static String TIMEOUT_MESSAGE = "" +
            "Your solution has timed out because it is inefficient. Try a different\n" +
            "solution that does not use nested loops for a each simulation run.\n" +
            "This can be accomplished by using different containers (covered in this\n" +
            "course module) or by using arrays or ArrayLists in different ways. There\n" +
            "are many efficient solution strategies for this problem. You just need to\n" +
            "come up with one that makes sense to you. You also need to pay attention\n" +
            "to note #3 in the project spec and stop a single simulation run as soon as\n" +
            "a duplicate birthday is detected. You should avoid generating all birthdays\n" +
            "when it is not necessary.\n";

    // The variables that are used in each test.
    Logic mLogic = new Logic(new TestingOutputInterface());

    /**
     * A rule to catch timeouts and print a more meaningful message.
     */
    @Rule
    public TestRule testRule = (base, description) ->
            new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    try {
                        base.evaluate();
                    } catch (TestTimedOutException e) {
                        System.err.println(TIMEOUT_MESSAGE);
                        throw e;
                    }
                }
            };

    /**
     * If students calculate percent using the formula
     * (a / b) * 100
     * there rounding error will not produce an exact match
     * with the solution set which expects the formula to be
     * (a * 100) / b
     * To work around this issue, Mike has added a tolerance
     * threshold.
     */
    @Rubric(
            value = "testCalculateThreshold",
            goal = "The goal of this evaluation is to test Calculate with return values within a threshold",
            points = 30.0,
            reference = "This Test fails when: calculate method failed to meet a 3% threshold."
    )
    @Test(timeout = 10_000)
    public void testCalculateThreshold() {
        double threshold = .03;
        assertEquals(2.71, mLogic.calculate(5, 10_000), 2.71 * threshold);
        assertEquals(5.34, mLogic.calculate(7, 5_000), 5.34 * threshold);
        assertEquals(0.27, mLogic.calculate(2, 10_000), .27 * threshold);
        assertEquals(9.47, mLogic.calculate(9, 10_000), 9.47 * threshold);
        assertEquals(70.675, mLogic.calculate(30, 20_000), 70.675 * threshold);
        assertEquals(25.576, mLogic.calculate(15, 50_000), 25.576 * threshold);
        assertEquals(81.434, mLogic.calculate(35, 50_000), 81.434 * threshold);
        assertEquals(94.2, mLogic.calculate(45, 50_000), 94.2 * threshold);
    }

    @Rubric(
            value = "testResultRange",
            goal = "The goal of this evaluation is to test ResultRange return values.",
            points = 30.0,
            reference = "This Test fails when: calculate returns a value outside the range."
    )
    @Test(timeout = 10_000)
    public void testResultRange() {
        // Check for edge cases
        //assertEquals(0, mLogic.calculate(0, 0), 0);  //test only positive values
        assertEquals(0, mLogic.calculate(1, 1), 0);
        assertEquals(100, mLogic.calculate(1000, 2), 0);

        // Check different group sizes.
        for (int size = 2; size <= 365; size++) {
            double result = mLogic.calculate(size, 2);
            assertTrue(result >= 0.0 && result <= 100.0);
        }
    }

    @Rubric(
            value = "testEfficiency",
            goal = "The goal of this evaluation is to test overall solution efficiency.",
            points = 30.0,
            reference = "This Test fails when: the solution is slow and inefficient."
    )
    @Test(timeout = 60_000)
    public void testEfficiency() {
        // Stress the solution.
        for (int i = 1; i <= 500; i++) {
            double result = mLogic.calculate(364, 50_001);
            assertTrue(result >= 0.0 && result <= 100.0);
        }
    }

    private static class TestingOutputInterface implements OutputInterface {
        String output = "";
        String alertText = null;

        public TestingOutputInterface() {
        }

        @Override
        public int getSize() {
            return 0;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public void makeAlertToast(String alertText) {
            this.alertText = alertText;
        }

        @Override
        public void resetText() {
            output = "";
        }

        @Override
        public void print(Object obj) {
            output += (obj != null ? obj.toString() : "null");
        }
    }
}
