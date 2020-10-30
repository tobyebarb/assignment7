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

    public interface OnItemClickListener {
        void onItemClick(View view, Inbox obj, int position);
    }

    public OnItemClickListener mListener;
    public int row_index = -1;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int index) { // final can be removed?

        InboxViewHolder inboxViewHolder = (InboxViewHolder) holder;
        final Inbox i = mItems.get(index);

        inboxViewHolder.from.setText(i.getFrom());
        inboxViewHolder.email.setText(i.getEmail());
        inboxViewHolder.message.setText(i.getMessage());
        inboxViewHolder.date.setText(i.getDate());
        inboxViewHolder.letter.setText(i.getLetter());

        inboxViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = index;
                if(i.isSelected()) {
                    removeItem(index);
                    row_index = -1;
                }
                notifyDataSetChanged();
            }
        });
        if(row_index == index) {
                inboxViewHolder.layout.setBackgroundColor(Color.parseColor("#C0C0C0"));
                inboxViewHolder.letter.setText("X");
                i.setSelected(true);
            }
        else{
                inboxViewHolder.layout.setBackgroundColor(Color.parseColor("#ffffff"));
                inboxViewHolder.letter.setText(i.getLetter());
                i.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

    public class InboxViewHolder extends RecyclerView.ViewHolder {

        public TextView from;
        public TextView email;
        public TextView message;
        public TextView date;
        public TextView letter;
        public View lyt_parent;
        public ConstraintLayout layout;

        public InboxViewHolder(final View itemView) {
            super(itemView);
            from = itemView.findViewById(R.id.from);
            email = itemView.findViewById(R.id.email);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.date);
            letter = itemView.findViewById(R.id.letter);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);


            layout = (ConstraintLayout)itemView.findViewById(R.id.background);

            lyt_parent.setOnClickListener(new View.OnClickListener() {
                //@Override
                public void onClick(View v) {
                    mListener.onItemClick(v, mItems.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition());
                }
            });
        }
    }
}
