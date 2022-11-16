package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Scanner;

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
        boolean isExistingUser = false;

        try (Scanner scan = new Scanner(new File("users.txt"))) {
            while (scan.hasNextLine()) {
                String currentLine = scan.nextLine();
                if (currentLine.contains(this.username)) {
                    isExistingUser = true;
                    break;
                }
            }
        } catch (FileNotFoundException err) {
            System.out.println("File Not Found");
        }

        if (!isExistingUser) {
            // handle new user
        }
        // get albums that belong to the existing user and store them in albums arraylist
    }
}


