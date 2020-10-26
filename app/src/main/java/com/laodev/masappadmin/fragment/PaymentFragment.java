package com.laodev.masappadmin.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.laodev.masappadmin.R;
import com.laodev.masappadmin.activity.MainActivity;
import com.laodev.masappadmin.adapter.PaymentAdapter;
import com.laodev.masappadmin.model.PaymentModel;
import com.laodev.masappadmin.util.FireManager;

import java.util.ArrayList;
import java.util.List;

public class PaymentFragment extends Fragment {

    private MainActivity mActivity;

    private List<PaymentModel> mShowPayments = new ArrayList<>();
    private PaymentAdapter paymentAdapter;
    private ProgressDialog dialog;


    public PaymentFragment(MainActivity context) {
        mActivity = context;
    }

    private void initEvents(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        initUIView(view);
        initEvents();

        return view;
    }

    private void initUIView(View view) {
        dialog = ProgressDialog.show(mActivity, "", getString(R.string.alt_connect_server));
        dialog.dismiss();

        ListView lst_payment = view.findViewById(R.id.lst_payment);
        paymentAdapter = new PaymentAdapter(mActivity, mShowPayments, new PaymentAdapter.PaymentAdapterCallback() {
            @Override
            public void onClickItem(PaymentModel history) {

            }
        });
        lst_payment.setAdapter(paymentAdapter);

        initWithDatas();
    }

    private void initWithDatas() {
        dialog.show();
        FireManager.getAllPayments(new FireManager.GetPaymentCallback() {
            @Override
            public void onSuccess(List<PaymentModel> payments) {
                dialog.dismiss();
                mShowPayments.clear();
                mShowPayments.addAll(payments);
                paymentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String error) {
                dialog.dismiss();
            }
        });
    }

}
