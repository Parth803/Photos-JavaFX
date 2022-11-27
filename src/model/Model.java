package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

public final class Model {
    public static ArrayList<User> users;
    public static User currentUser;
    public static ArrayList<Object> dataTransfer;
    private static Stack<Object> dataSnapshots;

    private Model() {}

    @SuppressWarnings("unchecked casting")
    public static void init() {
        dataTransfer = new ArrayList<>();
        dataSnapshots = new Stack<>();
        File serializedUsers = new File("data/admin/users.txt");
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
                stockAlbum.addPhoto("data/stock/one.jpeg", "first");
                stockAlbum.addPhoto("data/stock/two.jpeg", "second");
                stockAlbum.addPhoto("data/stock/three.jpeg", "third");
                stockAlbum.addPhoto("data/stock/four.jpeg", "fourth");
                stockAlbum.addPhoto("data/stock/five.jpeg", "fifth");
                stockAlbum.addPhoto("data/stock/six.jpeg", "sixth");
                stockAlbum.addPhoto("data/stock/seven.jpeg", "seventh");
            } catch (Exception e) {
                throw new RuntimeException("stock photos could not be added");
            }
        }
        else {
            try {
                FileInputStream file = new FileInputStream("data/admin/users.txt");
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

    public static void setCurrentUser(String username) throws Exception {
        for (User user : Model.users) {
            if (user.username.equals(username)) {
                currentUser = user;
                return;
            }
        }
        throw new Exception("username does not exist");
    }

    public static void addUser(String username) throws Exception {
        if (users.contains(new User(username))) {
            throw new Exception("User Already Exists");
        }
        users.add(new User(username));
    }

    public static void deleteUser(String username) throws Exception {
        if (!users.contains(new User(username))) {
            throw new Exception("User Does Not Exist");
        }
        users.remove(new User(username));
    }

    public static void initPreviousScene() {
        if (dataSnapshots.isEmpty()) {
            return;
        }
        Object o = dataSnapshots.pop();
    }
}



