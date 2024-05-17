package com.example.phonesaleapp.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.widget.Toast;

public class AlertBroadcastReceiver extends BroadcastReceiver {
    private boolean disconnected = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action= intent.getAction();
        if (action != null){
            switch (action){
                case ConnectivityManager.CONNECTIVITY_ACTION:
                    solveInternetConnection(context);
                    break;
                case Intent.ACTION_BATTERY_CHANGED:
                    solveBatteryLevel(context, intent);
                    break;
            }
        }
    }
    private void solveInternetConnection(Context context){
        if (!isConnectedToInternet(context)){
            disconnected = true;
            Toast.makeText(context, "Mất kết nối Internet !", Toast.LENGTH_SHORT).show();
        }else {
            if (disconnected){
                Toast.makeText(context, "Đã kết nối lại Internet !", Toast.LENGTH_SHORT).show();
                disconnected = false;
            }
        }
    }
    private void solveBatteryLevel(Context context, Intent intent){
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        if (level <= 15){
            Toast.makeText(context, "Pin yếu !", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
