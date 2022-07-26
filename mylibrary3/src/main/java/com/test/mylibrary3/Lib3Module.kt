package com.test.mylibrary3

import com.test.mylibrary1.MyLib1
import com.test.mylibrary4.MyLib4
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object Lib3Module {

    @Provides
    @ActivityRetainedScoped
    fun getLib1(myLib1: MyLib1): MyLib4 = MyLib4Impl(myLib1)
}
