package uk.gov.homeoffice;

import java.util.HashSet;
import java.util.Set;

public class CountingDuplicates {
    public static int duplicateCount(String text) {
        Set<Character> seenSet = new HashSet<>();

    var result = text.chars()
        .mapToObj(c -> (char)c)
        .map( c -> Character.toUpperCase(c.charValue()))
        .filter(c -> !seenSet.add(c))
        .distinct()
        .count();

        return (int)result;
    }
}