package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final String EXIT_CMD = "/exit";
        Map<String, Integer> mapVariable = new HashMap<>();

        Scanner scanner = new Scanner(System.in);

        do {
            String line = scanner.nextLine();
            if (line.equals(EXIT_CMD)) {
                break;
            }

            if (line.isEmpty()) {
                continue;
            }

            if (line.charAt(0) == '/') {
                command(line);
                continue;
            }

            if (line.contains("=")) {
                assignment(line, mapVariable);
                continue;
            }

            try {
                int sum = getSum(line, mapVariable);
                System.out.println(sum);
            } catch (NumberFormatException e) {
                System.out.println("Invalid expression");
            } catch (NullPointerException e) {
                System.out.println("Unknown variable");
            } catch (InvalidIdentifier e) {
                System.out.println("Invalid identifier");
            }
        } while (true);

        System.out.println("Bye!");
    }

    private static void command(String cmd) {
        final String HELP_CMD = "/help";

        if (cmd.equals(HELP_CMD)) {
            System.out.println("The program calculates the sum of numbers");
        } else {
            System.out.println("Unknown command");
        }
    }

    private static void assignment(String line, Map<String, Integer> mapVariable) {
        String[] splitLine = line.split("=");
        if (splitLine.length != 2 | !isVariable(splitLine[0].trim())) {
            System.out.println("Invalid assignment");
        }
        String var = splitLine[0].trim();
        if (isInvalidVariable(var)) {
            System.out.println("Invalid identifier");
        }
        int res = 0;
        String val = splitLine[1].trim();
        try {
            res = getSum(val, mapVariable);
            mapVariable.put(var, res);
        } catch (NumberFormatException e) {
            System.out.println("Invalid expression");
        } catch (NullPointerException e) {
            System.out.println("Unknown variable");
        } catch (InvalidIdentifier e) {
            System.out.println("Invalid assignment");
        }
    }

    private static int getSum(String line, Map<String, Integer> mapVariable) throws NumberFormatException, InvalidIdentifier, NullPointerException {
        while (line.contains("  ") || line.contains("--") || line.contains("++")) {
            line = line.replaceAll("\\+\\+", "");
            line = line.replaceAll(" {2}", " ");
            line = line.replaceAll("--", "");
        }

        String[] splitLine = line.split(" ");

        if (splitLine.length == 1) {
            return convertOneWordToInt(splitLine[0], mapVariable);
        }

        int sum = 0;
        boolean nextNegative = false;
        for (String integerString : splitLine) {
            if (integerString.equals("+")) {
                continue;
            }

            if (integerString.equals("-")) {
                nextNegative = true;
                continue;
            }

            if (nextNegative) {
                sum = sum - convertOneWordToInt(integerString, mapVariable);
                nextNegative = false;
            } else {
                sum = sum + convertOneWordToInt(integerString, mapVariable);
            }

        }
        return sum;
    }

    private static int convertOneWordToInt(String word, Map<String, Integer> mapVariable)  throws NumberFormatException, InvalidIdentifier, NullPointerException {
        if (!isVariable(word)) {
            return Integer.parseInt(word);
        }

        if (isInvalidVariable(word)) {
            throw new InvalidIdentifier();
        }

        return mapVariable.get(word);
    }

    private static boolean isVariable (String str) {
        return str.matches("[a-zA-Z]+");
    }

    private static boolean isInvalidVariable (String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }

        return false;
    }
}
