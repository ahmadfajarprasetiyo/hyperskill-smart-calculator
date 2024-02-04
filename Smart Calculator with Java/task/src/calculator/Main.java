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

            line = line.replaceAll("\\+", "");

            while (line.contains("  ") || line.contains("--")) {
                line = line.replaceAll(" {2}", " ");
                line = line.replaceAll("--", "");
            }

            String[] splitLine = line.split(" ");
            if (splitLine.length == 1) {
                System.out.println(splitLine[0]);
            } else {
                int sum = getSum(splitLine);
                System.out.println(sum);
            }
        } while (true);

        System.out.println("Bye!");
    }

    private static int getSum(String[] splitLine) {
        int sum = 0;
        boolean nextNegative = false;
        for (String integerString : splitLine) {
            if (integerString.equals("-")) {
                nextNegative = true;
                continue;
            }

            if (nextNegative) {
                sum = sum - Integer.parseInt(integerString);
                nextNegative = false;
            } else {
                sum = sum + Integer.parseInt(integerString);
            }

        }
        return sum;
    }
}
