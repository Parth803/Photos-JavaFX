package model;

import java.io.*;
import java.util.ArrayList;

public final class Model {
    public static ArrayList<User> users;
    public static User currentUser;

    private Model() {}

    @SuppressWarnings("unchecked casting")
    public static void initializeModel() {
        File serializedUsers = new File("data/admin/users.txt");
        if (serializedUsers.length() == 0) {
            users = new ArrayList<>();
            users.add(new User("admin"));
            users.add(new User("stock"));
        }
        else {
            try {
                FileInputStream file = new FileInputStream("data/admin/users.txt");
                ObjectInputStream input = new ObjectInputStream(file);
                users = (ArrayList<User>) input.readObject();
                input.close();
                file.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    public static void addUser(String newUsername) throws Exception {
        if (getUserIndex(newUsername) != -1) {
            throw new Exception("User Already Exists");
        }
    }

    public static void deleteUser(String deleteUsername) throws Exception {
        if (getUserIndex(deleteUsername) == -1) {
            throw new Exception("User Does Not Exist");
        }
    }

    public static int getUserIndex(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i) != null && users.get(i).username.compareTo(username) == 0) {
                return i;
            }
        }
        return -1;
    }
}



