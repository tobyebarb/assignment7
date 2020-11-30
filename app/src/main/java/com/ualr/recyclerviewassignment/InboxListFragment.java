package com.ualr.recyclerviewassignment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.adapter.AdapterListBasic;
import com.ualr.recyclerviewassignment.databinding.InboxFragmentBinding;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

public class InboxListFragment extends Fragment {

    private static final String INT_VALUE_KEY = "IntValue";
    private static final String TITLE_VALUE_KEY = "TitleValue";
    private static final String TAG = InboxListFragment.class.getSimpleName();

    private TextView from;
    private TextView email;
    private TextView message;
    private TextView date;
    private TextView letter;
    private ImageView thumbnail;
    private ImageView xImage;

    private InboxFragmentBinding mBinding;
    private AdapterListBasic mAdapter;
    private List<Inbox> mDataSource;
    private Context mContext;


    /*public static InboxListFragment newInstance() {

    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inbox_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "here");
        from = view.findViewById(R.id.from);
        email = view.findViewById(R.id.email);
        message = view.findViewById(R.id.message);
        date = view.findViewById(R.id.date);
        letter = view.findViewById(R.id.letter);
        thumbnail = view.findViewById(R.id.image);
        xImage = view.findViewById(R.id.x_image);

        mDataSource = DataGenerator.getInboxData(getActivity());
        // TODO 01. Generate the item list to be displayed using the DataGenerator class
        List<Inbox> items = DataGenerator.getInboxData(mContext);
        items.addAll(DataGenerator.getInboxData(mContext));
        items.addAll(DataGenerator.getInboxData(mContext));

        // TODO 03. Do the setup of a new RecyclerView instance to display the item list properly
        //RecyclerView contactListView = mBinding.recyclerView;
        // TODO 04. Define the layout of each item in the list
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AdapterListBasic(mContext, items);

        // TODO 09. Create a new instance of the created Adapter class and bind it to the RecyclerView instance created in step 03
        //AdapterListBasic adapter = new AdapterListBasic(items);
        //contactListView.setAdapter(adapter);
        mBinding.recyclerView.setAdapter(mAdapter);
    }
}
