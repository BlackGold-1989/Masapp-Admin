package com.laodev.masappadmin.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.laodev.masappadmin.R;
import com.laodev.masappadmin.model.NotifyModel;
import com.laodev.masappadmin.model.UserModel;
import com.laodev.masappadmin.util.FireManager;

import java.util.List;

public class HistoryCell extends LinearLayout {

    private Context mContext;
    private NotifyModel mHistory;

    public HistoryCell(Context context, NotifyModel history) {
        super(context);

        mContext = context;
        mHistory = history;

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.ui_history_cell, this, true);

        initUIView();
    }

    private void initUIView() {
        TextView lbl_seller_name = findViewById(R.id.lbl_history_seller_name);
        TextView lbl_buyer_name = findViewById(R.id.lbl_history_buyer_name);
        TextView lbl_start_time = findViewById(R.id.lbl_history_start_time);
        TextView lbl_end_time = findViewById(R.id.lbl_history_end_time);

        lbl_start_time.setText(mHistory.starttime);
        lbl_end_time.setText(mHistory.endtime);

        FireManager.getBuyerByUserId(mHistory.userid, new FireManager.GetBuyerCallback() {
            @Override
            public void onSuccess(List<UserModel> users) {
                lbl_buyer_name.setText(users.get(0).name);
            }

            @Override
            public void onFailed(String error) {
                Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
            }
        });

        FireManager.getSellerByUserId(mHistory.doctorid, new FireManager.GetSellerCallback() {
            @Override
            public void onSuccess(List<UserModel> users) {
                lbl_seller_name.setText(users.get(0).name);
            }

            @Override
            public void onFailed(String error) {
                Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
