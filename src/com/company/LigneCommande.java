package com.company;

public class LigneCommande {
    private Commande commande;
    private Expression expr;

    public LigneCommande(String ligneCommande) throws TypeCommandeException{
        String[] parts = ligneCommande.split(" ",2);
        String typeCommande = parts[0]; // type de commande
        if(typeCommande.equals("let")) {
            this.commande = new LetCommande();
        }
        else if(typeCommande.equals("print")) {
            this.commande = new PrintCommande();
            this.expr = new Expression(parts[1]);
        }
        else throw new TypeCommandeException();
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }
}


class TypeCommandeException extends Exception{
    @Override
    public String getMessage() {
        return "Commande non existante";
    }
}
