package com.example.notesapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.example.notesapp.databinding.ActivityMainBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        db = NotesDatabaseHelper(this)
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val note = Note(0, title, content)
            db.insertNote(note)
            finish()
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
        }

        binding.btnBack.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage("Bạn có muốn đóng không ?")
                .setNegativeButton("Huỷ")  {dialogInterface, _ -> dialogInterface.dismiss()}
                .setPositiveButton("Đồng ý") { _, _ ->
                    finish()
                }
                .create()
            dialog.show()
        }
    }
}