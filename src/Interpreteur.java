import java.util.*;

public class Interpreteur {
    //region Attributs
    private final Map<String, Double> tableDeSymbol = new HashMap<>();
    private String ligne;
    //endregion

    //region Constructeur
    public Interpreteur() {
        // initialiser la table des symboles à les fonctions
        tableDeSymbol.put("sin", 1.0);
        tableDeSymbol.put("cos", 2.0);
        tableDeSymbol.put("tan", 3.0);
        tableDeSymbol.put("abs", 4.0);
        tableDeSymbol.put("sqrt", 5.0);
        tableDeSymbol.put("log", 6.0);
    }
    //endregion

    //region Getters and Setters
    public Map<String, Double> getTableDeSymbol() {
        return tableDeSymbol;
    }

    public String getLigne() {
        return ligne;
    }

    public void setLigne(String ligne) {
        this.ligne = ligne;
    }
    //endregion

    //region Analyseurs
    public String analyserCommande(){  // analyser une ligne de commande et retourne le resultat de la commande
        String[] fonc = {"let", "print", "sin", "cos", "tan", "abs", "sqrt", "log"};  // liste des mots clés reservés pour l'intérpreteur
        try {
            String[] commandeLigne = ligne.split(" ", 2);  // diviser la ligne de commande en [0]: la commande et [1]: l'expression
            if (commandeLigne[0].equals("print")) {
                return "La valeur est : " + analyserExpression(commandeLigne[1].replace(" ", ""));  // retourner le resultat du "print"
            } else if (commandeLigne[0].equals("let")) {
                commandeLigne = commandeLigne[1].replace(" ", "").split("=", 2);  // diviser l'expression de la commande en [0]: la variable et [1]: l'expression
                String varNom = commandeLigne[0];
                if (Arrays.asList(fonc).contains(varNom)) throw new Exception("Erreur: le nom du variable ne peut pas etre un des mots clés du l'interpreteur");
                tableDeSymbol.put(varNom, analyserExpression(commandeLigne[1].replace(" ", "")));  // ajouter/MAJ du variable à la table des symboles
                return "Ok";
            } else throw new Exception("Erreur : Commande incorrect.");
        } catch (IndexOutOfBoundsException e) {
            return "Erreur : Expression non trouvée";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public double analyserExpression(String expression) throws Exception {
        String[] termes;
        Expression terme;
        if (!expression.contains("+") && !expression.contains("-")) terme = analyserTerme(expression);  // un seul terme
        else {
            boolean somme = expression.indexOf("+") < expression.indexOf("-");  // pour indiquer si c'est une addition ou soustraction
            termes = expression.split("[-+]", 2);
            if (!somme && termes[0].equals("")) terme = null;  // si l'expression commence avec un '-'
            else terme = analyserTerme(termes[0]);  // sinon, analyser le premier terme
            while (termes.length != 1) {  // parcourir tous les termes
                if (somme) {
                    somme = termes[1].indexOf("+") < termes[1].indexOf("-");
                    termes = termes[1].split("[-+]", 2);
                    terme = new Addition();  // avec terme et termes[0]
                } else {
                    somme = termes[1].indexOf("+") < termes[1].indexOf("-");
                    termes = termes[1].split("[-+]", 2);
                    terme = new Soustraction();  // avec terme et termes[0]
                }
            }
        }
        return 0;
    }

    public Expression analyserTerme(String terme) throws Exception {
        if (terme.equals("")) throw new Exception("Erreur : Expression erronée");
        String[] facteurs;
        Expression facteur;
        if (!terme.contains("*") && !terme.contains("/")) facteur = analyserFacteur(terme);  // un seul facteur
        else {
            boolean mul = terme.indexOf("*") < terme.indexOf("/");  // pour indiquer si c'est une multiplication ou division
            facteurs = terme.split("[*/]", 2);
            facteur = analyserFacteur(facteurs[0]);  // analyser le premier terme
            while (facteurs.length != 1) {  // parcourir tous les termes
                if (mul) {
                    mul = facteurs[1].indexOf("*") < facteurs[1].indexOf("/");
                    facteurs = facteurs[1].split("[*/]", 2);
                    facteur = new Multiplication();  // avec terme et termes[0]
                } else {
                    mul = facteurs[1].indexOf("*") < facteurs[1].indexOf("/");
                    facteurs = facteurs[1].split("[*/]", 2);
                    facteur = new Division();  // avec terme et termes[0]
                }
            }
        }
        return new Addition();
    }

    public Expression analyserFacteur(String facteur) throws Exception {
        if (facteur.equals("")) throw new Exception("Erreur : Expression erronée");
        String[] elements;
        Expression element;
        if (!facteur.contains("^")) element = analyserElement(facteur);  // un seul element
        else {
            elements = facteur.split("[\\^]", 2);
            element = analyserElement(elements[0]);  // analyser le premier element
            while (elements.length != 1) {  // parcourir tous les elements
                elements = elements[1].split("[\\^]", 2);
                element = new Puissance();  // avec terme et termes[0]
            }
        }
        return new Addition();
    }

    public Expression analyserElement(String element) {
        return new Addition();
    }
    //endregion

    static class IncorrectExpression extends Exception {
        public IncorrectExpression(String message) {
            super(message);
        }
    }
}
