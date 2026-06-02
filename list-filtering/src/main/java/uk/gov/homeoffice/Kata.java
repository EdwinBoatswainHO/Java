package uk.gov.homeoffice;

import java.util.List;

public class Kata {
  
  public static List<Object> filterList(final List<Object> list) {
    return list.stream()
    .filter(item -> item instanceof Integer)
    .toList();
  }
}
