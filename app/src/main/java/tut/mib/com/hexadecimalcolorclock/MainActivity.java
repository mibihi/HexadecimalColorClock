package tut.mib.com.hexadecimalcolorclock;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Timer timer;
    private View root;
    private TextView timeTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.root);
        timeTv =findViewById(R.id.timeTv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startClock();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(timer != null){timer.cancel();}
    }
    public void startClock(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final String hexaTime = getHexaTime();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        applyColor(hexaTime);
                    }
                });
            }
        }, 0, 1000);
    }

    private void applyColor(String hexaTime) {
        int color = Color.parseColor(hexaTime);
        root.setBackgroundColor(color);

        // white or black color for the text view
        int tmp = (int) (0.2126 * Color.red(color)) + (int) (0.7152 * Color.green(color)) + (int) (0.0722 * Color.blue(color));
        timeTv.setText(hexaTime);
        timeTv.setTextColor(tmp < 128 ? Color.WHITE : Color.BLACK);
    }

    private String getHexaTime() {
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY) * 255 / 23;
        int m = c.get(Calendar.MINUTE) * 255 / 59;
        int s = c.get(Calendar.SECOND) * 255 / 59;

        return "#" + String.format("%02X", h) + String.format("%02X", m) + String.format("%02X", s);
    }
}
