package com.ualr.recyclerviewassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

public class AdapterListBasic extends RecyclerView.Adapter {

    private List<Inbox> mItems;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(View view, Inbox obj, int position);
    }

    public OnItemClickListener mListener;

    public AdapterListBasic(Context context, List<Inbox> items) {
        this.mItems = items;
        this.mContext = context;
    }
    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int index) {
        InboxViewHolder inboxViewHolder = (InboxViewHolder) holder;
        Inbox i = mItems.get(index);

        inboxViewHolder.from.setText(i.getFrom());
        inboxViewHolder.email.setText(i.getEmail());
        inboxViewHolder.message.setText(i.getMessage());
        inboxViewHolder.date.setText(i.getDate());
        inboxViewHolder.letter.setText(i.getLetter());
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

        public InboxViewHolder(final View itemView) {
            super(itemView);
            from = itemView.findViewById(R.id.from);
            email = itemView.findViewById(R.id.email);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.date);
            letter = itemView.findViewById(R.id.letter);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
            lyt_parent.setOnClickListener(new View.OnClickListener() {
                //@Override
                public void onClick(View v) {
                    mListener.onItemClick(v, mItems.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition());
                }
            });
        }
    }
}
