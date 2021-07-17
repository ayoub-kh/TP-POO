import java.util.Map;

public class Multiplication implements Expression{
    private final Expression operand1;
    private final Expression operand2;

    public Multiplication(Expression operand1, Expression operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public double evaluer(Map<String, Double> tableDeSymbol) {
        return operand1.evaluer(tableDeSymbol) * operand2.evaluer(tableDeSymbol);
    }
}
