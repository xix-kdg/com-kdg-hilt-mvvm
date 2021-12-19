package com.kdg.hilt.mvvm.ui.users.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdg.hilt.mvvm.data.remote.domain.framework.ErrorComponent
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
    private val errorComponent: ErrorComponent,
    private val userRepositoryImpl: UserRepositoryImpl
) : ViewModel() {

    private val _isContentVisible = MutableLiveData<Boolean>()
    val isContentVisible: LiveData<Boolean> = _isContentVisible

    private val _isLoadingIndicatorVisible = MutableLiveData<Boolean>()
    val isLoadingIndicatorVisible: LiveData<Boolean> = _isLoadingIndicatorVisible

    private val _isNetworkErrorVisible = MutableLiveData<Boolean>()
    val isNetworkErrorVisible: LiveData<Boolean> = _isNetworkErrorVisible

    private val _isGenericErrorVisible = MutableLiveData<Boolean>()
    val isGenericErrorVisible: LiveData<Boolean> = _isGenericErrorVisible

    private val _isSessionExpiredErrorVisible = MutableLiveData<Boolean>()
    val isSessionExpiredErrorVisible: LiveData<Boolean> = _isSessionExpiredErrorVisible

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    init {
        loadUsers()
    }

    fun onUserClick() {
        // todo
    }

    fun onRetryClick() {
        loadUsers()
    }

    private fun loadUsers() {
        _isLoadingIndicatorVisible.value = true
        viewModelScope.launch(dispatcherProvider.computation) {
            val result = userRepositoryImpl.getUsers(0)
            withContext(dispatcherProvider.main) {
                when (result) {
                    is Result.Success -> {
                        showContent()
                        _users.value = result.data
                    }
                    is Result.Error -> {
                        errorComponent.processError(
                            result,
                            { showNetworkError() },
                            { showGenericError() },
                            { showSessionExpiredError() }
                        )
                    }
                }
                _isLoadingIndicatorVisible.value = false
            }
        }
    }

    private fun showContent() {
        _isLoadingIndicatorVisible.value = false
        _isGenericErrorVisible.value = false
        _isSessionExpiredErrorVisible.value = false
        _isNetworkErrorVisible.value = false
        _isContentVisible.value = true
    }

    private fun showNetworkError() {
        _isContentVisible.value = false
        _isLoadingIndicatorVisible.value = false
        _isGenericErrorVisible.value = false
        _isSessionExpiredErrorVisible.value = false
        _isNetworkErrorVisible.value = true
    }

    private fun showGenericError() {
        _isContentVisible.value = false
        _isLoadingIndicatorVisible.value = false
        _isNetworkErrorVisible.value = false
        _isSessionExpiredErrorVisible.value = false
        _isGenericErrorVisible.value = true
    }

    private fun showSessionExpiredError() {
        _isContentVisible.value = false
        _isLoadingIndicatorVisible.value = false
        _isNetworkErrorVisible.value = false
        _isGenericErrorVisible.value = false
        _isSessionExpiredErrorVisible.value = true
    }
}