package expression.arithmetic;

import expression.AbstractBinaryOperations;
import expression.ExpressionArray;

public class Multiply extends AbstractBinaryOperations<ArithmeticExpression> implements ArithmeticExpression {
    public Multiply(ArithmeticExpression first, ArithmeticExpression second) {
        super(first, second);
    }

    @Override
    protected String operator() {
        return "*";
    }

    @Override
    public ExpressionArray simplify() {
        ExpressionArray one = first.simplify();
        ExpressionArray two = second.simplify();
        ExpressionArray answer = new ExpressionArray();
        for (int i = 0; i < one.maxDegree(); i++) {
            for (int j = 0; j < two.maxDegree(); j++) {
                int expression = one.get(i) * two.get(j) + answer.get(i + j);
                answer.set(expression, i + j);
            }
        }
        return answer;
    }

    @Override
    public int evaluate(int element) {
        return first.evaluate(element) * second.evaluate(element);
    }
}
