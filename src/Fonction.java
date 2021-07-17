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
        else if (indiceFonc == 3.0) {
            if(argument % (2 * Math.PI) != Math.PI/2 || argument % (2 * Math.PI) != 3*Math.PI/2)
                return Math.tan(argument);
            throw new ArithmeticException("Erreur : L'argument du fonction 'tan' doit etre différent de PI/2+K*PI");
        }
        else if (indiceFonc == 4.0) return Math.abs(argument);
        else if (indiceFonc == 5.0) {
            if(argument >= 0)
                return Math.sqrt(argument);
            throw new ArithmeticException("Erreur : L'argument du fonction 'sqrt' doit etre >= 0");
        }
        else {
            if(argument > 0)
                return Math.log(argument);
            throw new ArithmeticException("Erreur : L'argument du fontion 'log' doit etre > 0");
        }
    }
}
