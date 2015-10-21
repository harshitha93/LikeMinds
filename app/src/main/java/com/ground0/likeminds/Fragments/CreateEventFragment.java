package com.ground0.likeminds.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.ground0.likeminds.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Arjun on 01-09-2015.
 */
public class CreateEventFragment extends Fragment {

    Context context;
    View rootView;
    android.app.FragmentManager fragmentManager;

    static class ViewHolder
    {
        public static TextView fromDate,toDate,fromTime,toTime;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        AppCompatActivity activity1 = (AppCompatActivity)activity;
        this.fragmentManager = activity1.getFragmentManager();
        this.context = activity;
    }

    class DatePickerListener implements View.OnClickListener
    {

        @Override
        public void onClick(final View v) {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                            Log.i("com.ground0.likeminds", "Date Set : "+year+month+day);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM ");

                            switch (v.getId())
                            {
                                case R.id.from_date:
                                    TextView from = (TextView)v;
                                    Date date = new Date(year, month, day);
                                    String result = dateFormat.format(date);
                                    from.setText(result+year);

                                    break;
                                case R.id.to_date:
                                    TextView to = (TextView)v;
                                    date = new Date(year, month, day);
                                    result = dateFormat.format(date);
                                    to.setText(result+year);
                                    break;
                            }
                        }
                    },
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.show(fragmentManager, "DatePicker");
        }
    }

    class TimePickerListener implements View.OnClickListener
    {

        @Override
        public void onClick(final View v) {
            Calendar now = Calendar.getInstance();
            TimePickerDialog dpd = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(RadialPickerLayout radialPickerLayout, int hour, int minute) {

                    Log.i("com.ground0.likeminds", "Time Set : "+hour+minute);

                    switch (v.getId()) {
                        case R.id.from_time:
                            ((TextView)v).setText(hour+":"+minute);
                            break;
                        case R.id.to_time:
                            ((TextView)v).setText(hour+":"+minute);
                            break;
                    }
                }},
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true);

            dpd.show(fragmentManager, "TimePicker");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_event,container,false);

        ViewHolder.fromDate = (TextView)rootView.findViewById(R.id.from_date);
        ViewHolder.toDate = (TextView)rootView.findViewById(R.id.to_date);
        ViewHolder.fromTime = (TextView)rootView.findViewById(R.id.from_time);
        ViewHolder.toTime = (TextView)rootView.findViewById(R.id.to_time);

        ViewHolder.fromDate.setText(new SimpleDateFormat("EEE, d MMM yyyy").format(new Date()));
        ViewHolder.toDate.setText(new SimpleDateFormat("EEE, d MMM yyyy").format(new Date()));

        ViewHolder.fromTime.setText(new SimpleDateFormat("k:mm").format(new Date()));
        ViewHolder.toTime.setText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+1+":"+
                                    Calendar.getInstance().get(Calendar.MINUTE));

        ViewHolder.fromDate.setOnClickListener(new DatePickerListener());
        ViewHolder.toDate.setOnClickListener(new DatePickerListener());
        ViewHolder.fromTime.setOnClickListener(new TimePickerListener());
        ViewHolder.toTime.setOnClickListener(new TimePickerListener());


        rootView.findViewById(R.id.action_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Event Posted", Toast.LENGTH_SHORT).show();
            }
        });


        String[] memberList = {"Arjun Biju","Shashank Sahay","Vikram Thomas","John Doe"};
        final AutoCompleteTextView textView = ((AutoCompleteTextView)rootView.findViewById(R.id.add_recipient_edittext));
        textView.setAdapter(new ArrayAdapter<String>(context, R.layout.simple_item_autocomplete, memberList));
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                ViewGroup viewGroup = (ViewGroup) rootView.findViewById(R.id.recepient_list_add);
                View item = LayoutInflater.from(context).inflate(R.layout.recipient_item, viewGroup, false);
                ((TextView) item.findViewById(R.id.recepient_name))
                        .setText(((TextView) view).getText());


                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ViewGroup)v.getParent()).removeView(v);
                    }
                });
                textView.setText("");

                item.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                viewGroup.addView(item);

                DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
                float dp = 2f;
                float fpixels = metrics.density * dp;
                int pixels = (int) (fpixels + 0.5f);

                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) item.getLayoutParams();
                marginLayoutParams.setMargins(pixels, pixels, pixels, pixels);

            }
        });

        return rootView;
    }
}
