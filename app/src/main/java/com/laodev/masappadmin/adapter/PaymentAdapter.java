package com.laodev.masappadmin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.laodev.masappadmin.model.NotifyModel;
import com.laodev.masappadmin.model.PaymentModel;
import com.laodev.masappadmin.ui.HistoryCell;
import com.laodev.masappadmin.ui.PaymentCell;

import java.util.List;

public class PaymentAdapter extends BaseAdapter {

    private Context context;
    private List<PaymentModel> mPayments;
    private PaymentAdapterCallback mCallback;

    public PaymentAdapter(Context context, List<PaymentModel> payments, PaymentAdapterCallback callback) {
        this.context = context;
        mPayments = payments;
        mCallback = callback;
    }

    @Override
    public int getCount() {
        return mPayments.size();
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
        PaymentModel model = mPayments.get(position);

        containView = new PaymentCell(context, model);
        containView.setOnClickListener(v -> mCallback.onClickItem(model));

        return containView;
    }

    public interface PaymentAdapterCallback {
        void onClickItem(PaymentModel history);
    }
}
