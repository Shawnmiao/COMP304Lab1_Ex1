package com.example.quicknotes.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quicknotes.model.Note
import com.example.quicknotes.ui.theme.QuickNotesTheme


class MainActivity : ComponentActivity() {
    private var notesList = mutableStateListOf<Note>()
    private val createNoteLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { data ->
                val noteId = data.getIntExtra("note_id", 0)
                val noteTitle = data.getStringExtra("note_title").orEmpty()
                val noteContent = data.getStringExtra("note_content").orEmpty()

                // Create a new note object
                val newNote = Note(
                    id = noteId,
                    title = noteTitle,
                    content = noteContent
                )

                // Add the new note to the list
                notesList.add(newNote)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notesList.addAll(
            listOf(
                Note(
                    id = 1,
                    title = "Sample Note 1",
                    content = "This is the content of Sample Note 1."
                ),
                Note(
                    id = 2,
                    title = "Sample Note 2",
                    content = "This is the content of Sample Note 2."
                )
            )
        )
        setContent {
            QuickNotesTheme {
                HomeScreen(notesList = notesList, onAddNote = {
                    val createNoteIntent = Intent(this, CreateNoteActivity::class.java)
                    createNoteLauncher.launch(createNoteIntent)
                })
            }
        }
    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        notesList.addAll(
//            listOf(
//                Note(
//                    id = 1,
//                    title = "Sample Note 1",
//                    content = "This is the content of Sample Note 1."
//                ),
//                Note(
//                    id = 2,
//                    title = "Sample Note 2",
//                    content = "This is the content of Sample Note 2."
//                )
//            )
//        )
//        setContent {
//            QuickNotesTheme {
//                HomeScreen(notesList = notesList, onAddNote = {
////                    startActivity(Intent(this, CreateNoteActivity::class.java))
//                    val createNoteIntent = Intent(this, CreateNoteActivity::class.java)
//                    startActivity(createNoteIntent, REQUEST_CODE_CREATE_NOTE)
//                })
//            }
//        }
//    }

//    override fun onResume() {
//        super.onResume()
//        // Refresh notes list if necessary
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == REQUEST_CODE_CREATE_NOTE && resultCode == Activity.RESULT_OK) {
//            data?.let {
//                // Retrieve the note details
//                val noteId = it.getIntExtra("note_id", 0)
//                val noteTitle = it.getStringExtra("note_title").orEmpty()
//                val noteContent = it.getStringExtra("note_content").orEmpty()
//
//                // Create a new note object
//                val newNote = Note(
//                    id = noteId,
//                    title = noteTitle,
//                    content = noteContent
//                )
//
//                // Add the new note to the list
//                notesList.add(newNote)
//            }
//        }
//    }
//
//    companion object {
//        const val REQUEST_CODE_CREATE_NOTE = 1001
//    }
    override fun onResume() {
        super.onResume()
        // Refresh notes list if necessary
    }
}

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(notesList: List<Note>, onAddNote: () -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddNote() }) {
                Text("+")
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(notesList) { note ->
                NoteCard(note)
            }
        }
    }
}


@Composable
fun NoteCard(note: Note) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = note.title, style = MaterialTheme.typography.headlineMedium)
            Text(text = note.content.take(100) + "...", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
