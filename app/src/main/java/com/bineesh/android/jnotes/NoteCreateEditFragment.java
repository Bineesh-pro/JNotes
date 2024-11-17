package com.bineesh.android.jnotes;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteCreateEditFragment extends Fragment {

    private NoteModel noteModel;
    EditText noteTittle,noteContent;
    TextView createDate,lastEditDate;
    Button submitButton;
    SqliteDB sqliteDB;

    public NoteCreateEditFragment(){

    }

    public NoteCreateEditFragment(NoteModel noteModel){
        this.noteModel = noteModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_create_edit, container, false);

        createDate = view.findViewById(R.id.created_date);
        lastEditDate = view.findViewById(R.id.last_edited_date);
        submitButton = view.findViewById(R.id.note_submit_button);
        noteTittle = view.findViewById(R.id.tittle);
        noteContent = view.findViewById(R.id.notes);
        sqliteDB = new SqliteDB(getContext());
        if(noteModel != null){
            noteTittle.setText(noteModel.getTittle());
            noteContent.setText(noteModel.getNote());
            String t = "created: "+noteModel.getCreatedDate();
            createDate.setText(t);
            t = "last edited: "+noteModel.getLastUpdatedDate();
            lastEditDate.setText(t);
        }else{
            createDate.setText(formatDateTime(new Date()));
            lastEditDate.setText("");
        }
        ((AppCompatActivity)getContext()).getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame,new NotesFragment())
                        .commit();
            }
        });

        submitButton.setOnClickListener(v->{
            String tittle = noteTittle.getText().toString();
            String note = noteContent.getText().toString();
            String dateTime = formatDateTime(new Date());

            if(!tittle.isEmpty() && !note.isEmpty()) {
                if(this.noteModel == null) {
                    sqliteDB.addNewNote(new NoteModel(note, tittle, dateTime, dateTime, 0));
                }else{
                    sqliteDB.updateNote(tittle,note,noteModel.getNoteId(),formatDateTime(new Date()));
                }
                ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, new NotesFragment())
                        .commit();
            }else{
                Toast.makeText(getContext(),"fill tittle and content",Toast.LENGTH_LONG).show();
            }

        });

        return view;
    }


    private String formatDateTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
        return simpleDateFormat.format(date);
    }


}