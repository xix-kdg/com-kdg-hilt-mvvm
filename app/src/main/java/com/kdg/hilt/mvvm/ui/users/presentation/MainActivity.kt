package com.kdg.hilt.mvvm.ui.users.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kdg.hilt.mvvm.R
import com.kdg.hilt.mvvm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect { uiState ->
                    binding.progressBar.isVisible = uiState.isLoadingIndicatorVisible
                    binding.layoutContent.isVisible = uiState.isContentVisible
                    binding.includeGenericError.root.isVisible = uiState.isGenericErrorVisible
                    binding.includeNetworkError.root.isVisible = uiState.isNetworkErrorVisible
                    // todo: session expired if supported
                }
            }
        }

        binding.includeGenericError.btnRetry.setOnClickListener {
            viewModel.onRetryClick()
        }

        binding.includeNetworkError.btnRetry.setOnClickListener {
            viewModel.onRetryClick()
        }
    }
}