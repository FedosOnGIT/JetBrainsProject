package expression;

import expression.arithmetic.*;

import java.util.Arrays;

public class ExpressionArray {
    private int[] coefficient = new int[2];
    private int len = 0;

    private void update(int size) {
        len = size;
        coefficient = Arrays.copyOf(coefficient, size * 2);
    }

    public void set(int expression, int index) {
        if (index >= coefficient.length) {
            update(index + 1);
        }
        len = Math.max(len, index + 1);
        coefficient[index] = expression;
    }

    public int get(int index) {
        if (index >= coefficient.length) {
            update(index + 1);
        }
        return coefficient[index];
    }

    public int maxDegree() {
        return len;
    }

    public ArithmeticExpression create() {
        ArithmeticExpression answer = null;
        boolean notZero = false;
        if (coefficient[0] != 0) {
            notZero = true;
            answer = new Const(coefficient[0]);
        }
        for (int i = 1; i < len; i++) {
            if (coefficient[i] != 0) {
                ArithmeticExpression help;
                help = multiply(i);
                if (!notZero) {
                    notZero = true;
                    if (coefficient[i] != 1) {
                        help = new Multiply(new Const(coefficient[i]), help);
                    }
                    answer = help;
                } else {
                    if (coefficient[i] > 0) {
                        if (coefficient[i] != 1) {
                            help = new Multiply(new Const(coefficient[i]), help);
                        }
                        answer = new Add(answer, help);
                    } else {
                        if (coefficient[i] != -1) {
                            help = new Multiply(new Const(-coefficient[i]), help);
                        }
                        answer = new Subtract(answer, help);
                    }
                }
            }
        }
        if (!notZero) {
            return new Const(0);
        }
        return answer;
    }

    private ArithmeticExpression multiply(int degree) {
        if (degree == 1) {
            return new Variable("element");
        }
        ArithmeticExpression power = multiply(degree / 2);
        ArithmeticExpression answer = new Multiply(power, power);
        if (degree % 2 == 1) {
            return new Multiply(new Variable("element"), answer);
        }
        return answer;
    }
}
