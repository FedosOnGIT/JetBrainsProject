package expression.bool;

import expression.AbstractBinaryOperations;
import expression.ExpressionArray;
import expression.arithmetic.*;

public class More extends AbstractBinaryOperations<ArithmeticExpression> implements BooleanExpression {
    public More(ArithmeticExpression first, ArithmeticExpression second) {
        super(first, second);
    }

    @Override
    protected String operator() {
        return ">";
    }

    @Override
    public boolean evaluate(int element) {
        return first.evaluate(element) > second.evaluate(element);
    }

    @Override
    public BooleanExpression simplify() {
        ArithmeticExpression expression = new Subtract(first, second);
        ExpressionArray array = expression.simplify();
        boolean alwaysFalse = true;
        for (int i = 0; i < array.maxDegree(); i++) {
            if (i % 2 == 0 && array.get(i) != 0) {
                alwaysFalse = false;
                break;
            } if (i % 2 == 1 && array.get(i) > 0) {
                alwaysFalse = false;
                break;
            }
        }
        if (alwaysFalse) {
            return Booleans.False;
        }
        ArithmeticExpression one = array.create();
        if (one instanceof Const) {
            if (one.evaluate(0) > 0) {
                return Booleans.True;
            } else {
                return Booleans.False;
            }
        }
        return new More(one, new Const(0));
    }

    public ArithmeticExpression getFirst() {
        return first;
    }
}
