package app.com.upadhyay.nilay.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import app.com.upadhyay.nilay.myapplication.adapters.Divider;
import app.com.upadhyay.nilay.myapplication.beans.AuctionDetail;
import app.com.upadhyay.nilay.myapplication.widget.AuctionRecyclerView;
import app.com.upadhyay.nilay.myapplication.adapters.AdapterDrops;
import app.com.upadhyay.nilay.myapplication.beans.AuctionMain;
import io.realm.Realm;
import io.realm.RealmChangeListener;
//import io.realm.RealmModel;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayAuctionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DisplayAuctionFragment} factory method to
 * create an instance of this fragment.
 */
public class DisplayAuctionFragment extends Fragment implements AdapterDrops.ClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    AuctionRecyclerView mRecycler;
    ImageView background;
    AdapterDrops mAdapter;
    Toolbar mtToolbar;
    private OnFragmentInteractionListener mListener;
    RealmResults<AuctionMain> mrealmResult;
    View emptyView;
    RealmResults realmResults;
    Communicator comm;
    public DisplayAuctionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment DisplayAuctionFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*public static DisplayAuctionFragment newInstance(String param1, String param2) {
        DisplayAuctionFragment fragment = new DisplayAuctionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_display_auction, container, false);
        mRecycler = (AuctionRecyclerView) view.findViewById(R.id.rv_drops);
        background = (ImageView)view.findViewById(R.id.iv_background);
        //mtToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        emptyView = view.findViewById(R.id.empty_drops);
        //some methods to use, when showing/hiding tool bar with recyclerview
        //mRecycler.hideIfEmpty(mtToolbar);
       // mRecycler.showIfEmpty(emptyView);

        //LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        //mRecycler.setLayoutManager(manager);
        Realm realm = Realm.getDefaultInstance();
        //realm.beginTransaction();
        //realm.deleteAll();
       // realm.commitTransaction();
        mrealmResult = realm.where(AuctionMain.class).findAll();
        realmResults =  mrealmResult.where().findAll();
        //Log.e("error", mrealmResult.get(0).getAuction().get(0).toString());
        //Log.e("error", String.valueOf(mrealmResult.get(0).getAuction().where().findAll().size()));
        mAdapter = new AdapterDrops(getActivity(),mrealmResult.get(0).getAuction().where().findAll());
        mAdapter.setClickListener(this);
        mRecycler.addItemDecoration(new Divider(getActivity(), LinearLayoutManager.VERTICAL));
        mRecycler.setAdapter(mAdapter);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mtToolbar);
       // getInfo();
        initBackground();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mrealmResult.addChangeListener(mChangeListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mrealmResult.removeChangeListener(mChangeListener);
    }
    private RealmChangeListener mChangeListener = new RealmChangeListener() {;
        @Override
        public void onChange(Object element) {
            Log.d("Nilay","onchange was called");
          mAdapter.update(mrealmResult.get(0).getAuction().where().findAll());
        }
    };
    private void initBackground() {
        Log.e("da","initback");
        Glide.with(this).load(R.drawable.background).centerCrop().into(background);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            comm = (Communicator)getActivity();
        }catch (ClassCastException e){
            throw new RuntimeException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void itemClicked(View view, int position) {
        //Log.d("view","item clicked"+view.get);
        AuctionDetail ad = mrealmResult.get(0).getAuction().where().findAll().get(position);
        String[] arr = new String[2];
        arr[0] = ad.getId();
        arr[1] = ad.getStartTime().toString();

        //Log.e("val",mrealmResult.get(0).getAuction().where().findAll().get(position).toString());
    comm.auctionClicked(view,position,arr);
    }
    /*class Auction  {
        Long auctionId;
        Long startTime;
        Auction(Long auctionId,Long startTime){
            this.auctionId = auctionId;
            this.startTime = startTime;
        }
    }*/
    class Viewholder {
        ImageView myImageView;
        Viewholder (View v){
            myImageView= (ImageView) v.findViewById(R.id.simageView);
        }

    }
    class MyAdapter extends BaseAdapter {
        ArrayList<ListAuctionFragment.Country> list;
        Context context;
        public MyAdapter(){

        }
        public MyAdapter(Context context) {
            this.context = context;
            Resources res = context.getResources();
            //String[] tempCountryNames = res.getStringArray(R.array.country_names);
            //int[] countryImages = {R.drawable.facebook,R.drawable.twitter,R.drawable.facebook,R.drawable.twitter,R.drawable.facebook,R.drawable.twitter,R.drawable.facebook,R.drawable.twitter,R.drawable.facebook,R.drawable.twitter};
            for (int i=0;i<10;i++){
                //ListAuctionFragment.Country country= new ListAuctionFragment.Country(countryImages[i],tempCountryNames[i]);
                //list.add(country);
            }
            //super();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ListAuctionFragment.Viewholder holder = null;
            if(row == null){
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row=layoutInflater.inflate(R.layout.single_item,parent,false);

                //ListAuctionFragment.Viewholder viewholder = new ListAuctionFragment.Viewholder(row);
                //row.setTag(viewholder);
            }
            else{
                holder = (ListAuctionFragment.Viewholder) row.getTag();
            }
            ListAuctionFragment.Country temp = list.get(position);
            holder.myImageView.setImageResource(temp.imageId);
            holder.myImageView.setTag(temp);
            return row;
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
