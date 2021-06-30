package com.company;

public class LigneCommande {
    private Commande commande;
    private Expression expr;

    public LigneCommande(String ligneCommande) throws TypeCommandeException {
        String[] parts = ligneCommande.split(" ",2);
        String typeCommande = parts[0]; // type de commande
        if(typeCommande.equals("let")) {
            commande = new LetCommande();
        }
        else if(typeCommande.equals("print")) {
            commande = new PrintCommande();
        }
        else{
            throw new TypeCommandeException();
        }

    }

    public void EvaluerLigneCommande() {
    };
}


class TypeCommandeException extends Exception{
    @Override
    public String getMessage() {
        return "Erreur: Commande non existante";
    }
}