package de.lindemann.niklas.mamasapp;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Niklas on 12.02.2016.
 */
public class RVAdaper extends RecyclerView.Adapter<RVAdaper.EntryViewHolder>{

    List<EntryMenuItem> mItemList;
    EntryActivity mEntryActivity;
    SearchActivity mSearchActivity;
    private boolean mIsFromSearchActivity;


    public RVAdaper(List<EntryMenuItem> items, EntryActivity activity){
        this.mItemList = items;
        this.mEntryActivity = activity;
        mIsFromSearchActivity = false;
    }

    public RVAdaper(List<EntryMenuItem> items, SearchActivity activity){
        this.mItemList = items;
        this.mSearchActivity = activity;
        mIsFromSearchActivity = true;
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView textHeader;
        TextView textText;
        ImageView imageCV;

        EntryViewHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardview);
            textHeader = (TextView) itemView.findViewById(R.id.cvLabel);
            textText = (TextView) itemView.findViewById(R.id.cvText);
            imageCV = (ImageView) itemView.findViewById(R.id.cvImage);




        }
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        EntryViewHolder v = new EntryViewHolder(view);


        return v;
    }

    @Override
    public void onBindViewHolder(EntryViewHolder holder, final int position) {
        Activity activity;

        if(mIsFromSearchActivity){
            activity = mSearchActivity;
        }
        else {
            activity = mEntryActivity;
        }

        holder.textHeader.setText(mItemList.get(position).getUeberschrift());
        holder.textText.setText(mItemList.get(position).getValue());

        holder.imageCV.setImageBitmap(MyResourceManager.getPictureByPosition(activity, mItemList.get(position).getHauptID()));
        //holder.imageCV.setImageResource(R.drawable.jumpingman);
        // TODO Hintergrund mit geringerer Sättigung

        //holder.cv.setBackgroundColor(ContextCompat.getColor(activity,R.color.card_bg_1));
        //holder.cv.setCardBackgroundColor(ContextCompat.getColor(activity,R.color.card_bg_1));
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsFromSearchActivity){
                    mSearchActivity.mOpenTextActivity(mItemList.get(position));
                }

                else{
                    mEntryActivity.mOpenTextActivity(mItemList.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
