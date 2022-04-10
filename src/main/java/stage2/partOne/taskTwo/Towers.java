package stage2.partOne.taskTwo;

import java.util.Stack;

public class Towers {
    private static Stack<Integer>[] columnTower = new Stack[3];

    private static void printTowers() {
        System.out.println(columnTower[0] +
                "     " + columnTower[1] + "     " + columnTower[2] + "\n\n");
    }

    public static void makeTowers(int size) {
        columnTower[0] = new Stack<>();
        columnTower[1] = new Stack<>();
        columnTower[2] = new Stack<>();
        for (int i = 1; i <= size; i++) {
            columnTower[0].push(i);
        }
        printTowers();
        reconstructionTowers(0, 1, 2, size);
    }

    private static void reconstructionTowers(int a, int b, int c, int size) {
        if (size > 0) {
            reconstructionTowers(a, c, b, size - 1);

            columnTower[c].push(columnTower[a].pop());

            printTowers();
            reconstructionTowers(b, a, c, size - 1);
        }
    }
}
