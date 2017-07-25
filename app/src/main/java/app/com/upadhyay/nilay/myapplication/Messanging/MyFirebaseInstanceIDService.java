package app.com.upadhyay.nilay.myapplication.Messanging;

/**
 * Created by nilay on 2/5/2017.
 */

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.load.resource.UnitTransformation;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static String TAG ="MyFirebaseInstanceIDService";
    public static final String MyPREFERENCES = "com.upadhyay.nilay.regis" ;
    String refreshedToken;
    @Override
    public void onTokenRefresh() {
        // super.onTokenRefresh();
        refreshedToken= FirebaseInstanceId.getInstance().getToken();
        Log.e("passenger", refreshedToken);
        ;
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(MyPREFERENCES, getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("firebaseToken", refreshedToken);
        editor.commit();

        /*Realm.init(getApplicationContext());
        Realm realm = Realm.getInstance(getApplicationContext())
        //Realm mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        RegistrationID registrationID = mRealm.createObject(RegistrationID.class);
        registrationID.setRegId(refreshedToken);
        mRealm.commitTransaction();
        mRealm.close();*/
        //startAsyncTask();
    }
    /*private void startAsyncTask() {
        RegistrationEndpointsAsyncTask registrationEndpointsAsyncTask = new RegistrationEndpointsAsyncTask(new registration() {
            @Override
            public void onTaskDone() {
               // Toast.makeText(getApplicationContext(), "Token Successfully sent to server", Toast.LENGTH_SHORT).show();
            }
        });
        registrationEndpointsAsyncTask.execute(refreshedToken);
    }*/
    public interface registration{
        public void onTaskDone();
    }
   }
