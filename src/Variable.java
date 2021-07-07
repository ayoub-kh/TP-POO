import java.util.Map;

public class Variable implements Expression{
    private final String nomVar;

    public Variable(String nomDeVariable) {
        nomVar = nomDeVariable;
    }

    public double evaluer(Map<String, Double> tableDeSymbol) {
        if (tableDeSymbol.containsKey(nomVar)) return tableDeSymbol.get(nomVar);
        else if (!nomVar.substring(0,1).matches("^[a-zA-Z]")) throw new AssertionError("Erreur : Expression erronnée");
        else throw new AssertionError("Erreur : Variable '" + nomVar + "' non declarée");
    }
}
