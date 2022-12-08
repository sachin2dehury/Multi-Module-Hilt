package com.test.mylibrary3

import com.test.mylibrary1.MyLib1
import com.test.mylibrary4.MyLib4
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
interface Lib3Module {

    @Binds
    @ActivityRetainedScoped
    fun getLib2(myLib: MyLib4Impl): MyLib4
}

//@Module
//@InstallIn(ActivityRetainedComponent::class)
//object A {
//    @Provides
//    @ActivityRetainedScoped
//    fun getLib1(myLib1: MyLib1) = MyLib4Impl(myLib1)
//}
