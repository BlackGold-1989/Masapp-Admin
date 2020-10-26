package com.laodev.masappadmin.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.laodev.masappadmin.R;
import com.laodev.masappadmin.adapter.HistoryAdapter;
import com.laodev.masappadmin.model.NotifyModel;
import com.laodev.masappadmin.util.AppUtils;
import com.laodev.masappadmin.util.Constants;
import com.laodev.masappadmin.util.FireManager;

import java.util.ArrayList;
import java.util.List;

public class SellerDetailActivity extends AppCompatActivity {

    private TextView lbl_status, lbl_name, lbl_sur_name, lbl_gender, lbl_email, lbl_phone, lbl_location, lbl_card;
    private LinearLayout llt_status;
    private Button btn_contact, btn_status;
    private ProgressDialog dialog;

    private List<NotifyModel> mHistories = new ArrayList<>();
    private HistoryAdapter historyAdapter;
    private HistoryAdapter.HistoryAdapterCallback historyAdapterCallback = history -> {
        AppUtils.gHistoryModel = history;
        AppUtils.showOtherActivity(SellerDetailActivity.this, HistoryActivity.class, 0);
    };


    private void initEvent() {
        btn_contact.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + AppUtils.gSellerUser.phone));
            startActivity(intent);
        });
        btn_status.setOnClickListener(v -> {
            if (AppUtils.gSellerUser.status.equals(Constants.STATUS_READY)) {
                AppUtils.gSellerUser.status = Constants.STATUS_PENDDING;
            } else {
                AppUtils.gSellerUser.status = Constants.STATUS_READY;
            }
            dialog.show();
            FireManager.updateSellerUserModel(AppUtils.gSellerUser, new FireManager.AddUserCallback() {
                @Override
                public void onSuccess() {
                    dialog.dismiss();
                    initWithData();
                }

                @Override
                public void onFailed(String error) {
                    dialog.dismiss();
                    Toast.makeText(SellerDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        setToolbar();
        initUIView();
        initEvent();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(view -> onBackPressed());
        }
    }

    private void initUIView() {
        dialog = ProgressDialog.show(this, "", getString(R.string.alt_connect_server));
        dialog.dismiss();

        lbl_status = findViewById(R.id.lbl_seller_status);
        lbl_name = findViewById(R.id.lbl_seller_name);
        lbl_sur_name = findViewById(R.id.lbl_seller_sur_name);
        lbl_gender = findViewById(R.id.lbl_seller_gender);
        lbl_email = findViewById(R.id.lbl_seller_email);
        lbl_phone = findViewById(R.id.lbl_seller_phone);
        lbl_location = findViewById(R.id.lbl_seller_location);
        lbl_card = findViewById(R.id.lbl_seller_card);

        llt_status = findViewById(R.id.llt_seller_status);

        btn_contact = findViewById(R.id.btn_seller_contact);
        btn_status = findViewById(R.id.btn_seller_status);

        ListView lst_history = findViewById(R.id.lst_seller_history);
        historyAdapter = new HistoryAdapter(this, mHistories, historyAdapterCallback);
        lst_history.setAdapter(historyAdapter);
        
        initWithData();
    }

    private void initWithData() {
        lbl_name.setText(AppUtils.gSellerUser.name);
        lbl_gender.setText(AppUtils.gSellerUser.gender);
        lbl_email.setText(AppUtils.gSellerUser.email);
        lbl_phone.setText(AppUtils.gSellerUser.phone);
        lbl_location.setText(AppUtils.gSellerUser.location);
        lbl_card.setText(AppUtils.gSellerUser.card);
        if (AppUtils.gSellerUser.status.equals(Constants.STATUS_PENDDING)) {
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

        dialog.show();
        FireManager.getAllHistory(new FireManager.GetHistoryCallback() {
            @Override
            public void onSuccess(List<NotifyModel> histories) {
                dialog.dismiss();
                mHistories.clear();
                mHistories.addAll(histories);

                historyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String error) {
                dialog.dismiss();
                Toast.makeText(SellerDetailActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
