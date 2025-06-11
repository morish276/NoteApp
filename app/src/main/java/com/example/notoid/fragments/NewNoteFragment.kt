package com.example.notoid.fragments

// Necessary imports
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.notoid.MainActivity
import com.example.notoid.R
import com.example.notoid.adapter.NoteAdapter
import com.example.notoid.databinding.FragmentNewNoteBinding
import com.example.notoid.model.Note
import com.example.notoid.viewmodel.NoteViewmodel

// Fragment for creating a new note. Uses fragment_new_note.xml layout.
class NewNoteFragment : Fragment(R.layout.fragment_new_note) {

    // ViewBinding: Automatically connects XML views to code
    private var _binding:  FragmentNewNoteBinding? = null
    private val binding get() = _binding!!  // Non-null access to binding

    // Declare ViewModel and Adapter
    private lateinit var noteViewmodel: NoteViewmodel
    private lateinit var noteAdapter: NoteAdapter

    // Used to get the fragment's root view inside saveNote()
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Tells the system that this fragment wants to add items to the options menu
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using ViewBinding
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false )
        return binding.root // Return the root view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel is shared with MainActivity
        noteViewmodel = (activity as MainActivity).noteViewModel

        // Save the current view for use later in saveNote()
        mView = view
    }

    // This function handles the saving of a note
    private fun saveNote(view: View){
        // Get text from input fields, and trim to remove spaces
        val noteTitle = binding.etNoteTitle.text.toString().trim()
        val noteBody = binding.etNoteBody.text.toString().trim()

        if (noteTitle.isNotEmpty()) {
            // If title is entered, create Note object (id = 0 because Room will auto-generate it)
            val note = Note(0, noteTitle, noteBody)

            // Call ViewModel function to insert the note into the database
            noteViewmodel.addNote(note)

            // Notify user
            Toast.makeText(mView.context, "Note Saved Successfully", Toast.LENGTH_LONG).show()

            // Navigate back to the home screen
            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)

        } else {
            // If title is empty, show an error toast
            Toast.makeText(mView.context, "Please enter note Title", Toast.LENGTH_LONG).show()
        }
    }

    // Inflate the menu for this fragment (e.g., Save icon)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear() // Clear previous menu items (if any)
        inflater.inflate(R.menu.new_note_menu, menu) // Load new_note_menu.xml
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Handle item clicks from the options menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save -> {
                saveNote(mView) // Save note when Save icon is tapped
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Prevent memory leaks by cleaning up binding when the view is destroyed
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}