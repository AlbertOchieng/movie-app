package com.example.albertzkiki.movieapp.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.MyOrder;
import com.example.albertzkiki.movieapp.R;
import com.example.albertzkiki.movieapp.model.Order;
import com.example.albertzkiki.movieapp.model.Request;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class ListenOrder extends Service implements ChildEventListener{

    FirebaseDatabase database;
    DatabaseReference requests;

    public ListenOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Request");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requests.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        Request request = dataSnapshot.getValue(Request.class);
        ShowNotification(dataSnapshot.getKey(),request);

    }

    private void ShowNotification(String key, Request request) {

        Intent intent = new Intent(getBaseContext(), MyOrder.class);
        intent.putExtra("UserPhone",request.getPhone());
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),0,intent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("Black Dynasty")
                .setContentInfo("Your Food order was updated")
                .setContentText("Order #"+key+" was updated to status"+ Common.convertCode(request.getStatus()))
                .setContentIntent(contentIntent)
                .setContentInfo("Info")
                .setSmallIcon(R.drawable.ic_notiflow);


        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
