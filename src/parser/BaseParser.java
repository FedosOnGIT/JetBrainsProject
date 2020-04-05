package parser;

import parser.exceptions.SyntaxException;

public class BaseParser {
    private ExpressionSource source;
    protected char ch;
    protected int position;

    protected void makeExpression(ExpressionSource source) {
        this.source = source;
        position = 0;
        nextChar();
    }

    protected void nextChar() {
        ch = source.hasNext() ? source.next() : '\0';
        position++;
    }

    protected boolean hasNext() {
        return ch != '\0';
    }

    protected boolean test(String test) {
        for (int i = 0; i < test.length(); i++) {
            if (test.charAt(i) != ch) {
                return false;
            }
            nextChar();
        }
        return true;
    }
}
