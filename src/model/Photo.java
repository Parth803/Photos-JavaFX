package model;

import java.io.Serial;
import java.util.ArrayList;

public final class Photo implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -1207060131086150206L;
    public String dateTaken;
    public ArrayList<Tag> tags;

    public Photo(String dateTaken) {
        this.dateTaken = dateTaken;
        this.tags = new ArrayList<>();
    }
}
