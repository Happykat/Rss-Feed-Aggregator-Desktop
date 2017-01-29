package rss.manager;


import java.io.Serializable;

public class Pair<F, S> implements Serializable {
    private final F first_;
    private final S second_;

    public Pair(F first, S second) {
        first_ = first;
        second_ = second;
    }

    public F getFirst() {
        return first_;
    }

    public S getSecond() {
        return second_;
    }
}