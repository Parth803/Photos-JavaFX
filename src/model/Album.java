package model;

import java.io.Serial;
import java.util.ArrayList;

public final class Album implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -2523531824640650719L;
    public String albumName;
    public ArrayList<Photo> photos;

    public Album(String albumName) {
        this.albumName = albumName;
        this.photos = new ArrayList<>();
    }

    public void addPhoto(String file, String caption) throws Exception {
        // add error handling for if photo already in album
        if (this.getPhotoIndex(file) != -1) {
            throw new Exception("Photo is already in album");
        }
        photos.add(new Photo(file, caption));
    }

    public void removePhoto(String file) throws Exception {
        // add error handling if photo not in album
        if (this.getPhotoIndex(file) == -1) {
            throw new Exception("Photo not in album");
        }
        photos.remove(this.getPhotoIndex(file));
    }

    public int getPhotoIndex(String file) {
        for (int i = 0; i < this.photos.size(); i++) {
            if (photos.get(i) != null && photos.get(i).path.compareTo(file) == 0) {
                return i;
            }
        }
        return -1;
    }
}
