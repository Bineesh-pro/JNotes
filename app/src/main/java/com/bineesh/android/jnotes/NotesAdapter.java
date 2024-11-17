package com.bineesh.android.jnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    List<NoteModel> noteModels;
    Context context;

    public NotesAdapter(List<NoteModel> notesList,Context context){
        this.noteModels = notesList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteModel currentNote = noteModels.get(position);
        holder.tittle.setText(currentNote.getTittle());
        holder.notes.setText(currentNote.getNote());
        String t = "created: "+currentNote.getCreatedDate();
        holder.createdDate.setText(t);
        t = "last edited: "+currentNote.getLastUpdatedDate();
        holder.lastEditedDate.setText(t);
        holder.itemView.setOnClickListener(v->{
            ((AppCompatActivity)context).getSupportFragmentManager()
                    .beginTransaction().replace(R.id.main_frame,new NoteCreateEditFragment(currentNote)).commit();
        });

    }

    @Override
    public int getItemCount() {
        return noteModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tittle,notes;
        TextView createdDate,lastEditedDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tittle = itemView.findViewById(R.id.tittle);
            notes = itemView.findViewById(R.id.notes);
            createdDate = itemView.findViewById(R.id.created_date);
            lastEditedDate = itemView.findViewById(R.id.last_edited_date);
        }
    }


}
