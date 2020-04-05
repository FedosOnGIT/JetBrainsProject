package parser;

import expression.Phrase;

import expression.*;
import expression.arithmetic.*;
import expression.bool.Equals;
import expression.bool.*;
import parser.exceptions.SyntaxException;
import parser.exceptions.TypeException;

public class ChainParser extends BaseParser {


    public Phrase simplify(String expression) throws SyntaxException {
        ExpressionParser parser = new ExpressionParser();
        String[] terms = expression.split("%>%");
        ArithmeticExpression replace = new Variable("element");
        BooleanExpression filter = new Equals(new Const(0), new Const(0));
        for (String term: terms) {
            int left = term.indexOf('{');
            int right = term.indexOf('}');
            if (left == -1 || right != term.length() - 1) {
                throw new SyntaxException("troubles with head, oh no with {}");
            }
            if (check(term,"m")) {
                if (check(term,"map{")) {
                    Expression parsed = parser.parse(term.substring(left + 1, right), replace);
                    if (parsed instanceof ArithmeticExpression) {
                        replace = (ArithmeticExpression) parsed;
                    } else {
                        throw new TypeException("BooleanExpression in " + term.substring(left + 1, right));
                    }
                } else {
                    throw new SyntaxException("Incorrect operation");
                }
            } else if (check(term, "f")) {
                if (check(term,"filter{")) {
                    Expression parsed = parser.parse(term.substring(left + 1, right), replace);
                    if (parsed instanceof BooleanExpression) {
                        filter = new And(filter, (BooleanExpression) parsed);
                    } else {
                        throw new TypeException("Arithmetic expression in " + term.substring(left + 1, right));
                    }
                } else {
                    throw new SyntaxException("Incorrect operation");
                }
            }
        }
        Filter answerFilter = new Filter(filter).simplify();
        Map answerMap = new Map(replace).simplify();
        return new Phrase(answerFilter, answerMap);
    }

    private boolean check(String test, String answer) {
        if (answer.length() > test.length()) {
            return false;
        }
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) != test.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
