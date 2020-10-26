package com.laodev.masappadmin.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.laodev.masappadmin.R;
import com.laodev.masappadmin.activity.MainActivity;
import com.laodev.masappadmin.adapter.BuyerAdapter;
import com.laodev.masappadmin.model.UserModel;
import com.laodev.masappadmin.util.FireManager;

import java.util.ArrayList;
import java.util.List;

public class BuyerFragment extends Fragment {

    private MainActivity mActivity;
    private ProgressDialog dialog;

    private List<UserModel> mShowUserModel = new ArrayList<>();
    private BuyerAdapter buyerAdapter;

    public BuyerFragment(MainActivity context) {
        mActivity = context;
    }

    private void initEvents(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buyer, container, false);

        initUIView(view);
        initEvents();

        return view;
    }

    private void initUIView(View view) {
        dialog = ProgressDialog.show(mActivity, "", getString(R.string.alt_connect_server));
        dialog.dismiss();

        ListView lst_user = view.findViewById(R.id.lst_buyer_users);
        buyerAdapter = new BuyerAdapter(mActivity, mShowUserModel);
        lst_user.setAdapter(buyerAdapter);

        initWithData();
    }

    private void initWithData() {
        dialog.show();
        FireManager.getAllUserModels(new FireManager.GetBuyerCallback() {
            @Override
            public void onSuccess(List<UserModel> users) {
                dialog.dismiss();
                mShowUserModel.addAll(users);
                buyerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String error) {
                dialog.dismiss();
                Toast.makeText(mActivity, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
