package expression.bool;

import expression.AbstractBinaryOperations;
import expression.ExpressionArray;
import expression.arithmetic.*;

public class Equals extends AbstractBinaryOperations<ArithmeticExpression> implements BooleanExpression {
    public Equals(ArithmeticExpression first, ArithmeticExpression second) {
        super(first, second);
    }

    @Override
    protected String operator() {
        return "=";
    }

    @Override
    public boolean evaluate(int element) {
        return first.evaluate(element) == second.evaluate(element);
    }

    @Override
    public BooleanExpression simplify() {
        ArithmeticExpression expression = new Subtract(first, second);
        ExpressionArray array = expression.simplify();
        ArithmeticExpression one = array.create();
        if (one instanceof Const) {
            if (one.evaluate(0) == 0) {
                return Booleans.True;
            } else {
                return Booleans.False;
            }
        }
        return new Equals(one, new Const(0));
    }

    public ArithmeticExpression getFirst() {
        return first;
    }
}
