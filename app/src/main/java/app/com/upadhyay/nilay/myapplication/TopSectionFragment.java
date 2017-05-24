package app.com.upadhyay.nilay.myapplication;

/**
 * Created by nilay on 12/1/2016.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;


public class TopSectionFragment extends Fragment {
    private static EditText topTextInput;
    private static EditText bottumTextInput;
    TopSectionListener activityCommander;

    public interface TopSectionListener{
        public void createMeme(String top,String bottum);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            activityCommander = (TopSectionListener) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view  =inflater.inflate(R.layout.top_section_fragment,container,false);
        topTextInput =(EditText) view.findViewById(R.id.topTextInput);
        bottumTextInput=(EditText)view.findViewById(R.id.bottumTextInput);
        final Button button = (Button)view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                buttonClicked(v);
            }
        });
        return  getView();
    }

    public void buttonClicked(View view){
    activityCommander.createMeme(topTextInput.getText().toString(),bottumTextInput.getText().toString());
    }
}
