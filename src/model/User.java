package model;

import javafx.util.Pair;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.function.Predicate;

public final class User implements java.io.Serializable, Comparable<User> {
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
        this.tagPreset.add(new Pair<>("Other", "Custom"));
        this.uniquePhotos = new HashMap<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User other)) {
            return false;
        }
        return this.username.equals(other.username);
    }

    public int compareTo(User other) {
        if (this.equals(other)) {
            return 0;
        }
        if (this.username.compareToIgnoreCase(other.username) < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public void addToTagPreset(String type, boolean isSingle) throws Exception {
        String property = isSingle ? "single" : "multiple";
        for (Pair<String, String> p : this.tagPreset) {
            if (p.getKey().equals(type)) {
                throw new Exception("tag already exists in preset");
            }
        }
        this.tagPreset.add(new Pair<>(type, property));
    }

    public String getTagProperty(String type) throws Exception {
        for (Pair<String, String> p : this.tagPreset) {
            if (p.getKey().equals(type)) {
                return p.getValue();
            }
        }
        throw new Exception("could not get property because tag is not in preset");
    }

    public void createAlbum(String name) throws Exception {
        if (this.albums.contains(new Album(name))) {
            throw new Exception("Album Already Exists");
        }
        this.albums.add(new Album(name));
    }

    public void createAlbum(String name, ArrayList<Photo> photos) throws Exception {
        if (this.albums.contains(new Album(name))) {
            throw new Exception("Album Already Exists");
        }
        this.albums.add(new Album(name, photos));
    }

    public void deleteAlbum(String name) throws Exception {
        if (!this.albums.contains(new Album(name))) {
            throw new Exception("Album Not Found");
        }
        this.albums.remove(new Album(name));
    }

    public void renameAlbum(String oldName, String newName) throws Exception {
        if (!this.albums.contains(new Album(oldName))) {
            throw new Exception("Album Not Found");
        }
        if (this.albums.contains(new Album(newName))) {
            throw new Exception("Album Already Exists");
        }
        this.albums.get(this.albums.indexOf(new Album(oldName))).name = newName;
    }

    public ArrayList<Photo> getAllPhotos() {
        return new ArrayList<>(this.uniquePhotos.values());
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

    public ArrayList<Photo> getPhotosInRange(Calendar start, Calendar end) {
        Predicate<Photo> inRange = p -> p.dateTaken.compareTo(start) >= 0 && p.dateTaken.compareTo(end) <= 0;
        return getPhotos(inRange);
    }
}


