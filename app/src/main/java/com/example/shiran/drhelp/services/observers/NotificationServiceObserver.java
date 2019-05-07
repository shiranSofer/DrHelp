package com.example.shiran.drhelp.services.observers;

public interface NotificationServiceObserver {
    void onNotificationSucceed();

    void onNotificationFailed(Exception e);
}
