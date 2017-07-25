package app.com.upadhyay.nilay.myapplication.UserInterface;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.com.upadhyay.nilay.myapplication.R;
import app.com.upadhyay.nilay.myapplication.UserSession.User;
import app.com.upadhyay.nilay.myapplication.UserSession.UserID;
import io.realm.Realm;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by nilay on 4/5/2017.
 */

public class UserInformationFragment extends Fragment {
    private Button mSendUserInfo;
    private EditText mUserName;
    private EditText mUserPassword,nameedit;
    ImageView background;
    Communicator comm;
    String regID,type;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView  textViewSignIn;
    private DatabaseReference mDatabase;
    String name;
    public UserInformationFragment(){
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        progressDialog = new ProgressDialog(getActivity());
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
//           comm.loginResponse("Login test","Login test");
            Log.e("Userinfo","OnCreate");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e("Userinfo","OnActivitycreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.mi_logout);
        item.setVisible(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("Userinfo","OnCreateView");
        // Log.e("userInfo","onCreateView");
        View view= inflater.inflate(R.layout.fragment_user, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Passenger App");
        mUserName = (EditText) view.findViewById(R.id.editUsername);
        mUserPassword =(EditText)view.findViewById(R.id.editPassword);
        nameedit =(EditText)view.findViewById(R.id.name);
        mSendUserInfo = (Button) view.findViewById(R.id.buttonUserInfo);
        background = (ImageView)view.findViewById(R.id.iv_Userbackground);
        textViewSignIn = (TextView) view.findViewById(R.id.textViewSignIn);
        initBackground();
        //getInfo();
        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"TextView Sign in", Toast.LENGTH_SHORT).show();
                LoginFragment loginFragment = new LoginFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,loginFragment).commit();
            }
        });
        comm = (Communicator) getActivity();
        mSendUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm.init(getActivity().getApplicationContext());
                Realm mRealm = Realm.getDefaultInstance();
                mRealm.beginTransaction();
                UserID uid = mRealm.createObject(UserID.class);
                uid.setUserId(mUserName.getText().toString());
                SharedPreferences prefs = getActivity().getSharedPreferences("com.example.myapp", Context.MODE_PRIVATE);
                mRealm.commitTransaction();
                mRealm.close();
                name = nameedit.getText().toString();
                String email = mUserName.getText().toString().trim();
                String password = mUserPassword.getText().toString().trim();
                registerUser(email,password);
            }
        });
        return view;
    }

    public void registerUser(String email, String password) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(),"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(),"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        //creating a new user
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            String email = firebaseAuth.getCurrentUser().getEmail();
                            User user = new User(email,name);
                            mDatabase.child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
                            comm.loginResponse(email,name);
                        }
                        else {
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Log.e("Firebase Exception",e.getReason());
                                Toast.makeText(getActivity(),e.getReason().toString(),Toast.LENGTH_SHORT).show();
                                progressDialog.setMessage(" Invalid Credentials");
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                progressDialog.setMessage(" Invalid Credentials");
                                Toast.makeText(getActivity(),task.getException().getMessage().toString(),Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(getActivity(),task.getException().getMessage().toString(),Toast.LENGTH_SHORT).show();
                                progressDialog.setMessage(" User  exception...");
                            } catch(Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                                progressDialog.hide();
                        }
                    }
                });
    }
    private void initBackground() {
        Glide.with(this).load(R.drawable.background).centerCrop().into(background);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Userinfo","onPause");
    }

    @Override
    public void onStop() {
        Log.e("Userinfo","onStop");
        super.onStop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
