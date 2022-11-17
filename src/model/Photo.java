package model;

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

    public Photo(String path, String caption) {
        this.path = path;
        this.caption = caption;
        this.dateTaken = Calendar.getInstance();
        dateTaken.set(Calendar.MILLISECOND, 0);
        this.tags = new ArrayList<>();
    }

    public void addTag(String tagType, String tagValue) throws Exception {
        if (this.getTagIndex(tagType, tagValue) != -1) {
            throw new Exception("Photo Already Has This Tag");
        }
        tags.add(new Tag(tagType, tagValue));
    }

    public void removeTag(String tagType, String tagValue) throws Exception {
        if (this.getTagIndex(tagType, tagValue) == -1) {
            throw new Exception("Photo Does Not Have This Tag");
        }
        tags.remove(this.getTagIndex(tagType, tagValue));
    }

    public int getTagIndex(String tagType, String tagValue) {
        for (int i = 0; i < this.tags.size(); i++) {
            if (tags.get(i) != null && tags.get(i).tagType.compareTo(tagType) == 0 && tags.get(i).tagValue.compareTo(tagValue) == 0) {
                return i;
            }
        }
        return -1;
    }
}
