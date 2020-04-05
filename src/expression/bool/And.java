package expression.bool;

import expression.AbstractBinaryOperations;
import expression.arithmetic.*;

public class And extends AbstractBinaryOperations<BooleanExpression> implements BooleanExpression {
    public And(BooleanExpression first, BooleanExpression second) {
        super(first, second);
    }

    @Override
    protected String operator() {
        return "&";
    }

    @Override
    public boolean evaluate(int element) {
        return first.evaluate(element) & second.evaluate(element);
    }

    @Override
    public BooleanExpression simplify() {
        BooleanExpression one = first.simplify();
        BooleanExpression two = second.simplify();
        if (one == Booleans.False || two == Booleans.False) {
            return Booleans.False;
        }
        if (one == Booleans.True) {
            return two;
        }
        if (two == Booleans.True) {
            return one;
        }
        return new And(one, two);
    }
}
