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

public class OrderCell extends LinearLayout {

    private Context mContext;
    private NotifyModel mOrder;

    public OrderCell(Context context, NotifyModel order) {
        super(context);

        mContext = context;
        mOrder = order;

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.ui_order_cell, this, true);

        initUIView();
    }

    private void initUIView() {
        TextView lbl_seller_name = findViewById(R.id.lbl_order_seller_name);
        TextView lbl_buyer_name = findViewById(R.id.lbl_order_buyer_name);
        TextView lbl_time = findViewById(R.id.lbl_order_time);
        TextView lbl_start_time = findViewById(R.id.lbl_history_start_time);
        LinearLayout llt_status = findViewById(R.id.llt_order_status);
        TextView lbl_status = findViewById(R.id.lbl_order_status);

        lbl_time.setText(mOrder.endtime);
        FireManager.getBuyerByUserId(mOrder.userid, new FireManager.GetBuyerCallback() {
            @Override
            public void onSuccess(List<UserModel> users) {
                lbl_buyer_name.setText(users.get(0).name);
            }

            @Override
            public void onFailed(String error) {
                Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
            }
        });

        if (mOrder.doctorid.length() > 0) {
            lbl_start_time.setText(mOrder.starttime);
            FireManager.getSellerByUserId(mOrder.doctorid, new FireManager.GetSellerCallback() {
                @Override
                public void onSuccess(List<UserModel> users) {
                    lbl_seller_name.setText(users.get(0).name);
                }

                @Override
                public void onFailed(String error) {
                    Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                }
            });
            llt_status.setBackground(getResources().getDrawable(R.drawable.green_8));
            lbl_status.setText(getResources().getString(R.string.order_processing));
        }
    }

}
