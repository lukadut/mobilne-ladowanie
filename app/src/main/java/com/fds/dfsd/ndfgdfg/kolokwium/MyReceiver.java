package com.fds.dfsd.ndfgdfg.kolokwium;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {

    }
    Calendar cal = Calendar.getInstance();
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        Intent intent1=new Intent(context,Powiadomienie.class);
        if(Intent.ACTION_POWER_CONNECTED.equals(action))
        {
            Powiadomienie.setPodlaczony(cal.getTimeInMillis());
            intent1.setAction("ACTION_POWER_CONNECTED");
            context.startService(intent1);
        }
        else
        if(Intent.ACTION_POWER_DISCONNECTED.equals(action))
        {
            Powiadomienie.setOdlaczony(cal.getTimeInMillis());
            intent1.setAction("ACTION_POWER_DISCONNECTED");
            context.startService(intent1);
        }
    }
}
