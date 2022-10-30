package model;

import java.io.Serial;
import java.util.ArrayList;

public final class Album implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -2523531824640650719L;
    public ArrayList<Photo> photos;

    public Album() {
        this.photos = new ArrayList<>();
    }
}
