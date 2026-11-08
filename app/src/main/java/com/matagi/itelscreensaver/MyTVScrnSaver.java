package com.matagi.itelscreensaver;

import android.service.dreams.DreamService;
import android.os.Handler;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyTVScrnSaver extends DreamService {
    private Handler handler = new Handler();
    private Runnable runnable;
    private TextView timeTextView, dateTextView;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);
        setFullscreen(true);
        setContentView(R.layout.screensaver_layout);

        timeTextView = findViewById(R.id.timeText);
        dateTextView = findViewById(R.id.dateText);

        startClock();
    }

    private void startClock() {
        runnable = new Runnable() {
            @Override
            public void run() {
                String currentTime = new SimpleDateFormat("hh:mm", Locale.getDefault()).format(new Date());
                String currentDate = new SimpleDateFormat("EEE, MMM d", Locale.getDefault()).format(new Date());
                
                timeTextView.setText(currentTime);
                dateTextView.setText(currentDate);

                handler.postDelayed(this, 10000);
            }
        };
        handler.post(runnable);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
