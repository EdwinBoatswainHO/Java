import java.util.*;

public class Kata {
    public static int[] deleteNth(int[] elements, int maxOccurrences) {
        Map<Integer, Integer> seenCount = new HashMap<>();

        return Arrays.stream(elements)
                .filter(ele -> {
                    int seenTimes = seenCount.getOrDefault(ele, 0);
                    if (seenTimes < maxOccurrences) {
                        seenCount.put(ele, ++seenTimes);
                        return true;
                    } else {
                        return false;
                    }
                })
                .toArray();
    }

    public static String getMiddle(String word) {
        int length = word.length();
        if (length < 2)
            return word;

        int mid = (length / 2);
        if (0 == (length % 2)) {
            return word.substring(mid - 1, mid + 1);
        } else {
            return word.substring(mid, mid + 1);
        }
    }

    public static String longestConsec(String[] strarr, int k) {
        if ((k <= 0) || (k > strarr.length))
            return "";

        String matched = "";
        int    len = 0;

        for (int i = 0; i <= strarr.length - k; i++) {
            StringBuilder concatenated = new StringBuilder();
            for (int j = 0; (j < k) ; j++)
                concatenated.append(strarr[i + j]);

            if (concatenated.length() > len) {
                matched = concatenated.toString();
                len = concatenated.length();
            }
        }

        return matched;
    }

    public static int findShort(String s) {
        return Arrays.stream(s.split("\\s+"))
                .mapToInt(String::length)
                .reduce(Integer.MAX_VALUE, Math::min);
    }

    public static int[] twoSum(int[] numbers, int target) {
        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = i + 1; j < numbers.length; j++)
                if ((numbers[i] + numbers[j]) == target) {
                    return new int[]{i,j};
                }
        }
        return null;
    }
    public static String rot13(String str) {
        final String lookupLower = "nopqrstuvwxyzabcdefghijklm";
        final String lookupUpper = "NOPQRSTUVWXYZABCDEFGHIJKLM";
        StringBuilder result = new StringBuilder();
        for (char ch : str.toCharArray()) {

            if (Character.isAlphabetic(ch)) {
                if (Character.isLowerCase(ch)) {
                    result.append(lookupLower.charAt(ch - 'a'));
                } else {
                    result.append(lookupUpper.charAt(ch - 'A'));
                }
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    // find outlier
    static int find(int[] integers) {
        boolean evens = mostlyEven(integers);
        for (int current : integers) {
            if (evens && (current % 2) != 0)
                return current;
            else if (!evens && (current % 2) == 0)
                return current;
        }
        return 0;
    }

    static boolean mostlyEven(int[] integers) {
        int even = 0;
        int odd  = 0;
        for (int i = 0; i < 3; i++) {
            if ((integers[i] % 2) == 0 )
               even ++;
            else
                odd ++;
        }

        return even > odd;
    }
}
