package com.fds.dfsd.ndfgdfg.kolokwium;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Powiadomienie extends Service {
    public Powiadomienie() {
    }

    public static void setPodlaczony(long podlaczony) {
        Powiadomienie.podlaczony = podlaczony;
    }

    static long podlaczony;

    public static void setOdlaczony(long odlaczony) {
        Powiadomienie.odlaczony = odlaczony;
    }

    static long odlaczony;

    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss.SSS");

    void playSound(){
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action=intent.getAction();

        if("ACTION_POWER_CONNECTED".equals(action))
        {
            playSound();
            Toast.makeText(getApplication(), "Podłączono zasilanie " + sdf.format(cal.getTime()), Toast.LENGTH_LONG).show();
        }
        else
        if("ACTION_POWER_DISCONNECTED".equals(action)) {
            playSound();
            Toast.makeText(getApplication(), "odłączono zasilanie " + sdf.format(cal.getTime()), Toast.LENGTH_LONG).show();

            Handler h = new Handler(Looper.getMainLooper());
            h.postDelayed(new Runnable() {
                long millis = (odlaczony - podlaczony);
                @Override
                public void run() {
                    if(podlaczony == 0 || millis <=0) {
                        return;
                    }

                    String a = String.format("%02d:%02d:%02d.%03d",
                            TimeUnit.MILLISECONDS.toHours(millis),
                            TimeUnit.MILLISECONDS.toMinutes(millis) -
                                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                            TimeUnit.MILLISECONDS.toSeconds(millis) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)), millis%1000);
                    Toast.makeText(getApplication(), "trwało " + a, Toast.LENGTH_LONG).show();
                }
            }, 8000);
        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
