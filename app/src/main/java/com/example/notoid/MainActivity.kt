package com.example.notoid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.notoid.database.NoteDatabase
import com.example.notoid.databinding.ActivityMainBinding
import com.example.notoid.repository.NoteRepository
import com.example.notoid.viewmodel.NoteViewmodel
import com.example.notoid.viewmodel.NoteViewmodelFactory

class MainActivity : AppCompatActivity() {

    // ViewBinding variable for accessing views in activity_main.xml
    lateinit var binding: ActivityMainBinding

    // ViewModel to be shared with fragments
    lateinit var noteViewModel: NoteViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate and set the layout using ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set custom status bar color (moon_gold is a color resource)
        window.statusBarColor = ContextCompat.getColor(this, R.color.moon_gold)

        // Setup ViewModel using repository and factory pattern
        setUpViewModel()

        // Set the top app bar (MaterialToolbar) as the ActionBar
        setSupportActionBar(binding.topAppBar)

        // Apply padding to handle system bars (like status & navigation bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Set padding so content isn't hidden behind status or nav bars
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Initializes ViewModel with Repository and custom Factory
    private fun setUpViewModel() {
        // Create the repository instance using database
        val noteRepository = NoteRepository(NoteDatabase(this))

        // Create ViewModelFactory with application context and repository
        val viewModelProviderFactory = NoteViewmodelFactory(application, noteRepository)

        // Use ViewModelProvider to get instance of ViewModel
        noteViewModel = ViewModelProvider(
            this, viewModelProviderFactory
        ).get(NoteViewmodel::class.java)
    }
}