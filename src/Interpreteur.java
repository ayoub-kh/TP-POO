import java.util.*;

public class Interpreteur {
    //region Attributs
    private static final String[] fonc = {"let", "print", "end", "var", "sin", "cos", "tan", "abs", "sqrt", "log"};  // liste des mots clés reservés pour l'intérpreteur
    private final Map<String, Double> tableDeSymbol = new HashMap<>();  // table des symboles
    private static final Map<Integer, String> sousExpressions = new HashMap<>();  // une liste pour stocker les sous expressions entre parenthéses
    private static int nb = 0;  // indice pour indiquer le nombre des sous expressions
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
    //endregion

    //region Analyseurs
    public String affichierVariables() {
        String result = "";
        for (String key : tableDeSymbol.keySet()) {
            if (Arrays.asList(fonc).contains(key)) continue;
            result = result.concat(key + " == " + tableDeSymbol.get(key).toString() + "\n");
        }
        return result;
    }

    public String analyserCommande(String ligne){  // analyser une ligne de commande et retourne le resultat de la commande
        sousExpressions.clear();
        nb = 0;
        try {
            String[] commandeLigne = ligne.split("\\s++", 2);  // diviser la ligne de commande en [0]: la commande et [1]: l'expression
            switch (commandeLigne[0]) {
                case "var":
                    String vars = affichierVariables();
                    if (vars.equals("")) return "Aucune variable déclarée pour le moment.";
                    return "Les variables stockées :\n" + vars;
                case "print":
                    return "La valeur est : " + analyserExpression(commandeLigne[1].replace(" ", ""));  // retourner le resultat du "print"

                case "let":
                    commandeLigne = commandeLigne[1].split("=", 2);  // diviser l'expression de la commande en [0]: la variable et [1]: l'expression
                    String varNom = commandeLigne[0].stripTrailing();
                    if (Arrays.asList(fonc).contains(varNom))
                        throw new Exception("Erreur: le nom du variable ne peut pas etre un nom du fonction ou nom d'une commande");
                    else if (!varNom.substring(0, 1).matches("^[a-zA-Z]"))
                        throw new Exception("Erreur: le nom du variable doit commencer avec un caractére");
                    else if (varNom.contains(" "))
                        throw new Exception("Erreur: le nom du variable ne doit pas contenir du blanc");
                    tableDeSymbol.put(varNom, analyserExpression(commandeLigne[1].replace(" ", "")));  // ajouter/MAJ du variable à la table des symboles
                    return "Ok";

                case "end":
                    return "Fin du programme";

                case "":
                    throw new Exception("Erreur : Commande introuvable.");

                default:
                    throw new Exception("Erreur : Commande incorrect.");
            }
        } catch (IndexOutOfBoundsException e) {
            return "Erreur : Expression non trouvée";
        } catch (AssertionError | Exception e) {
            return e.getMessage();
        }
    }

    public double analyserExpression(String expression) throws Exception {
        if (expression.equals("")) throw new Exception("Erreur : Expression manquante");
        String[] termes;
        Expression terme;
        while (expression.contains("(")) {  // on remplace tous les expression entre parenthéses par le charactére special '$<nb>$'
            String sousExp = analyserParenthese(expression);
            sousExpressions.put(nb, sousExp.substring(1, sousExp.length() - 1));  // ajouter la sous expression à la liste
            expression = expression.replace(sousExp, "$" + nb + "$");
            nb++;
        }

        if (!expression.contains("+") && !expression.contains("-")) terme = analyserTerme(expression);  // un seul terme
        else {
            boolean somme = !expression.contains("-") && expression.contains("+") || expression.contains("-") && expression.contains("+") && expression.indexOf("+") < expression.indexOf("-");  // pour indiquer si c'est une addition ou soustraction
            termes = expression.split("[-+]", 2);
            if (!somme && termes[0].equals("")) terme = null;  // si l'expression commence avec un '-'
            else terme = analyserTerme(termes[0]);  // sinon, analyser le premier terme
            while (termes.length != 1) {  // parcourir tous les termes
                if (somme) {
                    somme = !termes[1].contains("-") && termes[1].contains("+") || termes[1].contains("-") && termes[1].contains("+") && termes[1].indexOf("+") < termes[1].indexOf("-");
                    termes = termes[1].split("[-+]", 2);
                    terme = new Addition(terme, analyserTerme(termes[0]));  // avec terme et termes[0]
                } else {
                    somme = !termes[1].contains("-") && termes[1].contains("+") || termes[1].contains("-") && termes[1].contains("+") && termes[1].indexOf("+") < termes[1].indexOf("-");
                    termes = termes[1].split("[-+]", 2);
                    terme = new Soustraction(terme, analyserTerme(termes[0]));  // avec terme et termes[0]
                }
            }
        }
        if (terme == null) throw new Exception("Erreur : Expression erronée");
        return terme.evaluer(tableDeSymbol);
    }

