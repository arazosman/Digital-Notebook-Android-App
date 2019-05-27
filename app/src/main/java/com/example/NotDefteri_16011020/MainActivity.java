package com.example.NotDefteri_16011020;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.NotDefteri_16011020.Adapter.ListItemAdapter;
import com.example.NotDefteri_16011020.Model.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity
{
    List<Task> taskList = new ArrayList<>();
    FirebaseFirestore db;

    RecyclerView listItem;
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton fab;

    Button addButton;

    ListItemAdapter adapter;

    public AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        dialog = new SpotsDialog.Builder().setContext(MainActivity.this).build();

        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, AddNote.class);
                startActivity(intent);
            }
        });

        listItem = findViewById(R.id.list);
        listItem.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listItem.setLayoutManager(layoutManager);
        adapter = new ListItemAdapter(MainActivity.this, taskList);
        listItem.setAdapter(adapter);
        dialog.dismiss();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        loadData();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        if (item.getTitle().equals("DELETE"))
            deleteItem(item.getOrder());
        
        return super.onContextItemSelected(item);
    }

    private void deleteItem(int order)
    {
        db.collection("taskList").document(taskList.get(order).getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        loadData();
                    }
                });
    }

    private void loadData()
    {
        dialog.show();

        if (taskList.size() > 0)
            taskList.clear();

        db.collection("taskList").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task)
            {
                for (DocumentSnapshot doc: task.getResult())
                {
                    Task aTask = new Task(doc.getString("id"), doc.getString("title"), doc.getString("description"),
                                          doc.getString("date"), doc.getString("time"));
                    taskList.add(aTask);
                }

                adapter = new ListItemAdapter(MainActivity.this, taskList);
                listItem.setAdapter(adapter);
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}