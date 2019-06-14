package myview.test.com.myview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import myview.test.com.myview.widget.bezier.BezierView2;
import myview.test.com.myview.widget.bezier.BezierView3;
import myview.test.com.myview.widget.pathmeasure.CircleArrowTest;

public class MainActivity extends AppCompatActivity {
    RelativeLayout rl_root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rl_root = findViewById(R.id.rl_root);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        rl_root.removeAllViews();
        switch (item.getItemId()){
            case R.id.menu_test:{
                MyView myView = new MyView(this);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                rl_root.addView(myView, layoutParams);
                break;
            }
            case R.id.menu_bezier_curve2:{
                BezierView2 bezierView = new BezierView2(this);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                rl_root.addView(bezierView, layoutParams);
                break;
            }
            case R.id.menu_bezier_curve3:{
                BezierView3 bezierView = new BezierView3(this);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                rl_root.addView(bezierView, layoutParams);
                break;
            }
            case R.id.menu_path_measure:{
                CircleArrowTest circleArrowTest = new CircleArrowTest(this);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                rl_root.addView(circleArrowTest, layoutParams);
                break;
            }
        }
        return true;
    }
}
