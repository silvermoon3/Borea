package uqac.eslie.nova.BDD;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ESTEL on 02/11/2017.
 */

public class DataBaseHelper {

    private static CarPooling currentCarPooling;
    private static User currentUser;
    private static KP currentKP;
    private static int currentWeatherGraph = 2;
    private static List<Marker> markers = new ArrayList<Marker>();

    public static  List<Marker> getMarkers() {
        return markers;
    }

    public static void setMarkers(List<Marker> markers) {
        markers = markers;
    }

    public static int getCurrentWeatherGraph() {
        return currentWeatherGraph;
    }

    public static void setCurrentWeatherGraph(int _currentWeatherGraph) {
        currentWeatherGraph = _currentWeatherGraph;
    }

    public static KP getCurrentKP() {
        return KP.listAll(KP.class).get(0);
    }

    public static void setCurrentKP(KP currentKP) {
        DataBaseHelper.currentKP = currentKP;
    }

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
