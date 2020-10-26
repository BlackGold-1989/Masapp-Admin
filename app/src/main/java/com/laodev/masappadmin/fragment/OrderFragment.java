package com.laodev.masappadmin.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.laodev.masappadmin.R;
import com.laodev.masappadmin.activity.MainActivity;
import com.laodev.masappadmin.adapter.OrderAdapter;
import com.laodev.masappadmin.adapter.PaymentAdapter;
import com.laodev.masappadmin.model.NotifyModel;
import com.laodev.masappadmin.model.PaymentModel;
import com.laodev.masappadmin.util.FireManager;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private MainActivity mActivity;

    private ProgressDialog dialog;
    private List<NotifyModel> mShowOrders = new ArrayList<>();
    private OrderAdapter orderAdapter;

    public OrderFragment(MainActivity context) {
        mActivity = context;
    }

    private void initEvents(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        initUIView(view);
        initEvents();

        return view;
    }

    private void initUIView(View view) {
        dialog = ProgressDialog.show(mActivity, "", getString(R.string.alt_connect_server));
        dialog.dismiss();

        ListView lst_order = view.findViewById(R.id.lst_order);
        orderAdapter = new OrderAdapter(mActivity, mShowOrders, new OrderAdapter.OrderAdapterCallback() {
            @Override
            public void onClickItem(NotifyModel history) {

            }
        });
        lst_order.setAdapter(orderAdapter);

        initWithDatas();
    }

    private void initWithDatas() {
        dialog.show();
        FireManager.getAllOrders(new FireManager.GetOrderCallback() {
            @Override
            public void onSuccess(List<NotifyModel> orders) {
                dialog.dismiss();
                mShowOrders.clear();
                mShowOrders.addAll(orders);
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String error) {
                dialog.dismiss();
            }
        });
    }

}
