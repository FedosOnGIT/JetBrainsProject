package expression;

import java.util.Objects;

public abstract class AbstractBinaryOperations<T extends Expression> implements Expression {
    protected T first;
    protected T second;

    public AbstractBinaryOperations(T first, T second) {
        this.first = first;
        this.second = second;
    }
    protected abstract String operator();

    @Override
    public String toString() {
        return "(" + first.toString() + operator() + second.toString() + ")";
    }

    @Override
    public boolean equals(Object compareWith) {
        if (compareWith == null) {
            return false;
        } else if (compareWith.getClass() != getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        AbstractBinaryOperations<T> compare = (AbstractBinaryOperations<T>) compareWith;
        return Objects.equals(first, compare.first) && Objects.equals(second, compare.second);
    }


}

