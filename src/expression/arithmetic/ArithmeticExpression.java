package expression.arithmetic;

import expression.Expression;
import expression.ExpressionArray;

public interface ArithmeticExpression extends Expression {
    ExpressionArray simplify();
    int evaluate(int element);
}
