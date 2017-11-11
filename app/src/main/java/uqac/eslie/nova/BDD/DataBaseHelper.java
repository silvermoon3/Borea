package uqac.eslie.nova.BDD;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ESTEL on 02/11/2017.
 */

public class DataBaseHelper {

    private static CarPooling currentCarPooling;
    private static User currentUser;

    public static GoogleSignInAccount getGoogleAccount() {
        return googleAccount;
    }

    public static void setGoogleAccount(GoogleSignInAccount googleAccount) {
        DataBaseHelper.googleAccount = googleAccount;
    }

    private static GoogleSignInAccount googleAccount;


    public static CarPooling getCurrentCarPooling() {
        return currentCarPooling;
    }

    public static void setCurrentCarPooling(CarPooling _currentCarPooling) {
        currentCarPooling = _currentCarPooling;
    }


    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User _currentUser) {
        currentUser = _currentUser;
    }
}