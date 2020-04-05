package expression;

import java.util.Objects;

public class Phrase {
    Filter filter;
    Map map;
    public Phrase(Filter filter, Map map) {
        this.filter = filter;
        this.map = map;
    }

    public Integer evaluate(int element) {
        if (filter.evaluate(element)) {
            return map.evaluate(element);
        }
        return null;
    }

    @Override
    public String toString() {
        return filter.toString() + "%>%" + map.toString();
    }

    @Override
    public boolean equals(Object compareWith) {
        if (compareWith == null) {
            return false;
        } else if (compareWith.getClass() != getClass()) {
            return false;
        }
        Phrase compare = (Phrase) compareWith;
        return Objects.equals(filter, compare.filter) && Objects.equals(map, compare.map);
    }
}
