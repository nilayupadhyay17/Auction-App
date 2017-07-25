package app.com.upadhyay.nilay.myapplication.UserInterface;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.com.upadhyay.nilay.myapplication.R;
import app.com.upadhyay.nilay.myapplication.UserSession.SessionManager;
import app.com.upadhyay.nilay.myapplication.UserSession.User;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


/**
 * Created by nilay on 6/10/2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText mUserName;
    private EditText mUserPassword;
    private Button loginButton;
    private TextView signup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    ImageView background;
    Communicator comm;
    DatabaseReference ref;
    String username="";
    String type="";
    // Session Manager Class
    SessionManager session;
    public LoginFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();
        // Session Manager
        session = new SessionManager(getActivity().getApplicationContext());
        ref = FirebaseDatabase.getInstance().getReference("users");
        if(firebaseAuth.getCurrentUser() != null){
            // comm.loginResponse("Login test","Login test");
        }
        Log.e("Login Fragment"," onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("Login Fragment"," onCreateView");
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        background = (ImageView)view.findViewById(R.id.iv_Userbackground);
        mUserName = (EditText) view.findViewById(R.id.editUsername);
        mUserPassword =(EditText)view.findViewById(R.id.editPassword);
        signup = (TextView) view.findViewById(R.id.textViewSignUp);
        loginButton = (Button)view.findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(this);
        signup.setOnClickListener(this);
        comm = (Communicator) getActivity();
        initBackground();
        return  view;
    }
    private void initBackground() {
        Glide.with(this).load(R.drawable.background).centerCrop().into(background);
    }

    @Override
    public void onClick(View v) {
        if(v == loginButton){
            userLogin();
        }
        if(v == signup){
            userRegistration();
        }
    }

    private void userRegistration() {
        UserInformationFragment userInformationFragment = new UserInformationFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,userInformationFragment).commit();
    }

    private void userLogin() {
        String email = mUserName.getText().toString().trim();
        String password = mUserPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(),"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(),"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Loging Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //if the task is successfull
                        if(task.isSuccessful()){
                            progressDialog.setMessage(" Fetching User Profile...");
                            username =  firebaseAuth.getCurrentUser().getEmail();
                            queryDatabase();
                        }
                        if(!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                progressDialog.setMessage(" Weak Password exception...");

                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                progressDialog.setMessage(" Invalid Credentials");
                            } catch(FirebaseAuthUserCollisionException e) {
                                progressDialog.setMessage(" User  exception...");
                            } catch(Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                            progressDialog.hide();
                        }
                    }
                });
    }

    public void queryDatabase(){
        ref.child(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                type = user.type;
                session.createLoginSession(username,type);
                progressDialog.dismiss();
                comm.loginResponse(username,type);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Login Fragment"," onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Login Fragment"," onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Login Fragment"," onPause");
    }
}
