package com.kdg.hilt.mvvm.ui.users.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.kdg.hilt.mvvm.R
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
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        ).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = this@MainActivity.viewModel
        }
        // Init toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        // Init recycler view
        val layoutManager = LinearLayoutManager(this)
        userAdapter = UsersAdapter(arrayListOf(), object : UsersAdapter.OnUserClickListener {
            override fun onUserClick(login: String) {
                viewModel.onUserClick(login)
            }

        })
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = userAdapter
        // Init observers
        viewModel.users.observe(this, {
            userAdapter.addItems(it)
        })

        viewModel.navigateToProfile.observe(this, { event ->
            event.getContentIfNotHandled()?.let {
                startActivity(UserProfileActivity.newIntent(this, it))
            }
        })

        binding.includeGenericError.btnRetry.setOnClickListener {
            viewModel.onRetryClick()
        }

        binding.includeNetworkError.btnRetry.setOnClickListener {
            viewModel.onRetryClick()
        }
    }
}