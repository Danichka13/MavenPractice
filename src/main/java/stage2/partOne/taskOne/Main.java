package stage2.partOne.taskOne;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Введите своё выражение:");
        String entrance = scan.nextLine();
        CheckBrackets.control(entrance);
    }
}
