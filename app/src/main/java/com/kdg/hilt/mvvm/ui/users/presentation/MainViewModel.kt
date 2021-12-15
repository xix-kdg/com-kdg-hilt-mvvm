package com.kdg.hilt.mvvm.ui.users.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdg.hilt.mvvm.data.remote.domain.framework.Result
import com.kdg.hilt.mvvm.data.remote.domain.model.User
import com.kdg.hilt.mvvm.framework.CoroutinesDispatcherProvider
import com.kdg.hilt.mvvm.ui.users.data.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val userRepositoryImpl: UserRepositoryImpl
) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    fun loadUsers() {
        viewModelScope.launch(dispatcherProvider.computation) {
            val result = userRepositoryImpl.getUsers(0)
            withContext(dispatcherProvider.main) {
                when (result) {
                    is Result.Success -> {
                        Log.d("WTF", "${result.data.size}")
                        Log.d("WTF", "${result.data}")
                    }
                    is Result.Error -> {
                        Log.d("WTF", "error: ${result.exception}")
                    }
                }
            }
        }
    }
}