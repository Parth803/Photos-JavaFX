package model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Calendar;

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
        this.name = name;
        this.photos = photos;
        if (photos.isEmpty()) {
            this.start = Calendar.getInstance();
            this.start.set(Calendar.MILLISECOND, 0);
            this.end = this.start;
            return;
        }
        Calendar minCal = photos.get(0).dateTaken;
        Calendar maxCal = photos.get(0).dateTaken;
        for (Photo image : photos) {
            if (image.dateTaken.compareTo(minCal) < 0) {
                minCal = image.dateTaken;
            }
            if (image.dateTaken.compareTo(maxCal) > 0) {
                maxCal = image.dateTaken;
            }
        }
        this.start = minCal;
        this.end = maxCal;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Album other)) {
            return false;
        }
        return this.name.equals(other.name);
    }

    public void addPhoto(String path, String caption) throws Exception {
        if (this.getPhotoIndex(path) != -1) {
            throw new Exception("Photo is already in album");
        }
        photos.add(new Photo(path, caption));
    }

    public void removePhoto(String path) throws Exception {
        if (this.getPhotoIndex(path) == -1) {
            throw new Exception("Photo not in album");
        }
        photos.remove(this.getPhotoIndex(path));
    }

    public int getPhotoIndex(String path) {
        for (int i = 0; i < this.photos.size(); i++) {
            if (this.photos.get(i) != null && this.photos.get(i).path.compareTo(path) == 0) {
                return i;
            }
        }
        return -1;
    }
}



