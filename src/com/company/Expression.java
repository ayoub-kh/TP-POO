package com.company;

public class Expression {
    private String contenu;
    private double valeur;

    public Expression(String contenu) {
        this.contenu = contenu;
    }

    public void EvaluaterExpression(){}
}
class ExpressionNonTrouveeException extends Exception{
    @Override
    public String getMessage() {
        return "Erreur: Expression non trouvee";
    }
}