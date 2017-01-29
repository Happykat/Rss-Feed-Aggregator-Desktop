package rss.manager;

import java.io.Serializable;

public class ObjectDB implements Serializable {
    private static final long serialVersionUID = 5864896800675704551L;
    private Pair pair_;

    public Pair getPair() {
        return pair_;
    }

    public void setPair(Pair pair) {
        pair_ = pair;
    }
}