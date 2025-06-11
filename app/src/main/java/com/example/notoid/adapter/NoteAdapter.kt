package com.example.notoid.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notoid.databinding.NoteLayoutBinding
import com.example.notoid.fragments.HomeFragmentDirections
import com.example.notoid.model.Note
import java.util.*

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    // This is our ViewHolder — it holds the layout for each note item
    class NoteViewHolder(val itemBinding: NoteLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root)

    // DiffUtil helps us efficiently update only changed items (super useful for performance!)
    private val differCallback = object : DiffUtil.ItemCallback<Note>() {

        // This checks if two items represent the same note (based on ID + content)
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.noteBody == newItem.noteBody &&
                    oldItem.noteTitle == newItem.noteTitle
        }

        // This checks if the full content of the two notes is exactly the same
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    // AsyncListDiffer lets us submit a list of notes and updates the RecyclerView smoothly
    val differ = AsyncListDiffer(this, differCallback)

    // This is where we inflate our layout (note_layout.xml) for each list item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // Binds each note's data to its view (title, body, color, click actions)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        // Set the note title and body
        holder.itemBinding.tvNoteTitle.text = currentNote.noteTitle
        holder.itemBinding.tvNoteBody.text = currentNote.noteBody

        // Just for fun — we generate a random background color for each note box
        val random = Random()
        val color = Color.argb(
            255, // fully opaque
            random.nextInt(256), // red
            random.nextInt(256), // green
            random.nextInt(256)  // blue
        )
        holder.itemBinding.ibColor.setBackgroundColor(color)

        // Handle click: when a note is tapped, navigate to the update screen with this note's data
        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(currentNote)
            it.findNavController().navigate(direction)
        }
    }

    // Just returns the number of notes in the list
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
