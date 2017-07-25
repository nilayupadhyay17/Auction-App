package app.com.upadhyay.nilay.myapplication.UserSession;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by nilay on 4/10/2017.
 */

public class RegistrationInfo extends RealmObject {
    @Required
    String regID;
    String userID;

    public String getRegID() {
        return regID;
    }
    public void setRegID(String regID) {
        this.regID = regID;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
}
