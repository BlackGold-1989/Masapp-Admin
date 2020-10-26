package com.laodev.masappadmin.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.laodev.masappadmin.R;
import com.laodev.masappadmin.ui.InputLayout;
import com.laodev.masappadmin.util.AppUtils;
import com.laodev.masappadmin.util.SharePreferenceManager;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private InputLayout ilt_email, ilt_pass;
    private CheckBox chb_remember;
    private ProgressDialog dialog;


    private void initEvent() {

    }

    public void onClickGoToRegister(View view) {
        AppUtils.showOtherActivity(this, RegisterActivity.class, 0);
    }

    public void onClickLoginBtn(View view) {
        String email = ilt_email.getInputText();
        if (email.length() == 0) {
            Toast.makeText(this, R.string.alt_email, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            Toast.makeText(this, R.string.alt_email_type, Toast.LENGTH_SHORT).show();
            return;
        }

        String pass = ilt_pass.getInputText();
        if (pass.length() == 0) {
            Toast.makeText(this, R.string.alt_pass, Toast.LENGTH_SHORT).show();
            return;
        }

        dialog.show();
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser fbUser = mAuth.getCurrentUser();
                        if (!fbUser.isEmailVerified()) {
                            fbUser.sendEmailVerification();
                        } else {
                            if (chb_remember.isChecked()) {
                                SharePreferenceManager.setEmail(LoginActivity.this, email);
                                SharePreferenceManager.setPassword(LoginActivity.this, pass);
                            }
                            onShowMainActivity();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.alt_auth_failed), Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                });
    }

    public void onClickGoToForgot(View view) {
        String email = ilt_email.getInputText();
        if (email.length() == 0) {
            Toast.makeText(this, R.string.alt_email, Toast.LENGTH_SHORT).show();
            return;
        }

        dialog.show();
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, R.string.alt_login_forgot, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.alt_login_forgot_fail, Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        initUIView();
        initEvent();
    }

    private void initUIView() {
        ilt_email = findViewById(R.id.ilt_login_email);
        ilt_pass = findViewById(R.id.ilt_login_pass);

        chb_remember = findViewById(R.id.chb_login_remember);

        if (SharePreferenceManager.isCheckKey(this, "email")) {
            String email = SharePreferenceManager.getEmail(this);
            String password = SharePreferenceManager.getPassword(this);

            ilt_email.setInputText(email);
            ilt_pass.setInputText(password);
            chb_remember.setChecked(true);
        }

        dialog = ProgressDialog.show(this, "", getString(R.string.alt_connect_server));
        dialog.dismiss();
    }

    private void onShowMainActivity() {
        AppUtils.showOtherActivity(this, MainActivity.class, 0);
        finish();
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

}
