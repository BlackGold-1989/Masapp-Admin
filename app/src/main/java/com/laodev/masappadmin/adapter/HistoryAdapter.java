package com.laodev.masappadmin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.laodev.masappadmin.model.NotifyModel;
import com.laodev.masappadmin.ui.HistoryCell;

import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    private Context context;
    private List<NotifyModel> mHistories;
    private HistoryAdapterCallback mCallback;

    public HistoryAdapter(Context context, List<NotifyModel> histories, HistoryAdapterCallback callback) {
        this.context = context;
        mHistories = histories;
        mCallback = callback;
    }

    @Override
    public int getCount() {
        return mHistories.size();
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
        NotifyModel model = mHistories.get(position);

        containView = new HistoryCell(context, model);
        containView.setOnClickListener(v -> mCallback.onClickItem(model));

        return containView;
    }

    public interface HistoryAdapterCallback {
        void onClickItem(NotifyModel history);
    }
}
