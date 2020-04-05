package expression.arithmetic;

import expression.ExpressionArray;

public class Variable implements ArithmeticExpression {
    private String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public ExpressionArray simplify() {
        ExpressionArray one = new ExpressionArray();
        one.set(1, 1);
        return one;
    }

    @Override
    public int evaluate(int element) {
        return element;
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public boolean equals(Object compareVariable) {
        if (compareVariable == null) {
            return false;
        } else if (compareVariable.getClass() != getClass()) {
            return false;
        } else {
            Variable comparable = (Variable) compareVariable;
            return comparable.variable.equals(variable);
        }
    }
}
