package com.example.todoallpication;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoallpication.Models.ToDoModel;
import com.example.todoallpication.utils.DatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
     static final String TAG = "AddNewTask";

    //widgets
    private EditText mEditText;
    private Button btnSave;

    private DatabaseHelper myDB;

    public static AddNewTask newInstance(){
        return new AddNewTask();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_text_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText=view.findViewById(R.id.txtTask);
        btnSave=view.findViewById(R.id.btnSave);
        if(mEditText.getText().toString().isEmpty()){
            btnSave.setEnabled(false);
            btnSave.setBackgroundColor(Color.GRAY);
        }
        myDB=new DatabaseHelper(getActivity());
        boolean isUpdate=false;

        Bundle bundle=getArguments();
        if(bundle!=null){
            isUpdate=true;
            String task = bundle.getString("task");
            mEditText.setText(task);

            if(task.length()>0){
                btnSave.setEnabled(false);
            }
        }
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    btnSave.setEnabled(false);
                    btnSave.setBackgroundColor(Color.GRAY);
                }else{
                    btnSave.setEnabled(true);
                    btnSave.setBackgroundColor(getResources().getColor(R.color.x));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boolean finalIsUpdate = isUpdate;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=mEditText.getText()+"";
                if(finalIsUpdate){
                    myDB.updateTask(bundle.getInt("id"),text);
                }else{
                    ToDoModel item=new ToDoModel();
                    item.setTask(text);
                    item.setStatus(0);
                    myDB.insertTask(item);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity=getActivity();
        if(activity instanceof OnDialogCloseListner ){
            ((OnDialogCloseListner)activity).OnDialogClose(dialog);
        }
    }
}
