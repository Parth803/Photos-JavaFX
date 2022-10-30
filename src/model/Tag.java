package model;

import java.io.Serial;

public final class Tag implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -6115814363901437624L;
    public String name;
    public String value;

    public Tag(String name) {
        this.name = name;
        this.value = "";
    }
}
