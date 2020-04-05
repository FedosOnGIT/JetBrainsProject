package parser;

public class StringSource implements ExpressionSource {
    private String expression;
    private int position;
    StringSource(String expression) {
        this.expression = expression;
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < expression.length();
    }

    @Override
    public char next() {
        return expression.charAt(position++);
    }
}
