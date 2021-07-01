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
        }
        System.out.println(Arrays.toString("let x = 3+5 * 3 - 9 + 4".split(" ")));
        StringTokenizer tokenizer = new StringTokenizer("let x = 3+5 * 3+5/78");
        System.out.println(tokenizer.nextToken());
        tokenizer = new StringTokenizer(tokenizer.nextToken("").replace(" ", ""));
        System.out.println(tokenizer.nextToken("="));
        tokenizer = new StringTokenizer(tokenizer.nextToken());
        while (tokenizer.hasMoreTokens()) {
            System.out.println(tokenizer.nextToken("+"));
        }*/
        String[] k = "let x = -3+5 * 3 - 9 + ".split(" ", 2);
        System.out.println(k[0] + "/" + k[1]);
        k = k[1].replace(" ", "").split("=", 2);
        System.out.println(k[0] + "/" + k[1]);
        while (true){
            System.out.println(k[1].indexOf("+") > k[1].indexOf("-"));
            k = k[1].split("[-+]", 2);
            if (k.length == 1) break;
            if (k[1].equals("")) System.out.println("tnaket");
            System.out.println(k[0] + "/" + k[1]);
        }
    }
}
