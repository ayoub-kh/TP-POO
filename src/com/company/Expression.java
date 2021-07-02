package com.company;

public class Expression {
    private String contenu;
    private double valeur;

    public Expression(String contenu) {
        this.contenu = contenu;
    }

    public void evaluaterExpression(){
        Evalueur e=new Evalueur();
        try{
            this.valeur=e.evalue(this.contenu);
            System.out.println("="+valeur);
        }
        catch (ErreurSyntaxeException ex){
            System.out.println("Erreur: "+ex.getMessage());
        }
        catch (ParenthèseOuvranteManquanteException ex){
            System.out.println("Erreur: "+ex.getMessage());
        }
        catch (ParenthèseFermanteManquanteException ex) {
            System.out.println("Erreur: "+ex.getMessage());
        }
        catch (VariableInconnueException ex){
            System.out.println("Erreur: "+ex.getMessage());
        }
        catch (FonctionInconnueException ex){
            System.out.println("Erreur: "+ex.getMessage());
        }
        catch (ErreurException ex){
            System.out.println(ex.getMessage());
        }
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }
}
class ExpressionNonTrouveeException extends Exception{
    @Override
    public String getMessage() {
        return "Erreur: Expression non trouvee";
    }
}