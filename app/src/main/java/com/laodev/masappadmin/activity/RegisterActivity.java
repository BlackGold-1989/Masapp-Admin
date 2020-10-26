package com.laodev.masappadmin.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.laodev.masappadmin.R;
import com.laodev.masappadmin.model.AdminUser;
import com.laodev.masappadmin.ui.InputLayout;
import com.laodev.masappadmin.util.FireManager;

public class RegisterActivity extends AppCompatActivity {

    private InputLayout ilt_name, ilt_email, ilt_phone, ilt_pass, ilt_repass;
    private ProgressDialog dialog;

    private FirebaseAuth mAuth;
    private FireManager.AddUserCallback userCallback = new FireManager.AddUserCallback() {
        @Override
        public void onSuccess() {
            dialog.dismiss();

            Toast.makeText(RegisterActivity.this, "Added Admin User.", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

        @Override
        public void onFailed(String error) {
            dialog.dismiss();

            Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
        }
    };

    private void initEvent() {

    }

    public void onClickGoToLogin(View view) {
        onBackPressed();
    }

    public void onClickRegisterBtn(View view) {
        String name = ilt_name.getInputText();
        if (name.length() == 0) {
            Toast.makeText(this, R.string.alt_reg_name, Toast.LENGTH_SHORT).show();
            return;
        }

        String email = ilt_email.getInputText();
        if (email.length() == 0) {
            Toast.makeText(this, R.string.alt_email, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            Toast.makeText(this, R.string.alt_email_type, Toast.LENGTH_SHORT).show();
            return;
        }

        String phone = ilt_phone.getInputText();
        if (phone.length() == 0) {
            Toast.makeText(this, R.string.alt_reg_phone, Toast.LENGTH_SHORT).show();
            return;
        }

        String pass = ilt_pass.getInputText();
        String repass = ilt_repass.getInputText();
        if (pass.length() == 0) {
            Toast.makeText(this, R.string.alt_pass, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass.equals(repass)) {
            Toast.makeText(this, R.string.alt_reg_repass, Toast.LENGTH_SHORT).show();
            return;
        }

        AdminUser user = new AdminUser();
        user.name = name;
        user.email = email;
        user.phone = phone;

        dialog.show();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser fbUser = mAuth.getCurrentUser();
                        if (!fbUser.isEmailVerified()) {
                            fbUser.sendEmailVerification();
                        }
                        user.id = fbUser.getUid();
                        FireManager.addAdminToDatabase(user, userCallback);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        initUIView();
        initEvent();
    }

    private void initUIView() {
        ilt_name = findViewById(R.id.ilt_register_name);
        ilt_email = findViewById(R.id.ilt_register_email);
        ilt_phone = findViewById(R.id.ilt_register_phone);
        ilt_pass = findViewById(R.id.ilt_register_password);
        ilt_repass = findViewById(R.id.ilt_register_repass);

        dialog = ProgressDialog.show(this, "", getString(R.string.alt_connect_server));
        dialog.dismiss();
    }

}
