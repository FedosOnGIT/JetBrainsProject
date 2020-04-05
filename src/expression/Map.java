package expression;

import expression.arithmetic.ArithmeticExpression;

import java.util.Objects;

public class Map implements Expression{
    protected ArithmeticExpression expression;
    public Map(ArithmeticExpression expression) {
        this.expression = expression;
    }

    public Map simplify() {
        ExpressionArray coefficients = expression.simplify();
        return new Map(coefficients.create());
    }

    public int evaluate(int element) {
        return expression.evaluate(element);
    }

    @Override
    public String toString() {
        return "map{" + expression + "}";
    }

    @Override
    public boolean equals(Object compareWith) {
        if (compareWith == null) {
            return false;
        } else if (compareWith.getClass() != getClass()) {
            return false;
        }
        Map compare = (Map) compareWith;
        return Objects.equals(expression, compare.expression);
    }
}
