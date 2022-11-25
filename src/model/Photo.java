package model;

import java.io.File;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Calendar;

public final class Photo implements java.io.Serializable, Comparable<Photo> {
    @Serial
    private static final long serialVersionUID = -1207060131086150206L;
    public String path;
    public String caption;
    public Calendar dateTaken;
    public ArrayList<Tag> tags;

    public Photo(String path) {
        this.path = path;
        this.caption = "";
        File image = new File(path);
        this.dateTaken = Calendar.getInstance();
        this.dateTaken.setTimeInMillis(image.lastModified());
        this.dateTaken.set(Calendar.MILLISECOND, 0);
        this.tags = new ArrayList<>();
    }

    public Photo(String path, String caption) {
        this.path = path;
        this.caption = caption;
        File image = new File(path);
        this.dateTaken = Calendar.getInstance();
        this.dateTaken.setTimeInMillis(image.lastModified());
        this.dateTaken.set(Calendar.MILLISECOND, 0);
        this.tags = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Photo other)) {
            return false;
        }
        return this.path.equals(other.path);
    }

    public int compareTo(Photo other) {
        if (this.equals(other)) {
            return 0;
        }
        if (this.path.compareToIgnoreCase(other.path) < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public void addTag(String type, String value) throws Exception {
        if (this.tags.contains(new Tag(type, value))) {
            throw new Exception("Photo Already Has This Tag");
        }
        // needs fixing

        this.tags.add(new Tag(type, value));
    }

    public void removeTag(String type, String value) throws Exception {
        if (!this.tags.contains(new Tag(type, value))) {
            throw new Exception("Photo Does Not Have This Tag");
        }
        this.tags.remove(new Tag(type, value));
    }
}


