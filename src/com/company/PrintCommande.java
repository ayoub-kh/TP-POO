package com.company;

public class PrintCommande extends Commande{
    private Type_Commande typeCommande = Type_Commande.print;

    public PrintCommande() {
        this.typeCommande = Type_Commande.print;
    }

    @Override
    void excuterCommande() {

    }
}
