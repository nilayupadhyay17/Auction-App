package app.com.upadhyay.nilay.myapplication.UserInterface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import app.com.upadhyay.nilay.myapplication.R;

/**
 * Created by nilay on 1/2/2017.
 */

public class
BidCompletion extends Fragment {
    ImageView background;
    TextView tv1, tv2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bid_submit, container,false);
        background = (ImageView)view.findViewById(R.id.iv_backgroundW);
        tv1 = (TextView)view.findViewById(R.id.tv1);
        tv2 =(TextView)view.findViewById(R.id.tv2);

        initBackground();
        return view;
    }
    private void initBackground() {
        Glide.with(this).load(R.drawable.background).centerCrop().into(background);
    }
}
