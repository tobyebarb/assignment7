package com.ualr.recyclerviewassignment.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ualr.recyclerviewassignment.adapter.AdapterListBasic;

public class InboxViewModel extends ViewModel {
    MutableLiveData<Inbox> inboxData;

    public InboxViewModel() { inboxData = new MutableLiveData<>(); }

    public void setInbox(String from, String email, String message, String date) {
        Inbox currentInboxData = inboxData.getValue();
        if (currentInboxData == null) {
            currentInboxData = new Inbox();
        }
        currentInboxData.setFrom(from);
        currentInboxData.setEmail(email);
        currentInboxData.setMessage(message);
        currentInboxData.setDate(date);
        inboxData.setValue(currentInboxData);
    }

    public LiveData<Inbox> getInbox() { return this.inboxData; }
}
