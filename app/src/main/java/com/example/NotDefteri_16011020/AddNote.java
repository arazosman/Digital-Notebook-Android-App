package com.example.NotDefteri_16011020;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.NotDefteri_16011020.Adapter.ListItemAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class AddNote extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{
    FirebaseFirestore db;
    int year, month, day, hour, minute;
    String id;

    public MaterialEditText title, description, date, time;
    FloatingActionButton fab;
    public String idUpdate = "";

    ListItemAdapter adapter;
    public AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        db = FirebaseFirestore.getInstance();
        dialog = new SpotsDialog.Builder().setContext(AddNote.this).build();

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        fab = findViewById(R.id.fab);

        date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDatePickerDialog();
            }
        });

        time.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showTimePickerDialog();
            }
        });

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setData(title.getText().toString(), description.getText().toString(), date.getText().toString(), time.getText().toString());

                setAlarm();
                Toast.makeText(AddNote.this,"Yeni not eklendi.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void showDatePickerDialog()
    {
        Calendar cal = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;

        String dateText = day + "." + month + "." + year;
        date.setText(dateText);
    }

    private void showTimePickerDialog()
    {
        Calendar cal = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                this,
                cal.get(Calendar.HOUR),
                cal.get(Calendar.MINUTE),
                true);

        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        this.hour = hourOfDay;
        this.minute = minute;

        String timeText = String.format("%02d", hour) + ":" + String.format("%02d", minute);
        time.setText(timeText);
    }

    private void setData(String title, String description, String date, String time)
    {
        id = UUID.randomUUID().toString();

        Map<String, Object> todo = new HashMap<>();

        todo.put("id", id);
        todo.put("title", title);
        todo.put("description", description);
        todo.put("date", date);
        todo.put("time", time);

        db.collection("taskList").document(id).set(todo);
    }

    private void setAlarm()
    {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);

        //Log.d("HEEEYYYYYY", "setAlarm: " + year + " " + month + " " + day + " " + hour + " " + minute);

        Intent intent = new Intent(this, AlarmReceiver.class);

        Log.d("ID:::::: ", "setAlarm: " + id);

        intent.putExtra("notificationID", id);
        intent.putExtra("title", title.getText().toString());
        intent.putExtra("description", description.getText().toString());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (cal.before(Calendar.getInstance()))
            cal.add(Calendar.DATE, 1);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }
}