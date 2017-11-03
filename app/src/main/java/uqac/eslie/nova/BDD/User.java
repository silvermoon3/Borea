package uqac.eslie.nova.BDD;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ESTEL on 26/10/2017.
 */

public class User {
    private String displayName;
    private String email;

    public User(){

    }

    public User(FirebaseUser user){
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
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
