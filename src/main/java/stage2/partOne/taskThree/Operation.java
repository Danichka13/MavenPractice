package stage2.partOne.taskThree;

import java.util.*;

public class Operation {

    private static final TreeMap<Character, String> ALPHABET = new TreeMap<>();
    private static int size;

    public static void compress(String str) {
        int count = 0;
        BitSet bitset = new BitSet();

        for (int i = 0; i < str.length(); i++) {
            for (HashMap.Entry temp : ALPHABET.entrySet()) {
                if (str.charAt(i) == (char) temp.getKey()) {
                    String tempValue = (String) temp.getValue();
                    for (int j = 0; j < tempValue.length(); j++) {
                        if (tempValue.charAt(j) == '1') {
                            bitset.set(count);
                        }
                        count++;
                    }
                }
            }
        }
        String doubleView = "";
        for (int i = 0; i < getSize(str) * str.length(); i++) {
            if (bitset.get(i)) {
                doubleView += "1";
            } else {
                doubleView += "0";
            }
        }
        System.out.println("Битовое представление в BitSet: " + bitset);
        System.out.println("Двоичный вид: " + doubleView + "\n");
    }

    public static void decompress(String str) {
        // 11001010
        BitSet bitset = new BitSet();
        String temp = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                bitset.set(i);
            }
        }
        for (int i = 0; i < str.length(); ) {
            String numBin = "";
            while (size != numBin.length()) {
                if (bitset.get(i)) {
                    numBin += "1";
                } else {
                    numBin += "0";
                }
                i++;
            }
            boolean flag = false;
            for (HashMap.Entry pair : ALPHABET.entrySet()) {
                if (numBin.equals(pair.getValue())) {
                    temp += pair.getKey();
                    flag = true;
                }
            }
            if (!flag) {
                System.out.println("\nТакой буквы нет [" + numBin + "], проверьте двоичный код");
                return;
            }
            if (i == str.length()) {
                System.out.println("Битовое представление в BitSet: " + bitset);
                System.out.println("Строка: " + temp + "\n");
            }
        }
    }

    public static void makeAlphabet(String str) {
        int binary = 0;
        char[] arrCh = removeDub(str);
        size = getSize(str);
        Arrays.sort(arrCh);

        for (char c : arrCh) {
            String binStr = String.valueOf(binary);

            while (binStr.length() != size) {
                binStr = "0" + binStr;
            }
            ALPHABET.put(c, binStr);
            binary = addBinNum(binary);

        }
        System.out.println("Алфавит: " + ALPHABET);
    }

    public static int addBinNum(int num) {
        String strToInt = "";
        int end = 0;
        int secondNum = 1;
        int temp;

        while (num != 0 || secondNum != 0) {
            temp = (num % 10 + secondNum % 10 + end) % 2;
            strToInt += temp;

            end = (num % 10 + secondNum % 10 + end) / 2;
            num /= 10;
            secondNum /= 10;
        }
        if (end != 0) {
            strToInt += end;
        }
        String[] reverseStrToInt = strToInt.split("");
        String str = "";
        for (int i = strToInt.length() - 1; i >= 0; i--) {
            str += reverseStrToInt[i];
        }
        return Integer.parseInt(str);
    }

    public static int getSize(String str) {
        return (int) Math.round(Math.sqrt(removeDub(str).length));
    }

    public static char[] removeDub(String str) {
        String temp = "";
        String[] arrStr = str.split("");
        for (int i = 0; i < str.length(); i++) {
            if (!temp.contains(String.valueOf(str.charAt(i)))) {
                temp += String.valueOf(str.charAt(i));
            }
        }
        return temp.toCharArray();
    }
}
