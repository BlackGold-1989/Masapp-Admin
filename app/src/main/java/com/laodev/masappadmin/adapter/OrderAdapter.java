package com.laodev.masappadmin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.laodev.masappadmin.model.NotifyModel;
import com.laodev.masappadmin.ui.HistoryCell;
import com.laodev.masappadmin.ui.OrderCell;

import java.util.List;

public class OrderAdapter extends BaseAdapter {

    private Context context;
    private List<NotifyModel> mOrders;
    private OrderAdapterCallback mCallback;

    public OrderAdapter(Context context, List<NotifyModel> orders, OrderAdapterCallback callback) {
        this.context = context;
        mOrders = orders;
        mCallback = callback;
    }

    @Override
    public int getCount() {
        return mOrders.size();
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
        NotifyModel model = mOrders.get(position);

        containView = new OrderCell(context, model);
        containView.setOnClickListener(v -> mCallback.onClickItem(model));

        return containView;
    }

    public interface OrderAdapterCallback {
        void onClickItem(NotifyModel history);
    }
}
