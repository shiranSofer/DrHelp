package com.example.shiran.drhelp.services;

import com.example.shiran.drhelp.entities.User;
import com.example.shiran.drhelp.services.observers.NotificationServiceObserver;

public interface NotificationService {
    void subscribe(NotificationServiceObserver observer);

    void call(User toUser);
}
