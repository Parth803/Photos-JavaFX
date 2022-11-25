package model;

import java.io.File;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Calendar;

public final class Photo implements java.io.Serializable {
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

    public void addTag(String type, String value) throws Exception {
        if (this.getTagIndex(type, value) != -1) {
            throw new Exception("Photo Already Has This Tag");
        }
        tags.add(new Tag(type, value));
    }

    public void removeTag(String type, String value) throws Exception {
        if (this.getTagIndex(type, value) == -1) {
            throw new Exception("Photo Does Not Have This Tag");
        }
        tags.remove(this.getTagIndex(type, value));
    }

    public int getTagIndex(String type, String value) {
        for (int i = 0; i < this.tags.size(); i++) {
            if (tags.get(i) != null && tags.get(i).equals(new Tag(type, value))) {
                return i;
            }
        }
        return -1;
    }
}
