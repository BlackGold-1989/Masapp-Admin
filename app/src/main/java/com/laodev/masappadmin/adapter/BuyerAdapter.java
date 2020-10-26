package com.laodev.masappadmin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.laodev.masappadmin.activity.BuyerDetailActivity;
import com.laodev.masappadmin.model.UserModel;
import com.laodev.masappadmin.ui.BuyerCell;
import com.laodev.masappadmin.util.AppUtils;

import java.util.List;

public class BuyerAdapter extends BaseAdapter {

    private Context context;
    private List<UserModel> mBuyers;

    public BuyerAdapter(Context context, List<UserModel> buyers) {
        this.context = context;
        mBuyers = buyers;
    }

    @Override
    public int getCount() {
        return mBuyers.size();
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
        UserModel user = mBuyers.get(position);

        containView = new BuyerCell(context, user);

        return containView;
    }

}
