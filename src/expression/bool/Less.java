package expression.bool;

import expression.AbstractBinaryOperations;
import expression.arithmetic.*;

public class Less extends AbstractBinaryOperations<ArithmeticExpression> implements BooleanExpression {
    public Less(ArithmeticExpression first, ArithmeticExpression second) {
        super(first, second);
    }

    @Override
    protected String operator() {
        return "<";
    }

    @Override
    public boolean evaluate(int element) {
        return first.evaluate(element) < second.evaluate(element);
    }

    @Override
    public BooleanExpression simplify() {
        return (new More(second, first)).simplify();
    }
}
