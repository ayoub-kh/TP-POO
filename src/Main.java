import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Interpreteur i = new Interpreteur();
        while (true) {
            String result = i.analyserCommande(scanner.nextLine());
            System.out.println(result);
            if (result.equals("Fin du programme")) break;
        }
    }
}
