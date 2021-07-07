import java.util.Map;

public class Division implements Expression{
    private final Expression op1;
    private final Expression op2;

    public Division(Expression op1, Expression op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    public double evaluer(Map<String, Double> tableDeSymbol) {
        double c;
        if(( c=op2.evaluer(tableDeSymbol)) != 0)
            return op1.evaluer(tableDeSymbol) / c;
        throw new ArithmeticException("Erreur : Division par 0.");
    }
}
