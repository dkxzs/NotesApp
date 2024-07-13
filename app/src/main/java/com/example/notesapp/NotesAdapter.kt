package com.example.notesapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes : List<Note>, context : Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NotesDatabaseHelper = NotesDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateBtn : ImageView = itemView.findViewById(R.id.updateBtn)
        val deleteBtn : ImageView = itemView.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int =  notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateBtn.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply{
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteBtn.setOnClickListener {
            val dialog = AlertDialog.Builder(holder.itemView.context)
                .setTitle("Thông báo")
                .setMessage("Bạn có muốn xoá không")
                .setNegativeButton("Huỷ") {dialogInterface, _ -> dialogInterface.dismiss()}
                .setPositiveButton("Đồng ý") {_, _ ->
                    db.deleteNote(note.id)
                    refreshData(db.getAllNotes())
                    Toast.makeText(holder.itemView.context, "Note deleted", Toast.LENGTH_SHORT).show()
                }
            dialog.show()
        }
    }

    fun refreshData(newNotes : List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }

}