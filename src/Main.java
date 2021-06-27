import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*Map<String, Double> tableDeSymbol = new HashMap<>();
        tableDeSymbol.put("sin", 1.0);
        tableDeSymbol.put("cos", 2.0);
        tableDeSymbol.put("tan", 3.0);
        tableDeSymbol.put("abs", 4.0);
        tableDeSymbol.put("tan", 5.0);
        tableDeSymbol.put("abs", 6.0);
        tableDeSymbol.put("sqrt", 5.0);
        tableDeSymbol.put("log", 6.0);
        for (String f : tableDeSymbol.keySet()) {
            System.out.println(f + " val: " +tableDeSymbol.get(f));
        } */
        System.out.println(Arrays.toString("let x = 3+5 * 3".split(" ")));
        StringTokenizer tokenizer = new StringTokenizer("let x = 3+5 * 3+5/78");
        System.out.println(tokenizer.nextToken());
        tokenizer = new StringTokenizer(tokenizer.nextToken("").replace(" ", ""));
        System.out.println(tokenizer.nextToken("="));
        tokenizer = new StringTokenizer(tokenizer.nextToken());
        while (tokenizer.hasMoreTokens()) {
            System.out.println(tokenizer.nextToken("+"));
        }

    }
}
