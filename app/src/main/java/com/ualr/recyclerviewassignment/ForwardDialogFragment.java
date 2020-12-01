package com.ualr.recyclerviewassignment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ualr.recyclerviewassignment.model.Inbox;
import com.ualr.recyclerviewassignment.model.InboxViewModel;

import java.util.List;

public class ForwardDialogFragment extends DialogFragment {

    private static final String TAG = ForwardDialogFragment.class.getSimpleName();
    private EditText toET;
    private EditText nameET;
    private EditText textET;
    private static InboxViewModel viewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(getActivity()).get(InboxViewModel.class);
        viewModel.getInboxList().observe(this, new Observer<List<Inbox>>() {
            @Override
            public void onChanged(List<Inbox> inboxes) {
                InboxListFragment listFragment = (InboxListFragment) getFragmentManager().findFragmentByTag("InboxListFragment");
                listFragment.softUpdateItems(inboxes);
            }
        });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_fragment, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));

        toET = (EditText) dialoglayout.findViewById(R.id.toET);
        nameET = (EditText) dialoglayout.findViewById(R.id.nameET);
        textET = (EditText) dialoglayout.findViewById(R.id.textET);


        int index = viewModel.getSelectedIndex();
        toET.setText(viewModel.getInboxList().getValue().get(index).getEmail());
        nameET.setText(viewModel.getInboxList().getValue().get(index).getFrom());
        textET.setText(viewModel.getInboxList().getValue().get(index).getMessage());

        builder.setView(dialoglayout);

        builder.setTitle(R.string.forward_title)
                .setPositiveButton(R.string.send_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onSendBtnClicked(toET.getText().toString(), nameET.getText().toString(), textET.getText().toString(), viewModel.getSelectedIndex());
                    }
                });

        return builder.create();
    }

    public void onSendBtnClicked(String toET, String nameET, String textET, int index) {
        List<Inbox> currentData = viewModel.getInbox().getValue();
        currentData.get(index).setEmail(toET);
        currentData.get(index).setFrom(nameET);
        currentData.get(index).setMessage(textET);
        viewModel.setInboxList(currentData);
    }



}
