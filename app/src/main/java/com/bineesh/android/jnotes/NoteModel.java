package com.bineesh.android.jnotes;


public class NoteModel {
    String note;
    String tittle;
    String createdDate;
    String lastUpdatedDate;
    int noteId;

    public NoteModel(String note, String tittle, String createdDate, String lastUpdatedDate, int noteId) {
        this.note = note;
        this.tittle = tittle;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.noteId = noteId;
    }

    public String getNote() {
        return note;
    }

    public String getTittle() {
        return tittle;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public int getNoteId() {
        return noteId;
    }
}
