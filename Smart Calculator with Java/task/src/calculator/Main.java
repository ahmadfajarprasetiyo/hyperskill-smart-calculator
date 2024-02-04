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

            if (line.charAt(0) == '/') {
                System.out.println("Unknown command");
                continue;
            }

            while (line.contains("  ") || line.contains("--") || line.contains(" +")) {
                line = line.replaceAll(" \\+", " ");
                line = line.replaceAll(" {2}", " ");
                line = line.replaceAll("--", "");
            }

            String[] splitLine = line.split(" ");
            try {
                if (splitLine.length == 1) {
                    System.out.println(Integer.parseInt(splitLine[0]));
                } else {
                    int sum = getSum(splitLine);
                    System.out.println(sum);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid expression");
            }
        } while (true);

        System.out.println("Bye!");
    }

    private static int getSum(String[] splitLine) throws NumberFormatException {
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
