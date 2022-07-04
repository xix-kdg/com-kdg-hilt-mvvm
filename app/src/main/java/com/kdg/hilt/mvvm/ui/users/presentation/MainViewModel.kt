package com.kdg.hilt.mvvm.ui.users.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdg.hilt.mvvm.data.remote.domain.framework.ErrorComponent
import com.kdg.hilt.mvvm.data.remote.domain.framework.Result
import com.kdg.hilt.mvvm.data.remote.domain.model.User
import com.kdg.hilt.mvvm.framework.CoroutinesDispatcherProvider
import com.kdg.hilt.mvvm.framework.Event
import com.kdg.hilt.mvvm.ui.users.data.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val errorComponent: ErrorComponent,
    private val userRepositoryImpl: UserRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _navigateToProfile = MutableLiveData<Event<String>>()
    val navigateToProfile: LiveData<Event<String>> = _navigateToProfile

    init {
        loadUsers()
    }

    fun onUserClick(login: String) {
        _navigateToProfile.value = Event(login)
    }

    fun onRetryClick() {
        loadUsers()
    }

    private fun loadUsers() {
        showLoading()
        viewModelScope.launch(dispatcherProvider.computation) {
            val result = userRepositoryImpl.getUsers(0)
            withContext(dispatcherProvider.main) {
                when (result) {
                    is Result.Success -> {
                        _users.value = result.data
                        showContent()
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
            }
        }
    }

    private fun showLoading() {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                isLoadingIndicatorVisible = true,
                isSessionExpiredErrorVisible = false,
                isGenericErrorVisible = false,
                isNetworkErrorVisible = false,
                isContentVisible = false
            )
        }
    }

    private fun showContent() {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                isLoadingIndicatorVisible = false,
                isSessionExpiredErrorVisible = false,
                isGenericErrorVisible = false,
                isNetworkErrorVisible = false,
                isContentVisible = true
            )
        }
    }

    private fun showNetworkError() {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                isContentVisible = false,
                isLoadingIndicatorVisible = false,
                isSessionExpiredErrorVisible = false,
                isGenericErrorVisible = false,
                isNetworkErrorVisible = true,
            )
        }
    }

    private fun showGenericError() {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                isContentVisible = false,
                isLoadingIndicatorVisible = false,
                isNetworkErrorVisible = false,
                isSessionExpiredErrorVisible = false,
                isGenericErrorVisible = true
            )
        }
    }

    private fun showSessionExpiredError() {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                isContentVisible = false,
                isLoadingIndicatorVisible = false,
                isNetworkErrorVisible = false,
                isGenericErrorVisible = false,
                isSessionExpiredErrorVisible = true
            )
        }
    }

    data class MainUiState(
        val isContentVisible: Boolean = false,
        val isLoadingIndicatorVisible: Boolean = false,
        val isNetworkErrorVisible: Boolean = false,
        val isSessionExpiredErrorVisible: Boolean = false,
        val isGenericErrorVisible: Boolean = false
    )
}
