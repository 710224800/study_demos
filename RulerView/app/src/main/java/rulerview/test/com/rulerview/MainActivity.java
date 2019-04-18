package rulerview.test.com.rulerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TimeLineControlView time_line_controlview;
    private TextView text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_view = findViewById(R.id.text_view);
        time_line_controlview = findViewById(R.id.time_line_controlview);
        time_line_controlview.synCurrentTime(5 * 60 * 1000); // 5分钟
        List<TimeItem> timeItemList = new ArrayList<>();
        timeItemList.add(new TimeItem(1*60*1000, 1000, 0));
        timeItemList.add(new TimeItem(3*60*1000, 1000, 1));
        time_line_controlview.setTimeItems(timeItemList);

        time_line_controlview.setTimeLineCallback(new TimeLineControlView.TimeLineCallback() {
            @Override
            public void onUpdateTime(long time) {
                TimeLineControlView.TimeDay timeDay = new TimeLineControlView.TimeDay(time);
                text_view.setText((timeDay.hour > 9 ? timeDay.hour : "0" + timeDay.hour) + ":" +
                        (timeDay.minute > 9 ? timeDay.minute : "0" + timeDay.minute) + ":" +
                        (timeDay.second > 9 ? timeDay.second : "0" + timeDay.second) + "");
            }

            @Override
            public void onSelectTime(long time) {
                TimeLineControlView.TimeDay timeDay = new TimeLineControlView.TimeDay(time);
                text_view.setText((timeDay.hour > 9 ? timeDay.hour : "0" + timeDay.hour) + ":" +
                        (timeDay.minute > 9 ? timeDay.minute : "0" + timeDay.minute) + ":" +
                        (timeDay.second > 9 ? timeDay.second : "0" + timeDay.second) + "");
            }

            @Override
            public void onPlayLive() {

            }
        });
    }
}
