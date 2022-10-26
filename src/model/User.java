package model;

import java.io.Serial;
import java.util.ArrayList;

public final class User implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -379318737058451008L;
    public String username;
    public ArrayList<Album> albums;
    public User(String username) {
        this.username = username;
        this.albums = new ArrayList<Album>();
        this.albums.add(new Album());
    }

}


