package com.example.todoallpication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todoallpication.Adapter.toDoAdapter;
import com.example.todoallpication.Models.ToDoModel;
import com.example.todoallpication.utils.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  OnDialogCloseListner{
    RecyclerView recyclerView;
    FloatingActionButton button;
    private DatabaseHelper myDB;
    private List<ToDoModel> mList;
    private toDoAdapter  adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recTask);
        button=findViewById(R.id.fabBtn);

        myDB=new DatabaseHelper(MainActivity.this);

        mList=new ArrayList<>();
        adapter=new toDoAdapter(myDB,MainActivity.this);

        mList=myDB.allTask();
        Collections.reverse(mList);
        adapter.setTask(mList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void OnDialogClose(DialogInterface dialogInterface) {
        mList=myDB.allTask();
        Collections.reverse(mList);
        adapter.setTask(mList);
        adapter.notifyDataSetChanged();
    }
}