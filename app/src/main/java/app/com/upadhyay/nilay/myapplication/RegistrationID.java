package app.com.upadhyay.nilay.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by nilay on 4/7/2017.
 */

public class RegistrationID extends RealmObject {
    @Required
    private String regId;

    public String getRegId() {
        return regId;
    }
    public void setRegId(String regId) {
        this.regId = regId;
    }
}
