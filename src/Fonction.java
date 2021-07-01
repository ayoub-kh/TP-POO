import java.util.Map;

public class Fonction implements Expression{
    private final double indiceFonc;
    private final double argument;

    public Fonction(String fonc, double argument, Map<String, Double> tableDeSymbol) {
        indiceFonc = tableDeSymbol.get(fonc);
        this.argument = argument;
    }

    public double evaluer(Map<String, Double> tableDeSymbol) {
        if (indiceFonc == 1.0) return Math.sin(argument);
        else if (indiceFonc == 2.0) return Math.cos(argument);
        else if (indiceFonc == 3.0) return Math.tan(argument);
        else if (indiceFonc == 4.0) return Math.abs(argument);
        else if (indiceFonc == 5.0) return Math.sqrt(argument);
        else return Math.log(argument);
    }
}
