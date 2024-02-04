package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final String EXIT_CMD = "/exit";

        Scanner scanner = new Scanner(System.in);

        do {
            String line = scanner.nextLine();
            if (line.equals(EXIT_CMD)) {
                break;
            }

            if (line.isEmpty()) {
                continue;
            }

            String[] splitLine = line.split(" ");
            if (splitLine.length == 1) {
                System.out.println(splitLine[0]);
            } else {
                System.out.print(Integer.parseInt(splitLine[0]) + Integer.parseInt(splitLine[1]));
            }
        } while (true);

        System.out.println("Bye!");
    }
}
