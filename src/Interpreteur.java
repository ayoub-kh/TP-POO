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
    public String analyserCommande(){
        String[] commandeLigne = ligne.split(" ");
        String commande = commandeLigne[0];
        try {
            if (commande.equals("print")) {
                return "La valeur est : " + analyserExpression(commandeLigne[1].replace(" ", ""));
            } else if (commande.equals("let")) {
                commandeLigne = commandeLigne[1].replace(" ", "").split("=");
                tableDeSymbol.put(commandeLigne[0], analyserExpression(commandeLigne[1].replace(" ", "")));
                return "Ok";
            } else throw new Exception("Erreur : Commande incorrect.");
        } catch (IndexOutOfBoundsException e) {
            return "Erreur : Expression non trouvée";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public double analyserExpression(String expression){
        return 0;
    }
    //endregion
}
