package app.com.upadhyay.nilay.myapplication;

/**
 * Created by nilay on 12/2/2016.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

public class BottumSectionFragment extends Fragment{
    private static TextView topText;
    private static TextView bottumText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottum_section_fragment,container,false);
        topText = (TextView) view.findViewById(R.id.toptext);
        bottumText = (TextView)view.findViewById(R.id.bottumtext);

        return view;
    }
    public void setMemeText(String toptext,String bottumtext){
        topText.setText(toptext);
        bottumText.setText(bottumtext);

    }

}
