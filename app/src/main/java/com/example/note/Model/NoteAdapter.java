package com.example.note.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.myholder> {
    Context context;
    List<Notes>notesList;
    Clicklistener clicklistener;

    public NoteAdapter(Context context, List<Notes> notesList, Clicklistener clicklistener) {
        this.context = context;
        this.notesList = notesList;
        this.clicklistener = clicklistener;
    }


    @NonNull
    @Override
    public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notelist,parent,false);
        return new myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myholder holder, int position) {
        holder.title.setText(notesList.get(position).getTitle());
        holder.title.setSelected(true);
        holder.note.setText(notesList.get(position).getNote());
        holder.note.setSelected(true);
        holder.date.setText(notesList.get(position).getDate());
        holder.date.setSelected(true);
        int colorcode=getcolor();
        holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(colorcode,null));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            clicklistener.onClick(notesList.get(holder.getAdapterPosition()));
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clicklistener.onLongClick(notesList.get(holder.getAdapterPosition()),holder.cardView);
                return false;
            }
        });



    }
    private  int getcolor(){
        List<Integer>colorlist=new ArrayList<>();
        colorlist.add(R.color.color1);
        colorlist.add(R.color.color2);
        colorlist.add(R.color.color3);
        colorlist.add(R.color.color4);
        colorlist.add(R.color.color5);
        colorlist.add(R.color.color6);
        colorlist.add(R.color.color7);
        colorlist.add(R.color.color8);
        colorlist.add(R.color.color9);
        colorlist.add(R.color.color10);
        Random random=new Random();
        int randomcolor=random.nextInt(colorlist.size());
        return colorlist.get(randomcolor);


    }
    public  void  filterlist(List<Notes>filteredlist){
        notesList=filteredlist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    class myholder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title,note,date;

        public myholder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardviews);
            title=itemView.findViewById(R.id.titletext);
            note=itemView.findViewById(R.id.notetext);
            date=itemView.findViewById(R.id.datetext);

        }
    }
}
