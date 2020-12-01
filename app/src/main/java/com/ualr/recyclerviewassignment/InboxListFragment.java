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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.adapter.AdapterListBasic;
import com.ualr.recyclerviewassignment.databinding.InboxFragmentBinding;
import com.ualr.recyclerviewassignment.model.Inbox;
import com.ualr.recyclerviewassignment.model.InboxViewModel;

import java.util.List;


public class InboxListFragment extends Fragment{

    public static Context ctx;

    private RecyclerView mRecyclerView;
    private AdapterListBasic mAdapter;
    public Context mContext;
    private FloatingActionButton mFAB;
    private static InboxViewModel viewModel;

    private static final String TAG = InboxListFragment.class.getSimpleName();

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
        viewModel = new ViewModelProvider(getActivity()).get(InboxViewModel.class);
        viewModel.getInboxList().observe(this, new Observer<List<Inbox>>() {
            @Override
            public void onChanged(List<Inbox> inboxes) {
                updateItems(inboxes);
                mRecyclerView.scrollToPosition(0);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inbox_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        // TODO 01. Generate the item list to be displayed using the DataGenerator class
        List<Inbox> items = DataGenerator.getInboxData(mContext);
        items.addAll(DataGenerator.getInboxData(mContext));
        items.addAll(DataGenerator.getInboxData(mContext));
        viewModel.setInboxList(items); //setting the model up with items

        final Context ctx = mContext;

        // TODO 03. Do the setup of a new RecyclerView instance to display the item list properly
        //RecyclerView contactListView = mBinding.recyclerView;
        // TODO 04. Define the layout of each item in the list
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new AdapterListBasic(mContext, viewModel.getInboxList().getValue());

        // TODO 09. Create a new instance of the created Adapter class and bind it to the RecyclerView instance created in step 03
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AdapterListBasic.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Inbox obj, int position) {
                viewModel.setSelectedIndex(getIndex());
                Log.d(TAG, String.valueOf(viewModel.getSelectedIndex()));
            }
        });

    }

    public int getIndex(){
        return mAdapter.getIndex();
    }

    public void updateItems(List<Inbox> inboxes){
        mAdapter.updateItems(inboxes);
    }

    public void softUpdateItems(List<Inbox> inboxes){
        mAdapter.softUpdateItems(inboxes);
    }

    public void addItem() {
        Inbox newItem = DataGenerator.getRandomInboxItem(mContext);
        List<Inbox> currentData = viewModel.getInbox().getValue();
        currentData.add(0, newItem);
        viewModel.setInboxList(currentData);
    }

    public boolean itemSelected() {
        return viewModel.getInboxList().getValue().get(getIndex()).isSelected();
    }

    public void deleteItem(CoordinatorLayout parentView, String msg, int duration) {
        if(viewModel.getInboxList().getValue().get(getIndex()).isSelected()) {
            mAdapter.removeItem(mAdapter.getIndex());
            Snackbar snackbar = Snackbar.make(parentView, msg, duration);
            snackbar.show();
        }
    }
}