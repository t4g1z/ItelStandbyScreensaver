import android.service.dreams.DreamService;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyTVScrnSaver extends DreamService {
    private Handler handler = new Handler();
    private Runnable runnable;
    private TextView timeTextView, dateTextView;
    private ImageView slideImageView;
    
    // Array or local folder path of your uploaded files
    private int[] customImages = { /* your image resource IDs or file paths */ };
    private int currentIndex = 0;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        
        // Allow interactive touches to dismiss, or keep it purely ambient
        setInteractive(false);
        // Keep screen dim/low power mode
        setFullscreen(true);
        
        // Inflate your layout designed for dark mode & energy efficiency (#000000 background)
        setContentView(R.layout.screensaver_layout);

        timeTextView = findViewById(R.id.timeText);
        dateTextView = findViewById(R.id.dateText);
        slideImageView = findViewById(R.id.slideImage);

        startClockAndSlideshow();
    }

    private void startClockAndSlideshow() {
        runnable = new Runnable() {
            @Override
            public void run() {
                // Update Time & Date like iPhone StandBy Dock
                String currentTime = new SimpleDateFormat("hh:mm", Locale.getDefault()).format(new Date());
                String currentDate = new SimpleDateFormat("EEE, MMM d", Locale.getDefault()).format(new Date());
                
                timeTextView.setText(currentTime);
                dateTextView.setText(currentDate);

                // Rotate custom uploaded images every 10 seconds
                if (customImages.length > 0) {
                    slideImageView.setImageResource(customImages[currentIndex]);
                    currentIndex = (currentIndex + 1) % customImages.length;
                }

                // Loop every 10,000ms (10 seconds) to reduce CPU cycles and save energy
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
