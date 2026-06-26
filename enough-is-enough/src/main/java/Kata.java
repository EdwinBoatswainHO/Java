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
}
