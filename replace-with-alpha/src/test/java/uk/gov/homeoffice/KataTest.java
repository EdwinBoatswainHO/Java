package uk.gov.homeoffice;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KataTest {

    @Test
    void testTheSunSetsAtTwelveOClock() {
        doTest("20 8 5 19 21 14 19 5 20 19 5 20 19 1 20 20 23 5 12 22 5 15 3 12 15 3 11",
                "The sunset sets at twelve o' clock.");
    }

    @Test
    void testTheNarwhalBaconsAtMidnight() {
        doTest("20 8 5 14 1 18 23 8 1 12 2 1 3 15 14 19 1 20 13 9 4 14 9 7 8 20",
                "The narwhal bacons at midnight.");
    }

    private static void doTest(String expected, String input) {
        String message = String.format("for input: %s", input);
        assertEquals(expected, Kata.alphabetPosition(input), message);
    }
}