package rulerview.test.com.rulerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TimeLineControlView time_line_controlview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time_line_controlview = findViewById(R.id.time_line_controlview);
        time_line_controlview.synCurrentTime(System.currentTimeMillis());
    }
}
