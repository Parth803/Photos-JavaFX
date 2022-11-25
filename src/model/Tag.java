package model;

import javafx.util.Pair;

import java.io.Serial;
import java.util.ArrayList;

public final class Tag implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -6115814363901437624L;
    public String type;
    public String value;

    public Tag(String type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tag other)) {
            return false;
        }
        return this.type.compareTo(other.type) == 0 && this.value.compareTo(other.value) == 0;
    }

    @Override
    public String toString() {
        return this.type + "   :   " + this.value;
    }
}


