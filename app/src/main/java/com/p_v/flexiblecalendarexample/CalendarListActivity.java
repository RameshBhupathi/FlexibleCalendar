package com.p_v.flexiblecalendarexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tooltip.Tooltip;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

public class CalendarListActivity extends AppCompatActivity implements CalendarListAdapter.OnCalendarTypeClickListener {

    private List<String> calendarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_list);

        RecyclerView calendarRecyclerView = (RecyclerView) findViewById(R.id.calendar_list_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        calendarRecyclerView.setLayoutManager(layoutManager);

        calendarList = new ArrayList<>();
        calendarList.add("Calendar 1");
        calendarList.add("Calendar 2");
        calendarList.add("Calendar 3");
        calendarList.add("Calendar 4");
        calendarList.add("Calendar 5");

        CalendarListAdapter adatper = new CalendarListAdapter(calendarList);
        adatper.setCalendarTypeClickListener(this);
        calendarRecyclerView.setAdapter(adatper);
        Log.v("test", "" + showMessage("test"));
    }

    @DebugLog
    private boolean showMessage(String test) {
        Toast.makeText(this, test, Toast.LENGTH_SHORT).show();
        return true;
    }
    @DebugLog
    @Override
    public void onCalendarTypeClick(String calendarType, int position) {
        Log.v("cal type",calendarType+" "+position);
        switch (position) {
            case 0:
                Intent calActivity1 = new Intent(this, CalendarActivity.class);
                startActivity(calActivity1);
                break;
            case 1:
                Intent calActivity2 = new Intent(this, CalendarActivity2.class);
                startActivity(calActivity2);
                break;

            case 2:
                Intent calActivity3 = new Intent(this, CalendarActivity3.class);
                startActivity(calActivity3);
                break;

            case 3:
                Intent calActivity4 = new Intent(this, CalendarActivity4.class);
                startActivity(calActivity4);
                break;
            case 4:
                Intent calActivity5 = new Intent(this, CalendarActivity5.class);
                startActivity(calActivity5);
                break;

            default:


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showToolTip(View view) {

       /* SpannableString text = new SpannableString("I`m on the bottom of menu item X");
        text.setSpan(new ForegroundColorSpan(Color.RED), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Drawable d = ContextCompat.getDrawable(getBaseContext(), R.mipmap.ic_launcher);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        text.setSpan(span, 31, 32, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);*/

        Tooltip.Builder builder = new Tooltip.Builder(view)
                .setCornerRadius(10f)
                .setGravity(Gravity.BOTTOM)

                .setText(String.valueOf("It is yet another very simple tool tip!"));
        builder.show();
    }
}
