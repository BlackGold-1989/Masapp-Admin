package com.laodev.masappadmin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.laodev.masappadmin.R;
import com.laodev.masappadmin.fragment.BuyerFragment;
import com.laodev.masappadmin.fragment.OrderFragment;
import com.laodev.masappadmin.fragment.PaymentFragment;
import com.laodev.masappadmin.fragment.SellerFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public BottomNavigationView bottomNavigation;

    private void initEvent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIView();
        initEvent();
    }

    private void initUIView() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        loadFragmentByIndex(0);
    }

    private void loadFragmentByIndex(int index) {
        Fragment frg = null;
        switch (index) {
            case 0:
                frg = new SellerFragment(this);
                break;
            case 1:
                frg = new BuyerFragment(this);
                break;
            case 2:
                frg = new PaymentFragment(this);
                break;
            case 3:
                frg = new OrderFragment(this);
                break;
        }

        onLoadFragment(frg);
    }

    private void onLoadFragment(Fragment frg) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction frgTran = fm.beginTransaction();
        frgTran.replace(R.id.frg_main, frg);
        frgTran.commit();
    }

    private void exitDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setIcon(R.drawable.logo);
        dialog.setTitle(R.string.app_name);
        dialog.setMessage(getString(R.string.alert_onback));
        dialog.setPositiveButton(getResources().getString(R.string.alert_yes), (dialogInterface, i) -> {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });

        dialog.setNeutralButton(getResources().getString(R.string.alert_no), (dialogInterface, i) -> {

        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        exitDialog();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_seller:
                loadFragmentByIndex(0);
                break;
            case R.id.action_buyer:
                loadFragmentByIndex(1);
                break;
            case R.id.action_pay:
                loadFragmentByIndex(2);
                break;
            case R.id.action_order:
                loadFragmentByIndex(3);
                break;
        }
        return true;
    }

}
