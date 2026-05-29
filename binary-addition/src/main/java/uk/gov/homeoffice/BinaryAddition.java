package uk.gov.homeoffice;

public class BinaryAddition{
  
  public static String binaryAddition(int a, int b){
    var result = a + b;
    StringBuilder resultString = new StringBuilder();
    while (result > 0) {
      var digit = result & 1;
      resultString.append(Integer.toString(digit));
      result >>= 1;
    }

    return resultString.reverse().toString();

    // Looks like everybody else used Integer.toBinaryString()!
  }
}