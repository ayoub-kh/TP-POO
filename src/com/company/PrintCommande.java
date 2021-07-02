package com.company;

public class PrintCommande extends Commande{
    private Type_Commande typeCommande = Type_Commande.print;

    public PrintCommande() {
        this.typeCommande = Type_Commande.print;
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
