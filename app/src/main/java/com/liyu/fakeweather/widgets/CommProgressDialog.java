package com.liyu.fakeweather.widgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liyu.fakeweather.R;

/**
 * Created by liyu on 2018/6/12.
 */
public class CommProgressDialog extends DialogFragment {

    private String message;

    private TextView tvMessage;

    public static CommProgressDialog newInstance(String message) {
        CommProgressDialog fragment = new CommProgressDialog();
        Bundle args = new Bundle();
        args.putString("message", message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        message = getArguments().getString("message");

        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comm_progress_dialog, container, false);
        tvMessage = view.findViewById(R.id.message_dialog);
        if (TextUtils.isEmpty(message)) {
            tvMessage.setVisibility(View.GONE);
        } else {
            tvMessage.setText(message);
        }
        setCancelable(false);
        return view;
    }

    public void setMessage(String message) {
        this.message = message;
        tvMessage.setText(message);
    }
}
