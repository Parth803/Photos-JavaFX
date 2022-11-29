package model;

import javafx.util.Pair;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.function.Predicate;

/**
 * @author Parth Patel, Yash Patel
 */
public final class User implements java.io.Serializable, Comparable<User> {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -379318737058451008L;
    /**
     *
     */
    public String username;
    /**
     *
     */
    public ArrayList<Album> albums;
    /**
     *
     */
    public ArrayList<Pair<String, String>> tagPreset;
    /**
     *
     */
    public HashMap<String, Photo> uniquePhotos;

    /**
     *
     * @param username
     */
    public User(String username) {
        this.username = username;
        this.albums = new ArrayList<>();
        this.tagPreset = new ArrayList<>();
        this.tagPreset.add(new Pair<>("Other", "Custom"));
        this.uniquePhotos = new HashMap<>();
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User other)) {
            return false;
        }
        return this.username.equals(other.username);
    }

    /**
     *
     * @param other the object to be compared.
     * @return
     */
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

    /**
     *
     * @param type
     * @param isSingle
     * @throws Exception
     */
    public void addToTagPreset(String type, boolean isSingle) throws Exception {
        String property = isSingle ? "single" : "multiple";
        for (Pair<String, String> p : this.tagPreset) {
            if (p.getKey().equals(type)) {
                throw new Exception("tag already exists in preset");
            }
        }
        this.tagPreset.add(new Pair<>(type, property));
    }

    /**
     *
     * @param type
     * @return
     * @throws Exception
     */
    public String getTagProperty(String type) throws Exception {
        for (Pair<String, String> p : this.tagPreset) {
            if (p.getKey().equals(type)) {
                return p.getValue();
            }
        }
        throw new Exception("could not get property because tag is not in preset");
    }

    /**
     *
     * @param name
     * @throws Exception
     */
    public void createAlbum(String name) throws Exception {
        if (this.albums.contains(new Album(name))) {
            throw new Exception("Album Already Exists");
        }
        this.albums.add(new Album(name));
    }

    /**
     *
     * @param name
     * @param photos
     * @throws Exception
     */
    public void createAlbum(String name, ArrayList<Photo> photos) throws Exception {
        if (this.albums.contains(new Album(name))) {
            throw new Exception("Album Already Exists");
        }
        this.albums.add(new Album(name, photos));
    }

    /**
     *
     * @param name
     * @throws Exception
     */
    public void deleteAlbum(String name) throws Exception {
        if (!this.albums.contains(new Album(name))) {
            throw new Exception("Album Not Found");
        }
        this.albums.remove(new Album(name));
    }

    /**
     *
     * @param oldName
     * @param newName
     * @throws Exception
     */
    public void renameAlbum(String oldName, String newName) throws Exception {
        if (!this.albums.contains(new Album(oldName))) {
            throw new Exception("Album Not Found");
        }
        this.albums.get(this.albums.indexOf(new Album(oldName))).name = newName;
    }

    /**
     *
     * @return
     */
    public ArrayList<Photo> getAllPhotos() {
        return new ArrayList<>(this.uniquePhotos.values());
    }

    /**
     *
     * @param predicate
     * @return
     */
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

    /**
     *
     * @param type
     * @param value
     * @return
     */
    public ArrayList<Photo> getPhotosByTag(String type, String value) {
        Predicate<Photo> containsTag = p -> p.tags.contains(new Tag(type, value));
        return getPhotos(containsTag);
    }

    /**
     *
     * @param type1
     * @param value1
     * @param type2
     * @param value2
     * @param isAND
     * @return
     */
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

    /**
     *
     * @param start
     * @param end
     * @return
     */
    public ArrayList<Photo> getPhotosInRange(Calendar start, Calendar end) {
        Predicate<Photo> inRange = p -> p.dateTaken.compareTo(start) >= 0 && p.dateTaken.compareTo(end) <= 0;
        return getPhotos(inRange);
    }
}