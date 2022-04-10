package stage2.partOne.taskTwo;

import java.util.Scanner;

public class TaskTwo {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите количество дисков: ");
        int size = scan.nextInt();
        Towers.makeTowers(size);
    }
}
