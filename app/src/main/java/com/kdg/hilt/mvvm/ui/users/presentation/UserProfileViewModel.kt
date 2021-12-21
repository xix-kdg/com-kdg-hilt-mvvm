package com.kdg.hilt.mvvm.ui.users.presentation

import androidx.lifecycle.*
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
class UserProfileViewModel @Inject constructor(
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val errorComponent: ErrorComponent,
    private val userRepositoryImpl: UserRepositoryImpl,
    private val savedStateHandle: SavedStateHandle
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

    private val _imageBannerUrl = MutableLiveData<String>()
    val imageBannerUrl: LiveData<String> = _imageBannerUrl

    private val login = savedStateHandle.get<String>(UserProfileActivity.EXTRA_USERNAME)

    var user = MutableLiveData<User>()

    init {
        loadUserProfile(login)
    }

    fun onRetryClick() {
        loadUserProfile(login)
    }

    private fun loadUserProfile(login: String?) {
        if (login.isNullOrEmpty()) {
            showGenericError()
        } else {
            _isLoadingIndicatorVisible.value = true
            viewModelScope.launch(dispatcherProvider.computation) {
                val result = userRepositoryImpl.getUserProfile(login)
                withContext(dispatcherProvider.main) {
                    when (result) {
                        is Result.Success -> {
                            user.value = result.data
                            _imageBannerUrl.value = user.value?.avatarUrl
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
                    _isLoadingIndicatorVisible.value = false
                }
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
