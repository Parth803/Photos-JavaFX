package model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public final class Album implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -2523531824640650719L;
    public String name;
    public ArrayList<Photo> photos;
    public Calendar start;
    public Calendar end;

    public Album(String name) {
        this.name = name;
        this.photos = new ArrayList<>();
        this.start = Calendar.getInstance();
        this.start.set(Calendar.MILLISECOND, 0);
        this.end = this.start;
    }

    public Album(String name, ArrayList<Photo> photos) {
        this(name);
        if (!photos.isEmpty()) {
            this.photos = photos;
            this.start = photos.stream().min(Comparator.comparing(Photo::getDateTaken)).get().dateTaken;
            this.end = photos.stream().max(Comparator.comparing(Photo::getDateTaken)).get().dateTaken;
        }
    }

    public void addPhoto(String file, String caption) throws Exception {
        if (this.getPhotoIndex(file) != -1) {
            throw new Exception("Photo is already in album");
        }
        photos.add(new Photo(file, caption));
    }

    public void removePhoto(String file) throws Exception {
        if (this.getPhotoIndex(file) == -1) {
            throw new Exception("Photo not in album");
        }
        photos.remove(this.getPhotoIndex(file));
    }

    public int getPhotoIndex(String file) {
        for (int i = 0; i < this.photos.size(); i++) {
            if (this.photos.get(i) != null && this.photos.get(i).path.compareTo(file) == 0) {
                return i;
            }
        }
        return -1;
    }
}
