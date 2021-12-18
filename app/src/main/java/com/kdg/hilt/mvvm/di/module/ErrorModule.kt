package com.kdg.hilt.mvvm.di.module

import com.kdg.hilt.mvvm.data.remote.domain.framework.ErrorComponent
import com.kdg.hilt.mvvm.data.remote.domain.framework.ErrorComponentImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ErrorModule {

    @Binds
    abstract fun bindErrorComponent(errorComponent: ErrorComponentImpl): ErrorComponent
}
