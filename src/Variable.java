import java.util.Map;

public class Variable implements Expression{
    private final String nomVar;

    public Variable(String nomDeVariable) {
        nomVar = nomDeVariable;
    }

    public double evaluer(Map<String, Double> tableDeSymbol) {
        return tableDeSymbol.get(nomVar);
    }
}
