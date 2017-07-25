package app.com.upadhyay.nilay.myapplication.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.com.upadhyay.nilay.myapplication.R;
import app.com.upadhyay.nilay.myapplication.beans.AuctionDetail;
import io.realm.RealmResults;

/**
 * Created by nilay on 3/3/2017.
 */

public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private String TAG="Nilay";
    RealmResults<AuctionDetail> mResults ;
    public static final int item =0;
    Context con;
    ClickListener clickListener;
    public AdapterDrops(Context context,RealmResults<AuctionDetail> results){
        mLayoutInflater = LayoutInflater.from(context);
        this.con = context;
        update(results);
        notifyDataSetChanged();
    }
    public void update(RealmResults<AuctionDetail> results){
        mResults = results;
    }
    @Override
    public int getItemViewType(int position) {
        if(mResults == null ||position<mResults.size()){
            return item;
        }
        return item;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.single_item_new,parent,false);
        DropHolder dropHolder = new DropHolder(view);
        Log.d(TAG,"onCreateViewHolder");
        return dropHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  DropHolder) {
            DropHolder dropHolder  = (DropHolder)holder;
            AuctionDetail ad = mResults.get(position);
            dropHolder.mTextWhat.setText(ad.getName().toString());
        }
    }
    @Override
    public int getItemCount() {
        return mResults.size();
    }
    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }
    class DropHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTextWhat;
        TextView mTextWhen;
        public DropHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextWhat = (TextView) itemView.findViewById(R.id.tv_startTime);
           // mTextWhen = (TextView) itemView.findViewById(R.id.tv_when);
        }
        @Override
        public void onClick(View v) {
            if(clickListener != null){
                clickListener.itemClicked(v,getPosition());
            }
        }
    }
    public interface ClickListener {
        public void itemClicked(View view, int position);
    }
}