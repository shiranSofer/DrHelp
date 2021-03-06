package com.example.shiran.drhelp.services;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.shiran.drhelp.entities.Doctor;
import com.example.shiran.drhelp.entities.Translator;
import com.example.shiran.drhelp.entities.forms.RegistrationForm;
import com.example.shiran.drhelp.entities.User;
import com.example.shiran.drhelp.services.observables.UserServiceObservable;
import com.example.shiran.drhelp.services.observers.UserLoginObserver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.arch.core.util.Function;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

public class FirebaseUserService extends UserServiceObservable {

    /** Singleton **/
    private static FirebaseUserService instance;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private String currentUserId;
    private User currentUser;

    public FirebaseUserService(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    /** Singleton **/
    public static UserService getInstance(){
        if(instance == null){
            instance = new FirebaseUserService();
        }
        return instance;
    }

    @Override
    public void registerUser(final RegistrationForm registrationForm, final Activity activity) {
        firebaseAuth.createUserWithEmailAndPassword(
                registrationForm.getEmail(), registrationForm.getPassword())
                .addOnCompleteListener(activity,
                        task -> onRegistrationComplete(task, registrationForm));
    }

    private void onRegistrationComplete(final Task<AuthResult> task, RegistrationForm registrationForm){
        if(!task.isSuccessful()){
            publishAboutRegistration(observer -> {
                observer.onUserRegistrationFailed(task.getException());
                return null;
            });
        } else{
            Log.d("RegisterForm", registrationForm.toString());
            saveUserToFirebaseDatabase(registrationForm);
            saveUserToFirestore(registrationForm);
        }
    }

    private void saveUserToFirebaseDatabase(RegistrationForm registrationForm) {
        User user = registrationFormToUser(registrationForm);

        String userId = user.getId();
        if (userId != null){
            Log.d("User1", user.toString());
            databaseReference.child(userId).setValue(user);
            Log.d("User", databaseReference.child(userId).toString());
            publishAboutRegistration(observer -> {
                observer.onUserRegistrationSucceed(user);
                return null;
            });
        }
    }

    private void saveUserToFirestore(RegistrationForm registrationForm) {
        User user = registrationFormToUser(registrationForm);
        firestore.collection("users").document(user.getId()).set(user);
    }

    private User registrationFormToUser(RegistrationForm registrationForm){
        String token_id = FirebaseInstanceId.getInstance().getToken();
        String userId = firebaseAuth.getUid();
        User user = registrationForm.toUser(userId);
        user.setToken(token_id);
        return user;
    }

    @Override
    public void loginUser(String email, String password, Activity activity) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, this::onLogginInCompleted);
    }

    private void onLogginInCompleted(@NonNull Task<AuthResult> task) {
        if(!task.isSuccessful()){
            publishAboutLoggingIn(observer -> {observer.onLoginFailed();
            return null;
            });
        }
        else {
            publishAboutLoggingIn(observer -> {
                currentUserId = firebaseAuth.getCurrentUser().getUid();
                acquireToken(firebaseAuth, firestore);
                loadCurrentUser(observer);

                return null;
            });
        }
    }

    @Override
    public void resetPassword(String email) {
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this::onResetPasswordCompleted);
    }

    private void onResetPasswordCompleted(@NonNull Task<Void> task) {
        if(!task.isSuccessful()){
            publishAboutResetPassword(observer -> {observer.onResetPasswordFailed();
                return null;
            });
        } else {
            publishAboutResetPassword(observer -> {observer.onResetPasswordSucceed();
                return null;
            });
        }
    }

    @Override
    public User getUserById(String id) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public void logout(User user) {

    }

    @Override
    public String getUserStatus() {
        currentUserId = currentUser.getId();
        return databaseReference.child(currentUserId).child("available").getKey();
    }

    @Override
    public void setUserStatus(boolean available) {
        currentUserId = currentUser.getId();
        databaseReference.child(currentUserId).child("available").setValue(available);
    }

    @Override
    public int getNumberOfUsers() {
        return databaseReference.child("users").getKey().length();
    }

    @Override
    public void getAllAvailableUsers(Function<List<User>, Void> onAvailableUsersArrived) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> userList = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.i("USSR:",new Gson().toJson(ds.getValue(User.class)));
                    if(ds.hasChild("licenseNumber")){
                        //Doctor doctor = ds.getValue(Doctor.class);

                    }else{
                        Translator user = ds.getValue(Translator.class);
                        Log.i("USSR Translator?", new Gson().toJson(user));
                        if(user.getAvailable()){
                            userList.add(user);
                        }
                    }

                }
                onAvailableUsersArrived.apply(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public String getCurrentUserId() {
        return firebaseAuth.getCurrentUser().getUid();
    }

    @Override
    public User getCurrentUser(){
        return currentUser;
    }

    @Override
    public void setUserShifts(String shifts) {

    }

    @Override
    public String getCurrentUserName() {
        return firebaseAuth.getCurrentUser().getDisplayName();
    }

    private void loadCurrentUser(UserLoginObserver observer){

        firestore.collection("users").document(currentUserId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot data = task.getResult();
                User user;
                if(data.contains("licenseNumber")){
                    Doctor doctor = new Doctor();
                    doctor.setLicenseNumber(data.get("licenseNumber").toString());
                    user = doctor;
                }else{
                    user = new Translator();
                }
                user.setId(data.get("id").toString());
                user.setEmail(data.get("email").toString());
                user.setFirstName(data.get("firstName").toString());
                user.setLastName(data.get("lastName").toString());
                Object token = data.get("token");
                user.setToken(token.toString());
                currentUser = user;

                observer.onLoginSucceed(currentUser);
            }
        });
    }

    public void acquireToken(final FirebaseAuth mAuth, final FirebaseFirestore firestore){
        final String tokenId = FirebaseInstanceId.getInstance().getToken();
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", tokenId);

        firestore.collection("users").document(currentUserId).update(tokenMap);
    }
}
