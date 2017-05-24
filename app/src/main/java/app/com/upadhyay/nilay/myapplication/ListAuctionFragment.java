package app.com.upadhyay.nilay.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by nilay on 2/21/2017.
 */


public class ListAuctionFragment extends Fragment implements AdapterView.OnItemClickListener{
    GridView gridView;
    public ListAuctionFragment(){

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.view_grid,container,false);
        gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(new MyAdapter(getActivity()));
        return rootView;
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Viewholder viewholder= (Viewholder) view.getTag();
        Country temp = (Country) viewholder.myImageView.getTag();
        //intent.putExtra("name",temp.imageId);
        //intent.putExtra("Image",temp.countryname);
    }

    class Country{
        int imageId;
        String countryName;
        Country(int imageId,String countryName){
            this.imageId = imageId;
            this.countryName = countryName;
        }

    }
    class Viewholder {
        ImageView myImageView;
        Viewholder (View v){
            myImageView= (ImageView) v.findViewById(R.id.simageView);
        }

    }
    class MyAdapter extends BaseAdapter{
        ArrayList<Country> list;
        Context context;
        public MyAdapter(){

        }
        public MyAdapter(Context context) {
            this.context = context;
            Resources res = context.getResources();
            String[] tempCountryNames = res.getStringArray(R.array.country_names);
            int[] countryImages = {R.drawable.facebook,R.drawable.twitter,R.drawable.facebook,R.drawable.twitter,R.drawable.facebook,R.drawable.twitter,R.drawable.facebook,R.drawable.twitter,R.drawable.facebook,R.drawable.twitter};
            for (int i=0;i<10;i++){
                Country country= new Country(countryImages[i],tempCountryNames[i]);
                list.add(country);
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
            Viewholder holder = null;
            if(row == null){
              LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row=layoutInflater.inflate(R.layout.single_item,parent,false);

                Viewholder viewholder = new Viewholder(row);
                row.setTag(viewholder);
            }
            else{
                holder = (Viewholder) row.getTag();
            }
            Country temp = list.get(position);
            holder.myImageView.setImageResource(temp.imageId);
            holder.myImageView.setTag(temp);
            return row;
        }
    }
    private void addaction(){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Realm realm = Realm.getDefaultInstance();
       // Drop drop = new Drop("nilay",12,12,false);
        realm.beginTransaction();
       // realm.copyFromRealm(drop);
        realm.commitTransaction();
        realm.close();
        //Realm.


    }
}
