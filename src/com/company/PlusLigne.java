package com.company;

public class PlusLigne extends Exception{
    public PlusLigne(){
        super("Une commande ne peut pas etre ecrite dans plus qu'une ligne");
    }
}
