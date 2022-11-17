package model;

import java.io.Serial;

public final class Tag implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -6115814363901437624L;
    public String tagType;
    public String tagValue;

    public Tag(String tagType, String tagValue) {
        this.tagType = tagType;
        this.tagValue = tagValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Tag)) {
            return false;
        }
        return this.tagType.compareTo(((Tag) obj).tagType) == 0 && this.tagType.compareTo(((Tag) obj).tagValue) == 0;
    }

    @Override
    public String toString() {
        return tagType + ": " + tagValue;
    }
}
