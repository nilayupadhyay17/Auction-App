package app.com.upadhyay.nilay.myapplication;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import app.com.upadhyay.nilay.myapplication.beans.AuctionDetail;


/**
 *
 * Activities that contain this fragment must implement the
 * {@link PlaceBid.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PlaceBid extends Fragment {

    private OnFragmentInteractionListener mListener;
    private  Button placeBid;
    private EditText bidValue,pickup,drop;
    private TextView name,id,time;
    View line,line1;
    ImageView background;
    public PlaceBid() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String[]  obj =  getArguments().getStringArray("ad");
        View view= inflater.inflate(R.layout.fragment_test, container, false);
        placeBid =(Button) view.findViewById(R.id.buttonPlaceBid);
        bidValue =(EditText) view.findViewById(R.id.editTextBid);
        pickup = (EditText) view.findViewById(R.id.Pickup);
        drop = (EditText)view.findViewById(R.id.Drop);
        background = (ImageView)view.findViewById(R.id.iv_background);
        name =(TextView) view.findViewById(R.id.AuctionDetails);
        id =(TextView) view.findViewById(R.id.ID);
        time =(TextView) view.findViewById(R.id.TimeLeft);
         line = view.findViewById(R.id.line);
        line1 = view.findViewById(R.id.line1);
        String val = getTimePassed(obj[1]);
        id.setText("ID: "+obj[0]);
        time.setText(val);
        initBackground();
        bidValue.setTextColor(Color.parseColor("#ffffff"));
        bidValue.addTextChangedListener(watch);
        //vailidateBid(bidValue.getText().toString());
        placeBid.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startAsyncTask();
                new startAuction().execute();
                Log.e("OnClick","PlaceBid");
            }
        });
        return view;
    }
    private String getTimePassed(String time) {
        long currTime = System.currentTimeMillis();
        long startTime = Long.parseLong(time);
        String ct =String.format("started %02d Minutes and %02d Seconds ago",
                TimeUnit.MILLISECONDS.toMinutes(startTime)- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(startTime)),
                TimeUnit.MILLISECONDS.toSeconds(startTime)- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime)));
        long elapsed = currTime - startTime;
//        Date date = new Date(currTime);
        return ct;
    }
    private void initBackground() {
        Glide.with(this).load(R.drawable.background).centerCrop().into(background);
    }
    public void vailidateBid(String bid){
        System.out.print(bid);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttach(Context context) {
        Log.e("onAttach","placeBid");
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        Log.e("onDetach","placeBid");
        super.onDetach();
        mListener = null;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    TextWatcher watch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //Toast.makeText(getContext(),"before Change",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(start == 9) {
                Toast.makeText(getActivity(), "Maximum Limit Reached", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            //Toast.makeText(this,"after Change",Toast.LENGTH_SHORT).show();
            //Integer.parseInt(s.);
        }
    };

    public interface FragmentCallback {
        public void onTaskDone();

    }
    private void startAsyncTask() {
       // new EndpointsAsyncTask(getActivity()).execute(Integer.parseInt(bidValue.getText().toString()));

        EndpointsAsyncTask endpointAsyncTask = new EndpointsAsyncTask(new FragmentCallback() {

            @Override
            public void onTaskDone() {
                Log.e("OnTaskDone","AsyncTask");
                Toast.makeText(getActivity(), "Bid added succesfully", Toast.LENGTH_SHORT).show();
                BidCompletion bidCompletion = new BidCompletion();
                android.support.v4.app.FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,bidCompletion).commit();
            }})  ;

        endpointAsyncTask.execute(Integer.parseInt(bidValue.getText().toString()));
    }

}