    public Expression analyserTerme(String terme) throws Exception {
        if (terme.equals("")) throw new Exception("Erreur : Expression erronée");
        String[] facteurs;
        Expression facteur;
        if (!terme.contains("*") && !terme.contains("/")) facteur = analyserFacteur(terme);  // un seul facteur
        else {
            boolean mul = !terme.contains("/") && terme.contains("*") || terme.contains("/") && terme.contains("*") && terme.indexOf("*") < terme.indexOf("/");  // pour indiquer si c'est une multiplication ou division
            facteurs = terme.split("[*/]", 2);
            facteur = analyserFacteur(facteurs[0]);  // analyser le premier terme
            while (facteurs.length != 1) {  // parcourir tous les termes
                if (mul) {
                    mul = !facteurs[1].contains("/") && facteurs[1].contains("*") || facteurs[1].contains("*") && facteurs[1].contains("/") && facteurs[1].indexOf("*") < facteurs[1].indexOf("/");
                    facteurs = facteurs[1].split("[*/]", 2);
                    facteur = new Multiplication(facteur, analyserFacteur(facteurs[0]));  // avec terme et termes[0]
                } else {
                    mul = !facteurs[1].contains("/") && facteurs[1].contains("*") || facteurs[1].contains("*") && facteurs[1].contains("/") && facteurs[1].indexOf("*") < facteurs[1].indexOf("/");
                    facteurs = facteurs[1].split("[*/]", 2);
                    facteur = new Division(facteur, analyserFacteur(facteurs[0]));  // avec terme et termes[0]
                }
            }
        }
        return facteur;
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
                element = new Puissance(element, analyserElement(elements[0]));  // créer un element de puissance entre les deux élements
            }
        }
        return element;
    }

    public Expression analyserElement(String element) throws Exception {
        if (element.equals("")) throw new Exception("Erreur : Expression erronée");
        else if (element.contains(")")) throw new Exception("Erreur : Paranthése ouvrante manquante");
        else if (element.matches("[\\d.,]+")) {  // si c'est un nombre
            return new Nombre(Double.parseDouble(element.replace(",", ".")));
        } else if (!element.contains("$") && !Arrays.asList(fonc).contains(element)) {  // sinon si c'est un nom de variable
            return new Variable(element);
        } else if (element.startsWith("$") && element.endsWith("$")) {  // sinon si c'est une sous expression entre paranthéses
            int key = Integer.parseInt(element.substring(1, element.length() - 1));
            String sousExp = sousExpressions.get(key);
            sousExpressions.remove(key);
            return new Nombre(analyserExpression(sousExp));  // analyser l'expression entre les deux parenthéses
        } else {  // c'est une fonction ou incorrect element

            String[] args = element.split("\\$");  // args[0]: nom de fonction et args[1] l'expression du l'argument
            if (!args[0].equals("let") && !args[0].equals("print") && !args[0].equals("end") && Arrays.asList(fonc).contains(args[0])) {  // c'est une fonction
                int key = Integer.parseInt(args[1]);
                String sousExp = sousExpressions.get(key);
                sousExpressions.remove(key);
                return new Fonction(args[0], analyserExpression(sousExp), tableDeSymbol);  // recouperer la sous expression puis la passer avec le nom du fonction à un element de fonction
            } else throw new Exception("Erreur : nom de fonction incorrect");
        }
    }

    private String analyserParenthese(String expression) throws Exception {  // retourner la sous expression entre parenthéses du 'expression'
        int i, begin = i = expression.indexOf("(");  // recuperer l'index du premier '('
        Deque<Integer> pile = new ArrayDeque<>();
        pile.add(begin);
        try {
            while (!pile.isEmpty()) {  // parcourir la chaine
                i++;
                if (expression.charAt(i) == '(') pile.add(i);  // empiler si on trouve '('
                else if (expression.charAt(i) == ')') pile.removeLast();  // depiler si on trouve ')'
            }
            return expression.substring(begin, i + 1);
        } catch (IndexOutOfBoundsException e) {
            throw new Exception("Erreur : Parenthéses férmante manquante");
        }
    }
    //endregion
}
