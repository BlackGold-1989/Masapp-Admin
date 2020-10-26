package com.laodev.masappadmin.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.laodev.masappadmin.R;
import com.laodev.masappadmin.model.PaymentModel;
import com.laodev.masappadmin.model.UserModel;
import com.laodev.masappadmin.util.FireManager;

import java.util.List;

public class PaymentCell extends LinearLayout {

    private Context mContext;
    private PaymentModel mPayment;

    public PaymentCell(Context context, PaymentModel payment) {
        super(context);

        mContext = context;
        mPayment = payment;

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.ui_payment_cell, this, true);

        initUIView();
    }

    private void initUIView() {
        TextView lbl_name = findViewById(R.id.lbl_payment_name);
        TextView lbl_price = findViewById(R.id.lbl_payment_price);
        TextView lbl_date = findViewById(R.id.lbl_payment_date);

        lbl_price.setText("$ " + mPayment.count * 25);
        lbl_date.setText(mPayment.createdate);
        FireManager.getBuyerByUserId(mPayment.userid, new FireManager.GetBuyerCallback() {
            @Override
            public void onSuccess(List<UserModel> users) {
                lbl_name.setText(users.get(0).name);
            }

            @Override
            public void onFailed(String error) {
                Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
