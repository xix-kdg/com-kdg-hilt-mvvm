package com.kdg.hilt.mvvm.ui.users.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdg.hilt.mvvm.data.remote.domain.framework.ErrorComponent
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

    var followerCount = MutableLiveData<String>()
    var followingCount = MutableLiveData<String>()
    var name = MutableLiveData<String>()
    var company = MutableLiveData<String>()
    var blog = MutableLiveData<String>()

    fun loadUserProfile(login: String) {
        _isLoadingIndicatorVisible.value = true
        viewModelScope.launch(dispatcherProvider.computation) {
            val result = userRepositoryImpl.getUserProfile(login)
            withContext(dispatcherProvider.main) {

                _isLoadingIndicatorVisible.value = false
            }
        }
    }
}
