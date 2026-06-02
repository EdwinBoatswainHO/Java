package uk.gov.homeoffice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class KataTest 
{
  @Test
  public void examples() {
    assertEquals(List.of(1, 2), Kata.filterList(List.of(1, 2, "a", "b")), "For input: [1, 2, \"a\", \"b\"]");
    assertEquals(List.of(1, 0, 15), Kata.filterList(List.of(1, "a", "b", 0, 15)), "For input: [1, \"a\", \"b\", 0, 15]");
    assertEquals(List.of(1, 2, 123), Kata.filterList(List.of(1, 2, "aasf", "1", "123", 123)), "For input: [1, 2, \"aasf\", \"1\", \"123\", 123]");    
  } 
}
