package com.ualr.recyclerviewassignment.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ualr.recyclerviewassignment.MainActivity;
import com.ualr.recyclerviewassignment.adapter.AdapterListBasic;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import android.util.Log;

import static android.widget.Adapter.NO_SELECTION;

public class InboxViewModel extends ViewModel {

    private MutableLiveData<List<Inbox>> inboxList;
    private int selectedIndex;
    private String TAG = InboxViewModel.class.getSimpleName();

    public InboxViewModel() {
        inboxList = new MutableLiveData<>();
        selectedIndex = 0;
    }

    public LiveData<List<Inbox>> getInboxList() {
        if (inboxList == null) {
            inboxList = new MutableLiveData<List<Inbox>>();
            loadInbox();
        }
        return inboxList;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int position) {
        this.selectedIndex = position;
    }

    public void loadInbox() {
        Log.d(TAG,inboxList.getValue().toString() + " HEREHERE");
    }

    public void setInboxList(List<Inbox> list) {
        this.inboxList.setValue(list);
    }

    public LiveData<List<Inbox>> getInbox() { return this.inboxList; }
}
