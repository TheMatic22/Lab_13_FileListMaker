import java.util.Scanner;

public class SafeInput {
    private static Scanner in = new Scanner(System.in);

    public static String getRegExString(String prompt, String regEx) {
        String input;
        boolean valid;
        do {
            System.out.print(prompt);
            input = in.nextLine();
            valid = input.matches(regEx);
            if (!valid) {
                System.out.println("Invalid input. Try again.");
            }
        } while (!valid);
        return input;
    }

    public static int getRangedInt(String prompt, int low, int high) {
        int value;
        do {
            System.out.print(prompt);
            while (!in.hasNextInt()) {
                System.out.println("Invalid input. Enter an integer.");
                in.nextLine();
                System.out.print(prompt);
            }
            value = in.nextInt();
            in.nextLine(); // clear buffer
        } while (value < low || value > high);
        return value;
    }

    public static String getNonZeroLenString(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = in.nextLine();
        } while (input.trim().isEmpty());
        return input;
    }

    public static boolean getYNConfirm(String prompt) {
        String input;
        do {
            System.out.print(prompt + " [Y/N]: ");
            input = in.nextLine().trim().toUpperCase();
        } while (!input.equals("Y") && !input.equals("N"));
        return input.equals("Y");
    }
}
