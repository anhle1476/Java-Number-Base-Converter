package converter;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<String> digit = Arrays.asList("0123456789abcdefghijklmnopqrstuvwxy".split(""));

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter number's base: ");
            int sourceBase = scanner.nextInt();
            System.out.print("Enter number: ");
            String[] num = scanner.next().split("\\.");
            System.out.print("Enter new base: ");
            int newBase = scanner.nextInt();

            // Input validation
            if (sourceBase < 1 || sourceBase > 36 || newBase < 1 || newBase > 36) {
                System.out.println("Error: The base must be integer from 1 to 36.");
                return;
            }
            if (num.length < 1 || num.length > 2) {
                System.out.println("Error: Number invalid.");
                return;
            }

            // Convert
            StringBuilder result = new StringBuilder("Result: ");

            convertIntegerPart(result, num[0], sourceBase, newBase);
            if (num.length == 2 && newBase != 1) {
                result.append(".");
                convertFractionPart(result, num[1], sourceBase, newBase);
            }

            System.out.println(result.toString());
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    public static void convertIntegerPart(StringBuilder newNum, String intPart, int sourceBase, int newBase) {
        int intPartToDec = sourceBase > 1 ? Integer.parseInt(intPart, sourceBase) : intPart.length();
        newNum.append(newBase > 1 ? Integer.toString(intPartToDec, newBase) : "1".repeat(intPartToDec));
    }

    public static void convertFractionPart(StringBuilder newNum, String fractionPart, int sourceBase, int newBase) {
        String[] frPart = fractionPart.split("");

        // convert to base 10
        double frPartInDec = 0;
        for (int i = 0; i < frPart.length; i++) {
            frPartInDec += (digit.indexOf(frPart[i]) / Math.pow(sourceBase, i + 1));
        }

        // convert to new base
        for (int i = 1; i <= 5; i++) {
            double multiplyByNewBase = frPartInDec * newBase;
            int newIntPart = (int) multiplyByNewBase;
            double newFrPart = multiplyByNewBase  - (double) newIntPart;
            newNum.append(digit.get(newIntPart));
            frPartInDec = newFrPart;
        }
    }
}
