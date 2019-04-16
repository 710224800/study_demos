package rulerview.test.com.rulerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TimeLineControlView time_line_controlview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time_line_controlview = findViewById(R.id.time_line_controlview);
        time_line_controlview.synCurrentTime(5 * 60 * 1000); // 5分钟
        List<TimeItem> timeItemList = new ArrayList<>();
        timeItemList.add(new TimeItem(1*60*1000, 1000, 0));
        timeItemList.add(new TimeItem(3*60*1000, 1000, 1));
        time_line_controlview.setTimeItems(timeItemList);
    }
}
