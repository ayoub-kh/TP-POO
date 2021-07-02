import java.util.Map;

public class Division implements Expression{
    private final Expression op1;
    private final Expression op2;

    public Division(Expression op1, Expression op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    public double evaluer(Map<String, Double> tableDeSymbol) {
        return op1.evaluer(tableDeSymbol) / op2.evaluer(tableDeSymbol);
    }
}
