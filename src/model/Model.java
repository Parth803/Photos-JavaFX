package model;

import java.util.ArrayList;

public final class Model {
    public static ArrayList<User> users;

    private Model() {
    }

    public static void initializeModel() {
        // if (users.txt isEmpty) ->
        users = new ArrayList<>();
        // else ->
        // users = readSerializedArrayListOfTypeUser();
    }
    // use java.io.ObjectOutputStream/java.io.ObjectInputStream classes to:
    // create methods to store and retrieve the ArrayList of users in data/admin/users.txt

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


