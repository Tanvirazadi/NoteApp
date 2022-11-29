package com.example.note.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.note.Model.Notes;
import com.example.note.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Notescreate extends AppCompatActivity {
    Notes notes;
    EditText title, note;
    ImageButton img;
    boolean isedit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notescreate);
        title = findViewById(R.id.titleedit);
        note = findViewById(R.id.notesedit);
        img = findViewById(R.id.savebtn);

        notes=new Notes();
        try {
            notes= (Notes) getIntent().getSerializableExtra("newnotes");
            title.setText(notes.getTitle());
            note.setText(notes.getNote());
            isedit=true;

        }catch (Exception e){
            e.printStackTrace();
        }


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titls = title.getText().toString();
                String description = note.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter some notes", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:a");
                Date date = new Date();

                if (!isedit){
                    notes = new Notes();
                }

                notes.setTitle(titls);
                notes.setNote(description);
                notes.setDate(format.format(date));

                Intent intent = getIntent();
                intent.putExtra("notes", notes);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}