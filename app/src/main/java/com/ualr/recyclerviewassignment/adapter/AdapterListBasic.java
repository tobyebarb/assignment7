package com.ualr.recyclerviewassignment.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.MainActivity;
import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;
import java.util.logging.Handler;

public class AdapterListBasic extends RecyclerView.Adapter {

    private List<Inbox> mItems;
    private Context mContext;
    private static final String TAG = MainActivity.class.getSimpleName();
    public int position;

    public interface OnItemClickListener {
        void onItemClick(View v, Inbox obj, int position);
    }

    public OnItemClickListener mListener;

    public AdapterListBasic(Context context, List<Inbox> items) {
        this.mItems = items;
        this.mContext = context;
    }
    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

    public void removeItem(int position) {
        if (position >= mItems.size()) {
            return;
        }
        mItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public boolean itemSelected(int position) {
        return mItems.get(position).isSelected();
    }

    public void updateItems(List<Inbox> items) {
        this.mItems = items;
        notifyItemInserted(0);
    }

    public void softUpdateItems(List<Inbox> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    public int getIndex() {
        return position;
    }

    public void addItem(int position, Inbox item) {
        mItems.add(position, item);
        notifyItemInserted(position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inbox, parent, false);
        vh = new InboxViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int index) { // final can be removed?

        InboxViewHolder inboxViewHolder = (InboxViewHolder) holder;
        Inbox i = mItems.get(index);

        inboxViewHolder.from.setText(i.getFrom());
        inboxViewHolder.email.setText(i.getEmail());
        inboxViewHolder.message.setText(i.getMessage());
        inboxViewHolder.date.setText(i.getDate());
        inboxViewHolder.letter.setText(i.getLetter());

        if(i.isSelected()) {
            inboxViewHolder.background.setBackgroundColor(mContext.getResources().getColor(R.color.grey_300));
            inboxViewHolder.letter.setText("");
            inboxViewHolder.xImage.setVisibility(View.VISIBLE);
        }
        else {
            inboxViewHolder.background.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
            inboxViewHolder.letter.setText(i.getLetter());
            inboxViewHolder.xImage.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

    public int checkEntries(){
        for (int i=0; i< getItemCount();i++) {
            if(mItems.get(i).isSelected()) return i;
        }
        return -1;
    }

    public class InboxViewHolder extends RecyclerView.ViewHolder {

        public TextView from;
        public TextView email;
        public TextView message;
        public TextView date;
        public TextView letter;
        public View lyt_parent;
        public View background;
        public ImageView thumbnail;
        public ImageView xImage;

        public InboxViewHolder(final View itemView) {
            super(itemView);
            from = itemView.findViewById(R.id.from);
            email = itemView.findViewById(R.id.email);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.date);
            letter = itemView.findViewById(R.id.letter);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
            background = itemView.findViewById(R.id.background);
            thumbnail = itemView.findViewById(R.id.image);
            xImage = itemView.findViewById(R.id.x_image);


            lyt_parent.setOnClickListener(new View.OnClickListener() {
                //@Override
                public void onClick(View v) {
                    position = getAbsoluteAdapterPosition();
                    mListener.onItemClick(v, mItems.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition());
                    int old_position=checkEntries();
                    if (old_position==-1) mItems.get(getAbsoluteAdapterPosition()).toggleSelection();
                    else {
                        mItems.get(old_position).toggleSelection();
                        mItems.get(getAbsoluteAdapterPosition()).toggleSelection();
                    }
                    notifyDataSetChanged();
                }
            });

        }
    }
}
