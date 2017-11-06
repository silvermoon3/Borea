package uqac.eslie.nova.BDD;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 * Created by ESTEL on 26/10/2017.
 */

public class User {
    private String displayName;
    private String email;
    private String UID;
    private Uri image;
    private List<CarPooling> carPoolings;
    private String givenName;

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    private String familyName;

    public List<CarPooling> getCarPoolings() {
        return carPoolings;
    }

    public void setCarPoolings(List<CarPooling> carPoolings) {
        this.carPoolings = carPoolings;
    }

    public void addCarPooling(CarPooling _carPooling){
        this.carPoolings.add(_carPooling);
    }



    public User(){

    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public User(FirebaseUser user){
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
        this.UID = user.getUid();


        this.image = user.getPhotoUrl();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
