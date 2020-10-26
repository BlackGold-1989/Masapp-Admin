package com.laodev.masappadmin.util;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laodev.masappadmin.model.AdminUser;
import com.laodev.masappadmin.model.NotifyModel;
import com.laodev.masappadmin.model.PaymentModel;
import com.laodev.masappadmin.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class FireManager {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static final DatabaseReference mainRef = database.getReference();

    //users ref that contain user's data (name,phone,photo etc..)
    private static final DatabaseReference sellerRef = mainRef.child("user");
    private static final DatabaseReference buyerRef = mainRef.child("user");
    private static final DatabaseReference adminRef = mainRef.child("admin");

    // noti
    private static final DatabaseReference notiRef = mainRef.child("noti");

    // history
    private static final DatabaseReference historyRef = mainRef.child("history");

    // payment
    private static final DatabaseReference paymentRef = mainRef.child("payment");

    // token
    private static final DatabaseReference tokenRef = mainRef.child("token");


    public static void addAdminToDatabase(AdminUser user, AddUserCallback callback) {
        if (user.id.length() == 0) {
            callback.onFailed("Failed User");
            return;
        }
        adminRef.child(user.id).setValue(user)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailed(e.getMessage()));
    }

    public interface AddUserCallback {
        void onSuccess();
        void onFailed(String error);
    }

    public static void getAllUserModels(GetSellerCallback callback) {
        sellerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<UserModel> users = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    UserModel user = snapshot.getValue(UserModel.class);
                    if (user.type.equals(Constants.USER_SELLER)) {
                        users.add(user);
                    }
                }
                callback.onSuccess(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailed(databaseError.getMessage());
            }
        });
    }

    public static void getSellerByUserId(String userid, GetSellerCallback callback) {
        sellerRef.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<UserModel> users = new ArrayList<>();
                UserModel user = dataSnapshot.getValue(UserModel.class);
                if (user.type.equals(Constants.USER_SELLER)) {
                    users.add(user);
                }
                callback.onSuccess(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailed(databaseError.getMessage());
            }
        });
    }

    public interface GetSellerCallback {
        void onSuccess(List<UserModel> users);
        void onFailed(String error);
    }

    public static void getAllUserModels(GetBuyerCallback callback) {
        buyerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<UserModel> users = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    UserModel user = snapshot.getValue(UserModel.class);
                    if (user.type.equals(Constants.USER_BUYER)) {
                        users.add(user);
                    }
                }
                callback.onSuccess(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailed(databaseError.getMessage());
            }
        });
    }

    public static void getBuyerByUserId(String userid, GetBuyerCallback callback) {
        buyerRef.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<UserModel> users = new ArrayList<>();
                UserModel user = dataSnapshot.getValue(UserModel.class);
                if (user.type.equals(Constants.USER_BUYER)) {
                    users.add(user);
                }
                callback.onSuccess(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailed(databaseError.getMessage());
            }
        });
    }

    public interface GetBuyerCallback {
        void onSuccess(List<UserModel> users);
        void onFailed(String error);
    }

    public static void updateSellerUserModel(UserModel user, AddUserCallback callback) {
        sellerRef.child(user.id).setValue(user)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailed(e.getMessage()));
    }

    public static void updateBuyerUserModel(UserModel user, AddUserCallback callback) {
        buyerRef.child(user.id).setValue(user)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailed(e.getMessage()));
    }

    public static void getAllHistory(GetHistoryCallback callback) {
        historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<NotifyModel> histories = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    NotifyModel history = snapshot.getValue(NotifyModel.class);
                    histories.add(history);
                }
                callback.onSuccess(histories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailed(databaseError.getMessage());
            }
        });
    }

    public interface GetHistoryCallback {
        void onSuccess(List<NotifyModel> histories);
        void onFailed(String error);
    }

    public static void getAllOrders(GetOrderCallback callback) {
        notiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<NotifyModel> orders = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    NotifyModel order = snapshot.getValue(NotifyModel.class);
                    orders.add(order);
                }
                callback.onSuccess(orders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailed(databaseError.getMessage());
            }
        });
    }

    public interface GetOrderCallback {
        void onSuccess(List<NotifyModel> orders);
        void onFailed(String error);
    }

    public static void getAllPayments(GetPaymentCallback callback) {
        paymentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<PaymentModel> paymentModels = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    for (DataSnapshot paymentSnapshot: snapshot.getChildren()) {
                        PaymentModel paymentModel = paymentSnapshot.getValue(PaymentModel.class);
                        paymentModels.add(paymentModel);
                    }
                }
                callback.onSuccess(paymentModels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailed(databaseError.getMessage());
            }
        });
    }

    public interface GetPaymentCallback {
        void onSuccess(List<PaymentModel> payments);
        void onFailed(String error);
    }

}
