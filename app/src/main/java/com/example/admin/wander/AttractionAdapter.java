package com.example.admin.wander;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/*
* The adapter class for the RecyclerView, containing information about attraction
 * we will need two classes one for RecyclerView.Adapter
 * and one to hold view for RecyclerView.ViewHolder
 */
public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.AttractionViewHolder>{
        // step 3
    // Member variables.
    private Context mContext;
    private ArrayList<Attraction> mAttractionList;
    // step 3a create a constructor
    public AttractionAdapter(Context context, ArrayList<Attraction> mAttractionList) {
        this.mContext = context;
        this.mAttractionList = mAttractionList;
    }

    // step 2 extends to AttractionAdapter to REcyclerView.Adapter class and implements
    // all three methods onCreateViewHolder, onBindViewHolder, and getItemCount

    @NonNull
    @Override
    public AttractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AttractionViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionViewHolder holder, int position) {
        Attraction mAttraction = mAttractionList.get(position);
        // bind the data to holder
        holder.mTextViewTitle.setText(mAttraction.getTitle());
        holder.mInfo.setText(mAttraction.getInfo());
            }

    @Override
    public int getItemCount() {
        return mAttractionList.size();
    }


    // step 1 create a ViewHolder class within the Attraction Adapter class and extends it to
    // RecyclerView.ViewHolder
    class AttractionViewHolder extends RecyclerView.ViewHolder {
        // step 1a, generate constructor
        /**
         * constructor for AttractionViewHolder
         * */
        ImageView mImageView;
        TextView mTextViewTitle, mInfo;

        public AttractionViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextViewTitle = itemView.findViewById(R.id.title);
            mInfo = itemView.findViewById(R.id.info);
        }
    }
}
