import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KataTest {

    @Test
    public void deleteNth() throws Exception {
        assertArrayEquals(
                new int[] { 20, 37, 21 },
                Kata.deleteNth( new int[] { 20, 37, 20, 21 }, 1 )
        );
        assertArrayEquals(
                new int[] { 1, 1, 3, 3, 7, 2, 2, 2 },
                Kata.deleteNth( new int[] { 1, 1, 3, 3, 7, 2, 2, 2, 2 }, 3 )

        );
        assertArrayEquals(
                new int[] { 1, 2, 3, 1, 1, 2, 2, 3, 3, 4, 5 },
                Kata.deleteNth( new int[] { 1, 2, 3, 1, 1, 2, 1, 2, 3, 3, 2, 4, 5, 3, 1 }, 3 )
        );
        assertArrayEquals(
                new int[] { 1, 1, 1, 1, 1 },
                Kata.deleteNth( new int[] { 1, 1, 1, 1, 1 }, 5 )
        );
        assertArrayEquals(
                new int[] { },
                Kata.deleteNth( new int[] { }, 5 )
        );

    }

    // Get middle
    @Test
    public void evenTests() {
        assertEquals("es", Kata.getMiddle("test"));
        assertEquals("dd", Kata.getMiddle("middle"));
    }

    @Test
    public void oddTests() {
        assertEquals("t", Kata.getMiddle("testing"));
        assertEquals("A", Kata.getMiddle("A"));
        assertEquals("3", Kata.getMiddle("12345"));
    }
    
    // longest consectutive
    private static void testing(String actual, String expected) {
        assertEquals(expected, actual);
    }
    @Test
    public void longestConsecTest() {
        System.out.println("longestConsec Fixed Tests");
        testing(Kata.longestConsec(new String[] {"zone", "abigail", "theta", "form", "libe", "zas", "theta", "abigail"}, 2), "abigailtheta");
        testing(Kata.longestConsec(new String[] {"ejjjjmmtthh", "zxxuueeg", "aanlljrrrxx", "dqqqaaabbb", "oocccffuucccjjjkkkjyyyeehh"}, 1), "oocccffuucccjjjkkkjyyyeehh");
        testing(Kata.longestConsec(new String[] {}, 3), "");
        testing(Kata.longestConsec(new String[] {"itvayloxrp","wkppqsztdkmvcuwvereiupccauycnjutlv","vweqilsfytihvrzlaodfixoyxvyuyvgpck"}, 2), "wkppqsztdkmvcuwvereiupccauycnjutlvvweqilsfytihvrzlaodfixoyxvyuyvgpck");
        testing(Kata.longestConsec(new String[] {"wlwsasphmxx","owiaxujylentrklctozmymu","wpgozvxxiu"}, 2), "wlwsasphmxxowiaxujylentrklctozmymu");
        testing(Kata.longestConsec(new String[] {"zone", "abigail", "theta", "form", "libe", "zas"}, -2), "");
        testing(Kata.longestConsec(new String[] {"it","wkppv","ixoyx", "3452", "zzzzzzzzzzzz"}, 3), "ixoyx3452zzzzzzzzzzzz");
        testing(Kata.longestConsec(new String[] {"it","wkppv","ixoyx", "3452", "zzzzzzzzzzzz"}, 15), "");
        testing(Kata.longestConsec(new String[] {"it","wkppv","ixoyx", "3452", "zzzzzzzzzzzz"}, 0), "");
    }
}