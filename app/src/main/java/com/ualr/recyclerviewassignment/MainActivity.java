package com.ualr.recyclerviewassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.adapter.AdapterListBasic;
import com.ualr.recyclerviewassignment.databinding.ActivityListMultiSelectionBinding;
import com.ualr.recyclerviewassignment.databinding.InboxFragmentBinding;
import com.ualr.recyclerviewassignment.model.Inbox;
import com.ualr.recyclerviewassignment.model.InboxViewModel;

import java.util.List;

import javax.sql.DataSource;

import static com.ualr.recyclerviewassignment.InboxListFragment.ctx;
//import static com.ualr.recyclerviewassignment.InboxListFragment.mContext;

// TODO 05. Create a new Adapter class and the corresponding ViewHolder class in a different file. The adapter will be used to populate
//  the recyclerView and manage the interaction with the items in the list
// TODO 06. Detect click events on the list items. Implement a new method to toggle items' selection in response to click events
// TODO 07. Detect click events on the thumbnail located on the left of every list row when the corresponding item is selected.
//  Implement a new method to delete the corresponding item in the list
// TODO 08. Create a new method to add a new item on the top of the list. Use the DataGenerator class to create the new item to be added.

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FRAGMENT_TAG = "InboxListFragment";
    private static final String FORWARD_FRAGMENT_TAG = "ForwardDialogFragment";

    private FloatingActionButton mFAB;
    private InboxFragmentBinding mBinding;
    private AdapterListBasic mAdapter;
    private List<Inbox> mDataSource;
    public static Context here;
    private RecyclerView mRecyclerView;
    private Button mDelete;
    private InboxViewModel viewModel;
    private CoordinatorLayout parentView;
    private String msg;
    private int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mBinding = InboxFragmentBinding.inflate(getLayoutInflater());
        //setContentView(mBinding.getRoot());
        setContentView(R.layout.activity_list_multi_selection);
        viewModel = new ViewModelProvider(this).get(InboxViewModel.class);
        parentView = findViewById(R.id.rootView);
        msg = "Email deleted";
        duration = Snackbar.LENGTH_LONG;

        final Context here = (this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initComponent();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, new InboxListFragment(), FRAGMENT_TAG);
        ft.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void showDialog() {
        ForwardDialogFragment dialog = new ForwardDialogFragment();
        dialog.show(getSupportFragmentManager(), FORWARD_FRAGMENT_TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_action:
                InboxListFragment listFragment = (InboxListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                listFragment.deleteItem(parentView,msg,duration);
                return true;
            case R.id.forward_action:
                InboxListFragment listFragment1 = (InboxListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                if (listFragment1.itemSelected())showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onFAB(View view) {
        mRecyclerView = findViewById(R.id.recyclerView);
        mFAB = findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InboxListFragment listFragment = (InboxListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                listFragment.addItem();

                /*List<Inbox> currentData = viewModel.getInboxList();
                currentData.add(0, newItem);
                viewModel.setInboxList(currentData);*/

            }
        });
    }
}