package app.com.upadhyay.nilay.myapplication.UserSession;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by nilay on 6/11/2017.
 */

@IgnoreExtraProperties
public class User {

    public String email;
    public String type;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String email, String type) {
        this.email = email;
        this.type = type;
    }
}