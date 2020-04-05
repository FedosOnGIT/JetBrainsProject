package expression;

import expression.arithmetic.ArithmeticExpression;
import expression.bool.BooleanExpression;

import java.util.Objects;

public class Filter implements Expression {
    protected BooleanExpression expression;
    public Filter(BooleanExpression expression) {
        this.expression = expression;
    }

    public Filter simplify() {
        return new Filter(expression.simplify());
    }

    public boolean evaluate(int element) {
        return expression.evaluate(element);
    }

    @Override
    public String toString() {
        return "filter{" + expression + "}";
    }

    @Override
    public boolean equals(Object compareWith) {
        if (compareWith == null) {
            return false;
        } else if (compareWith.getClass() != getClass()) {
            return false;
        }
        Filter compare = (Filter) compareWith;
        return Objects.equals(expression, compare.expression);
    }
}
