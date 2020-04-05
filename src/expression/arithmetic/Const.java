package expression.arithmetic;

import expression.ExpressionArray;

public class Const implements ArithmeticExpression {
    private int constant;

    public Const(int constant) {
        this.constant = constant;
    }

    @Override
    public ExpressionArray simplify() {
        ExpressionArray one = new ExpressionArray();
        one.set(constant, 0);
        return one;
    }

    @Override
    public int evaluate(int element) {
        return constant;
    }

    @Override
    public String toString() {
        return Integer.toString(constant);
    }

    @Override
    public boolean equals(Object compareConstant) {
        if (compareConstant == null) {
            return false;
        } else if (compareConstant.getClass() != getClass()) {
            return false;
        } else {
            Const comparable = (Const) compareConstant;
            return comparable.constant == constant;
        }
    }
}
