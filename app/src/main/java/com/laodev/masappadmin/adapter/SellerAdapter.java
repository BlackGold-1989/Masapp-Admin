package com.laodev.masappadmin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.laodev.masappadmin.activity.SellerDetailActivity;
import com.laodev.masappadmin.model.UserModel;
import com.laodev.masappadmin.ui.SellerCell;
import com.laodev.masappadmin.util.AppUtils;

import java.util.List;

public class SellerAdapter extends BaseAdapter {

    private Context context;
    private List<UserModel> mSellers;
    private SellerAdapterCallback mCallback;

    public SellerAdapter(Context context, List<UserModel> sellers, SellerAdapterCallback callback) {
        this.context = context;
        mSellers = sellers;
        mCallback = callback;
    }

    @Override
    public int getCount() {
        return mSellers.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View containView, ViewGroup parent) {
        UserModel user = mSellers.get(position);

        containView = new SellerCell(context, user, user1 -> mCallback.onChangeStatus(user1));
        containView.setOnClickListener(v -> {
            AppUtils.gSellerUser = user;
            AppUtils.showOtherActivity(context, SellerDetailActivity.class, 0);
        });

        return containView;
    }

    public interface SellerAdapterCallback {
        void onChangeStatus(UserModel user);
    }

}
