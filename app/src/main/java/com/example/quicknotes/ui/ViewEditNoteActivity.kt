package com.example.quicknotes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quicknotes.model.Note

class ViewEditNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the note passed from HomeActivity (not implemented in this example)
        val note = Note(1, "Sample Note", "Sample content")

        setContent {
            ViewEditNoteScreen(note = note, onSaveNote = { updatedTitle, updatedContent ->
                note.title = updatedTitle
                note.content = updatedContent
                finish()
            })
        }
    }
}

@Composable
fun ViewEditNoteScreen(note: Note, onSaveNote: (String, String) -> Unit) {
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = content, onValueChange = { content = it }, label = { Text("Content") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onSaveNote(title, content) }) {
            Text("Save")
        }
    }
}
