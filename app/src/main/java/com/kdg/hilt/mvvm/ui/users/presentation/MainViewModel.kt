package com.kdg.hilt.mvvm.ui.users.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdg.hilt.mvvm.data.remote.domain.framework.Result
import com.kdg.hilt.mvvm.data.remote.domain.model.User
import com.kdg.hilt.mvvm.framework.CoroutinesDispatcherProvider
import com.kdg.hilt.mvvm.framework.Event
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

    private val _isLoadingIndicatorVisible = MutableLiveData<Boolean>()
    val isLoadingIndicatorVisible: LiveData<Boolean> = _isLoadingIndicatorVisible

    private val _showError = MutableLiveData<Event<String>>()
    val showError: LiveData<Event<String>> = _showError

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    fun loadUsers() {
        _isLoadingIndicatorVisible.value = true
        viewModelScope.launch(dispatcherProvider.computation) {
            val result = userRepositoryImpl.getUsers(0)
            withContext(dispatcherProvider.main) {
                when (result) {
                    is Result.Success -> {
                        _users.value = result.data
                    }
                    is Result.Error -> {
                        _showError.value = Event(result.exception.message ?: "")
                    }
                }
                _isLoadingIndicatorVisible.value = false
            }
        }
    }
}