package model;

import java.io.Serializable;

public class Entity<ID> implements Serializable {
    private ID id;

    public ID getId() {
        return this.id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
