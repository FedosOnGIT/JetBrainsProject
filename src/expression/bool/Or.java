package expression.bool;

import expression.AbstractBinaryOperations;
import expression.arithmetic.*;

public class Or extends AbstractBinaryOperations<BooleanExpression> implements BooleanExpression {
    public Or(BooleanExpression first, BooleanExpression second) {
        super(first, second);
    }

    @Override
    protected String operator() {
        return "|";
    }

    @Override
    public boolean evaluate(int element) {
        return first.evaluate(element) || second.evaluate(element);
    }

    @Override
    public BooleanExpression simplify() {
        BooleanExpression one = first.simplify();
        BooleanExpression two = second.simplify();
        if (one == Booleans.True || two == Booleans.True) {
            return Booleans.True;
        }
        if (one == Booleans.False) {
            return two;
        }
        if (two == Booleans.False) {
            return one;
        }
        if (one instanceof Equals && two instanceof Equals) {
            BooleanExpression expression = new Equals(
                    new Multiply(((Equals) one).getFirst(),
                            ((Equals) two).getFirst()), new Const(0));
            return expression.simplify();
        }
        return new Or(one, two);
    }
}
