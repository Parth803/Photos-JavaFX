package model;

import java.io.Serial;

public final class Tag implements java.io.Serializable, Comparable<Tag> {
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
        return this.type.equals(other.type) && this.value.equals(other.value);
    }

    public int compareTo(Tag other) {
        if (this.equals(other)) {
            return 0;
        }
        if (this.type.compareToIgnoreCase(other.type) < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return this.type + "   :   " + this.value;
    }
}


