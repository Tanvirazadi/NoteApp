package com.example.note.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.note.Database.Roomdb;
import com.example.note.Model.Clicklistener;
import com.example.note.Model.NoteAdapter;
import com.example.note.Model.Notes;
import com.example.note.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    List<Notes> notesList = new ArrayList<>();
    NoteAdapter adapter;
    Roomdb database;
    SearchView searchView;
    Notes selectednotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rcview);
        fab = findViewById(R.id.flotbtn);
        searchView=findViewById(R.id.searchs);

        database = Roomdb.getInstance(this);
        notesList = database.getNoteDao().getall();

        updaterycle(notesList);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Notescreate.class);
                startActivityForResult(intent, 101);
            }
        });
    }

    private void filter(String newText) {
        List<Notes>filerednotes=new ArrayList<>();
        for (Notes singleNote :notesList){
            if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
            || singleNote.getNote().toLowerCase().contains(newText.toLowerCase())){
                filerednotes.add(singleNote);

            }
        }
        adapter.filterlist(filerednotes);

    }

    private void updaterycle(List<Notes> notesList) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        adapter = new NoteAdapter(MainActivity.this, notesList, clicklistener);
        recyclerView.setAdapter(adapter);


    }
    public final Clicklistener clicklistener = new Clicklistener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent=new Intent(MainActivity.this,Notescreate.class);
            intent.putExtra("newnotes",notes);
            startActivityForResult(intent,102);


        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selectednotes=new Notes();
            selectednotes=notes;

           showpopup(cardView);

        }
    };

    private void showpopup(CardView cardView) {
        PopupMenu popupMenu=new PopupMenu(this,cardView);
        popupMenu.inflate(R.menu.menu);
        popupMenu.setOnMenuItemClickListener(MainActivity.this);
        popupMenu.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            Notes notes = (Notes) data.getSerializableExtra("notes");
            database.getNoteDao().insert(notes);
            notesList.clear();
            notesList.addAll(database.getNoteDao().getall());
            adapter.notifyDataSetChanged();
        }
        else if (requestCode==102 && resultCode==Activity.RESULT_OK){
            Notes newnotes= (Notes) data.getSerializableExtra("newnotes");
            database.getNoteDao().update(newnotes.getId(), newnotes.getTitle() ,newnotes.getNote());
            notesList.clear();
            notesList.addAll(database.getNoteDao().getall());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                database.getNoteDao().delete(selectednotes);
                notesList.remove(selectednotes);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Note "+selectednotes.getTitle()+" Deleted!", Toast.LENGTH_SHORT).show();


                return  true;

        }
        return false;
    }
}