package com.test.mylibrary2

import com.test.mylibrary1.MyLib1
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object Lib2Module {

    @Provides
    @ActivityRetainedScoped
    fun getLib1(): MyLib1 = MyLib1Impl()
}
