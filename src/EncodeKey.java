import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Java program to calculate SHA hash value

public class EncodeKey {

  /***
   * Calculate the SHA-256 for the input data
   * @param data
   * @return string in SHA-256
   */
  public static String getSHA256(String data) {
    StringBuilder sb = new StringBuilder();
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(data.getBytes());
      byte[] byteData = md.digest();
      sb.append(toHexString(byteData));
    } catch (Exception e) {
      System.out.println("No Such Algorithm Exception");
    }
    return sb.toString();
  }

  /**
   *  Auxiliar method to represent the byte array  code has
   * @param hash
   * @return string representation
   */

  private static String toHexString(byte[] hash) {
    // Convert byte array into signum representation
    BigInteger number = new BigInteger(1, hash);

    // Convert message digest into hex value
    StringBuilder hexString = new StringBuilder(number.toString(16));

    // Pad with leading zeros
    while (hexString.length() < 64) {
      hexString.insert(0, '0');
    }
    return hexString.toString();
  }

  public static void main(String args[]) {
    String s2 = "hello world";
    System.out.println("\n" + s2 + " : " + getSHA256(s2));
  }
}
