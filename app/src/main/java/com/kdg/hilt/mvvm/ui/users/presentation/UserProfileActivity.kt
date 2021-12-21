package com.kdg.hilt.mvvm.ui.users.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kdg.hilt.mvvm.R
import com.kdg.hilt.mvvm.databinding.ActivityUserProfileBinding
import com.kdg.hilt.mvvm.ui.extension.loadImageCircular
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class UserProfileActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "UserProfileActivity.Extras.Login"

        @JvmStatic
        fun newIntent(
            launchContext: Context,
            login: String,
        ): Intent {
            val intent = Intent(launchContext, UserProfileActivity::class.java)
            intent.putExtra(EXTRA_USERNAME, login)
            return intent
        }
    }

    private val viewModel by viewModels<UserProfileViewModel>()
    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Init binding
        binding = DataBindingUtil.setContentView<ActivityUserProfileBinding>(
            this,
            R.layout.activity_user_profile
        ).apply {
            lifecycleOwner = this@UserProfileActivity
            viewModel = this@UserProfileActivity.viewModel
        }
        // Init tool bar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(EXTRA_USERNAME)
            ?.replaceFirstChar { it.uppercase() }
        // Init observer
        viewModel.imageBannerUrl.observe(this, {
            binding.imageBanner.loadImageCircular(it, 0)
        })

        binding.includeGenericError.btnRetry.setOnClickListener {
            viewModel.onRetryClick()
        }

        binding.includeNetworkError.btnRetry.setOnClickListener {
            viewModel.onRetryClick()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
