package app.com.upadhyay.nilay.myapplication;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nilay.myapplication.backend.auctionTimeApi.model.AuctionTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import app.com.upadhyay.nilay.myapplication.beans.AuctionMain;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements Communicator,DisplayAuctionFragment.OnFragmentInteractionListener,PlaceBid.OnFragmentInteractionListener,AdapterView.OnItemClickListener {

    private DrawerLayout drawerLayout;
    private ListView listview;
    private  MyAdapter myAdapter;
    private ActionBarDrawerToggle drawerListener;
    Toolbar mToolbar;
    String regID;
    String userID;
    String traderType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new Counter(100000,1000).start();
        drawerLayout =(DrawerLayout) findViewById(R.id.activity_main);
        listview = (ListView)findViewById(R.id.drawerList);
       // mToolbar = (Toolbar) findViewById(R.id.toolbar);
        listview.setOnItemClickListener(this);
        myAdapter = new MyAdapter(this);
       // setSupportActionBar(mToolbar);
        listview.setAdapter(myAdapter);
        drawerListener = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerListener);

        UserInformationFragment userInformationFragment = new UserInformationFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,userInformationFragment).commit();
       // getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Realm.init(this);

        //Drop drop = new Drop();
      /*Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction(); */      //realm.close();
      //initBackground();
        //ListAuctionFragment listAuctionFragment = new ListAuctionFragment();

        //PlaceBid placeBid = new PlaceBid();

         //fragmentTransaction.replace(R.id.fragment_container, listAuctionFragment).commit();
        //getFragmentManager().beginTransaction().add(R.id.fragment_container,placeBid).commit();
        //get the regestration token from the user
    }

    /*    public void getQuotes(View v) {
                new EndpointsAsyncTask(this).execute();
            }*/

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void respond() {
/*        AuctionDetailsEndpoint auctionDetailsEndpoint = new AuctionDetailsEndpoint(new AsyncRespose() {
            int k=0;
            JSONArray auctiondataarr = new JSONArray();
            JSONObject auctiondata  = new JSONObject();

            @Override
            public void progressFinish(List<AuctionTime> auctionTime) {
                if(auctionTime.size() > 0) {
                    Log.e("size","10");
                    try {
                        for (k = 0; k < auctionTime.size(); k++) {
                            JSONObject auction = new JSONObject();
                            auction.put("ID", auctionTime.get(k).get("startTime"));
                            auction.put("startTime", auctionTime.get(k).get("startTime"));
                            auction.put("endTime", auctionTime.get(k).get("endTime"));
                            auctiondataarr.put(auction);
                        }
                        auctiondata.put("auction", auctiondataarr);
                        Log.e("data", String.valueOf(auctiondataarr.length()));
                    } catch (JSONException je) {
                    }
                    Realm realm = Realm.getDefaultInstance();
                    // can be more optimized here.. by not deleting data every time
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                        RealmResults<AuctionMain> auctionMains= realm.where(AuctionMain.class).findAll();
                           //RealmResults<RegistrationID> registrationIDs =realm.where(RegistrationID.class).findAll();
                           // RegistrationID registrationID= registrationIDs.get(0);
                            //Log.e("regId",registrationID.toString());
                            //auctionMains.get(0).deleteFromRealm();
                            auctionMains.where().findAll().deleteAllFromRealm();
                          //  auctionMains.get(1).deleteFromRealm();
                            //realm.deleteAll();

                            realm.createObjectFromJson(AuctionMain.class, auctiondata);
                        }
                    });
               }
               // realm.close();
                DisplayAuctionFragment displayAuctionFragment = new DisplayAuctionFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,displayAuctionFragment).commit();
            }
        });
        auctionDetailsEndpoint.execute();*/
        getInfo();
    }

    @Override
    public void auctionClicked(View view, int position, String[] auctionDetail) {
        //FragmentTransaction fragmentTransaction = ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack("AuctionDetails");
        Bundle args = new Bundle();
        args.putStringArray("ad",auctionDetail);
        PlaceBid placeBid = new PlaceBid();
        placeBid.setArguments(args);
        fragmentTransaction.replace(R.id.fragment_container,placeBid).commit();
    }

    private void getInfo() {
//        Log.e("da","getinfo");
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        RealmResults<RegistrationID> registrationID = mRealm.where(RegistrationID.class).findAll();
        RealmResults<UserID> userIDs = mRealm.where(UserID.class).findAll();
        regID = registrationID.get(0).getRegId();
        userID = userIDs.get(0).getUserId();
        traderType = userIDs.get(0).getTraderType();
        RegistrationInfo registrationInfo = mRealm.createObject(RegistrationInfo.class);
        //registrationInfo.setUserID(String.valueOf(mUserName.getText()));
        registrationInfo.setRegID(regID);
        registrationInfo.setUserID(userID);
        mRealm.commitTransaction();
        startAsyncTask();
    }

    private void startAsyncTask() {
        Log.e("da","asynctaskregis");
        RegistrationEndpointsAsyncTask registrationEndpointsAsyncTask = new RegistrationEndpointsAsyncTask(new AsyncRegistraion() {
            @Override
            public void onSuccess() {
                Log.e("da","asynctaskauction");
                AuctionDetailsEndpoint auctionDetailsEndpoint = new AuctionDetailsEndpoint(new AsyncRespose() {
            int k=0;
            JSONArray auctiondataarr = new JSONArray();
            JSONObject auctiondata  = new JSONObject();

            @Override
            public void progressFinish(List<AuctionTime> auctionTime) {
                if(auctionTime.size() > 0) {
                    //Log.e("size","10");
                    try {
                        for (k = 0; k < auctionTime.size(); k++) {
                            JSONObject auction = new JSONObject();
                            auction.put("ID", "Auction "+k);
                            auction.put("startTime", auctionTime.get(k).get("startTime"));
                            auction.put("endTime", auctionTime.get(k).get("endTime"));
                            auctiondataarr.put(auction);
                        }
                        auctiondata.put("auction", auctiondataarr);
                       // Log.e("data", String.valueOf(auctiondataarr.length()));
                    } catch (JSONException je) {
                    }
                    Realm realm = Realm.getDefaultInstance();
                    // can be more optimized here.. by not deleting data every time
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                        RealmResults<AuctionMain> auctionMains= realm.where(AuctionMain.class).findAll();
                           //RealmResults<RegistrationID> registrationIDs =realm.where(RegistrationID.class).findAll();
                           // RegistrationID registrationID= registrationIDs.get(0);
                            //Log.e("regId",registrationID.toString());
                            //auctionMains.get(0).deleteFromRealm();
                            auctionMains.where().findAll().deleteAllFromRealm();
                          //  auctionMains.get(1).deleteFromRealm();
                            //realm.deleteAll();

                            realm.createObjectFromJson(AuctionMain.class, auctiondata);
                        }
                    });
               }
               // realm.close();
                DisplayAuctionFragment displayAuctionFragment = new DisplayAuctionFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,displayAuctionFragment).commit();
            }
        });
        auctionDetailsEndpoint.execute();
            }
        });
        registrationEndpointsAsyncTask.execute(userID,regID,traderType);
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("onItemclick","MainActiviy");
        selectItem(position);
    }
    public void selectItem(int position) {
        listview.setItemChecked(position,true);
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerListener.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }
    class Counter extends CountDownTimer{

        public Counter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.e("time !!!!!!!!!", String.valueOf(millisUntilFinished/1000));
         }
        @Override
        public void onFinish() {
        }
    }
    public interface AsyncRespose {
        void progressFinish(List<AuctionTime> output);
    }
    public interface AsyncRegistraion {
        void onSuccess();
    }
    class MyAdapter extends BaseAdapter {
        String[]  items;
        private Context context;
        View row =null;
        int[] images= {R.drawable.facebook,R.drawable.googleplus,R.drawable.youtube ,R.drawable.twitter};
        public MyAdapter(Context context){
            this.context = context;
            items = context.getResources().getStringArray(R.array.items);
        }
        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row=  null;
            if(convertView ==null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.custom_row,parent,false);
            }
            else {
                row = convertView;
            }
            TextView titleTextView = (TextView) row.findViewById(R.id.textView);
            ImageView imageView = (ImageView) row.findViewById(R.id.imageView3);
            titleTextView.setText(items[position]);
            imageView.setImageResource(images[position]);
            return row;
        }
    }
    // Create a new Fragment to be placed in the activity layout
    // In case this activity was started with special instructions from an
    // Intent, pass the Intent's extras to the fragment as arguments
    //firstFragment.setArguments(getIntent().getExtras());

    // Add the fragment to the 'fragment_container' FrameLayout

    //getFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();


    //this method gets call by Top Frgment when user clicks on TopFragment
/*    @Override
   public void createMeme(String top, String bottum) {
        BottumSectionFragment bottumSectionFragment=(BottumSectionFragment)getSupportFragmentManager().findFragmentById(R.id.fragment3);
        bottumSectionFragment.setMemeText(top,bottum);

    }*/

}
