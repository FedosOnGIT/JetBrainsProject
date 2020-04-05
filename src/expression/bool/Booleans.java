package expression.bool;

import expression.arithmetic.*;

public enum Booleans implements BooleanExpression {
    True, False;

    @Override
    public boolean evaluate(int element) {
        return this == True;
    }

    @Override
    public BooleanExpression simplify() {
        if (this == False) {
            return new Equals(new Const(0),
                    new Const(1));
        }
        return new Equals(new Const(0),
                new Const(0));
    }


    @Override
    public String toString() {
        return this.simplify().toString();
    }
}
