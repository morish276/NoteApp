package com.example.notoid.fragments

// Import required Android and project-specific classes
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notoid.MainActivity
import com.example.notoid.R
import com.example.notoid.databinding.FragmentUpdateNoteBinding
import com.example.notoid.model.Note
import com.example.notoid.viewmodel.NoteViewmodel

// Fragment to update or delete an existing note
class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    // ViewBinding setup to safely access views
    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    // ViewModel to access note-related functions
    private lateinit var noteViewmodel: NoteViewmodel

    // Holds the note passed via navigation arguments
    private lateinit var currentNote: Note

    // navArgs() lets us receive passed data using SafeArgs plugin
    private val args: UpdateNoteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        // Note: You could call setHasOptionsMenu(true) here if you had a menu for update
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout using ViewBinding
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel from MainActivity
        noteViewmodel = (activity as MainActivity).noteViewModel

        // Get the current note passed from HomeFragment (or wherever it's called)
        currentNote = args.note!!  // !! asserts it's non-null (SafeArgs ensures this)

        // Pre-fill the EditTexts with existing note data
        binding.tNoteTitleUpdate.setText(currentNote.noteTitle)
        binding.tNoteBodyUpdate.setText(currentNote.noteBody)

        // if the user update the note
        binding.fabDone.setOnClickListener {
            // Get updated values from input fields
            val title = binding.tNoteTitleUpdate.text.toString().trim()
            val body = binding.tNoteBodyUpdate.text.toString().trim()

            // If the title is not empty, update the note
            if (title.isNotEmpty()){
                // Create updated Note object with the same ID
                val note = Note(currentNote.id, title, body)

                // Call ViewModel to update note in database
                noteViewmodel.updateNote(note)

                // Navigate back to HomeFragment
                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            } else {
                // If title is empty, show error toast
                Toast.makeText(
                    context,
                    "Please enter note Title",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    // This function shows a confirmation dialog and deletes the note if confirmed
    private fun deleteNote(){
        // Create a native Android AlertDialog
        android.app.AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("You want to delete this Note?")
            setPositiveButton("Delete") {_,_ ->
                // Delete the note from database
                noteViewmodel.deleteNote(currentNote)

                // Navigate back to HomeFragment
                view?.findNavController()?.navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            }
            setNegativeButton("Cancel", null) // Cancel button does nothing
        }.create().show()
    }

    // If you add a menu (like delete or share), inflate it here
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_note_menu, menu) // Inflate custom menu (if exists)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Handle delete menu item click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete -> {
                deleteNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Clean up binding to avoid memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}