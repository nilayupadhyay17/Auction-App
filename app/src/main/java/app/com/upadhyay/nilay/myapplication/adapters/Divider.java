package app.com.upadhyay.nilay.myapplication.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import app.com.upadhyay.nilay.myapplication.R;

/**
 * Created by nilay on 3/22/2017.
 */

public class Divider extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private  int mOrientation;
    public Divider(Context context, int orientation){
        mDivider = ContextCompat.getDrawable(context,R.drawable.divider);
        if(orientation != LinearLayoutManager.VERTICAL) {
            throw  new IllegalArgumentException("Can only be used with Vertical Recycler View");
        }
        mOrientation = orientation;
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //super.onDraw(c, parent, state);
        if(mOrientation == LinearLayout.VERTICAL){
            drawHorizontalDivider(c,parent,state);
        }
    }
    private void drawHorizontalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left,top,right,bottum;
        left=parent.getPaddingLeft();
        right =parent.getWidth() -parent.getPaddingRight();
        int count =parent.getChildCount();
            for(int i=0;i<count;i++){
                View current = parent.getChildAt(i);
                RecyclerView.LayoutParams params= (RecyclerView.LayoutParams)current.getLayoutParams();
                top =current.getTop() - params.topMargin;
                bottum = top+mDivider.getIntrinsicHeight();
                mDivider.setBounds(left,top,right,bottum);
                mDivider.draw(c);
            }
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
