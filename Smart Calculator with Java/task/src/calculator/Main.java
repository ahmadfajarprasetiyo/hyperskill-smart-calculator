package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final String EXIT_CMD = "/exit";
        final String HELP_CMD = "/help";

        Scanner scanner = new Scanner(System.in);

        do {
            String line = scanner.nextLine();
            if (line.equals(EXIT_CMD)) {
                break;
            }

            if (line.equals(HELP_CMD)) {
                System.out.println("The program calculates the sum of numbers");
                continue;
            }

            if (line.isEmpty()) {
                continue;
            }

            String[] splitLine = line.split(" ");
            if (splitLine.length == 1) {
                System.out.println(splitLine[0]);
            } else {
                int sum = 0;

                for (String integerString : splitLine) {
                    sum = sum + Integer.parseInt(integerString);
                }
                System.out.println(sum);
            }
        } while (true);

        System.out.println("Bye!");
    }
}
