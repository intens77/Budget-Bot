import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SecondaryFunctions {

  public static String readFileContent(String filename) {
    try {
      Scanner scanner = new Scanner(new File(String.format("src%1$smain%1$sresources%1$s%2$s",
          File.separator, filename)));
      scanner.useDelimiter("\\Z");
      return scanner.next();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
}
