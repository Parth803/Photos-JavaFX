package model;

import java.io.Serial;

public final class Tag implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -6115814363901437624L;
    public String tagType;
    public String tagValue;

    public Tag(String tagType) {
        this.tagType = tagType;
        this.tagValue = "";
    }
}
