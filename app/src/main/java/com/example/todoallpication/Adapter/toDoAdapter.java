package com.example.todoallpication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoallpication.MainActivity;
import com.example.todoallpication.Models.ToDoModel;
import com.example.todoallpication.R;
import com.example.todoallpication.utils.DatabaseHelper;

import java.util.List;

public class toDoAdapter extends RecyclerView.Adapter<toDoAdapter.MyViewHolder>{
    private List<ToDoModel> mList;
    private MainActivity activity;
    private DatabaseHelper myDB;
    public toDoAdapter(DatabaseHelper myDB,MainActivity activity){
        this.activity=activity;
        this.myDB=myDB;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_laout,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ToDoModel item=mList.get(position);
        holder.mCheckbox.setText(item.getTask());
        holder.mCheckbox.setChecked(toBoolean(item.getStatus()));
        holder.mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    myDB.updateStatus(item.getId(),1);
                }else {
                    myDB.updateStatus(item.getId(),0);
                }
            }
        });
    }
    public boolean toBoolean(int num){
        return num!=0;
    }
    public Context getContext(){
        return activity;
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTask(List<ToDoModel> mList){
        this.mList=mList;
        notifyDataSetChanged();
    }
    public void deleteTask(int position){
        ToDoModel item=mList.get(position);
        myDB.deleteTask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        ToDoModel item=mList.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());

    }
    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        CheckBox mCheckbox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckbox=itemView.findViewById(R.id.todoCheckBox);
        }
    }
}
