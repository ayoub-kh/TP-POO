package com.company;

public class LetCommande extends Commande{
    private Type_Commande typeCommande ;

    public LetCommande() {
        this.typeCommande = Type_Commande.let;
    }

    @Override
    void excuterCommande() {

    }
}
