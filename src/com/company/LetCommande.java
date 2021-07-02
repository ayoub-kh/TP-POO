package com.company;

public class LetCommande extends Commande{
    private Type_Commande typeCommande ;

    public LetCommande() {
        this.typeCommande = Type_Commande.let;
    }

    public Type_Commande getTypeCommande() {
        return typeCommande;
    }

    public void setTypeCommande(Type_Commande typeCommande) {
        this.typeCommande = typeCommande;
    }

    @Override
    void excuterCommande() {

    }
}
