package model;

import photos.Photos;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * @author Parth Patel, Yash Patel
 */
public final class Model {
    /**
     *
     */
    public static ArrayList<User> users;
    /**
     *
     */
    public static User currentUser;
    /**
     *
     */
    public static ArrayList<Object> dataTransfer;
    /**
     *
     */
    private static ArrayDeque<Object> dataSnapshots;

    /**
     *
     */
    private Model() {}

    /**
     *
     */
    @SuppressWarnings("unchecked casting")
    public static void init() {
        dataTransfer = new ArrayList<>();
        dataSnapshots = new ArrayDeque<>();
        String path = "data/admin/users.txt";
        File serializedUsers = new File(path);
        if (serializedUsers.length() == 0) {
            users = new ArrayList<>();
            users.add(new User("admin"));
            users.add(new User("stock"));
            try {
                setCurrentUser("stock");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Album stockAlbum = new Album("stock");
            currentUser.albums.add(stockAlbum);
            try {
                path = new File("data/stock/one.jpeg").getAbsolutePath();
                stockAlbum.addPhoto(path, "first");
                path = new File("data/stock/two.jpeg").getAbsolutePath();
                stockAlbum.addPhoto(path, "second");
                path = new File("data/stock/three.jpeg").getAbsolutePath();
                stockAlbum.addPhoto(path, "third");
                path = new File("data/stock/four.jpeg").getAbsolutePath();
                stockAlbum.addPhoto(path, "fourth");
                path = new File("data/stock/five.jpeg").getAbsolutePath();
                stockAlbum.addPhoto(path, "fifth");
                path = new File("data/stock/six.jpeg").getAbsolutePath();
                stockAlbum.addPhoto(path, "sixth");
                path = new File("data/stock/seven.jpeg").getAbsolutePath();
                stockAlbum.addPhoto(path, "seventh");
            } catch (Exception e) {
                throw new RuntimeException("stock photos could not be added");
            }
        }
        else {
            try {
                FileInputStream file = new FileInputStream(path);
                ObjectInputStream input = new ObjectInputStream(file);
                users = (ArrayList<User>) input.readObject();
                if (users.size() == 0) {
                    users.add(new User("admin"));
                }
                currentUser = users.get(0);
                input.close();
                file.close();
            } catch (Exception e) {
                throw new RuntimeException("could not read serialized object");
            }
        }
    }

    /**
     *
     */
    public static void persist() {
        try {
            FileOutputStream file = new FileOutputStream("data/admin/users.txt");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(users);
            output.close();
            file.close();
        } catch (IOException e) {
            throw new RuntimeException("could not serialize the object");
        }
    }

    /**
     *
     * @param username
     * @throws Exception
     */
    public static void setCurrentUser(String username) throws Exception {
        for (User user : Model.users) {
            if (user.username.equals(username)) {
                currentUser = user;
                return;
            }
        }
        throw new Exception("username does not exist");
    }

    /**
     *
     * @param username
     * @throws Exception
     */
    public static void addUser(String username) throws Exception {
        if (users.contains(new User(username))) {
            throw new Exception("User Already Exists");
        }
        users.add(new User(username));
    }

    /**
     *
     * @param username
     * @throws Exception
     */
    public static void deleteUser(String username) throws Exception {
        if (!users.contains(new User(username))) {
            throw new Exception("User Does Not Exist");
        }
        users.remove(new User(username));
    }

    /**
     * Call just before changing to previous scene
     */
    @SuppressWarnings("unchecked casting")
    public static void initPreviousScene() {
        if (dataSnapshots.isEmpty()) {
            throw new RuntimeException("there is no previous scene");
        }
        dataTransfer = (ArrayList<Object>) dataSnapshots.pop();
    }

    /**
     * Call just before adding objects to Model.dataTransfer for next scene
     * @param comeBack Whether the next scene can directly come back to the current scene in a linear transition
     *                 (usually only valid for scenes on the same stage because otherwise, the user can go back in multiple
     *                 stages at the same time)
     *                 (Valid: A <-> B <-> C, linear transition) (Invalid: A <- C, C can not jump over B)
     */
    public static void initNextScene(boolean comeBack) {
        if (comeBack) {
            dataSnapshots.push(dataTransfer.clone());
        }
        dataTransfer.clear();
    }

    /**
     *
     */
    public static void logOut() {
        dataSnapshots.clear();
        dataTransfer.clear();
        Photos.closeViewPhotoStage();
    }
}


