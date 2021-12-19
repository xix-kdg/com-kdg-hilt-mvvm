package com.kdg.hilt.mvvm.ui.users.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kdg.hilt.mvvm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private lateinit var userAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Init binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Init toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        // Init recycler view
        val layoutManager = LinearLayoutManager(this)
        userAdapter = UsersAdapter(arrayListOf(), object : UsersAdapter.OnUserClickListener {
            override fun onUserClick(username: String) {
                TODO("Not yet implemented")
            }

        })
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = userAdapter
        // Init observers
        viewModel.users.observe(this, {
            userAdapter.addItems(it)
        })

        viewModel.loadUsers()
    }
}