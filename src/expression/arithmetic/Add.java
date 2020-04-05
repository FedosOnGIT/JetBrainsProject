package expression.arithmetic;

import expression.AbstractBinaryOperations;
import expression.ExpressionArray;

public class Add extends AbstractBinaryOperations<ArithmeticExpression> implements ArithmeticExpression {
    public Add(ArithmeticExpression first, ArithmeticExpression second) {
        super(first, second);
    }

    @Override
    protected String operator() {
        return "+";
    }

    @Override
    public ExpressionArray simplify() {
        ExpressionArray one = first.simplify();
        ExpressionArray two = second.simplify();
        for (int i = 0; i < two.maxDegree(); i++) {

            one.set(one.get(i) + two.get(i), i);
        }
        return one;
    }

    @Override
    public int evaluate(int element) {
        return first.evaluate(element) + second.evaluate(element);
    }
}
