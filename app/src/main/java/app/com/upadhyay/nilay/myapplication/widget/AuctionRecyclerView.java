package app.com.upadhyay.nilay.myapplication.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import app.com.upadhyay.nilay.myapplication.extras.Util;

/**
 * Created by nilay on 3/14/2017.
 */

public class AuctionRecyclerView extends RecyclerView {
    private List<View> mnonEmptyViews = Collections.emptyList();
    private List<View> mEmptyViews = Collections.emptyList();
    private AdapterDataObserver mObserver= new AdapterDataObserver(){
        @Override
        public void onChanged() {
            toggleViews();
        }
        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            toggleViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            toggleViews();
        }
    };

    private void toggleViews() {
        if(getAdapter() !=null && !mEmptyViews.isEmpty() && !mnonEmptyViews.isEmpty()){
            if (getAdapter().getItemCount() ==0){
                //show all empty views
                Util.showViews(mEmptyViews);
                //hide recyclerview
                setVisibility(View.GONE);
                //hide non empty views
                Util.hideViews(mnonEmptyViews);
            }
            else {

                Util.showViews(mnonEmptyViews);
                //hide recyclerview
                setVisibility(View.VISIBLE);
                //hide non empty views
                Util.hideViews(mEmptyViews);
            }
        }
    }

    public AuctionRecyclerView(Context context) {
        super(context);
    }

    public AuctionRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AuctionRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if(adapter != null){
            adapter.registerAdapterDataObserver(mObserver);
        }
        //mObserver.onChanged();

    }

    public void hideIfEmpty(View ...views) {
        mnonEmptyViews = Arrays.asList(views);
    }

    public void showIfEmpty(View ...emptyViews) {
        mEmptyViews = Arrays.asList(emptyViews);
    }

}
