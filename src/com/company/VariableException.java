package com.company;

public class VariableException extends Exception{
    public VariableException(){
        super("Variable non déclarée");
    }
}
