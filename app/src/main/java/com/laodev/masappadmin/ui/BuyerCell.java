package com.laodev.masappadmin.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laodev.masappadmin.R;
import com.laodev.masappadmin.model.UserModel;
import com.laodev.masappadmin.util.Constants;

public class BuyerCell extends LinearLayout {

    private Context mContext;
    private UserModel mUser;

    public BuyerCell(Context context, UserModel user) {
        super(context);

        mContext = context;
        mUser = user;

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.ui_buyer_cell, this, true);

        initUIView();
    }

    private void initUIView() {
        TextView lbl_name = findViewById(R.id.lbl_buyer_name);
        TextView lbl_email = findViewById(R.id.lbl_buyer_email);
        TextView lbl_phone = findViewById(R.id.lbl_buyer_phone);
        TextView lbl_card = findViewById(R.id.lbl_buyer_card);
        LinearLayout llt_status = findViewById(R.id.llt_buyer_status);
        TextView lbl_status = findViewById(R.id.lbl_buyer_status);
        Button btn_contact = findViewById(R.id.btn_buyer_contact);

        lbl_name.setText(mUser.name);
        lbl_email.setText(mUser.email);
        lbl_phone.setText(mUser.phone);
        lbl_card.setText(mUser.card);
        llt_status.setBackground(getResources().getDrawable(R.drawable.green_8));
        lbl_status.setText(getResources().getString(R.string.seller_active));

        btn_contact.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + mUser.phone));
            mContext.startActivity(intent);
        });
    }

}
