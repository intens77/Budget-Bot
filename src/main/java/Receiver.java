import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Receiver {
    public String startProcess() {
        String startMessage = "";
        try {
            Scanner scanner = new Scanner(new File(String.format("src%1$smain%1$sresources%1$sstart.txt",
                    File.separator)));
            scanner.useDelimiter("\\Z");
            startMessage = scanner.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return startMessage;
    }

    public String generateError() {
        return "Я не знаю такую команду:(";
    }

    public String getStrategies() {
        return "Стратегия 1\n Стратегия 2\n Стратегия 3";
    }
}
