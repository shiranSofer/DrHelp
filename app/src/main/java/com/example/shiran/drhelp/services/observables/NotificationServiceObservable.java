package com.example.shiran.drhelp.services.observables;

import com.example.shiran.drhelp.entities.User;
import com.example.shiran.drhelp.services.NotificationService;
import com.example.shiran.drhelp.services.observers.NotificationServiceObserver;

import java.util.HashSet;
import java.util.Set;

public abstract class NotificationServiceObservable  implements NotificationService{
    private Set<NotificationServiceObserver> observers;

    public NotificationServiceObservable() {
        observers = new HashSet<>();
    }


    public void subscribe(NotificationServiceObserver observer){
        observers.add(observer);
    }

    protected void fireNotficationSent(){
        for (NotificationServiceObserver observer: observers) {
            observer.onNotificationSucceed();
        }
    }


    protected void fireNotficationFailed(Exception ex){
        for (NotificationServiceObserver observer: observers) {
            observer.onNotificationFailed(ex);
        }
    }

    public abstract void call(User toUser);
}
