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
        if (one instanceof More && two instanceof More) {
            BooleanExpression expression = new More(
                    new Multiply(((More) one).getFirst(),
                            ((More) two).getFirst()), new Const(0));
            return expression.simplify();
        }
        if (one instanceof Equals && two instanceof Equals) {
            BooleanExpression expression = new Equals(
                    new Add(((Equals) one).getFirst(),
                            ((Equals) two).getFirst()), new Const(0));
            return expression.simplify();
        }
        return new And(one, two);
    }
}
