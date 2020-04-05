package expression.bool;

import expression.Expression;

public interface BooleanExpression extends Expression {
    boolean evaluate(int element);
    BooleanExpression simplify();
}
