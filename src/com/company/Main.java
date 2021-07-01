package com.company;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws TypeCommandeException {
        System.out.println("Entrez vos commandes. Tapez end pour terminer votre programme.\n" +
                "Une commande doit être de la forme\n" +
                "let <variable> = <expression>\n" +
                "ou\n" +
                "print <expression>");
        Scanner sc = new Scanner(System.in);
        System.out.print(">     ");
        String ligne = sc.nextLine();  // Read user input
        while (ligne!="end"){
            try{
                LigneCommande ligneCommande = new LigneCommande(ligne);
                System.out.println("Commande juste");
            }
            catch (TypeCommandeException e){
                System.out.println("Erreur: "+e.getMessage());
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Erreur: Expression non trouvee");
            }
            System.out.print(">     ");
            ligne = sc.nextLine();
        }
    }
}
