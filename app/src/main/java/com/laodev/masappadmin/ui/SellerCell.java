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

public class SellerCell extends LinearLayout {

    private Context mContext;
    private UserModel mUser;
    private SellerCellCallback mCallback;

    public SellerCell(Context context, UserModel user, SellerCellCallback callback) {
        super(context);

        mContext = context;
        mUser = user;
        mCallback = callback;

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.ui_seller_cell, this, true);

        initUIView();
    }

    private void initUIView() {
        TextView lbl_name = findViewById(R.id.lbl_seller_name);
        TextView lbl_email = findViewById(R.id.lbl_seller_email);
        TextView lbl_phone = findViewById(R.id.lbl_seller_phone);
        TextView lbl_card = findViewById(R.id.lbl_seller_card);
        LinearLayout llt_status = findViewById(R.id.llt_seller_status);
        TextView lbl_status = findViewById(R.id.lbl_seller_status);
        Button btn_contact = findViewById(R.id.btn_seller_contact);
        Button btn_status = findViewById(R.id.btn_seller_status);

        lbl_name.setText(mUser.name);
        lbl_email.setText(mUser.email);
        lbl_phone.setText(mUser.phone);
        lbl_card.setText(mUser.card);
        if (!mUser.status.equals(Constants.STATUS_READY)) {
            llt_status.setBackground(getResources().getDrawable(R.drawable.green_8));
            lbl_status.setText(getResources().getString(R.string.seller_active));
            btn_status.setText(getResources().getString(R.string.seller_inactive));
            btn_status.setBackground(getResources().getDrawable(R.drawable.red_8));
        } else {
            llt_status.setBackground(getResources().getDrawable(R.drawable.red_8));
            lbl_status.setText(getResources().getString(R.string.seller_inactive));
            btn_status.setText(getResources().getString(R.string.seller_active));
            btn_status.setBackground(getResources().getDrawable(R.drawable.green_8));
        }

        btn_contact.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + mUser.phone));
            mContext.startActivity(intent);
        });
        btn_status.setOnClickListener(v -> mCallback.onClickStatusBtn(mUser));
    }

    public interface SellerCellCallback {
        void onClickStatusBtn(UserModel user);
    }

}
