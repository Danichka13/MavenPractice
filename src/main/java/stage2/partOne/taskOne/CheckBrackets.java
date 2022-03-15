package stage2.partOne.taskOne;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class CheckBrackets {
    private static final List<String> firstBrack = Arrays.asList("(", "[", "{");
    private static final List<String> secondBrack = Arrays.asList(")", "]", "}");

    public static String getFirst(String str) {
        if (str.equals(")")) {
            return "(";
        } else if (str.equals("]")) {
            return "[";
        } else {
            return "{";
        }
    }

    public static String getSecond(String str) {
        if (str.equals("(")) {
            return ")";
        } else if (str.equals("[")) {
            return "]";
        } else {
            return "}";
        }
    }

    // (abc*(a)-{[a/c+a,b,c}]
    // ( ( ) { [ } ]

    public static void control(String str) {
        Stack<String> stack = new Stack<>();
        String[] arrCh = onlyBrackets(str);

        for (String ch : arrCh) {
            if (firstBrack.contains(ch)) {
                stack.push(ch);
            } else if (secondBrack.contains(ch)) {
                if (stack.empty()) {
                    System.out.println("Нет открывающей " + getFirst(ch));
                    return;
                } else {
                    if (stack.peek().equals(getFirst(ch))) {
                        stack.pop();
                    } else {
                        System.out.println("Нет закрывающей " + getSecond(stack.peek()));
                        return;
                    }
                }
            }
        }
        if (!stack.empty()) {
            System.out.println("Нет закрывающей " + getSecond(stack.peek()));
            return;
        }
        System.out.println("В строке у всех открывающих скобок есть закрывающие скобки!");
    }

    public static String[] onlyBrackets(String str) {
        String[] arrTemp = str.split("");
        String temp = "";
        for (int i = 0; i < arrTemp.length; i++) {
            if (firstBrack.contains(arrTemp[i]) || secondBrack.contains(arrTemp[i])) {
                temp += arrTemp[i];
            }
        }
        return temp.split("");
    }
}
