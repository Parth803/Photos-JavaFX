package model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * @author Parth Patel, Yash Patel
 */
public final class Album implements java.io.Serializable, Comparable<Album> {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -2523531824640650719L;
    /**
     *
     */
    public String name;
    /**
     *
     */
    public ArrayList<Photo> photos;
    /**
     *
     */
    public Calendar start;
    /**
     *
     */
    public Calendar end;

    /**
     *
     * @param name
     */
    public Album(String name) {
        this.name = name;
        this.photos = new ArrayList<>();
        this.start = Calendar.getInstance();
        this.start.set(Calendar.MILLISECOND, 0);
        this.end = this.start;
    }

    /**
     *
     * @param name
     * @param photos
     */
    public Album(String name, ArrayList<Photo> photos) {
        this.name = name;
        this.photos = photos;
        if (photos.isEmpty()) {
            this.start = Calendar.getInstance();
            this.start.set(Calendar.MILLISECOND, 0);
            this.end = this.start;
            return;
        }
        this.start = Collections.min(photos).dateTaken;
        this.end = Collections.max(photos).dateTaken;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Album other)) {
            return false;
        }
        return this.name.equals(other.name);
    }

    /**
     *
     * @param other the object to be compared.
     * @return
     */
    public int compareTo(Album other) {
        if (this.equals(other)) {
            return 0;
        }
        if (this.name.compareToIgnoreCase(other.name) < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     *
     * @param path
     * @throws Exception
     */
    public void addPhoto(String path) throws Exception {
        if (this.photos.contains(new Photo(path))) {
            throw new Exception("Photo is already in album");
        }
        Model.currentUser.uniquePhotos.putIfAbsent(path, new Photo(path));
        Photo newPhoto;
        newPhoto = Model.currentUser.uniquePhotos.get(path);
        this.photos.add(newPhoto);
        if (this.photos.size() == 1) {
            this.start = newPhoto.dateTaken;
            this.end = this.start;
            return;
        }
        if (newPhoto.dateTaken.compareTo(this.start) < 0) this.start = newPhoto.dateTaken;
        if (newPhoto.dateTaken.compareTo(this.end) > 0) this.end = newPhoto.dateTaken;
    }

    /**
     *
     * @param path
     * @param caption
     * @throws Exception
     */
    public void addPhoto(String path, String caption) throws Exception {
        if (this.photos.contains(new Photo(path))) {
            throw new Exception("Photo is already in album");
        }
        Model.currentUser.uniquePhotos.putIfAbsent(path, new Photo(path, caption));
        Photo newPhoto = Model.currentUser.uniquePhotos.get(path);
        this.photos.add(newPhoto);
        if (this.photos.size() == 1) {
            this.start = newPhoto.dateTaken;
            this.end = this.start;
        }
        if (newPhoto.dateTaken.compareTo(this.start) < 0) this.start = newPhoto.dateTaken;
        if (newPhoto.dateTaken.compareTo(this.end) > 0) this.end = newPhoto.dateTaken;
    }

    /**
     *
     * @param path
     * @throws Exception
     */
    public void removePhoto(String path) throws Exception {
        if (!this.photos.contains(new Photo(path))) {
            throw new Exception("Photo not in album");
        }
        this.photos.remove(new Photo(path));
        if (this.photos.isEmpty()) {
            this.start = Calendar.getInstance();
            this.start.set(Calendar.MILLISECOND, 0);
            this.end = this.start;
            return;
        }
        Photo photo = Model.currentUser.uniquePhotos.get(path);
        if (photo.dateTaken.compareTo(this.start) == 0) this.start = Collections.min(this.photos).dateTaken;
        if (photo.dateTaken.compareTo(this.end) == 0) this.end = Collections.max(this.photos).dateTaken;
    }
}
