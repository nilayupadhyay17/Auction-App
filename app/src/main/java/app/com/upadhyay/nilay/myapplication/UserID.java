package app.com.upadhyay.nilay.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by nilay on 4/12/2017.
 */

public class UserID extends RealmObject {
    @Required
    private String userId;
    private String traderType;

    public String getTraderType() {
        return traderType;
    }
    public void setTraderType(String traderType) {
        this.traderType = traderType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
