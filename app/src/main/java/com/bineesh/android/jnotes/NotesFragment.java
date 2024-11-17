package com.bineesh.android.jnotes;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    RecyclerView notesRecy;
    CardView addNewButton;
    TextView emptyTag;
    SqliteDB sqliteDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_notes, container, false);
        notesRecy = view.findViewById(R.id.notes_recy);
        addNewButton = view.findViewById(R.id.add_new_note);
        emptyTag = view.findViewById(R.id.empty_tag);

        sqliteDB = new SqliteDB(getContext());
        List<NoteModel> noteModels = sqliteDB.getAllNotes();

        if(noteModels.isEmpty()){
            notesRecy.setVisibility(View.GONE);
            emptyTag.setVisibility(View.VISIBLE);
        }else {
            notesRecy.setVisibility(View.VISIBLE);
            emptyTag.setVisibility(View.GONE);
            notesRecy.setAdapter(new NotesAdapter(noteModels, getContext()));
            notesRecy.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.HORIZONTAL, false));
        }
        addNewButton.setOnClickListener(v ->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new NoteCreateEditFragment()).commit();
        });

        return view;
    }

}