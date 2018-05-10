package com.example.mlubli.chuckauey2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static java.security.AccessController.getContext;

public class SignsAdapter extends RecyclerView.Adapter<SignsAdapter.ViewHolder> {
   private Context mContext;
    private ArrayList<SignItem> mSignList;

    public SignsAdapter (Context mContext, ArrayList<SignItem> mSignList){
        this.mContext = mContext;
        this.mSignList = mSignList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public CardView mCardView;





        public ViewHolder(View itemView) {

            super(itemView);

            mImageView = itemView.findViewById(R.id.imageView);
            mCardView = (CardView) itemView.findViewById(R.id.cardview_id);



        }



    }

    public void test()
    {

    }

    public SignsAdapter(ArrayList<SignItem> signList){
    mSignList = signList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.signs_item, parent, false);
        ViewHolder mvh = new ViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final SignItem currentItem = mSignList.get(position);


        holder.mImageView.setImageResource(currentItem.getImageResource());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pass data to SignActivity
            Intent intent = new Intent(mContext, SignActivity.class);
            intent.putExtra("Image",currentItem.getImageResource());
            intent.putExtra("Name",currentItem.getsName());
            intent.putExtra("Description",currentItem.getsDescription());



            //start the activity
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSignList.size();
    }

    public void filterList(ArrayList<SignItem> filteredList)
    {

        mSignList = filteredList;
        notifyDataSetChanged();

    }


}


