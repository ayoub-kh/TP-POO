import java.util.Map;

public class Nombre implements Expression{
    private final double valeur;

    public Nombre(double nombre) {
        valeur = nombre;
    }

    public double evaluer(Map<String, Double> tableDeSymbol) {
        return valeur;
    }
}
