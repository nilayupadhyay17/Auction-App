package app.com.upadhyay.nilay.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import io.realm.Realm;

/**
 * Created by nilay on 4/5/2017.
 */

public class UserInformationFragment extends Fragment {
    private Button mSendUserInfo;
    private RadioGroup mRadioTraderGroup;
    private RadioButton mRadioTraderButton;
    private EditText mUserName;

    ImageView background;
    Communicator comm;
    String regID;
    public UserInformationFragment(){
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user, container, false);
        mUserName = (EditText) view.findViewById(R.id.editUsername);
        mRadioTraderGroup = (RadioGroup) view.findViewById(R.id.radioTrader);
        mSendUserInfo = (Button) view.findViewById(R.id.buttonUserInfo);
        background = (ImageView)view.findViewById(R.id.iv_Userbackground);
        initBackground();
        //getInfo();
        comm = (Communicator) getActivity();
        mSendUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId= mRadioTraderGroup.getCheckedRadioButtonId();
                Realm mRealm = Realm.getDefaultInstance();
                mRealm.beginTransaction();
                UserID uid = mRealm.createObject(UserID.class);
                uid.setUserId(mUserName.getText().toString());

                //Log.e("regId",registrationID.get(0).getRegId());
                //Log.e("userId",String.valueOf(mUserName.getText()));
               // mRealm.close();
               // Log.e("realm",registrationIDs.get(0).toString());
              //  Log.e("id", String.valueOf(selectedId));
                mRadioTraderButton = (RadioButton) getActivity().findViewById(selectedId);
                if(mRadioTraderButton.getText()=="Driver"){
                   uid.setTraderType("D");
                    Toast.makeText(getActivity(),
                            String.valueOf(mRadioTraderButton.getText()), Toast.LENGTH_SHORT).show();
                }else
                {
                    uid.setTraderType("P");
                    Toast.makeText(getActivity(),
                            String.valueOf(mRadioTraderButton.getText()), Toast.LENGTH_SHORT).show();
                }
                mRadioTraderButton.setTextColor(Color.parseColor("#ffffff"));
                mRealm.commitTransaction();
                mRealm.close();
                comm.respond();
            }
        });
        return view;
    }
     public void addListenerOnButton() {
    }
    private void initBackground() {
        Glide.with(this).load(R.drawable.background).centerCrop().into(background);
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
