package com.laodev.masappadmin.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.laodev.masappadmin.R;
import com.laodev.masappadmin.activity.MainActivity;
import com.laodev.masappadmin.adapter.SellerAdapter;
import com.laodev.masappadmin.model.UserModel;
import com.laodev.masappadmin.util.Constants;
import com.laodev.masappadmin.util.FireManager;

import java.util.ArrayList;
import java.util.List;

public class SellerFragment extends Fragment {

    private MainActivity mActivity;
    private TextView lbl_active, lbl_inactive;
    private ProgressDialog dialog;

    private List<UserModel> mAllUserModel = new ArrayList<>();
    private List<UserModel> mShowUserModel = new ArrayList<>();
    private SellerAdapter sellerAdapter;
    private SellerAdapter.SellerAdapterCallback sellerAdapterCallback = new SellerAdapter.SellerAdapterCallback() {
        @Override
        public void onChangeStatus(UserModel user) {
            if (isActive) {
                user.status = Constants.STATUS_READY;
            } else {
                user.status = Constants.STATUS_PENDDING;
            }
            dialog.show();
            FireManager.updateSellerUserModel(user, new FireManager.AddUserCallback() {
                @Override
                public void onSuccess() {
                    dialog.dismiss();
                    refreshList();
                }

                @Override
                public void onFailed(String error) {
                    dialog.dismiss();
                    Toast.makeText(mActivity, error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
    private boolean isActive = true;


    public SellerFragment(MainActivity context) {
        mActivity = context;
    }

    private void initEvents(){
        lbl_active.setOnClickListener(v -> {
            isActive = true;
            refreshList();
        });
        lbl_inactive.setOnClickListener(v -> {
            isActive = false;
            refreshList();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller, container, false);

        initUIView(view);
        initEvents();

        return view;
    }

    private void initUIView(View view) {
        dialog = ProgressDialog.show(mActivity, "", getString(R.string.alt_connect_server));
        dialog.dismiss();

        lbl_active = view.findViewById(R.id.lbl_seller_active);
        lbl_inactive = view.findViewById(R.id.lbl_seller_inactive);

        ListView lst_users = view.findViewById(R.id.lst_seller_users);
        sellerAdapter = new SellerAdapter(mActivity, mShowUserModel, sellerAdapterCallback);
        lst_users.setAdapter(sellerAdapter);

        initWithData();
    }

    private void initWithData() {
        dialog.show();
        FireManager.getAllUserModels(new FireManager.GetSellerCallback() {
            @Override
            public void onSuccess(List<UserModel> users) {
                dialog.dismiss();
                mAllUserModel.addAll(users);
                refreshList();
            }

            @Override
            public void onFailed(String error) {
                dialog.dismiss();
                Toast.makeText(mActivity, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshList() {
        mShowUserModel.clear();
        if (isActive) {
            for (UserModel user: mAllUserModel) {
                if (user.status.equals(Constants.STATUS_PENDDING)) {
                    mShowUserModel.add(user);
                }
            }
            lbl_active.setBackground(getResources().getDrawable(R.drawable.main_8_left));
            lbl_active.setTextColor(getResources().getColor(R.color.colorWhite));
            lbl_inactive.setBackground(null);
            lbl_inactive.setTextColor(getResources().getColor(R.color.colorYellow));
        } else {
            for (UserModel user: mAllUserModel) {
                if (user.status.equals(Constants.STATUS_READY)) {
                    mShowUserModel.add(user);
                }
            }
            lbl_active.setBackground(null);
            lbl_active.setTextColor(getResources().getColor(R.color.colorYellow));
            lbl_inactive.setBackground(getResources().getDrawable(R.drawable.main_8_right));
            lbl_inactive.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        sellerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }
}
