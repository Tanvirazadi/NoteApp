package com.example.note.Model;

import androidx.cardview.widget.CardView;

public interface Clicklistener {
    void onClick(Notes notes);
    void onLongClick(Notes notes,CardView cardView);
}
