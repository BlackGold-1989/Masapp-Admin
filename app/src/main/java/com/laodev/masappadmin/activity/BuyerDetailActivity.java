package com.laodev.masappadmin.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.laodev.masappadmin.util.FireManager;

import java.util.ArrayList;
import java.util.List;

public class BuyerDetailActivity extends AppCompatActivity {

    private TextView lbl_status, lbl_name, lbl_gender, lbl_email, lbl_phone, lbl_location, lbl_card, lbl_age;
    private LinearLayout llt_status;
    private Button btn_contact;
    private ProgressDialog dialog;

    private List<NotifyModel> mHistroies = new ArrayList<>();
    private HistoryAdapter historyAdapter;
    private HistoryAdapter.HistoryAdapterCallback historyAdapterCallback = history -> {
        AppUtils.gHistoryModel = history;
        AppUtils.showOtherActivity(BuyerDetailActivity.this, HistoryActivity.class, 0);
    };


    private void initEvent() {
        btn_contact.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + AppUtils.gBuyerUser.phone));
            startActivity(intent);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

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

        lbl_status = findViewById(R.id.lbl_buyer_status);
        lbl_name = findViewById(R.id.lbl_buyer_name);
        lbl_gender = findViewById(R.id.lbl_buyer_gender);
        lbl_email = findViewById(R.id.lbl_buyer_email);
        lbl_phone = findViewById(R.id.lbl_buyer_phone);
        lbl_location = findViewById(R.id.lbl_buyer_location);
        lbl_card = findViewById(R.id.lbl_buyer_card);
        lbl_age = findViewById(R.id.lbl_buyer_age);

        llt_status = findViewById(R.id.llt_buyer_status);

        btn_contact = findViewById(R.id.btn_buyer_contact);

        ListView lst_history = findViewById(R.id.lst_buyer_history);
        historyAdapter = new HistoryAdapter(this, mHistroies, historyAdapterCallback);
        lst_history.setAdapter(historyAdapter);
        
        initWithData();
    }

    private void initWithData() {
        lbl_name.setText(AppUtils.gBuyerUser.name);
        lbl_gender.setText(AppUtils.gBuyerUser.gender);
        lbl_email.setText(AppUtils.gBuyerUser.email);
        lbl_phone.setText(AppUtils.gBuyerUser.phone);
        lbl_location.setText(AppUtils.gBuyerUser.location);
        lbl_card.setText(AppUtils.gBuyerUser.card);
        lbl_age.setText(AppUtils.gBuyerUser.birth);
        llt_status.setBackground(getResources().getDrawable(R.drawable.green_8));
        lbl_status.setText(getResources().getString(R.string.seller_active));

        dialog.show();
        FireManager.getAllHistory(new FireManager.GetHistoryCallback() {
            @Override
            public void onSuccess(List<NotifyModel> histories) {
                dialog.dismiss();
                mHistroies.clear();
                for (NotifyModel history: histories) {
                    if (history.userid.equals(AppUtils.gBuyerUser.id)) {
                        mHistroies.add(history);
                    }
                }

                historyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String error) {
                dialog.dismiss();
                Toast.makeText(BuyerDetailActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
