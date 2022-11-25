package model;

import javafx.util.Pair;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.function.Predicate;

public final class User implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -379318737058451008L;
    public String username;
    public ArrayList<Album> albums;
    public ArrayList<Pair<String, String>> tagPreset;
    public HashMap<String, Photo> uniquePhotos;

    public User(String username) {
        this.username = username;
        this.albums = new ArrayList<>();
        this.tagPreset = new ArrayList<>();
        this.uniquePhotos = new HashMap<>();
    }

    public void addToTagPreset(String type, boolean isSingle) throws Exception {
        String property = isSingle ? "single" : "multiple";
        for (Pair<String, String> p : this.tagPreset) {
            if (p.getKey().equals(type)) {
                throw new Exception("tag already exists in preset");
            }
        }
        this.tagPreset.add(new Pair<String, String>(type, property));
    }

    public void createAlbum(String albumName) throws Exception {
        if (this.getAlbumIndex(albumName) != -1) {
            throw new Exception("Album Already Exists");
        }
        albums.add(new Album(albumName));
    }

    public void createAlbum(String albumName, ArrayList<Photo> searchResults) throws Exception {
        if (this.getAlbumIndex(albumName) != -1) {
            throw new Exception("Album Already Exists");
        }
        albums.add(new Album(albumName, searchResults));
    }

    public void deleteAlbum(String albumName) throws Exception {
        if (this.getAlbumIndex(albumName) == -1) {
            throw new Exception("Album Not Found");
        }
        albums.remove(this.getAlbumIndex(albumName));
    }

    public void renameAlbum(String oldAlbumName, String newAlbumName) throws Exception {
        if (this.getAlbumIndex(oldAlbumName) == -1) {
            throw new Exception("Album Not Found");
        }
        albums.get(this.getAlbumIndex(oldAlbumName)).name = newAlbumName;
    }

    public int getAlbumIndex(String albumName) {
        for (int i = 0; i < this.albums.size(); i++) {
            if (this.albums.get(i) != null && this.albums.get(i).name.compareTo(albumName) == 0) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Photo> getAllPhotos() {
        return new ArrayList<Photo>(this.uniquePhotos.values());
    }

    public ArrayList<Photo> getPhotos(Predicate<? super Photo> predicate) {
        ArrayList<Photo> allPhotos = getAllPhotos();
        ArrayList<Photo> result = new ArrayList<>();
        for (Photo p : allPhotos) {
            if (predicate.test(p)) {
                result.add(p);
            }
        }
        return result;
    }

    public ArrayList<Photo> getPhotosByTag(String type, String value) {
        Predicate<Photo> containsTag = p -> p.tags.contains(new Tag(type, value));
        return getPhotos(containsTag);
    }

    public ArrayList<Photo> getPhotosByTags(String type1, String value1, String type2, String value2, boolean isAND) {
        Predicate<Photo> containsTag1 = p -> p.tags.contains(new Tag(type1, value1));
        Predicate<Photo> containsTag2 = p -> p.tags.contains(new Tag(type2, value2));
        Predicate<Photo> containsTags;
        if (isAND) {
            containsTags = containsTag1.and(containsTag2);
        } else {
            containsTags = containsTag1.or(containsTag2);
        }
        return getPhotos(containsTags);
    }

    public ArrayList<Photo> getPhotosAtTime(Calendar time) {
        Predicate<Photo> atTime = p -> p.dateTaken.compareTo(time) == 0;
        return getPhotos(atTime);
    }

    public ArrayList<Photo> getPhotosInRange(Calendar start, Calendar end) {
        Predicate<Photo> inRange = p -> p.dateTaken.compareTo(start) >= 0 && p.dateTaken.compareTo(end) <= 0;
        return getPhotos(inRange);
    }
}


