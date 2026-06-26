package uk.gov.homeoffice;

public class Kata {
    static String alphabetPosition(String text) {
        String sep = "";
        StringBuilder result =  new StringBuilder();

        for(char ch : text.toUpperCase().toCharArray()) {
            if (Character.isAlphabetic(ch)) {
                result.append(sep).append(ch - 'A' + 1);
                sep = " ";
            }
        }

        return result.toString();
    }
}
