package stage2.partOne.taskThree;

import java.util.Scanner;

public class TaskThree {
    public static void main(String[] args) {

        boolean needIteration = true;
        while (needIteration) {
            needIteration = menu();
        }
    }

    public static boolean menu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Выберите операцию:\n" +
                "1) Compress\n" +
                "2) Decompress\n" +
                "0) Exit");
        String flag = scan.nextLine();
        switch (flag) {
            case "1": {
                System.out.println("Введите текст:");
                String str = scan.nextLine();
                Operation.makeAlphabet(str);
                Operation.compress(str);
                return true;
            }
            case "2": {
                System.out.println("Введите алфавит: ");
                String str = scan.nextLine();
                Operation.makeAlphabet(str);
                System.out.println("Введите бинарный код: ");
                str = scan.nextLine();
                Operation.decompress(str);
                return true;
            }
            case "0": {
                return false;
            }
            default: {
                System.out.println("Проверьте введённое значение:");
                return true;
            }
        }
    }
}
