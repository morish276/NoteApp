package com.example.notoid.fragments

// Required imports for fragment operations, view handling, navigation, RecyclerView, etc.
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notoid.MainActivity
import com.example.notoid.R
import com.example.notoid.adapter.NoteAdapter
import com.example.notoid.databinding.FragmentHomeBinding
import com.example.notoid.model.Note
import com.example.notoid.viewmodel.NoteViewmodel

// HomeFragment shows the list of notes and handles user actions like add/search
class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    // ViewBinding setup (used to access views without findViewById)
    private var _binding:  FragmentHomeBinding? = null
    private val binding get() = _binding!!  // Safe way to access binding (non-null)

    // Declare ViewModel and Adapter
    private lateinit var noteViewmodel: NoteViewmodel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Enable this if using toolbar menu like Search
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using ViewBinding
        _binding = FragmentHomeBinding.inflate(inflater, container, false )
        return binding.root // Return the root view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access the ViewModel from MainActivity
        noteViewmodel = (activity as MainActivity).noteViewModel

        // Setup the RecyclerView (grid of notes)
        setUpRecyclerView()

        // Floating Action Button click opens "Add New Note" screen
        binding.fabAddNote.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_homeFragment_to_newNoteFragment // Uses Navigation Component to move to newNoteFragment
            )
        }
    }

    private fun setUpRecyclerView() {
        noteAdapter = NoteAdapter() // Initialize adapter

        // Configure RecyclerView
        binding.recyclerView.apply {
            // Use staggered layout (like Pinterest) with 2 columns
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true) // Improves performance if item size doesn't change
            adapter = noteAdapter
        }

        // Observe the note list from ViewModel
        activity?.let {
            noteViewmodel.getAllNotes().observe(viewLifecycleOwner) { note ->
                noteAdapter.differ.submitList(note) // Submit list to adapter (handled efficiently via AsyncListDiffer)
                updateUI(note) // Show/hide "no notes" card depending on list
            }
        }
    }

    // Show empty-state card if list is empty, otherwise show note list
    private fun updateUI(note: List<Note>?) {
        if(note!!.isNotEmpty()) {
            binding.cardView.visibility = View.GONE // Hide "No notes yet" card
            binding.recyclerView.visibility = View.VISIBLE // Show list
        } else {
            binding.cardView.visibility = View.VISIBLE // Show "No notes yet" card
            binding.recyclerView.visibility = View.GONE // Hide list
        }
    }

    // Optional: Creates the search menu in the toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear() // Clear old menu items
        inflater.inflate(R.menu.home_menu, menu) // Inflate new menu layout

        // Set up SearchView from menu
        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch.isSubmitButtonEnabled = false // Hides submit icon (we search in real-time)
        mMenuSearch.setOnQueryTextListener(this) // Assign this fragment as listener
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        // searchNote(query) // Uncomment if you want search on submit
        return false // We handle in onQueryTextChange instead
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            searchNote(it)
        }
        return true
    }

    private fun searchNote(query: String) {
        val searchQuery = "%${query.trim()}%" // Ensures spacing doesn't break the query
        noteViewmodel.searchNote(searchQuery).observe(viewLifecycleOwner) { list ->
            noteAdapter.differ.submitList(list)
            updateUI(list) // Optional: toggle empty view if needed during search
        }
    }

    // Clear binding when view is destroyed to prevent memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}