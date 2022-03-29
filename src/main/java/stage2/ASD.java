package stage2;

import java.util.Arrays;
import java.util.Scanner;

public class ASD {

    public static void main(String[] args) {
        System.out.println("Введите количество элементов массива: ");
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int key[] = new int[n];
        long start = 0, finish = 0;

        for (int i = 0; i < key.length; i++) {
            key[i] = (int) (Math.random() * 100);
        }
        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < key.length; i++) {
                if (key[i] < key[i - 1]) {
                    int tmp = key[i];
                    key[i] = key[i - 1];
                    key[i - 1] = tmp;
                    needIteration = true;
                }
            }
        }
        System.out.println(Arrays.toString(key));
        System.out.println("Введите ключ для поиска:");
        int search = scan.nextInt();
        int left = 0, right = n - 1;
        start = System.nanoTime();
        search = interpolSearch(key, left, right, search);
        finish = System.nanoTime();
        if (search != -1) {
            System.out.println("Аргумент найден: " + (search + 1));
        } else {
            System.out.println("Аргумент не найден!");
        }
        System.out.println("Время выполнения поставленной задачи: " + (finish - start));
    }

    public static int interpolSearch(int[] key, int left, int right, int search) {
        int k;

        if (key[left] <= search && key[right] >= search) {

            k = left + ((search - key[left]) * (right - left)) / (key[right] - key[left]);
            if (key[k] < search) {
                return interpolSearch(key, k + 1, right, search);
            }
            if (key[k] > search) {
                return interpolSearch(key, left, k - 1, search);
            }
            if (search == key[k]) {
                return k;
            }
        } else {
            return -1;
        }
        return -1;
    }
}