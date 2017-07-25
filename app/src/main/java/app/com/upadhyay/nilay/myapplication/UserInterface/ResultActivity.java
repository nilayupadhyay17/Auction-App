package app.com.upadhyay.nilay.myapplication.UserInterface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import app.com.upadhyay.nilay.myapplication.R;
import app.com.upadhyay.nilay.myapplication.UserSession.SessionManager;

public class ResultActivity extends AppCompatActivity {
    ImageView background;
    SessionManager session;
    String name;
    int dbid, pbid = 0;
    String dname,pname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_result);
        Intent intent = getIntent();
        String message = intent.getStringExtra("msg");
        TextView pnameEdit = (TextView) findViewById(R.id.pname);
        TextView aname = (TextView) findViewById(R.id.aname);
        TextView result = (TextView) findViewById(R.id.result);
        session =  new SessionManager(getApplicationContext());
        //getgetSupportActionBar().show();
       // getSupportActionBar().setTitle("Matched");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Passenger app - "+"     "+"Auction Finished");
        setSupportActionBar(toolbar);
        //textView.setText(id);
        background = (ImageView)findViewById(R.id.iv_backgroundW);
        initBackground();
        try {
            JSONArray ja = new JSONArray(message);

            JSONObject mainobj = ja.getJSONObject(0);
            name = mainobj.getString("name");
            dname = mainobj.getString("dbid");
            String  price = mainobj.getString("price");
           // Log.e("name", String.valueOf(dbid));
            //Log.e("driver bid", String.valueOf(dbid));
            pname = mainobj.getString("pbid");
            String dlocation = mainobj.getString("dl");
            aname.setText(String.valueOf(name) +" is ended \n    price for your ride: "+price);
            pnameEdit.setText("Hello, "+pname.toString());
            result.setText("Driver Name: "+String.valueOf(dname)+"\nDriver Location: "+String.valueOf(dlocation));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(session.checkLogin()){
            // get user data from session
            HashMap<String, String> user = session.getUserDetails();

            // name
            String type = user.get(SessionManager.KEY_TYPE);

            // email
            String email = user.get(SessionManager.KEY_EMAIL);

        }
    }
    private void initBackground() {

        Glide.with(this).load(R.drawable.background).centerCrop().into(background);
    }


}

