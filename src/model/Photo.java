package model;

import java.util.ArrayList;

public final class Photo {
    public String dateTaken;
    public ArrayList<Tag> tags;

    public Photo(String dateTaken) {
        this.dateTaken = dateTaken;
        this.tags = new ArrayList<>();
    }
}
