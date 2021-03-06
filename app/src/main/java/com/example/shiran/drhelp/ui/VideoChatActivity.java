package com.example.shiran.drhelp.ui;

import android.support.design.button.MaterialButton;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.shiran.drhelp.util.Permissions;
import com.example.shiran.drhelp.R;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

public class VideoChatActivity extends AppCompatActivity implements Session.SessionListener, PublisherKit.PublisherListener {

    private static String API_KEY = "46243432";
    private static String SESSION_ID = "2_MX40NjI0MzQzMn5-MTU0NjE4NDI3NjQ5NX5sVzhobjlRVndFTVRQM1dsaW1rSlF4ZTR-fg";
    private static String TOKEN = "T1==cGFydG5lcl9pZD00NjI0MzQzMiZzaWc9MmI5OWMwY2U4YWE1NjE0OWEzYTEwZDQ0ZGM0ODliOTc0Y2E2MTQwMjpzZXNzaW9uX2lkPTJfTVg0ME5qSTBNelF6TW41LU1UVTBOakU0TkRJM05qUTVOWDVzVnpob2JqbFJWbmRGVFZSUU0xZHNhVzFyU2xGNFpUUi1mZyZjcmVhdGVfdGltZT0xNTQ2MTg0MzU0Jm5vbmNlPTAuNjM0MDk5MjM0ODU4MTMyNSZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNTQ4Nzc2MzU1JmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";
    private static final String LOG_TAG = VideoChatActivity.class.getSimpleName();

    private FrameLayout frameLayout_subscriber;
    private FrameLayout frameLayout_publisher;
    private MaterialButton button_endCall;
    private FloatingActionButton button_flipCamera;
    private TextView textView_subscriber;
    private TextView textView_publisher;

    private Session session;
    private Publisher publisher;
    private Subscriber subscriber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chat);

        checkPermissions();
        initializeReferences();
        createSession();
        button_endCall.setOnClickListener(this::onEndCallButtonPressed);
        button_flipCamera.setOnClickListener(this::onFlipCameraButtonPressed);
    }

    private void onFlipCameraButtonPressed(View view) {
        /* ToDo */
    }

    private void onEndCallButtonPressed(View view) {
        session.disconnect();
        finish();
    }

    private void checkPermissions(){
        Permissions permissions = new Permissions();
        if(!permissions.requestPermissions(this)){
            Log.d("video chat", "permission denied");
            finish();
        }
    }

    private void initializeReferences(){
        frameLayout_subscriber = findViewById(R.id.subscriber_frameLayout);
        frameLayout_publisher = findViewById(R.id.publisher_frameLayout);
        button_endCall = findViewById(R.id.end_call_button);
        textView_subscriber = findViewById(R.id.subscriber_textView);
        textView_publisher = findViewById(R.id.publisher_textView);
        button_flipCamera = findViewById(R.id.flip_camera_button);
    }

    private void createSession(){
        session = new Session.Builder(this, API_KEY, SESSION_ID).build();
        session.setSessionListener(this);
        session.connect(TOKEN);
    }

    @Override
    public void onConnected(Session session) {
        Log.d(LOG_TAG, "Session Connected");

        streamSession(session);
    }

    private void streamSession(Session session) {
        publisher = new Publisher.Builder(this).build();
        publisher.setPublisherListener(this);
        frameLayout_publisher.addView(publisher.getView());
        session.publish(publisher);
    }

    @Override
    public void onDisconnected(Session session) {
        Log.d(LOG_TAG, "Session Disconnected");
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.d(LOG_TAG, "Stream Received");
        if(subscriber == null){
            subscriber = new Subscriber.Builder(this, stream).build();
            session.subscribe(subscriber);
            frameLayout_subscriber.addView(subscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.d(LOG_TAG, "Stream Dropped");
        if(subscriber != null){
            subscriber = null;
            frameLayout_subscriber.removeAllViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.d(LOG_TAG, "Session Error: " + opentokError.getMessage());
    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.d(LOG_TAG, "Publisher Stream Created");
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        Log.d(LOG_TAG, "Publisher Stream Destroyed");
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        Log.d(LOG_TAG, "Publisher Error: " + opentokError.getMessage());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
