package com.example.shiran.drhelp.services.observers;

import com.example.shiran.drhelp.entities.User;

public interface UserLoginObserver {
    void onLoginSucceed(User user);

    void onLoginFailed();
}
