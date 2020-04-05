package parser;

import expression.*;
import expression.arithmetic.*;
import expression.bool.*;
import parser.exceptions.SyntaxException;
import parser.exceptions.TypeException;

import java.util.Set;

public class ExpressionParser extends BaseParser {

    Expression replace;

    public Expression parse(String expression, Expression replace) throws SyntaxException {
        this.replace = replace;
        makeExpression(new StringSource(expression));
        Expression Result = ParseOperation();
        if (!hasNext()) {
            return Result;
        }
        throw new SyntaxException("Can't parse " + expression);
    }

    public static final Set<Character> BinaryOperations = Set.of(
            '|',
            '&',
            '>',
            '<',
            '=',
            '+',
            '-',
            '*'
    );

    public Expression ParseOperation() throws SyntaxException {
        if (test("(")) {
            return ParseExpression();
        }
        return VariableOrNum();
    }

    public char getOperator(char operator) throws SyntaxException {
        if (BinaryOperations.contains(operator)) {
            nextChar();
            return operator;
        }
        throw new SyntaxException("Unexpected operator " + operator);
    }



    public Expression ParseExpression() throws SyntaxException {
        Expression first = ParseOperation();
        char operator = getOperator(ch);
        Expression second = ParseOperation();
        if (!test(")")) {
            throw new SyntaxException("Missing brace!");
        }
        switch (operator) {
            case '+':
                if (first instanceof ArithmeticExpression && second instanceof ArithmeticExpression) {
                    return new Add((ArithmeticExpression) first, (ArithmeticExpression) second);
                }
                throw new TypeException("Add, first or second are not ArithmeticExpression");
            case '-':
                if (first instanceof ArithmeticExpression && second instanceof ArithmeticExpression) {
                    return new Subtract((ArithmeticExpression) first, (ArithmeticExpression) second);
                }
                throw new TypeException("Subtract, first or second are not ArithmeticExpression");
            case '*':
                if (first instanceof ArithmeticExpression && second instanceof ArithmeticExpression) {
                    return new Multiply((ArithmeticExpression) first, (ArithmeticExpression) second);
                }
                throw new TypeException("Multiply, first or second are not ArithmeticExpression");
            case '>':
                if (first instanceof ArithmeticExpression && second instanceof ArithmeticExpression) {
                    return new More((ArithmeticExpression) first, (ArithmeticExpression) second);
                }
                throw new TypeException("More, first or second are not ArithmeticExpression");
            case '<':
                if (first instanceof ArithmeticExpression && second instanceof ArithmeticExpression) {
                    return new Less((ArithmeticExpression) first, (ArithmeticExpression) second);
                }
                throw new TypeException("Less, first or second are not ArithmeticExpression");
            case '=':
                if (first instanceof ArithmeticExpression && second instanceof ArithmeticExpression) {
                    return new Equals((ArithmeticExpression) first, (ArithmeticExpression) second);
                }
                throw new TypeException("Equals, first or second are not ArithmeticExpression");
            case '&':
                if (first instanceof BooleanExpression && second instanceof BooleanExpression) {
                    return new And((BooleanExpression) first, (BooleanExpression) second);
                }
                throw new TypeException("And, first or second are not BooleanExpression");
            case '|':
                if (first instanceof BooleanExpression && second instanceof BooleanExpression) {
                    return new Or((BooleanExpression) first, (BooleanExpression) second);
                }
                throw new TypeException("Or, first or second are not BooleanExpression");
            default:
                throw new IllegalStateException("Unexpected value: " + operator);
        }
    }

    private Expression VariableOrNum() throws SyntaxException {
        StringBuilder debuilder = new StringBuilder();
        int count = 0;
        while (hasNext() && Character.isLetter(ch)
                || count > 0 && Character.isDigit(ch)) {
            count++;
            debuilder.append(ch);
            nextChar();
        }
        if (count != 0) {
            String variable = debuilder.toString();
            if (variable.equals("element")) {
                return replace;
            }
            throw new SyntaxException("Illegal variable " + variable);
        }
        return Number();
    }

    private Expression Number() throws SyntaxException {
        StringBuilder debuilder = new StringBuilder();
        if (ch == '-') {
            debuilder.append('-');
            nextChar();
        }
        while (hasNext() && Character.isDigit(ch)) {
            debuilder.append(ch);
            nextChar();
        }
        try {
            int constant = Integer.parseInt(debuilder.toString());
            return new Const(constant);
        } catch (NumberFormatException e) {
            if (debuilder.length() > 1) {
                throw new SyntaxException("To big to parse " + debuilder.toString());
            } else {
                throw new SyntaxException("Not a number " + debuilder.toString());
            }
        }
    }
}