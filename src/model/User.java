package model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Calendar;

public final class User implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -379318737058451008L;
    public String username;
    public ArrayList<Album> albums;

    public User(String username) {
        this.username = username;
        if (username.equals("admin")) {
            return;
        }
        this.albums = new ArrayList<>();
        fetchAlbums();
    }

    public void fetchAlbums() {
        if (this.username.equals("stock")) {
            // get stock album which stores the stock photos
            return;
        }

        if (Model.getUserIndex(this.username) != -1) {
            // get albums that belong to the existing user and store them in albums arraylist
            return;
        }

        // we have a new user, so we don't fetch anything.
    }

    public void createAlbum(String albumName) {

    }

    public void createAlbum(ArrayList<Photo> searchResults) {

    }

    public void deleteAlbum(String albumName) {

    }

    public void renameAlbum(String oldAlbumName, String newAlbumName) {

    }

    public int getAlbumIndex(String albumName) {

    }

    public ArrayList<Photo> getPhotosByTag(String tagType, String tagValue) {

    }

    public ArrayList<Photo> getPhotosInRange(Calendar start, Calendar end) {

    }
}


