package com.laodev.masappadmin.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.laodev.masappadmin.R;
import com.laodev.masappadmin.model.UserModel;
import com.laodev.masappadmin.util.AppUtils;
import com.laodev.masappadmin.util.FireManager;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private TextView lbl_seller_name, lbl_seller_email, lbl_seller_phone;
    private TextView lbl_buyer_name, lbl_buyer_email, lbl_buyer_phone;
    private TextView lbl_order_title, lbl_order_sub, lbl_order_detail;
    private ProgressDialog dialog;

    boolean isDialog = false;

    private void initEvent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

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

        lbl_buyer_name = findViewById(R.id.lbl_buyer_name);
        lbl_buyer_email = findViewById(R.id.lbl_buyer_email);
        lbl_buyer_phone = findViewById(R.id.lbl_buyer_phone);

        lbl_seller_name = findViewById(R.id.lbl_seller_name);
        lbl_seller_email = findViewById(R.id.lbl_seller_email);
        lbl_seller_phone = findViewById(R.id.lbl_seller_phone);

        lbl_order_title = findViewById(R.id.lbl_history_title);
        lbl_order_sub = findViewById(R.id.lbl_history_sub);
        lbl_order_detail = findViewById(R.id.lbl_history_description);

        initWithData();
    }

    private void initWithData() {
        lbl_order_title.setText(AppUtils.gHistoryModel.title);
        lbl_order_sub.setText(AppUtils.gHistoryModel.sub_title);
        lbl_order_detail.setText(AppUtils.gHistoryModel.desc);

        isDialog = false;
        dialog.show();
        FireManager.getBuyerByUserId(AppUtils.gHistoryModel.userid, new FireManager.GetBuyerCallback() {
            @Override
            public void onSuccess(List<UserModel> users) {
                if (!isDialog) {
                    isDialog = true;
                } else {
                    dialog.dismiss();
                }
                lbl_buyer_name.setText(users.get(0).name);
                lbl_buyer_email.setText(users.get(0).email);
                lbl_buyer_phone.setText(users.get(0).phone);
            }

            @Override
            public void onFailed(String error) {
                dialog.dismiss();
                Toast.makeText(HistoryActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });

        FireManager.getSellerByUserId(AppUtils.gHistoryModel.doctorid, new FireManager.GetSellerCallback() {
            @Override
            public void onSuccess(List<UserModel> users) {
                if (!isDialog) {
                    isDialog = true;
                } else {
                    dialog.dismiss();
                }

                lbl_seller_name.setText(users.get(0).name);
                lbl_seller_email.setText(users.get(0).email);
                lbl_seller_phone.setText(users.get(0).phone);
            }

            @Override
            public void onFailed(String error) {
                dialog.dismiss();
                Toast.makeText(HistoryActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
