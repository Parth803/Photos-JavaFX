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
        albums.get(this.getAlbumIndex(oldAlbumName)).albumName = newAlbumName;
    }

    public int getAlbumIndex(String albumName) {
        for (int i = 0; i < this.albums.size(); i++) {
            if (this.albums.get(i) != null && this.albums.get(i).albumName.compareTo(albumName) == 0) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Photo> getPhotosByTag(String tagType, String tagValue) {
        ArrayList<Photo> allPhotos = new ArrayList<Photo>();
        for (Album x: albums) {
            allPhotos.addAll(x);
        }
    }

    public ArrayList<Photo> getPhotosInRange(Calendar start, Calendar end) {

    }
}


