package calculator;

import java.math.BigInteger;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        final String EXIT_CMD = "/exit";
        Map<String, BigInteger> mapVariable = new HashMap<>();

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
                BigInteger sum = getSum(line, mapVariable);
                if (sum == null) {
                    System.out.println("Unknown variable");
                } else {
                    System.out.println(sum);
                }
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

    private static void assignment(String line, Map<String, BigInteger> mapVariable) {
        String[] splitLine = line.split("=");
        if (splitLine.length != 2 | !isVariable(splitLine[0].trim())) {
            System.out.println("Invalid assignment");
        }
        String var = splitLine[0].trim();
        if (isInvalidVariable(var)) {
            System.out.println("Invalid identifier");
        }
        BigInteger res = BigInteger.ZERO;
        String val = splitLine[1].trim();
        try {
            res = getSum(val, mapVariable);
            if (res == null) {
                System.out.println("Unknown variable");
            } else {
                mapVariable.put(var, res);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid expression");
        } catch (NullPointerException e) {
            System.out.println("Unknown variable");
        } catch (InvalidIdentifier e) {
            System.out.println("Invalid assignment");
        }
    }

    private static BigInteger getSum(String line, Map<String, BigInteger> mapVariable) throws NumberFormatException, InvalidIdentifier, NullPointerException {
        if (line.contains("//") || line.contains("**")) {
            throw new NumberFormatException();
        }

        line = line.replaceAll("\\s+", "");
        while ( line.contains("--") || line.contains("++")) {
            line = line.replaceAll("\\+\\+", "+");
            line = line.replaceAll("--", "+");
            line = line.replaceAll("-\\+", "-");
            line = line.replaceAll("\\+-", "-");
        }

        String[] tokens = line.split("(?<=[-+*/()])|(?=[-+*/()])");


        // Stack to hold numbers and operators
        Stack<BigInteger> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        // Operator precedence map
        Map<Character, Integer> precedence = new HashMap<>();
        precedence.put('+', 1);
        precedence.put('-', 1);
        precedence.put('*', 2);
        precedence.put('/', 2);

        if (tokens.length == 1) {
            return convertOneWordToInt(tokens[0], mapVariable);
        }

        for (String token : tokens) {
            if (token.equals("(")) {
                operators.push('(');
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    evaluateOperation(numbers, operators);
                }

                if (operators.isEmpty()) {
                    throw new NumberFormatException();
                }
                operators.pop();
            } else if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")){
                while (!operators.isEmpty() && !operators.peek().equals('(') && precedence.get(operators.peek()) >= precedence.get(token.charAt(0))) {
                    evaluateOperation(numbers, operators);
                }
                operators.push(token.charAt(0));
            } else {
                numbers.push(convertOneWordToInt(token, mapVariable));
            }
        }

        // Evaluate any remaining operators
        while (!operators.isEmpty()) {
            try {
                evaluateOperation(numbers, operators);
            } catch (NumberFormatException e) {
                throw new NumberFormatException();
            }

        }

        // The result is the only number remaining on the numbers stack
        return numbers.pop();
    }

    private static BigInteger convertOneWordToInt(String word, Map<String, BigInteger> mapVariable)  throws InvalidIdentifier, NullPointerException, IllegalArgumentException {
        if (!isVariable(word)) {
            return new BigInteger(word);
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

    private static void evaluateOperation(Stack<BigInteger> numbers, Stack<Character> operators) throws NumberFormatException {
        char operator = operators.pop();
        BigInteger operand2 = numbers.pop();
        BigInteger operand1 = null;

        if (!numbers.isEmpty()) {
            operand1 = numbers.pop();
        }

        BigInteger result;
        switch (operator) {
            case '+':
                result = operand1.add(operand2);
                break;
            case '-':
                if (operand1 == null) {
                    result = operand2.negate();
                    break;
                }
                result = operand1.subtract(operand2);
                break;
            case '*':
                result = operand1.multiply(operand2);
                break;
            case '/':
                result = operand1.divide(operand2);
                break;
            default:
                throw new NumberFormatException();
        }
        numbers.push(result);
    }
}
