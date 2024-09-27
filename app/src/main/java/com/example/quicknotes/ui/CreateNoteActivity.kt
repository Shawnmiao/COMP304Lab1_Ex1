package com.example.quicknotes.ui

import android.app.Activity
import android.content.Intent
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

class CreateNoteActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateNoteScreen(onSaveNote = { title, content ->
                // Create a new Note and return to HomeActivity
                val newNote = Note(
                    id = System.currentTimeMillis().toInt(),
                    title = title,
                    content = content
                )
                // Create an Intent to pass data back to MainActivity
                val resultIntent = Intent().apply {
                    putExtra("note_id", newNote.id)
                    putExtra("note_title", newNote.title)
                    putExtra("note_content", newNote.content)
                }

                // Set result and finish activity
                setResult(Activity.RESULT_OK, resultIntent)

                finish()
            })
        }
    }
}

@Composable
fun CreateNoteScreen(onSaveNote: (String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

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
