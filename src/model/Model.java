package model;

import java.util.ArrayList;

public final class Model {
    public static ArrayList<User> users;
    private Model() {}
    public static void initializeModel() {
        // if (users.txt isEmpty) ->
        users = new ArrayList<User>();
        // else ->
        // users = readSerializedArrayListOfTypeUser();
    }
    // use java.io.ObjectOutputStream/java.io.ObjectInputStream classes to:
    // create methods to store and retrieve the ArrayList of users in data/admin/users.txt

}


