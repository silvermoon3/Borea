package uqac.eslie.nova.BDD;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ESTEL on 02/11/2017.
 */

public class DataBaseHelper {

    private static CarPooling currentCarPooling;
    private static FirebaseUser currentUser;


    public static CarPooling getCurrentCarPooling() {
        return currentCarPooling;
    }

    public static void setCurrentCarPooling(CarPooling _currentCarPooling) {
        currentCarPooling = _currentCarPooling;
    }


    public static FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(FirebaseUser _currentUser) {
        currentUser = _currentUser;
    }
}
