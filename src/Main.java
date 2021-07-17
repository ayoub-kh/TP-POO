import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Interpreteur i = new Interpreteur();
        System.out.println("Entrez vos commandes. Tapez 'end' pour terminer votre programme.\n" +
                "Une commande doit être de la forme:\n" +
                "  let <variable> = <expression>\n" +
                "ou\n" +
                "  print <expression>\n" +
                "ou\n" +
                "  var (pour afficher la liste des variables stockées)");
        while (true) {
            System.out.print("> ");
            String result = i.analyserCommande(scanner.nextLine());
            System.out.println(result);
            if (result.equals("End")) break;
        }
    }
}
